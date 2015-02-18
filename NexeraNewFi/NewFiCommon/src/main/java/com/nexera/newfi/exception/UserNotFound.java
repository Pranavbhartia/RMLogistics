package com.nexera.newfi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="User Not Found") //404
public class UserNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotFound(int id){
        super("EmployeeNotFoundException with id="+id);
    }
	
	public UserNotFound() {
		super();
	}

	public UserNotFound(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFound(String message) {
		super(message);
	}

	public UserNotFound(Throwable cause) {
		super(cause);
	}


}
