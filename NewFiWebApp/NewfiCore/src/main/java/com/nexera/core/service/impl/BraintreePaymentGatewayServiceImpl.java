package com.nexera.core.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.braintreegateway.ValidationErrors;
import com.braintreegateway.exceptions.UnexpectedException;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.TransactionDetailsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.entity.User;
import com.nexera.common.exception.CreditCardException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.PaymentException;
import com.nexera.common.exception.PaymentUnsuccessfulException;
import com.nexera.core.service.BraintreePaymentGatewayService;

/**
 * @author karthik This is the class that has methods to make api calls to Braintree payment gateway
 *         to initiate transactions.
 */

@Component
public class BraintreePaymentGatewayServiceImpl implements BraintreePaymentGatewayService, InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(BraintreePaymentGatewayServiceImpl.class);

	private BraintreeGateway gateway = null;

	@Value("${MERCHANT_ID}")
	private String merchantId;

	@Value("${PUBLIC_KEY}")
	private String publicKey;

	@Value("${PRIVATE_KEY}")
	private String privateKey;

	@Value("${ENVIRONMENT_SANDBOX}")
	private int sandboxMode;

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private TransactionDetailsDao transactionDetailsDao;
	
	/**
	 * Method to generate client token to be used by the front end.
	 * @return
	 */
	@Override
	public String getClientToken() {
		LOG.info("Making API call to fetch client token!");
		LOG.debug("BrainTreePaymentImpl : getClientToken() : Executing method.");

		// API call to generate client token
		String clientToken = gateway.clientToken().generate();

		LOG.info("Client token : " + clientToken);
		return clientToken;
	}

	private String createTransaction(String paymentNonce, double amount) throws PaymentException, CreditCardException,
			PaymentUnsuccessfulException {

		LOG.info("createTransaction called to make a new transaction");
		String transactionId = null;

		// Initiating a Braintree transaction
		TransactionRequest request = new TransactionRequest();
		request.amount(new BigDecimal(amount));
		request.paymentMethodNonce(paymentNonce);

		Result<Transaction> result = null;

		// API call to make the transaction
		try {
			LOG.debug("Making api call to create transaction");
			result = gateway.transaction().sale(request);
			LOG.debug("Api call complete");
		}
		catch (UnexpectedException e) {
			LOG.error("makePayment : Unexpected exception has occurred, message : " + e.getMessage());
			throw new PaymentException("makePayment : Unexpected exception has occurred, message : " + e.getMessage(), DisplayMessageConstants.GENERAL_ERROR);
		}

		LOG.debug("Payment status : " + result.isSuccess() + " Message : " + result.getMessage());

		if (result.isSuccess()) {
			LOG.info("Result : " + result.isSuccess());
			transactionId = result.getTarget().getId();
		}
		else {
			LOG.info("Result : " + result.isSuccess() + " message : " + result.getMessage());
			ValidationErrors creditCardErrors = result.getErrors().forObject("customer").forObject("creditCard");
			if (creditCardErrors.size() > 0) {
				String errorMessage = "";
				for (ValidationError error : creditCardErrors.getAllValidationErrors()) {
					errorMessage += " Error Code : " + error.getCode();
					errorMessage += " Error message : " + error.getMessage() + "\n";
				}
				throw new CreditCardException("Credit Card Validation failed, reason : \n " + errorMessage,
						DisplayMessageConstants.CREDIT_CARD_INVALID);
			}
			List<ValidationError> allErrors = result.getErrors().getAllDeepValidationErrors();
			if (allErrors.size() > 0) {
				String errorMessage = "";
				for (ValidationError error : allErrors) {
					errorMessage += " Error Code : " + error.getCode();
					errorMessage += " Error message : " + error.getMessage() + "\n";
				}
				throw new PaymentUnsuccessfulException("Transaction creation failed, reason : \n " + errorMessage,
						DisplayMessageConstants.PAYMENT_UNSUCCESSFUL);
			}
		}

		LOG.info("createTransaction : returning " + transactionId);
		return transactionId;
	}

	private void updateTransactionDetails(String transactionId, int loanId, User user, float amount) throws InvalidInputException,
			NoRecordsFetchedException {

		if (transactionId == null || transactionId.isEmpty()) {
			LOG.error("updateTransactionDetails : transactionId is null or empty!");
			throw new InvalidInputException("updateTransactionDetails : transactionId is null or empty!");
		}
		if (loanId <= 0) {
			LOG.error("updateTransactionDetails : loanId is invalid!");
			throw new InvalidInputException("updateTransactionDetails : loanId is invalid!");
		}
		if (user == null) {
			LOG.error("updateTransactionDetails : user is null or empty!");
			throw new InvalidInputException("updateTransactionDetails : user is null or empty!");
		}
		if (amount <= 0) {
			LOG.error("updateTransactionDetails : amount is invalid!");
			throw new InvalidInputException("updateTransactionDetails : amount is invalid!");
		}

		LOG.info("updateTransactionDetails called to update the database");
		// First we fetch the loan from the database
		Loan loan = (Loan) loanDao.load(Loan.class, loanId);

		if (loan == null) {
			LOG.error("Loan not found for loanId : " + loanId);
			throw new NoRecordsFetchedException("Loan not found for loanId : " + loanId);
		}

		// We create a new transaction details object
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setLoan(loan);
		transactionDetails.setTransaction_id(transactionId);
		transactionDetails.setUser(user);
		transactionDetails.setAmount(amount);
		transactionDetails.setCreated_by(user.getId());
		transactionDetails.setCreated_date(new Timestamp(System.currentTimeMillis()));
		transactionDetails.setModified_by(user.getId());
		transactionDetails.setModified_date(new Timestamp(System.currentTimeMillis()));

		LOG.debug("Updating database with the new transaction details record!");
		transactionDetailsDao.save(transactionDetails);
		LOG.info("Transaction details successfully updated!");
	}
	
	/**
	 * Function to create a Braintree transaction with a particular payment method token and an amount
	 * @param paymentMethodToken
	 * @param amount
	 * @return
	 * @throws InvalidInputException
	 * @throws PaymentException 
	 * @throws PaymentUnsuccessfulException 
	 * @throws CreditCardException 
	 * @throws NoRecordsFetchedException 
	 */
	@Transactional
	@Override
	public void makePayment(String paymentNonce, float amount, int loanId, User user) throws InvalidInputException, PaymentException,
			PaymentUnsuccessfulException, CreditCardException, NoRecordsFetchedException {

		String transactionId = null;

		if (paymentNonce == null || paymentNonce.isEmpty()) {
			LOG.error("makePayment() : paymentMethodToken parameter is null or invalid!");
			throw new InvalidInputException("makePayment() : paymentMethodToken parameter is null or invalid!");
		}
		if (amount <= 0.0) {
			LOG.error("makePayment() : amount parameter is null or invalid!");
			throw new InvalidInputException("makePayment() : amount parameter is null or invalid!");
		}
		if (loanId <= 0) {
			LOG.error("makePayment() : loanId parameter is null or invalid!");
			throw new InvalidInputException("makePayment() : loanId parameter is null or invalid!");
		}
		if (user== null) {
			LOG.error("makePayment() : user parameter is null or invalid!");
			throw new InvalidInputException("makePayment() : user parameter is null or invalid!");
		}

		LOG.info("Executing makePayment with parameters : paymentMethodToken : " + paymentNonce + " , amount" + amount);

		transactionId = createTransaction(paymentNonce, amount);

		if (transactionId != null) {
			LOG.debug("Transaction successfully created. Updating the database.");
			updateTransactionDetails(transactionId, loanId, user, amount);
			LOG.debug("Database updated with the new transaction details.");
		}

		LOG.info("makePayment successfully complete!");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("afterPropertiesSet() : Executing method ");
		if (gateway == null) {
			if (sandboxMode == CommonConstants.SANDBOX_MODE_TRUE) {
				LOG.info("Initialising gateway with keys: " + merchantId + " : " + publicKey + " : " + privateKey);
				gateway = new BraintreeGateway(Environment.SANDBOX, merchantId, publicKey, privateKey);
			}
			else {
				LOG.info("Initialising gateway with keys: " + merchantId + " : " + publicKey + " : " + privateKey);
				gateway = new BraintreeGateway(Environment.PRODUCTION, merchantId, publicKey, privateKey);
			}
		}
	}

}
