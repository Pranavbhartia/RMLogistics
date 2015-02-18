package com.nexera.newfi.core.service;

import com.nexera.newfi.common.model.User;
import com.nexera.newfi.common.model.UserModel;

public interface UserService {

	public String getName();

	public UserModel findByUserId(Integer id);

	public User addUser(User user);
}
