package com.nexera.core.service;

import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.UserVO;

public interface UserProfileService {
	
	public UserVO findUser(Integer userid);

	public Integer updateUser(UserVO userVO);

	public Integer updateCustomerDetails(UserVO userVO);


}
