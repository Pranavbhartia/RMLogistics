package com.nexera.common.exception;

public class MuleException extends BaseRestException {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	public MuleException(ErrorCode errorCode, String debugMessage) {
		super();
		this.debugMessage = debugMessage;
		this.errorCode = errorCode;
	}

}
