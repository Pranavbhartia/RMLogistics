package com.nexera.core.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.InternalUserDetail;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.UserProfileService;

@Component
@Transactional
public class UserProfileServiceImpl implements UserProfileService, InitializingBean {

	@Autowired
	private UserProfileDao userProfileDao;
	
	@Autowired
	private SendGridEmailService sendGridEmailService;
	
	@Value("${NEW_USER_TEMPLATE_ID}")
	private String newUserMailTemplateId; 
	
	private SecureRandom randomGenerator;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserProfileServiceImpl.class);


	@Override
	public UserVO findUser(Integer userid) {

		User user = userProfileDao.findByUserId(userid);

		UserVO userVO = new UserVO();

		userVO.setId(user.getId());
		userVO.setFirstName(user.getFirstName());
		userVO.setLastName(user.getLastName());
		userVO.setEmailId(user.getEmailId());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setPhotoImageUrl(user.getPhotoImageUrl());

		userVO.setUserRole(this.buildUserRoleVO(user.getUserRole()));

		CustomerDetail customerDetail = user.getCustomerDetail();
		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		if (customerDetail != null) {
			customerDetailVO.setId(customerDetail.getId());
			customerDetailVO.setAddressCity(customerDetail.getAddressCity());
			customerDetailVO.setAddressState(customerDetail.getAddressState());
			customerDetailVO.setAddressZipCode(customerDetail
			        .getAddressZipCode());
			customerDetailVO.setSecPhoneNumber(customerDetail
			        .getSecPhoneNumber());
			customerDetailVO.setSecEmailId(customerDetail.getSecEmailId());
			if (customerDetail.getDateOfBirth() != null) {
				customerDetailVO.setDateOfBirth(customerDetail.getDateOfBirth()
				        .getTime());
			}

			customerDetailVO.setProfileCompletionStatus(customerDetail
			        .getProfileCompletionStatus());

		}

		userVO.setCustomerDetail(customerDetailVO);

		return userVO;
	}

	@Override
	public Integer updateUser(UserVO userVO) {

		User user = new User();

		user.setId(userVO.getId());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setEmailId(userVO.getEmailId());
		user.setPhoneNumber(userVO.getPhoneNumber());
		user.setPhotoImageUrl(userVO.getPhotoImageUrl());

		Integer userVOObj = userProfileDao.updateUser(user);

		return userVOObj;
	}

	@Override
	public Integer updateCustomerDetails(UserVO userVO) {

		CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();
		CustomerDetail customerDetail = new CustomerDetail();

		customerDetail.setId(customerDetailVO.getId());
		customerDetail.setAddressCity(customerDetailVO.getAddressCity());
		customerDetail.setAddressState(customerDetailVO.getAddressState());
		customerDetail.setAddressZipCode(customerDetailVO.getAddressZipCode());
		customerDetail.setSecPhoneNumber(customerDetailVO.getSecPhoneNumber());
		customerDetail.setSecEmailId(customerDetailVO.getSecEmailId());
		if (customerDetailVO.getDateOfBirth() != null) {
			customerDetail.setDateOfBirth(new Date(customerDetailVO
			        .getDateOfBirth()));
		} else {
			customerDetail.setDateOfBirth(null);
		}
		customerDetail.setProfileCompletionStatus(customerDetailVO
		        .getProfileCompletionStatus());

		Integer customerDetailVOObj = userProfileDao
		        .updateCustomerDetails(customerDetail);
		return customerDetailVOObj;
	}

	@Override
	public Integer updateUser(String s3ImagePath, Integer userid) {

		Integer number = userProfileDao.updateUser(s3ImagePath, userid);
		return number;
	}

	public List<UserVO> searchUsers(UserVO userVO) {

		return this.buildUserVOList(userProfileDao
		        .searchUsers(parseUserModel(userVO)));

	}

	@Override
	public List<UserVO> buildUserVOList(List<User> userList) {

		if (userList == null)
			return null;

		List<UserVO> voList = new ArrayList<UserVO>();
		for (User user : userList) {
			voList.add(this.buildUserVO(user));
		}

		return voList;
	}

	public UserRoleVO buildUserRoleVO(UserRole role) {

		if (role == null)
			return null;

		UserRoleVO roleVO = new UserRoleVO();

		roleVO.setId(role.getId());
		roleVO.setRoleCd(role.getRoleCd());
		roleVO.setLabel(role.getLabel());
		roleVO.setRoleDescription(role.getRoleDescription());

		return roleVO;

	}

	private UserRole parseUserRoleModel(UserRoleVO roleVO) {

		if (roleVO == null)
			return null;

		UserRole role = new UserRole();

		role.setId(roleVO.getId());
		role.setRoleCd(roleVO.getRoleCd());
		role.setLabel(roleVO.getLabel());
		role.setRoleDescription(roleVO.getRoleDescription());

		return role;

	}

	@Override
	public Integer competeUserProfile(UserVO userVO) {

		User user = new User();

		user.setId(userVO.getId());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setEmailId(userVO.getEmailId());
		user.setPhoneNumber(userVO.getPhoneNumber());

		Integer rowCount = userProfileDao.competeUserProfile(user);

		return rowCount;
	}

	@Override
	public Integer completeCustomerDetails(UserVO userVO) {

		CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();
		CustomerDetail customerDetail = new CustomerDetail();

		customerDetail.setId(customerDetailVO.getId());
		customerDetail.setAddressCity(customerDetailVO.getAddressCity());
		customerDetail.setAddressState(customerDetailVO.getAddressState());
		customerDetail.setAddressZipCode(customerDetailVO.getAddressZipCode());
		customerDetail.setSecPhoneNumber(customerDetailVO.getSecPhoneNumber());
		customerDetail.setSecEmailId(customerDetailVO.getSecEmailId());

		if (customerDetailVO.getDateOfBirth() != null) {
			customerDetail.setDateOfBirth(new Date(customerDetailVO
			        .getDateOfBirth()));
		} else {
			customerDetail.setDateOfBirth(null);
		}
		customerDetail.setProfileCompletionStatus(customerDetailVO
		        .getProfileCompletionStatus());

		Integer rowCount = userProfileDao
		        .completeCustomerDetails(customerDetail);
		return rowCount;
	}

	@Override
	public Integer managerUpdateUserProfile(UserVO userVO) {

		User user = new User();

		user.setId(userVO.getId());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setEmailId(userVO.getEmailId());
		user.setPhoneNumber(userVO.getPhoneNumber());

		Integer rowCount = userProfileDao.managerUpdateUserProfile(user);

		return rowCount;
	}

	@Override
	public Integer managerUpdateUCustomerDetails(UserVO userVO) {

		CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();
		CustomerDetail customerDetail = new CustomerDetail();

		customerDetail.setId(customerDetailVO.getId());
		customerDetail.setAddressCity(customerDetailVO.getAddressCity());
		customerDetail.setAddressState(customerDetailVO.getAddressState());
		customerDetail.setAddressZipCode(customerDetailVO.getAddressZipCode());
		customerDetail.setSecPhoneNumber(customerDetailVO.getSecPhoneNumber());
		customerDetail.setSecEmailId(customerDetailVO.getSecEmailId());

		if (customerDetailVO.getDateOfBirth() != null) {
			customerDetail.setDateOfBirth(new Date(customerDetailVO
			        .getDateOfBirth()));
		} else {
			customerDetail.setDateOfBirth(null);
		}
		customerDetail.setProfileCompletionStatus(customerDetailVO
		        .getProfileCompletionStatus());

		Integer rowCount = userProfileDao
		        .managerUpdateUCustomerDetails(customerDetail);
		return rowCount;
	}

	@Override
	public UserVO buildUserVO(User user) {

		if (user == null)
			return null;

		UserVO userVO = new UserVO();

		userVO.setId(user.getId());
		userVO.setFirstName(user.getFirstName());
		userVO.setLastName(user.getLastName());
		userVO.setEmailId(user.getEmailId());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setPhotoImageUrl(user.getPhotoImageUrl());

		userVO.setUserRole(this.buildUserRoleVO(user.getUserRole()));

		userVO.setInternalUserDetail(this.buildInternalUserDetailsVO(user
		        .getInternalUserDetail()));

		return userVO;
	}

	@Override
	public User parseUserModel(UserVO userVO) {

		if (userVO == null)
			return null;
		User userModel = new User();

		userModel.setId(userVO.getId());
		userModel.setFirstName(userVO.getFirstName());
		userModel.setLastName(userVO.getLastName());
		userModel.setUsername(userVO.getUsername());
		userModel.setEmailId(userVO.getEmailId());
		userModel.setPhoneNumber(userVO.getPhoneNumber());
		userModel.setPhotoImageUrl(userVO.getPhotoImageUrl());

		userModel.setUserRole(this.parseUserRoleModel(userVO.getUserRole()));

		userModel.setInternalUserDetail(this
		        .parseInternalUserDetailsModel(userVO.getInternalUserDetail()));

		return userModel;
	}

	@Override
	public UserVO createUser(UserVO userVO) {
		
		User user = this.parseUserModel(userVO);
		user.setStatus(true);
		Integer userID = (Integer) userProfileDao.saveInternalUser(user);
		user = null;

		if (userID != null && userID > 0)
			user = (User) userProfileDao.findInternalUser(userID);

		return this.buildUserVO(user);
	}

	@Override
	public InternalUserDetailVO buildInternalUserDetailsVO(
	        InternalUserDetail internalUserDetail) {
		// TODO Auto-generated method stub

		if (internalUserDetail == null)
			return null;

		InternalUserDetailVO detailVO = new InternalUserDetailVO();
		detailVO.setInternalUserRoleMasterVO(buildInternalUserRoleMasterVO(internalUserDetail
		        .getInternaUserRoleMaster()));

		return detailVO;
	}

	private InternalUserRoleMasterVO buildInternalUserRoleMasterVO(
	        InternalUserRoleMaster internal) {
		if (internal == null)
			return null;

		InternalUserRoleMasterVO masterVO = new InternalUserRoleMasterVO();
		masterVO.setId(internal.getId());
		masterVO.setRoleName(internal.getRoleName());
		masterVO.setRoleDescription(internal.getRoleDescription());

		return masterVO;
	}

	private InternalUserDetail parseInternalUserDetailsModel(
	        InternalUserDetailVO internalUserDetailVO) {
		// TODO Auto-generated method stub

		if (internalUserDetailVO == null)
			return null;

		InternalUserDetail detail = new InternalUserDetail();
		detail.setInternaUserRoleMaster(parseInternalUserRoleMasterModel(internalUserDetailVO
		        .getInternalUserRoleMasterVO()));

		return detail;
	}

	private InternalUserRoleMaster parseInternalUserRoleMasterModel(
	        InternalUserRoleMasterVO internalVO) {
		if (internalVO == null)
			return null;

		InternalUserRoleMaster master = new InternalUserRoleMaster();
		master.setId(internalVO.getId());
		master.setRoleDescription(internalVO.getRoleDescription());

		return master;
	}

	@Override
	public UserVO loadInternalUser(Integer userID) {
		// TODO Auto-generated method stub
		return this.buildUserVO(userProfileDao.findInternalUser(userID));
	}

	@Override
	public void disableUser(int userId) throws NoRecordsFetchedException {
		
		User user = userProfileDao.findByUserId(userId);
		if( user == null){
			throw new NoRecordsFetchedException("User not found in the user table");
		}
		
		user.setStatus(false);
		userProfileDao.update(user);
	}
	
	@Override
	public void enableUser(int userId) throws NoRecordsFetchedException {
		
		User user = userProfileDao.findByUserId(userId);
		if( user == null){
			throw new NoRecordsFetchedException("User not found in the user table");
		}
		
		user.setStatus(true);
		userProfileDao.update(user);
	}
	
	private String generateRandomPassword(){
		return new BigInteger( CommonConstants.RANDOM_PASSWORD_LENGTH * 5, randomGenerator).toString(32);
	}
	
	private void sendNewUserEmail(User user) throws InvalidInputException, UndeliveredEmailException{
		
		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();
		
		//We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[]{user.getFirstName() + " " + user.getLastName()});
		substitutions.put("-username-", new String[]{user.getUsername()});
		substitutions.put("-password-", new String[]{user.getPassword()});
		
		recipientVO.setEmailID(user.getEmailId());
		emailEntity.setRecipients(new ArrayList<EmailRecipientVO>(Arrays.asList(recipientVO)));
		emailEntity.setSenderEmailId(CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("You have been subscribed to Nexera");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(newUserMailTemplateId);

		sendGridEmailService.sendMail(emailEntity);
	}

	@Override
	public UserVO createNewUserAndSendMail(UserVO userVO) throws InvalidInputException, UndeliveredEmailException {
		LOG.info("createNewUserAndSendMail called!");
		LOG.debug("PArsing the VO");
		User newUser = parseUserModel(userVO);
		newUser.setStatus(true);
		LOG.debug("Done parsing, Setting a new random password");
		newUser.setPassword(generateRandomPassword());	
		LOG.debug("Saving the user to the database");
		int userID = userProfileDao.saveInternalUser(newUser);
		LOG.debug("Saved, sending the email");
		sendNewUserEmail(newUser);
		newUser = null;
		if (userID > 0)
			newUser = (User) userProfileDao.findInternalUser(userID);
		LOG.info("Returning the userVO");
		return this.buildUserVO(newUser);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		randomGenerator = new SecureRandom();
	}

	@Override
	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	public UserVO findUserByMail(String userMailAddress) {

		User user = null;
		UserVO userVO = null;
		try {
			user = userProfileDao.findByUserName(userMailAddress);
			if (user != null) {
				userVO = buildUserVO(user);
			}

		} catch (DatabaseException e) {

		} catch (NoRecordsFetchedException e) {

		}
		return userVO;
	}
	
	/**
	 * This method is use to create a recode of the user in the user table when the user as a shopper comes from the customer engagement path 
	 * 
	 */
	@Override
    public UserVO saveUser(UserVO userVO) {
	  
		User user = new User();
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
	
		user.setEmailId(userVO.getEmailId().split(":")[0]);
		user.setUsername(userVO.getEmailId().split(":")[0]);
		user.setPassword("123abc");
		user.setStatus(true);
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		user.setUserRole(userRole);
		
		
		user = userProfileDao.saveUser(user);
		UserVO userVOobj  = new UserVO();
		userVOobj.setFirstName(user.getFirstName());
		userVOobj.setUsername(user.getUsername());
		userVOobj.setLastName(user.getLastName());
		userVOobj.setPassword(user.getPassword());
		
	    return userVOobj;
    }
	
	

}
