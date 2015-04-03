package com.nexera.core.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.commons.ErrorConstants;
import com.nexera.common.commons.MessageUtils;
import com.nexera.common.dao.InternalUserStateMappingDao;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.StateLookupDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.InternalUserDetail;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.DisplayMessageType;
import com.nexera.common.enums.ServiceCodes;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.GenericErrorCode;
import com.nexera.common.exception.InputValidationException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;
import com.nexera.common.vo.RealtorDetailVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.UserProfileService;

@Component
@Transactional
public class UserProfileServiceImpl implements UserProfileService,
        InitializingBean {

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private InternalUserStateMappingDao internalUserStateMappingDao;

	@Autowired
	private StateLookupDao stateLookupDao;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Value("${NEW_USER_TEMPLATE_ID}")
	private String newUserMailTemplateId;

	@Autowired
	private MessageUtils messageUtils;

	private SecureRandom randomGenerator;

	private static final Logger LOG = LoggerFactory
	        .getLogger(UserProfileServiceImpl.class);

	@Override
	public UserVO findUser(Integer userid) {

		User user = userProfileDao.findByUserId(userid);
		UserVO userListVO = User.convertFromEntityToVO(user);
		/*
		 * UserVO userVO = new UserVO();
		 * 
		 * userVO.setId(user.getId()); userVO.setFirstName(user.getFirstName());
		 * userVO.setLastName(user.getLastName());
		 * userVO.setEmailId(user.getEmailId());
		 * userVO.setPhoneNumber(user.getPhoneNumber());
		 * userVO.setPhotoImageUrl(user.getPhotoImageUrl());
		 * 
		 * userVO.setUserRole(UserRole.convertFromEntityToVO(user.getUserRole()))
		 * ;
		 * 
		 * CustomerDetail customerDetail = user.getCustomerDetail();
		 * CustomerDetailVO customerDetailVO = CustomerDetail
		 * .convertFromEntityToVO(customerDetail);
		 * 
		 * userVO.setCustomerDetail(customerDetailVO);
		 */

		return userListVO;
	}

	@Override
	public Integer updateUser(UserVO userVO) {

		User user = User.convertFromVOToEntity(userVO);

		Integer userVOObj = userProfileDao.updateUser(user);

		return userVOObj;
	}

	@Override
	public Integer updateCustomerDetails(UserVO userVO) {

		CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();
		CustomerDetail customerDetail = CustomerDetail
		        .convertFromVOToEntity(customerDetailVO);
		if (customerDetail.getProfileCompletionStatus() != null) {
			if (userVO.getCustomerDetail().getMobileAlertsPreference() != null) {
				if (userVO.getCustomerDetail().getMobileAlertsPreference()
				        && userVO.getPhoneNumber() != null) {
					if (customerDetail.getProfileCompletionStatus() != 100) {
						customerDetail
						        .setProfileCompletionStatus(customerDetail
						                .getProfileCompletionStatus()
						                + (100 / 3));
					}

				} else if (!userVO.getCustomerDetail()
				        .getMobileAlertsPreference()) {
					customerDetail.setMobileAlertsPreference(userVO
					        .getCustomerDetail().getMobileAlertsPreference());

					if (customerDetail.getProfileCompletionStatus() == 100) {
						customerDetail
						        .setProfileCompletionStatus(customerDetail
						                .getProfileCompletionStatus()
						                - (100 / 3));
					} else {
						customerDetail.setProfileCompletionStatus(200 / 3);
					}
				}
			}
		} else {
			if (userVO.getPhotoImageUrl() == null) {
				customerDetail.setProfileCompletionStatus(100 / 3);
				if (userVO.getPhoneNumber() != null
				        && userVO.getCustomerDetail()
				                .getMobileAlertsPreference())
					customerDetail.setProfileCompletionStatus(customerDetail
					        .getProfileCompletionStatus() + (100 / 3));
			} else if (userVO.getPhoneNumber() != null
			        && userVO.getCustomerDetail().getMobileAlertsPreference()
			        && userVO.getPhotoImageUrl() != null) {
				customerDetail.setProfileCompletionStatus(100);
			} else if (userVO.getCustomerDetail().getMobileAlertsPreference()
			        && userVO.getPhoneNumber() != null) {
				customerDetail.setProfileCompletionStatus(200 / 3);
			} else if (!userVO.getCustomerDetail().getMobileAlertsPreference()
			        && userVO.getPhotoImageUrl() != null
			        && userVO.getPhoneNumber() != null) {
				customerDetail.setMobileAlertsPreference(userVO
				        .getCustomerDetail().getMobileAlertsPreference());
				if (customerDetail.getProfileCompletionStatus() == 100) {
					customerDetail.setProfileCompletionStatus(customerDetail
					        .getProfileCompletionStatus() - (100 / 3));
				} else {
					customerDetail.setProfileCompletionStatus(200 / 3);
				}
			}

		}
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

		return this.buildUserVOList(userProfileDao.searchUsers(User
		        .convertFromVOToEntity(userVO)));

	}

	@Override
	public List<UserVO> buildUserVOList(List<User> userList) {

		if (userList == null)
			return null;

		List<UserVO> voList = new ArrayList<UserVO>();
		for (User user : userList) {
			voList.add(User.convertFromEntityToVO(user));
		}

		return voList;
	}

	@Override
	public List<UserVO> getUsersList() {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		userVOList = buildUserVOList(userProfileDao.getUsersList());
		return userVOList;

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

		CustomerDetail customerDetail = CustomerDetail
		        .convertFromVOToEntity(customerDetailVO);

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
		CustomerDetail customerDetail = CustomerDetail
		        .convertFromVOToEntity(customerDetailVO);

		Integer rowCount = userProfileDao
		        .managerUpdateUCustomerDetails(customerDetail);
		return rowCount;
	}

	@Override
	public UserVO loadInternalUser(Integer userID) {
		User user = userProfileDao.findInternalUser(userID);
		return User.convertFromEntityToVO(user);
	}

	@Override
	public void disableUser(int userId) throws NoRecordsFetchedException {

		User user = userProfileDao.findByUserId(userId);
		if (user == null) {
			throw new NoRecordsFetchedException(
			        "User not found in the user table");
		}

		user.setStatus(false);
		userProfileDao.update(user);
	}

	@Override
	public void enableUser(int userId) throws NoRecordsFetchedException {

		User user = userProfileDao.findByUserId(userId);
		if (user == null) {
			throw new NoRecordsFetchedException(
			        "User not found in the user table");
		}

		user.setStatus(true);
		userProfileDao.update(user);
	}

	private String generateRandomPassword() {
		return new BigInteger(CommonConstants.RANDOM_PASSWORD_LENGTH * 5,
		        randomGenerator).toString(32);
	}

	private void sendNewUserEmail(User user) throws InvalidInputException,
	        UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		substitutions.put("-username-", new String[] { user.getUsername() });
		substitutions.put("-password-", new String[] { user.getPassword() });

		recipientVO.setEmailID(user.getEmailId());
		emailEntity.setRecipients(new ArrayList<EmailRecipientVO>(Arrays
		        .asList(recipientVO)));
		emailEntity.setSenderEmailId(CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("You have been subscribed to Nexera");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(newUserMailTemplateId);

		sendGridEmailService.sendMail(emailEntity);
	}

	@Override
	public UserVO createNewUserAndSendMail(UserVO userVO)
	        throws InvalidInputException, UndeliveredEmailException {
		LOG.info("createNewUserAndSendMail called!");
		LOG.debug("Parsing the VO");

		User newUser = User.convertFromVOToEntity(userVO);
		if (newUser.getCustomerDetail() != null) {
			newUser.getCustomerDetail().setProfileCompletionStatus(100 / 3);
		}
		LOG.debug("Done parsing, Setting a new random password");
		newUser.setPassword(generateRandomPassword());
		newUser.setStatus(true);
		LOG.debug("Saving the user to the database");
		int userID = userProfileDao.saveUserWithDetails(newUser);
		LOG.debug("Saved, sending the email");
		try {
			sendNewUserEmail(newUser);
		} catch (InvalidInputException | UndeliveredEmailException e) {
			// TODO: Need to handle this and try a resend, since password will
			// not be stored
			LOG.error("Error sending email, proceeding with the email flow");
		}

		// We set password to null so that it isnt sent back to the front end
		// newUser.setPassword(null);

		// newUser = null;
		if (userID > 0
		        && newUser.getUserRole().getId() == UserRolesEnum.INTERNAL
		                .getRoleId()) {
			newUser = (User) userProfileDao.findInternalUser(userID);
		}
		LOG.info("Returning the userVO");

		return User.convertFromEntityToVO(newUser);

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		randomGenerator = new SecureRandom();
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public void deleteUser(UserVO userVO) throws Exception {

		User user = User.convertFromVOToEntity(userVO);
		boolean canUserBeDeleted = loanDao.checkLoanDependency(user);
		if (canUserBeDeleted) {
			user.getInternalUserDetail().setActiveInternal(false);
			Integer count = userProfileDao.updateInternalUserDetail(user);

		} else {
			throw new InputValidationException(new GenericErrorCode(
			        ServiceCodes.USER_PROFILE_SERVICE.getServiceID(),
			        ErrorConstants.LOAN_MANAGER_DELETE_ERROR),
			        ErrorConstants.LOAN_MANAGER_DELETE_ERROR);
		}

	}

	public User findUserByMail(String userMailAddress) {

		User user = null;

		try {
			user = userProfileDao.findByUserName(userMailAddress);

		} catch (DatabaseException e) {

		} catch (NoRecordsFetchedException e) {

		}
		return user;
	}

	/**
	 * This method is use to create a recode of the user in the user table when
	 * the user as a shopper comes from the customer engagement path
	 * 
	 */
	@Override
	public UserVO saveUser(UserVO userVO) {

		// 1st create a entry in the customer details table
		// use that id to put in the user table .

		Integer userID = (Integer) userProfileDao.saveCustomerDetails(User
		        .convertFromVOToEntity(userVO));
		User user = null;
		if (userID != null && userID > 0)
			user = (User) userProfileDao.findInternalUser(userID);

		return User.convertFromEntityToVO(user);

	}

	private UserRoleVO convertTOUserRoleVO(UserRole userRole) {

		UserRoleVO userRoleVO = new UserRoleVO();

		if (userRole == null) {

			userRoleVO.setId(1);
		} else {

			userRoleVO.setId(userRole.getId());
			userRoleVO.setRoleCd(userRole.getRoleCd());
			userRoleVO.setLabel(userRole.getLabel());
			userRoleVO.setRoleDescription(userRole.getRoleDescription());
		}

		return userRoleVO;

	}

	private InternalUserDetailVO convertTOInternalUserDetailVO(
	        InternalUserDetail internalUserDetail) {
		InternalUserDetailVO internalUserDetailVO = new InternalUserDetailVO();
		if (internalUserDetail == null)
			return internalUserDetailVO;

		InternalUserRoleMasterVO internalUserRoleMasterVO = convertTOInternalUserRoleMasterVO(internalUserDetail
		        .getInternaUserRoleMaster());
		internalUserDetailVO
		        .setInternalUserRoleMasterVO(internalUserRoleMasterVO);

		return internalUserDetailVO;
	}

	public List<User> fetchAllActiveUsers() {
		return userProfileDao.fetchAllActiveUsers();
	}

	private InternalUserRoleMasterVO convertTOInternalUserRoleMasterVO(
	        InternalUserRoleMaster internalUserRoleMaster) {

		if (internalUserRoleMaster == null)
			return null;

		InternalUserRoleMasterVO internalUserRoleMasterVO = new InternalUserRoleMasterVO();
		internalUserRoleMasterVO.setId(internalUserRoleMaster.getId());
		internalUserRoleMasterVO.setRoleName(internalUserRoleMaster
		        .getRoleName());
		internalUserRoleMasterVO.setRoleDescription(internalUserRoleMaster
		        .getRoleDescription());

		return internalUserRoleMasterVO;
	}

	private boolean validateEmail(String emailId) {
		boolean result = false;
		result = emailId.matches(CommonConstants.EMAIL_REGEX);
		return result;
	}

	private boolean validateName(String name) {
		return name.matches(CommonConstants.NAME_REGEX);
	}

	private Date validateDate(String dateString) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	private boolean checkStateCode(String stateCode) {
		boolean status = false;

		try {
			stateLookupDao.findStateLookupByStateCode(stateCode);
			status = true;
		} catch (NoRecordsFetchedException e) {
			status = false;
		}
		return status;
	}

	private String checkCsvLine(String[] csvRow) {
		String message = null;

		if (csvRow.length != CommonConstants.CSV_COLUMN_LENGTH) {
			message = messageUtils.getDisplayMessage(
			        DisplayMessageConstants.INVALID_COLUMN_LENGTH,
			        DisplayMessageType.ERROR_MESSAGE).toString();
			return message;
		}
		if (csvRow[CommonConstants.FNAME_COLUMN] == null
		        || csvRow[CommonConstants.FNAME_COLUMN].isEmpty()
		        || !validateName(csvRow[CommonConstants.FNAME_COLUMN])) {
			message = messageUtils.getDisplayMessage(
			        DisplayMessageConstants.INVALID_FIRST_NAME,
			        DisplayMessageType.ERROR_MESSAGE).toString();
			return message;
		}
		if (csvRow[CommonConstants.LNAME_COLUMN] == null
		        || csvRow[CommonConstants.LNAME_COLUMN].isEmpty()
		        || !validateName(csvRow[CommonConstants.LNAME_COLUMN])) {
			message = messageUtils.getDisplayMessage(
			        DisplayMessageConstants.INVALID_LAST_NAME,
			        DisplayMessageType.ERROR_MESSAGE).toString();
			return message;
		}
		if (csvRow[CommonConstants.EMAIL_COLUMN] == null
		        || csvRow[CommonConstants.EMAIL_COLUMN].isEmpty()
		        || !validateEmail(csvRow[CommonConstants.EMAIL_COLUMN])) {
			message = messageUtils.getDisplayMessage(
			        DisplayMessageConstants.INVALID_EMAIL_ID,
			        DisplayMessageType.ERROR_MESSAGE).toString();
			return message;
		} else {
			// Check for user with same email id
			try {
				User existingUser = userProfileDao
				        .findByUserName(csvRow[CommonConstants.EMAIL_COLUMN]);
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.EMAIL_ALREADY_EXISTS,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			} catch (NoRecordsFetchedException e) {
				// User doesnt exist in database. So we can go ahead and add the
				// user.
			}
		}
		if (!csvRow[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.CUSTOMER
		        .toString())
		        && !csvRow[CommonConstants.ROLE_COLUMN]
		                .equals(UserRolesEnum.LOANMANAGER.toString())
		        && !csvRow[CommonConstants.ROLE_COLUMN]
		                .equals(UserRolesEnum.REALTOR.toString())) {
			message = messageUtils.getDisplayMessage(
			        DisplayMessageConstants.INVALID_ROLE_NAME,
			        DisplayMessageType.ERROR_MESSAGE).toString();
			return message;
		}
		if (csvRow[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.CUSTOMER
		        .toString())) {
			if (csvRow[CommonConstants.CITY_COLUMN] == null
			        || csvRow[CommonConstants.CITY_COLUMN].isEmpty()) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_CITY,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
			if (csvRow[CommonConstants.STATE_COLUMN] == null
			        || csvRow[CommonConstants.STATE_COLUMN].isEmpty()) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_STATE,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
			if (csvRow[CommonConstants.ZIPCODE_COLUMN] == null
			        || csvRow[CommonConstants.ZIPCODE_COLUMN].isEmpty()) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_ZIPCODE,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
			if (csvRow[CommonConstants.SECONDARY_PHONE_COLUMN] == null
			        || csvRow[CommonConstants.SECONDARY_PHONE_COLUMN].isEmpty()) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_SECONDARY_PHONE_NUMBER,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
			if (csvRow[CommonConstants.SECONDARY_EMAIL_COLUMN] == null
			        || csvRow[CommonConstants.SECONDARY_EMAIL_COLUMN].isEmpty()) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_SECONDARY_EMAIL,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
			if (csvRow[CommonConstants.DATE_OF_BIRTH_COLUMN] == null
			        || csvRow[CommonConstants.DATE_OF_BIRTH_COLUMN].isEmpty()
			        || validateDate(csvRow[CommonConstants.DATE_OF_BIRTH_COLUMN]) == null) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_DATE_OF_BIRTH,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
		}

		if (csvRow[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.REALTOR
		        .toString())) {
			if (csvRow[CommonConstants.LICENSE_INFO_COLUMN] == null
			        || csvRow[CommonConstants.LICENSE_INFO_COLUMN].isEmpty()) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_LICENSE_INFO,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
			if (csvRow[CommonConstants.PROFILE_LINK_COLUMN] == null
			        || csvRow[CommonConstants.PROFILE_LINK_COLUMN].isEmpty()) {
				message = messageUtils.getDisplayMessage(
				        DisplayMessageConstants.INVALID_PROFILE_URL,
				        DisplayMessageType.ERROR_MESSAGE).toString();
				return message;
			}
		}

		if (csvRow[CommonConstants.ROLE_COLUMN]
		        .equals(UserRolesEnum.LOANMANAGER.toString())) {
			if (csvRow[CommonConstants.STATE_CODE_COLUMN] != null
			        && !csvRow[CommonConstants.STATE_CODE_COLUMN].isEmpty()) {
				String[] stateCodes = csvRow[CommonConstants.STATE_CODE_COLUMN]
				        .split(CommonConstants.STATE_CODE_STRING_SEPARATOR);
				if (stateCodes.length <= 0) {
					message = messageUtils
					        .getDisplayMessage(
					                DisplayMessageConstants.INVALID_STATE_CODE_LIST_LENGTH,
					                DisplayMessageType.ERROR_MESSAGE)
					        .toString();
					return message;
				}

				int counter = 1;
				for (String stateCode : stateCodes) {
					if (!checkStateCode(stateCode)) {
						message = messageUtils.getDisplayMessage(
						        DisplayMessageConstants.INVALID_STATE_CODE,
						        DisplayMessageType.ERROR_MESSAGE).toString()
						        + counter;
						return message;
					}
					counter += 1;
				}

			}
		}

		return message;
	}

	private JsonObject buildErrorItem(int lineNumber, String[] csvRow,
	        String message) {
		JsonObject errorObject = new JsonObject();

		errorObject.addProperty(CommonConstants.ERROR_LINE_NUMBER, lineNumber);
		errorObject.addProperty(CommonConstants.ERROR_CSV_LINE,
		        StringUtils.join(csvRow, ','));
		errorObject.addProperty(CommonConstants.ERROR_MESSAGE, message);

		return errorObject;

	}

	private UserVO buildUserVOAndCreateUser(String[] rowData)
	        throws InvalidInputException, UndeliveredEmailException {

		UserVO userVO = new UserVO();

		userVO.setDefaultLoanId(CommonConstants.DEFAULT_LOAN_ID);
		userVO.setFirstName(rowData[CommonConstants.FNAME_COLUMN]);
		userVO.setLastName(rowData[CommonConstants.LNAME_COLUMN]);
		userVO.setEmailId(rowData[CommonConstants.EMAIL_COLUMN]);

		UserRoleVO userRoleVO = new UserRoleVO();
		if (rowData[CommonConstants.ROLE_COLUMN]
		        .equals(UserRolesEnum.LOANMANAGER.toString())) {

			userRoleVO.setId(UserRolesEnum.INTERNAL.getRoleId());
			userRoleVO.setRoleCd(UserRolesEnum.INTERNAL.toString());
			userVO.setUserRole(userRoleVO);

			InternalUserDetailVO internalUserDetailVO = new InternalUserDetailVO();
			internalUserDetailVO
			        .setInternalUserRoleMasterVO(new InternalUserRoleMasterVO());
			internalUserDetailVO.getInternalUserRoleMasterVO().setId(
			        UserRolesEnum.LOANMANAGER.getRoleId());
			userVO.setInternalUserDetail(internalUserDetailVO);

		} else if (rowData[CommonConstants.ROLE_COLUMN]
		        .equals(UserRolesEnum.CUSTOMER.toString())) {
			userRoleVO.setId(UserRolesEnum.CUSTOMER.getRoleId());
			userRoleVO.setRoleCd(UserRolesEnum.CUSTOMER.toString());
			userVO.setUserRole(userRoleVO);
			CustomerDetailVO customerDetail = new CustomerDetailVO();
			customerDetail.setAddressCity(rowData[CommonConstants.CITY_COLUMN]);
			customerDetail
			        .setAddressState(rowData[CommonConstants.STATE_COLUMN]);
			customerDetail
			        .setAddressZipCode(rowData[CommonConstants.ZIPCODE_COLUMN]);
			customerDetail
			        .setSecPhoneNumber(rowData[CommonConstants.SECONDARY_PHONE_COLUMN]);
			customerDetail
			        .setSecEmailId(rowData[CommonConstants.SECONDARY_EMAIL_COLUMN]);
			customerDetail.setDateOfBirth(validateDate(
			        rowData[CommonConstants.DATE_OF_BIRTH_COLUMN]).getTime());
			userVO.setCustomerDetail(customerDetail);
		} else {
			userRoleVO.setId(UserRolesEnum.REALTOR.getRoleId());
			userRoleVO.setRoleCd(UserRolesEnum.REALTOR.toString());
			userVO.setUserRole(userRoleVO);

			RealtorDetailVO realtorDetailVO = new RealtorDetailVO();
			realtorDetailVO
			        .setLicenceInfo(rowData[CommonConstants.LICENSE_INFO_COLUMN]);
			realtorDetailVO
			        .setProfileUrl(rowData[CommonConstants.PROFILE_LINK_COLUMN]);
			userVO.setRealtorDetail(realtorDetailVO);
		}

		UserVO createdUserVo = createNewUserAndSendMail(userVO);
		return createdUserVo;
	}

	private void createUserStateMapping(UserVO userVO, String stateCodes)
	        throws NoRecordsFetchedException {

		String[] stateCodesList = stateCodes
		        .split(CommonConstants.STATE_CODE_STRING_SEPARATOR);

		InternalUserStateMapping internalUserStateMapping;
		User user = userProfileDao.findByUserId(userVO.getId());

		for (String stateCode : stateCodesList) {
			internalUserStateMapping = new InternalUserStateMapping();
			internalUserStateMapping.setStateLookup(stateLookupDao
			        .findStateLookupByStateCode(stateCode));
			internalUserStateMapping.setUser(user);
			internalUserStateMappingDao.save(internalUserStateMapping);
		}

	}

	@Override
	public JsonObject parseCsvAndAddUsers(MultipartFile file)
	        throws IOException, InvalidInputException,
	        UndeliveredEmailException, NoRecordsFetchedException {

		Reader reader = new InputStreamReader(file.getInputStream());
		CSVReader csvReader = new CSVReader(reader, ',');
		int lineCounter = 1;

		JsonObject errors = new JsonObject();
		JsonArray errorList = new JsonArray();

		List<String[]> allRows = csvReader.readAll();
		for (String[] rowString : allRows) {
			String message = checkCsvLine(rowString);
			if (message == null) {
				// LOG.info(StringUtils.join(rowString,','));
				if (rowString[CommonConstants.STATE_CODE_COLUMN].isEmpty()) {
					buildUserVOAndCreateUser(rowString);
				} else {
					createUserStateMapping(buildUserVOAndCreateUser(rowString),
					        rowString[CommonConstants.STATE_CODE_COLUMN]);
				}
			} else {
				errorList.add(buildErrorItem(lineCounter, rowString, message));
			}
			lineCounter += 1;
		}

		csvReader.close();
		errors.add("errors", errorList);
		return errors;
	}

}
