package com.nexera.core.authentication;

import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;



/**
 * Contains the methods to be implemented for authentication
 */
public interface AuthenticationService {
	
	public void validateUser(User user, String password) throws InvalidInputException;

	public User getUserWithLoginName(String userId,String password) throws NoRecordsFetchedException,DatabaseException;

	public User getUserWithLoginName(String username) throws NoRecordsFetchedException, DatabaseException;

	

}

