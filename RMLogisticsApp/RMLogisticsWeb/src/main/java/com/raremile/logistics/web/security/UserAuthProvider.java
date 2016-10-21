package com.raremile.logistics.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserAuthProvider extends DaoAuthenticationProvider {

	private static final Logger LOG = LoggerFactory
			.getLogger(UserAuthProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.info("Inside authenticate Method of UserAuthProvider");
		return null;
	}

	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		LOG.info("Inside setUserDetailsService Method of UserAuthProvider");
		super.setUserDetailsService(userDetailsService);
		LOG.info("Completed setUserDetailsService Method of UserAuthProvider");
	}

	/**
	 * Verify the login Form Parameters
	 * 
	 * @param username
	 * @param password
	 * @throws InvalidInputException
	 */
	private void validateLoginFormParameters(String username, String password)
			throws Exception {
		LOG.debug("Validating Login form paramters loginName :" + username);
		if (username == null || username.isEmpty()) {
			throw new Exception("User name passed can not be null");
		}
		if (password == null || password.isEmpty()) {
			throw new Exception("Password passed can not be null");
		}
		LOG.debug("Login form parameters validated successfully");
	}
}