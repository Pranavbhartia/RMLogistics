package com.nexera.web.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.entity.User;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.core.authentication.AuthenticationService;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.UserProfileService;

public class UserAuthProvider extends DaoAuthenticationProvider {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UserAuthProvider.class);

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private MessageServiceHelper messageServiceHelper;

	@Autowired
	private UserProfileService userProfileService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		LOG.info("Inside authenticate Method of UserAuthProvider");
		String username = authentication.getName().split(":")[0];

		String offSet = authentication.getName().split(":")[1];

		String[] tokens = authentication.getName().split(":");
		boolean isShopper = false;
		if (tokens.length == 3
		        && authentication.getName().split(":")[2]
		                .equals(DisplayMessageConstants.IS_SHOPPER)) {
			isShopper = true;
		}

		String password = authentication.getCredentials().toString();

		User user = null;
		try {
			LOG.debug("Validating the form parameters");
			validateLoginFormParameters(username, password);
			User userFromTable = userProfileService.findUserByMail(username);
			if(authentication.getDetails().equals(DisplayMessageConstants.FROM_CHANGE_PASSWORD) && !userFromTable.getEmailVerified() ){
				userFromTable.setEmailVerified(true);
			}
			if (userFromTable != null
			        && userFromTable.getEmailVerified() != null
			        && !userFromTable.getEmailVerified() && !isShopper) {
				throw new DisabledException("First time login");
			}
			else if (userFromTable != null
			        && userFromTable.getEmailVerified() == null
			         && !isShopper) {
				throw new DisabledException("First time login");
			}
			user = authenticationService.getUserWithLoginName(username,
			        password);
			if(authentication.getDetails().equals(DisplayMessageConstants.FROM_CHANGE_PASSWORD) && !user.getEmailVerified() ){
				user.setEmailVerified(true);
			}
			LOG.debug("Checking if user is not in inactive mode");
			if (user.getStatus() == -1) {
				throw new DisabledException(
				        DisplayMessageConstants.USER_DISABLED);
			} else if (user.getStatus() == 0) {
				throw new DisabledException(
				        DisplayMessageConstants.USER_INACTIVE);
			} else if (user.getEmailVerified() != null
			        && !user.getEmailVerified() && !isShopper) {
				throw new DisabledException("First time login");
			}
			authenticationService.validateUser(user, password);
			if (user != null) {
				List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
				grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
				user.setMinutesOffset(offSet);
				Authentication auth = new UsernamePasswordAuthenticationToken(
				        user, password, grantedAuths);
				messageServiceHelper.checkIfUserFirstLogin(user, isShopper);
				LOG.info("Authentication provided for user : "
				        + user.getEmailId());
				return auth;
			}
		} catch (NoRecordsFetchedException e) {
			LOG.error("User not found in the system: " + e.getMessage());
			throw new UsernameNotFoundException(
			        "Please enter valid credentials");
		} catch (InvalidInputException e) {
			throw new BadCredentialsException("Please enter valid credentials");
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
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
	        throws InvalidInputException {
		LOG.debug("Validating Login form paramters loginName :" + username);
		if (username == null || username.isEmpty()) {
			throw new InvalidInputException("User name passed can not be null",
			        DisplayMessageConstants.INVALID_USERNAME);
		}
		if (password == null || password.isEmpty()) {
			throw new InvalidInputException("Password passed can not be null",
			        DisplayMessageConstants.INVALID_PASSWORD);
		}
		LOG.debug("Login form parameters validated successfully");
	}
}