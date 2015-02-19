package com.nexera.common.exception;

/**
 * Holds the error code details, basic implementation
 *
 */
public interface ErrorCode {

	int getErrorCode();
	int getServiceId();
	String getMessage();
}
