package com.nexera.core.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.braintreegateway.exceptions.NotFoundException;
import com.braintreegateway.exceptions.UnexpectedException;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.TransactionDetailsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.entity.User;
import com.nexera.common.enums.Milestones;
import com.nexera.common.exception.CreditCardException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.PaymentException;
import com.nexera.common.exception.PaymentUnsuccessfulException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.BraintreePaymentGatewayService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.ReceiptPdfService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;

/**
 * @author karthik This is the class that has methods to make api calls to
 *         Braintree payment gateway to initiate transactions.
 */

@Component
public class BraintreePaymentGatewayServiceImpl implements
        BraintreePaymentGatewayService, InitializingBean {

	private static final Logger LOG = LoggerFactory
	        .getLogger(BraintreePaymentGatewayServiceImpl.class);

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
	private TemplateService templateService;

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private TransactionDetailsDao transactionDetailsDao;

	@Autowired
	private SendGridEmailService sendGridMailService;

	@Autowired
	private LoanService loanSerivce;

	@Autowired
	private ReceiptPdfService receiptPdfService;

	@Autowired
	private SendEmailService sendEmailService;

	@Autowired
	private Utils utils;

	/**
	 * Method to generate client token to be used by the front end.
	 * 
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

	private void sendPaymentMail(User user, Template template, String subject,
	        LoanApplicationFee loanApplicationFee) {
		LOG.debug("Sending mail");
		// Making the Mail VOs
		EmailVO emailVO = new EmailVO();

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() });
		if (loanApplicationFee != null) {

			substitutions.put("-date-", new String[] { utils
			        .getDateAndTimeForDisplay(loanApplicationFee
			                .getPaymentDate()) });

			if (user.getCustomerDetail() != null) {
				if (user.getCustomerDetail().getAddressStreet() != null) {
					substitutions.put("-address-", new String[] { user
					        .getCustomerDetail().getAddressStreet() });
				} else {
					substitutions.put("-address-", new String[] { "" });
				}
				if (user.getCustomerDetail().getAddressCity() != null) {
					substitutions.put("-city-", new String[] { user
					        .getCustomerDetail().getAddressCity() });
				} else {
					substitutions.put("-city-", new String[] { "" });
				}
				if (user.getCustomerDetail().getAddressState() != null) {
					substitutions.put("-state-", new String[] { user
					        .getCustomerDetail().getAddressState() });
				} else {
					substitutions.put("-state-", new String[] { "" });
				}
				if (user.getCustomerDetail().getAddressCity() != null) {
					substitutions.put("-zip-", new String[] { user
					        .getCustomerDetail().getAddressZipCode() });
				} else {
					substitutions.put("-zip-", new String[] { "" });
				}
			}

			if (loanApplicationFee != null) {
				String appFee = "$"
				        + String.valueOf(String.format("%.2f",
				                loanApplicationFee.getFee()));
				substitutions.put("-amount-", new String[] { appFee });
			}

		}
		if (user.getUsername() != null) {
			emailVO.setSenderEmailId(user.getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailVO.setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			        + CommonConstants.SENDER_EMAIL_ID);
		}
		emailVO.setSenderName(CommonConstants.SENDER_NAME);
		emailVO.setSubject(subject);
		emailVO.setTemplateId(template.getValue());
		emailVO.setTokenMap(substitutions);
		List<String> ccList = new ArrayList<String>();
		ccList.add(user.getUsername() + CommonConstants.SENDER_EMAIL_ID);
		emailVO.setCCList(ccList);
		try {
			sendEmailService.sendEmailForCustomer(emailVO, user, template);
		} catch (InvalidInputException e) {
			LOG.error("Exception caught " + e.getMessage());
		} catch (UndeliveredEmailException e) {
			LOG.error("Exception caught " + e.getMessage());
		}
	}

	private String createTransaction(String paymentNonce, double amount)
	        throws PaymentException, CreditCardException,
	        PaymentUnsuccessfulException {

		LOG.info("createTransaction called to make a new transaction");
		String transactionId = null;

		// Initiating a Braintree transaction
		TransactionRequest request = new TransactionRequest();
		request.amount(new BigDecimal(amount));
		request.paymentMethodNonce(paymentNonce);
		request.options().submitForSettlement(true);

		Result<Transaction> result = null;

		// API call to make the transaction
		try {
			LOG.debug("Making api call to create transaction");
			result = gateway.transaction().sale(request);
			LOG.debug("Api call complete");
		} catch (UnexpectedException e) {
			LOG.error("makePayment : Unexpected exception has occurred, message : "
			        + e.getMessage());
			throw new PaymentException(
			        "makePayment : Unexpected exception has occurred, message : "
			                + e.getMessage(),
			        DisplayMessageConstants.GENERAL_ERROR);
		}

		LOG.debug("Payment status : " + result.isSuccess() + " Message : "
		        + result.getMessage());

		if (result.isSuccess()) {
			LOG.info("Result : " + result.isSuccess());
			transactionId = result.getTarget().getId();
		} else {
			if (result != null
			        && result.getMessage().equalsIgnoreCase("Declined")) {
				throw new CreditCardException("Credit Card Declined ",
				        DisplayMessageConstants.CREDIT_CARD_DECLINED);
			}
			LOG.info("Result : " + result.isSuccess() + " message : "
			        + result.getMessage());
			ValidationErrors creditCardErrors = result.getErrors()
			        .forObject("customer").forObject("creditCard");
			if (creditCardErrors.size() > 0) {
				String errorMessage = "";
				for (ValidationError error : creditCardErrors
				        .getAllValidationErrors()) {
					errorMessage += " Error Code : " + error.getCode();
					errorMessage += " Error message : " + error.getMessage()
					        + "\n";
				}
				throw new CreditCardException(
				        "Credit Card Validation failed, reason : \n "
				                + errorMessage,
				        DisplayMessageConstants.CREDIT_CARD_INVALID);
			}
			List<ValidationError> allErrors = result.getErrors()
			        .getAllDeepValidationErrors();
			if (allErrors.size() > 0) {
				String errorMessage = "";
				for (ValidationError error : allErrors) {
					errorMessage += " Error Code : " + error.getCode();
					errorMessage += " Error message : " + error.getMessage()
					        + "\n";
				}
				throw new PaymentUnsuccessfulException(
				        "Transaction creation failed, reason : \n "
				                + errorMessage,
				        DisplayMessageConstants.PAYMENT_UNSUCCESSFUL);
			}
		}

		LOG.info("createTransaction : returning " + transactionId);
		return transactionId;
	}

	private void updateTransactionDetails(String transactionId, int loanId,
	        User user, float amount) throws InvalidInputException,
	        NoRecordsFetchedException {

		if (transactionId == null || transactionId.isEmpty()) {
			LOG.error("updateTransactionDetails : transactionId is null or empty!");
			throw new InvalidInputException(
			        "updateTransactionDetails : transactionId is null or empty!");
		}
		if (loanId <= 0) {
			LOG.error("updateTransactionDetails : loanId is invalid!");
			throw new InvalidInputException(
			        "updateTransactionDetails : loanId is invalid!");
		}
		if (user == null) {
			LOG.error("updateTransactionDetails : user is null or empty!");
			throw new InvalidInputException(
			        "updateTransactionDetails : user is null or empty!");
		}
		if (amount <= 0) {
			LOG.error("updateTransactionDetails : amount is invalid!");
			throw new InvalidInputException(
			        "updateTransactionDetails : amount is invalid!");
		}

		LOG.info("updateTransactionDetails called to update the database");
		// First we fetch the loan from the database
		Loan loan = (Loan) loanDao.load(Loan.class, loanId);

		if (loan == null) {
			LOG.error("Loan not found for loanId : " + loanId);
			throw new NoRecordsFetchedException("Loan not found for loanId : "
			        + loanId);
		}

		// We create a new transaction details object
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setLoan(loan);
		transactionDetails.setTransaction_id(transactionId);
		transactionDetails.setUser(user);
		transactionDetails.setAmount(amount);
		transactionDetails
		        .setStatus(CommonConstants.TRANSACTION_STATUS_ENABLED);
		transactionDetails.setCreated_by(user.getId());
		transactionDetails.setCreated_date(new Timestamp(System
		        .currentTimeMillis()));
		transactionDetails.setModified_by(user.getId());
		transactionDetails.setModified_date(new Timestamp(System
		        .currentTimeMillis()));

		LOG.debug("Updating database with the new transaction details record!");
		transactionDetailsDao.save(transactionDetails);
		loanSerivce.saveLoanMilestone(loanId,
		        Milestones.APP_FEE.getMilestoneID(),
		        LoanStatus.APP_PAYMENT_PENDING);
		LOG.info("Transaction details successfully updated!");
	}

	/**
	 * Function to create a Braintree transaction with a particular payment
	 * method token and an amount
	 * 
	 * @param paymentMethodToken
	 * @param amount
	 * @return
	 * @throws InvalidInputException
	 * @throws PaymentException
	 * @throws PaymentUnsuccessfulException
	 * @throws CreditCardException
	 * @throws NoRecordsFetchedException
	 * @throws UndeliveredEmailException
	 */
	@Transactional
	@Override
	public void makePayment(String paymentNonce, int loanId, User user)
	        throws InvalidInputException, PaymentException,
	        PaymentUnsuccessfulException, CreditCardException,
	        NoRecordsFetchedException, UndeliveredEmailException {

		String transactionId = null;

		if (paymentNonce == null || paymentNonce.isEmpty()) {
			LOG.error("makePayment() : paymentMethodToken parameter is null or invalid!");
			throw new InvalidInputException(
			        "makePayment() : paymentMethodToken parameter is null or invalid!");
		}
		if (loanId <= 0) {
			LOG.error("makePayment() : loanId parameter is null or invalid!");
			throw new InvalidInputException(
			        "makePayment() : loanId parameter is null or invalid!");
		}
		if (user == null) {
			LOG.error("makePayment() : user parameter is null or invalid!");
			throw new InvalidInputException(
			        "makePayment() : user parameter is null or invalid!");
		}

		int amount = loanSerivce.getApplicationFee(loanId);

		LOG.info("Executing makePayment with parameters : paymentMethodToken : "
		        + paymentNonce + " , amount" + amount);

		transactionId = createTransaction(paymentNonce, amount);

		if (transactionId != null) {
			LOG.debug("Transaction successfully created. Updating the database.");
			updateTransactionDetails(transactionId, loanId, user, amount);
			LOG.debug("Database updated with the new transaction details.");
			// sendPaymentMail(user,
			// paymentTemplateId,DisplayMessageConstants.PAYMENT_SUCCESSFUL_SUBJECT,null);
		} else {
			throw new PaymentUnsuccessfulException(
			        "Could not create transaction in Brain Tree");

		}

		LOG.info("makePayment successfully complete!");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("afterPropertiesSet() : Executing method ");
		if (gateway == null) {
			if (sandboxMode == CommonConstants.SANDBOX_MODE_TRUE) {
				LOG.info("Initialising gateway with keys: " + merchantId
				        + " : " + publicKey + " : " + privateKey);
				gateway = new BraintreeGateway(Environment.SANDBOX, merchantId,
				        publicKey, privateKey);
			} else {
				LOG.info("Initialising gateway with keys: " + merchantId
				        + " : " + publicKey + " : " + privateKey);
				gateway = new BraintreeGateway(Environment.PRODUCTION,
				        merchantId, publicKey, privateKey);
			}
		}
	}

	@Override
	@Transactional
	public String checkAndUpdateTransactions(
	        TransactionDetails transactionDetails)
	        throws NoRecordsFetchedException, InvalidInputException {

		String paymentStatus = LoanStatus.APP_PAYMENT_PENDING;
		if (transactionDetails.getTransaction_id() == null) {
			throw new InvalidInputException(
			        "The transaction details object has empty transaction id entity");
		}

		if (transactionDetails.getUser() == null) {
			throw new InvalidInputException(
			        "The transaction details object has empty user entity");
		}

		if (transactionDetails.getLoan() == null) {
			throw new InvalidInputException(
			        "The transaction details object has empty loan entity");
		}

		// First we make an api call to fetch the transaction
		Transaction transaction = null;

		try {
			transaction = gateway.transaction().find(
			        transactionDetails.getTransaction_id());
			LOG.debug("Transaction fetched, status of the transaction is : "
			        + transaction.getStatus());

		} catch (NotFoundException e) {
			LOG.error("No records fetched from Braintree for the transaction id : "
			        + transactionDetails.getTransaction_id());
			throw new NoRecordsFetchedException(
			        "No records fetched from Braintree for the transaction id : "
			                + transactionDetails.getTransaction_id());
		} catch (UnexpectedException e) {
			LOG.error(
			        "UnexpectedException has occured while fetching transaction details from Braintree. Message : "
			                + e.getMessage(), e);
			throw e;
		}

		// Now we check the different possible cases of the transaction status
		if (transaction.getStatus() == Transaction.Status.SETTLED) {
			User sysAdmin = new User();
			sysAdmin.setId(CommonConstants.SYSTEM_ADMIN_ID);

			LoanApplicationFee applicationFee = new LoanApplicationFee();
			applicationFee.setLoan(transactionDetails.getLoan());
			applicationFee.setFee(transaction.getAmount().doubleValue());
			applicationFee.setModifiedBy(sysAdmin);
			applicationFee
			        .setModifiedDate(new Date(System.currentTimeMillis()));
			applicationFee.setPaymentType(transaction.getCreditCard()
			        .getCardType());
			applicationFee.setPaymentDate(transaction.getCreatedAt().getTime());
			applicationFee.setTransactionDetails(transactionDetails);
			applicationFee.setTransactionMetadata(transaction.getStatus()
			        .toString());

			// Use the loan dao object to make a general save
			loanDao.save(applicationFee);

			// Update the transaction details table to soft delete the record.
			transactionDetails
			        .setStatus(CommonConstants.TRANSACTION_STATUS_DISABLED);
			loanDao.update(transactionDetails);			
			paymentStatus = LoanStatus.APP_PAYMENT_SUCCESS;
		} else if (transaction.getStatus() == Transaction.Status.AUTHORIZATION_EXPIRED
		        || transaction.getStatus() == Transaction.Status.FAILED
		        || transaction.getStatus() == Transaction.Status.GATEWAY_REJECTED
		        || transaction.getStatus() == Transaction.Status.PROCESSOR_DECLINED
		        || transaction.getStatus() == Transaction.Status.SETTLEMENT_DECLINED
		        || transaction.getStatus() == Transaction.Status.UNRECOGNIZED
		        || transaction.getStatus() == Transaction.Status.VOIDED) {

			Template template = templateService
			        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_PAYMENT_UNSUCCESSFUL);
			sendPaymentMail(transactionDetails.getUser(), template,
			        DisplayMessageConstants.PAYMENT_UNSUCCESSFUL_SUBJECT, null);

			// Update the transaction details table to change status to 2.
			transactionDetails
			        .setStatus(CommonConstants.TRANSACTION_STATUS_FAILED);
			loanDao.update(transactionDetails);
			paymentStatus = LoanStatus.APP_PAYMENT_FAILURE;
		} else {
			// We dont do anything and just poll it the next time to check the
			// status as the transaction is pending.
			LOG.debug("The transaction with id : "
			        + transactionDetails.getTransaction_id()
			        + " is pending for settlement");
			paymentStatus = LoanStatus.APP_PAYMENT_PENDING;
		}
		return paymentStatus;
	}
}
