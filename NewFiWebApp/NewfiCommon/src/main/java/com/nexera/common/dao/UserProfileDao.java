package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.UserRoleNameImageVO;

public interface UserProfileDao extends GenericDao {

	public User findByUserName(String userName)
	        throws NoRecordsFetchedException, DatabaseException;

	public User findByUserId(Integer userId);

	public Integer updateUser(User user);

	public Integer updateCustomerDetails(CustomerDetail CustomerDetail);

	public Integer updateUser(String s3ImagePath, Integer userid);

	public Integer competeUserProfile(User user);

	public Integer completeCustomerDetails(CustomerDetail customerDetail);

	public Integer managerUpdateUserProfile(User user);

	public Integer managerUpdateUCustomerDetails(CustomerDetail customerDetail);

	public List<User> searchUsers(User user);

	public Integer saveInternalUser(User user);

	public User findInternalUser(Integer userID);

	public String findUserRole(Integer userID)
	        throws NoRecordsFetchedException, DatabaseException;

	public UserRoleNameImageVO findUserDetails(int intValue);

	String findUserRoleForMongo(Integer userID)
	        throws NoRecordsFetchedException, DatabaseException;

	public List<UserRoleNameImageVO> finUserDetailsList(List<Long> userList);

	
	public User saveUser(User user) throws DatabaseException;

	public User authenticateUser(String userName, String password) throws NoRecordsFetchedException, DatabaseException;
}
