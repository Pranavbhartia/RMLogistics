package com.nexeracommon.newfi.dao;

import com.nexeracommon.newfi.model.UserModel;

public interface UserDao {
	
	public UserModel findByUserName(String userName);

	public UserModel findByUserId(Integer userId);
}
