package com.nexera.core.service;

import java.util.List;
import com.nexera.common.entity.InternalUserDetail;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;

public interface UserProfileService {

	public UserVO findUser(Integer userid);

	public Integer updateUser(UserVO userVO);

	public Integer updateCustomerDetails(UserVO userVO);

	public Integer updateUser(String s3ImagePath, Integer userid);

	public Integer competeUserProfile(UserVO userVO);

	public Integer completeCustomerDetails(UserVO userVO);

	public Integer managerUpdateUserProfile(UserVO userVO);

	public Integer managerUpdateUCustomerDetails(UserVO userVO);

	public UserVO createUser(UserVO userVO);

	public List<UserVO> searchUsers(UserVO userVO);

	public UserVO loadInternalUser(Integer userID);

	public UserRoleVO buildUserRoleVO(UserRole userRole);

	public InternalUserDetailVO buildInternalUserDetailsVO(
	        InternalUserDetail internalUserDetail);

	public UserVO buildUserVO(User user);

	public List<UserVO> buildUserVOList(List<User> team);
	
	public void disableUser(int userId) throws NoRecordsFetchedException;
	
	public void enableUser(int userId) throws NoRecordsFetchedException;


	User parseUserModel(UserVO userVO);
	
	public UserVO createNewUserAndSendMail(UserVO userVO) throws InvalidInputException, UndeliveredEmailException;
	
	public void deleteUser(int userId);

	public User findUserByMail(String userMailAddress);

	public UserVO saveUser(UserVO userVO);
	
	public List<User> fetchAllActiveUsers();

}
