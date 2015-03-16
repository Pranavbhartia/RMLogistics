package com.nexera.common.exception;

/**
 * Exception class for handling invalid credit cards
 */
public class CreditCardException extends NonFatalException {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 2015020702364453334L;

	public CreditCardException() {
		super();
	}

	public CreditCardException(String message) {
		super(message);
	}

	public CreditCardException(String message, Throwable thrw) {
		super(message, thrw);
	}
	
	public CreditCardException(String message, String errorCode) {
		super(message,errorCode);
	}

	public CreditCardException(String message, String errorCode, Throwable thrw) {
		super(message, errorCode, thrw);
	}
}
