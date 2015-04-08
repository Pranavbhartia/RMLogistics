package com.nexera.core.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.User;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.LoanAppFormVO;
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

	public List<UserVO> searchUsers(UserVO userVO);

	public UserVO loadInternalUser(Integer userID);

	public List<UserVO> buildUserVOList(List<User> team);

	public void disableUser(int userId) throws NoRecordsFetchedException;

	public void enableUser(int userId) throws NoRecordsFetchedException;

	public UserVO createNewUserAndSendMail(UserVO userVO)
	        throws InvalidInputException, UndeliveredEmailException;

	public void deleteUser(UserVO userVO) throws Exception;

	public User findUserByMail(String userMailAddress);

	public UserVO saveUser(UserVO userVO);

	public List<User> fetchAllActiveUsers();

	public List<UserVO> getUsersList();

	public JsonObject parseCsvAndAddUsers(MultipartFile file)
	        throws FileNotFoundException, IOException, InvalidInputException,
	        UndeliveredEmailException, NoRecordsFetchedException;

	public UserVO registerCustomer(LoanAppFormVO loaAppFormVO);

	public void crateWorkflowItems(int defaultLoanId) throws Exception;

	public boolean changeUserPassword(UserVO uservo);

	public void updateCustomerScore(CustomerDetail customerDetails);

	public void updateCustomerSpouseScore(
	        CustomerSpouseDetail customerSpouseDetail);

	public Integer updateLQBUsercred(UserVO userVO) throws Exception;
	
	public void forgetPassword(User user);

	CustomerDetail getCustomerDetail(int id);

}