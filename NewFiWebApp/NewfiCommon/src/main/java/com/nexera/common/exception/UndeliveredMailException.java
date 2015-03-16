package com.nexera.common.exception;

/**
 * Exception class for handling email exceptions.
 */
public class UndeliveredMailException extends NonFatalException {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 2015020702364453334L;

	public UndeliveredMailException() {
		super();
	}

	public UndeliveredMailException(String message) {
		super(message);
	}

	public UndeliveredMailException(String message, Throwable thrw) {
		super(message, thrw);
	}
	
	public UndeliveredMailException(String message, String errorCode) {
		super(message,errorCode);
	}

	public UndeliveredMailException(String message, String errorCode, Throwable thrw) {
		super(message, errorCode, thrw);
	}
}
