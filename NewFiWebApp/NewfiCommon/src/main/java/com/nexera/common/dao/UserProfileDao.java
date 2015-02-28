package com.nexera.common.dao;

import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.UserVO;

public interface UserProfileDao {
	
	public User findByUserName(String userName) throws NoRecordsFetchedException,DatabaseException;

	public User findByUserId(Integer userId);
	
	public Integer updateUser(User user);

	public Integer updateCustomerDetails(CustomerDetail CustomerDetail);

}
