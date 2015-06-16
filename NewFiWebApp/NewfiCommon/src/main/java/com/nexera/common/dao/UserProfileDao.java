package com.nexera.common.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.RealtorDetail;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.InternalUserStateMappingVO;
import com.nexera.common.vo.UpdatePasswordVO;
import com.nexera.common.vo.UserRoleNameImageVO;
import com.nexera.common.vo.UserVO;

public interface UserProfileDao extends GenericDao {

	public User findByUserName(String userName)
	        throws NoRecordsFetchedException, DatabaseException;

	public User findByToken(String token);

	public User findByUserId(Integer userId);

	public Integer updateUser(User user);

	public Integer updateCustomerDetails(CustomerDetail CustomerDetail);

	public Integer updateTokenDetails(User user);

	public Integer updateCustomerScore(CustomerDetail CustomerDetail);

	public Integer updateCustomerSpouseScore(
	        CustomerSpouseDetail CustomerSpouseDetail);

	public Integer updatePhotoURL(String s3ImagePath, Integer userid);

	public Integer competeUserProfile(User user);

	public Integer completeCustomerDetails(CustomerDetail customerDetail);

	public Integer managerUpdateUserProfile(User user);

	public Integer managerUpdateUCustomerDetails(CustomerDetail customerDetail);

	public List<User> searchUsers(User user);

	public Integer saveUserWithDetails(User user);

	public User findInternalUser(Integer userID);

	public String findUserRole(Integer userID)
	        throws NoRecordsFetchedException, DatabaseException;

	public UserRoleNameImageVO findUserDetails(int intValue);

	String findUserRoleForMongo(Integer userID)
	        throws NoRecordsFetchedException, DatabaseException;

	public List<UserRoleNameImageVO> finUserDetailsList(List<Long> userList);

	public User saveUser(User user) throws DatabaseException;

	public User authenticateUser(String userName, String password)
	        throws NoRecordsFetchedException, DatabaseException;

	public Integer saveCustomerDetails(User user);

	public List<User> getEmailAddress(List<Integer> list);

	public List<User> fetchAllActiveUsers();

	public List<User> getUsersList();

	public List<User> getLoanManagerForState(String stateName);

	public List<User> getLoanManagerWithLeastWork();

	public UserVO getDefaultLoanManagerForRealtor(UserVO realtor,
	        String stateName);

	Integer updateInternalUserDetail(User user);

	UserVO getDefaultSalesManager();

	void updateLoginTime(Date date, int userId);

	public boolean changeUserPassword(UpdatePasswordVO updatePasswordVO);

	public Integer updateLqbProfile(User user);

	public void updateLMID(Integer realtorID, int loanManagerId);

	public List<String> getDefaultUsers(String userName);

	public Integer updateRealtorDetails(RealtorDetail realtor);

	public Integer UpdateUserProfile(String phoneNumber, Integer userId);

	public User getUserByUserName(String userName)
	        throws NoRecordsFetchedException;

	public InternalUserStateMapping updateInternalUserStateMapping(
	        InternalUserStateMappingVO inputVo);

	public InternalUserStateMapping deleteInternalUserStateMapping(
	        InternalUserStateMappingVO inputVo);

	public Integer updateUserStatus(User user);

	public Integer updateTutorialStatus(Integer id);

	public void verifyEmail(int userID) throws DatabaseException,
	        HibernateException;

	public List<User> getAllSalesManagers();

	public List<User> getUserBySecondaryMail(String emailAddress);

	public Integer updateNMLS(User user);

	public void updateTokenDetails(int internalUserId, String token,
	        long currentTimeMillis);

}
