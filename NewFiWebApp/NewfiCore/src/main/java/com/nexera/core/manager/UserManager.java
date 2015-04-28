package com.nexera.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.User;

@Component
@Scope(value = "prototype")
public class UserManager implements Runnable {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(UserManager.class);

	private User user;
	private ExceptionMaster exceptionMaster;

	@Override
	public void run() {
		LOGGER.debug("Inside method run");

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ExceptionMaster getExceptionMaster() {
		return exceptionMaster;
	}

	public void setExceptionMaster(ExceptionMaster exceptionMaster) {
		this.exceptionMaster = exceptionMaster;
	}

}
