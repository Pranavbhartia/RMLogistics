package com.nexera.common.exception;

/*
 * Refactor this class to the appropriate implementation name, 
 * reason to have multiple classes of this interface.
 */
public class GenericErrorCode implements ErrorCode {

	private int errorCode;
	private int serviceId;
	private String message;

	
	
	public GenericErrorCode(int serviceId, String message) {
		this.errorCode = -1;
		this.serviceId = serviceId;
		this.message = message;
	}

	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public int getServiceId() {
		return serviceId;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
