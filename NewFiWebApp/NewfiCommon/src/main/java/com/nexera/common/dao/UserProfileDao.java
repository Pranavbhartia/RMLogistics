package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;

public interface UserProfileDao extends GenericDao {
	
	public User findByUserName(String userName) throws NoRecordsFetchedException,DatabaseException;

	public User findByUserId(Integer userId);
	
	public Integer updateUser(User user);

	public Integer updateCustomerDetails(CustomerDetail CustomerDetail);
	
	public List<User> searchUsersByName(String name,UserRole role);

	public Integer updateUser(String s3ImagePath, Integer userid);

	public Integer competeUserProfile(User user);

	public Integer completeCustomerDetails(CustomerDetail customerDetail);

	public Integer managerUpdateUserProfile(User user);

	public Integer managerUpdateUCustomerDetails(CustomerDetail customerDetail);

}
