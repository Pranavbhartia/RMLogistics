package com.nexera.common.exception;

/**
 * Exception class for handling failure in delivery of mails
 */
public class UndeliveredEmailException extends NonFatalException {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = -215264659528341674L;

	public UndeliveredEmailException() {
		super();
	}

	public UndeliveredEmailException(String message) {
		super(message);
	}

	public UndeliveredEmailException(String message, Throwable thrw) {
		super(message, thrw);
	}

	public UndeliveredEmailException(String message, String errorCode,
			Throwable thrw) {
		super(message, errorCode, thrw);
	}

	public UndeliveredEmailException(String message, String errorCode) {
		super(message, errorCode);
	}

}
