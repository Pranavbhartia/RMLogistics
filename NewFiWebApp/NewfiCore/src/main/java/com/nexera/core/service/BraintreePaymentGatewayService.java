package com.nexera.core.service;

import org.springframework.stereotype.Component;

import com.braintreegateway.Transaction;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.entity.User;
import com.nexera.common.exception.CreditCardException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.PaymentException;
import com.nexera.common.exception.PaymentUnsuccessfulException;
import com.nexera.common.exception.UndeliveredEmailException;

/**
 * @author karthik
 * 
 * This is the interface that has methods that are needed to be implemented for payments.
 *
 */

@Component
public interface BraintreePaymentGatewayService {
	
	/**
	 * Method to generate client token to be used by the front end.
	 * @return
	 */
	public String getClientToken();
	
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
	 * @throws UndeliveredEmailException 
	 */
	public void makePayment(String paymentNonce, int loanId, User user) throws InvalidInputException, PaymentException, PaymentUnsuccessfulException, CreditCardException, NoRecordsFetchedException, UndeliveredEmailException;
	
	
	/**
	 * Checks the status of transaction and takes necessary actions
	 * @param transactionDetails
	 * @throws NoRecordsFetchedException
	 * @throws InvalidInputException
	 */
	public void checkAndUpdateTransactions(TransactionDetails transactionDetails) throws NoRecordsFetchedException, InvalidInputException;
	
	public Transaction getTrasactionDetails(LoanApplicationFee applicationFee) throws InvalidInputException, NoRecordsFetchedException, PaymentException;
}
