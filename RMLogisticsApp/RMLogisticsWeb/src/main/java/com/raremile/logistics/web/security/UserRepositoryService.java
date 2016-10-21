package com.raremile.logistics.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserRepositoryService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UserRepositoryService.class);


	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {
		LOG.info("Method: loadUserByUsername called.");
		
		LOG.info("Method: loadUserByUsername finished.");
		return null;
	}

}