package com.nexera.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.core.authentication.AuthenticationService;

public class UserRepositoryService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryService.class);

	@Autowired
	private AuthenticationService authenticationService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.info("Method: loadUserByUsername called.");
		User user = null;
		try {
			LOG.info("auth service : " + authenticationService);
			user = authenticationService.getUserWithLoginName(username);
			LOG.info("user : " + user.getEmail());
		}
		catch (NoRecordsFetchedException e) {
			throw new UsernameNotFoundException("User not found in the system");
		}
		catch (DatabaseException ex) {
			throw new UsernameNotFoundException("Some technicall error happened in the system. Please try later");
		}
		LOG.info("Method: loadUserByUsername finished.");
		return user;
	}
}