package com.nexera.common.exception;

/*
 * Refractor this class to the appropriate implementation name, 
 * reason to have multiple classes of this interface.
 */
public class SampleErrorCode implements ErrorCode {

	private int errorCode;
	private int serviceId;
	private String message;

	public SampleErrorCode(int errorCode, int serviceId, String message) {
		this.errorCode = errorCode;
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
