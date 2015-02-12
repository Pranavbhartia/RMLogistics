package com.nexeracore.newfi.service;

import com.nexeracommon.newfi.model.UserModel;

public interface UserService {

	public String getName();

	public UserModel findByUserId(Integer id);
}
