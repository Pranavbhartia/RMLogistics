package com.nexera.newfi.persistence.dao;

import com.nexera.newfi.common.model.User;
import com.nexera.newfi.common.model.UserModel;

public interface UserDao {
	
	public UserModel findByUserName(String userName);

	public UserModel findByUserId(Integer userId);

	public User addUser(User user);
}
