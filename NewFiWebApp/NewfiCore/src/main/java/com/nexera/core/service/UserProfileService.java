package com.nexera.core.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.InternalUserDetail;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.InputValidationException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.InternalUserStateMappingVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.UpdatePasswordVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;

public interface UserProfileService {

	public UserVO findUser(Integer userid);

	public Integer updateUser(UserVO userVO) throws InputValidationException,
	        NonFatalException;

	public Integer updateCustomerDetails(UserVO userVO);

	public Integer updatePhotoURL(String s3ImagePath, Integer userid);

	public void updateTokenDetails(User user);

	public Integer competeUserProfile(UserVO userVO);

	public Integer completeCustomerDetails(UserVO userVO);

	public Integer managerUpdateUserProfile(UserVO userVO);

	public Integer managerUpdateUCustomerDetails(UserVO userVO);

	public List<UserVO> searchUsers(UserVO userVO);

	public UserVO loadInternalUser(Integer userID);

	public List<UserVO> buildUserVOList(List<User> team);

	public void disableUser(int userId) throws NoRecordsFetchedException;

	public void enableUser(int userId) throws NoRecordsFetchedException;

	public User createNewUser(UserVO userVO) throws InvalidInputException,
	        UndeliveredEmailException, FatalException;

	public void deleteUser(UserVO userVO) throws Exception;

	public User findUserByMail(String userMailAddress);

	public User findUserByToken(String userToken);

	public UserVO saveUser(UserVO userVO);

	public List<User> fetchAllActiveUsers();

	public List<UserVO> getUsersList();

	public JsonObject parseCsvAndAddUsers(MultipartFile file)
	        throws FileNotFoundException, IOException, InvalidInputException,
	        UndeliveredEmailException, NoRecordsFetchedException;

	public UserVO registerCustomer(LoanAppFormVO loaAppFormVO,
	        List<LqbTeaserRateVo> teaseRateDatalist);

	public void crateWorkflowItems(int defaultLoanId) throws Exception;

	public boolean changeUserPassword(UpdatePasswordVO updatePasswordVO);

	public void updateCustomerScore(CustomerDetail customerDetails);

	public void updateCustomerSpouseScore(
	        CustomerSpouseDetail customerSpouseDetail);

	public Integer updateLQBUsercred(UserVO userVO) throws Exception;

	public void resetPassword(User user) throws InvalidInputException,
	        UndeliveredEmailException;

	public CustomerDetail getCustomerDetail(int id);

	public void addDefaultLM(UserVO userVO);

	public String getLQBUrl(Integer userId, Integer loanId);

	public List<String> getDefaultUsers(String userName);

	public User validateRegistrationLink(String userToken, int clientRawOffset)
	        throws InvalidInputException;

	public UserVO findByUserName(String userName) throws DatabaseException,
	        NoRecordsFetchedException;

	public InternalUserStateMappingVO updateInternalUserStateMapping(
	        InternalUserStateMappingVO internalUserStateMappingVO);

	public InternalUserStateMappingVO deleteInternalUserStateMapping(
	        InternalUserStateMappingVO internalUserStateMappingVO);

	public Integer updateUserStatus(UserVO userVO);

	public Integer updateTutorialStatus(Integer id) throws Exception;

	public void dismissAlert(
	        MilestoneNotificationTypes mileStoneNotificationType, int loanId,
	        String notificationContent);

	public void createAlert(
	        MilestoneNotificationTypes mileStoneNotificationType, int loanId,
	        String notificationContent);

	public void resendRegistrationDetails(User user)
	        throws InvalidInputException, UndeliveredEmailException;

	public void updateLoginTime(Date date, int userId);

	public void verifyEmail(int userId);

	public List<User> geAllSalesManagers();

	public List<User> findBySecondaryEmail(String fromAddressString);

	public void sendEmailPreQualification(LoanAppFormVO loaAppFormVO,
	        ByteArrayOutputStream byteArrayOutputStream)
	        throws InvalidInputException, UndeliveredEmailException;

	public Integer updateNMLSId(UserVO userVO);

	public void updateInternalUserDetails(InternalUserDetail internalUserDetail);

	/**
	 * @param newUser
	 */
	void sendEmailToCustomer(User newUser);

	public void sendContactAlert(UserVO user) throws InvalidInputException,
	        UndeliveredEmailException;
}
