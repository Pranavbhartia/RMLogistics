package com.nexera.common.exception;

/**
 * Exception class thrown when payment is unsuccessful
 */
public class PaymentUnsuccessfulException extends NonFatalException {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 2015020702364453334L;

	public PaymentUnsuccessfulException() {
		super();
	}

	public PaymentUnsuccessfulException(String message) {
		super(message);
	}

	public PaymentUnsuccessfulException(String message, Throwable thrw) {
		super(message, thrw);
	}
	
	public PaymentUnsuccessfulException(String message, String errorCode) {
		super(message,errorCode);
	}

	public PaymentUnsuccessfulException(String message, String errorCode, Throwable thrw) {
		super(message, errorCode, thrw);
	}
}
