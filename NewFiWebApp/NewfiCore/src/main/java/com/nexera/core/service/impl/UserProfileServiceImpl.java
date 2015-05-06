package com.nexera.core.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.commons.ErrorConstants;
import com.nexera.common.commons.MessageUtils;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.dao.InternalUserStateMappingDao;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.StateLookupDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.InternalUserDetail;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.RealtorDetail;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.ActiveInternalEnum;
import com.nexera.common.enums.DisplayMessageType;
import com.nexera.common.enums.LoanTypeMasterEnum;
import com.nexera.common.enums.ServiceCodes;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.GenericErrorCode;
import com.nexera.common.exception.InputValidationException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;
import com.nexera.common.vo.InternalUserStateMappingVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.PropertyTypeMasterVO;
import com.nexera.common.vo.PurchaseDetailsVO;
import com.nexera.common.vo.RealtorDetailVO;
import com.nexera.common.vo.RefinanceVO;
import com.nexera.common.vo.UpdatePasswordVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.InternalUserStateMappingService;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.WorkflowCoreService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.workflow.vo.WorkflowVO;

@Component
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
	private LqbInvoker lqbInvoker;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private MessageUtils messageUtils;

	private SecureRandom randomGenerator;

	@Autowired
	NexeraUtility nexeraUtility;

	@Autowired
	private LoanService loanService;

	@Autowired
	WorkflowCoreService workflowCoreService;

	@Autowired
	private Utils utils;

	@Autowired
	protected LoanAppFormService loanAppFormService;

	@Autowired
	private InternalUserStateMappingService internalUserStateMappingService;

	@Value("${lqb.defaulturl}")
	private String lqbDefaultUrl;

	@Value("${profile.url}")
	private String baseUrl;
	private static final Logger LOG = LoggerFactory
	        .getLogger(UserProfileServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public UserVO findUser(Integer userid) {

		User user = userProfileDao.findByUserId(userid);
		UserVO userListVO = User.convertFromEntityToVO(user);

		return userListVO;
	}

	@Override
	@Transactional()
	public Integer updateUser(UserVO userVO) throws InputValidationException,
	        NonFatalException {

		// TODO update user details
		User user = User.convertFromVOToEntity(userVO);
		Integer userVOObj = userProfileDao.updateUser(user);

		// TODO for update customer details
		UserVO userVODetails = findUser(userVO.getId());
		userVO.setUserRole(userVODetails.getUserRole());
		
		/*if(userVO.getInternalUserStateMappingVOs()!=null){
			internalUserStateMappingService.saveOrUpdateUserStates(userVO.getInternalUserStateMappingVOs());
		}
*/
		if (userVO.getCustomerDetail() != null) {
			userVO.getCustomerDetail().setProfileCompletionStatus(
			        userVODetails.getCustomerDetail()
			                .getProfileCompletionStatus());
		}
		if (userVODetails.getPhotoImageUrl() != null) {
			userVO.setPhotoImageUrl(userVODetails.getPhotoImageUrl());
		}

		// calling another service method
		Integer customerDetailsUpdateCount = updateCustomerDetails(userVO);
		if (customerDetailsUpdateCount < 0) {

			throw new NonFatalException(ErrorConstants.UPDATE_ERROR_CUSTOMER);
		}

		// TODO update realtor
		String loanManagerEmail = userVO.getLoanManagerEmail();
		if (userVODetails.getUserRole().getId() == UserRolesEnum.REALTOR
		        .getRoleId()) {
			if (loanManagerEmail != null) {
				User userDetails = findUserByMail(loanManagerEmail);
				if (userDetails != null) {

					if (userDetails.getUserRole().getId() == UserRolesEnum.INTERNAL
					        .getRoleId()) {
						userVODetails.getRealtorDetail().setUser(
						        User.convertFromEntityToVO(userDetails));
						Integer updateCount = userProfileDao
						        .updateRealtorDetails(RealtorDetail
						                .convertFromVOToEntity(userVODetails
						                        .getRealtorDetail()));
						if (updateCount < 0) {
							throw new NonFatalException(
							        ErrorConstants.UPDATE_ERROR_CUSTOMER);
						}
					} else {

						throw new InputValidationException(
						        new GenericErrorCode(
						                ServiceCodes.USER_PROFILE_SERVICE
						                        .getServiceID(),
						                ErrorConstants.LOAN_MANAGER_DOESNOT_EXSIST),
						        ErrorConstants.LOAN_MANAGER_DOESNOT_EXSIST);
					}
				}

			}

		}

		return userVOObj;
	}

	@Override
	@Transactional
	public Integer updateCustomerDetails(UserVO userVO) {

		LOG.info("To update the users");
		CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();
		CustomerDetail customerDetail = CustomerDetail
		        .convertFromVOToEntity(customerDetailVO);

		if (userVO.getUserRole().getId() == UserRolesEnum.CUSTOMER.getRoleId()) {
			float completionStatus = CommonConstants.PROFILE_STATUS_WEIGHTAGE;

			if (userVO.getPhotoImageUrl() != null) {
				completionStatus = completionStatus
				        + CommonConstants.PROFILE_STATUS_WEIGHTAGE;
			}
			if (userVO.getMobileAlertsPreference() != null) {
				completionStatus = completionStatus
				        + CommonConstants.PROFILE_STATUS_WEIGHTAGE;
			}
			customerDetail.setProfileCompletionStatus((int) Math
			        .ceil(completionStatus));
		}
		Integer customerDetailVOObj = userProfileDao
		        .updateCustomerDetails(customerDetail);
		return customerDetailVOObj;
	}

	@Override
	@Transactional
	public Integer updatePhotoURL(String s3ImagePath, Integer userid) {
		Integer number = userProfileDao.updatePhotoURL(s3ImagePath, userid);
		return number;
	}

	@Override
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
	@Transactional(readOnly = true)
	public List<UserVO> getUsersList() {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		userVOList = buildUserVOList(userProfileDao.getUsersList());
		return userVOList;

	}

	@Override
	@Transactional
	public boolean changeUserPassword(UpdatePasswordVO updatePasswordVO)
	        throws InputValidationException {
		try {
			if (updatePasswordVO.getNewPassword() == null
			        || updatePasswordVO.getNewPassword().equals("")) {
				throw new InputValidationException(new GenericErrorCode(
				        ServiceCodes.USER_PROFILE_SERVICE.getServiceID(),
				        ErrorConstants.NULL_PASSWORD),
				        ErrorConstants.NULL_PASSWORD);
			}
			return userProfileDao.changeUserPassword(updatePasswordVO);
		} catch (HibernateException hibernateException) {
			throw new FatalException("Error in updating the password",
			        hibernateException);
		} catch (DatabaseException databaseException) {
			throw new FatalException("Error in updating the password",
			        databaseException);
		}
	}

	@Override
	@Transactional
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
	@Transactional
	public Integer completeCustomerDetails(UserVO userVO) {

		CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();

		CustomerDetail customerDetail = CustomerDetail
		        .convertFromVOToEntity(customerDetailVO);

		Integer rowCount = userProfileDao
		        .completeCustomerDetails(customerDetail);
		return rowCount;
	}

	@Override
	@Transactional
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
	@Transactional
	public Integer managerUpdateUCustomerDetails(UserVO userVO) {

		CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();
		CustomerDetail customerDetail = CustomerDetail
		        .convertFromVOToEntity(customerDetailVO);

		Integer rowCount = userProfileDao
		        .managerUpdateUCustomerDetails(customerDetail);
		return rowCount;
	}

	@Override
	@Transactional(readOnly = true)
	public UserVO loadInternalUser(Integer userID) {
		User user = userProfileDao.findInternalUser(userID);
		return User.convertFromEntityToVO(user);
	}

	@Override
	@Transactional
	public void disableUser(int userId) throws NoRecordsFetchedException {

		User user = userProfileDao.findByUserId(userId);
		if (user == null) {
			throw new NoRecordsFetchedException(
			        "User not found in the user table");
		}

		if (user.getInternalUserDetail() != null) {
			user.getInternalUserDetail().setActiveInternal(
			        ActiveInternalEnum.INACTIVE);
			userProfileDao.update(user.getInternalUserDetail());
		}
	}

	@Override
	@Transactional
	public void enableUser(int userId) throws NoRecordsFetchedException {

		User user = userProfileDao.findByUserId(userId);
		if (user == null) {
			throw new NoRecordsFetchedException(
			        "User not found in the user table");
		}

		if (user.getInternalUserDetail() != null) {
			user.getInternalUserDetail().setActiveInternal(
			        ActiveInternalEnum.ACTIVE);
			userProfileDao.update(user.getInternalUserDetail());
		}
	}

	private String generateRandomPassword() {
		return new BigInteger(CommonConstants.RANDOM_PASSWORD_LENGTH * 5,
		        randomGenerator).toString(32);
	}

	private void sendNewUserEmail(User user) throws InvalidInputException,
	        UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		substitutions.put("-username-", new String[] { user.getEmailId() });
		String uniqueURL = baseUrl + "reset.do?reference="
		        + user.getEmailEncryptionToken();

		substitutions.put("-baseUrl-", new String[] { baseUrl });
		substitutions.put("-passwordurl-", new String[] { uniqueURL });

		recipientVO.setEmailID(user.getEmailId());
		emailEntity.setRecipients(new ArrayList<EmailRecipientVO>(Arrays
		        .asList(recipientVO)));
		emailEntity.setSenderEmailId(CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("You have been subscribed to Nexera");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());

		sendGridEmailService.sendMail(emailEntity);
	}

	@Override
	@Transactional
	public UserVO createNewUserAndSendMail(UserVO userVO)
	        throws InvalidInputException, UndeliveredEmailException {
		LOG.info("createNewUserAndSendMail called!");
		LOG.debug("Parsing the VO");

		User newUser = User.convertFromVOToEntity(userVO);

		String encryptedMailId = nexeraUtility.encryptEmailAddress(newUser
		        .getEmailId());
		newUser.setTokenGeneratedTime(new Timestamp(System.currentTimeMillis()));
		newUser.setEmailEncryptionToken(encryptedMailId);
		if (newUser.getCustomerDetail() != null) {
			newUser.getCustomerDetail().setProfileCompletionStatus(
			        (int) Math.ceil(CommonConstants.PROFILE_STATUS_WEIGHTAGE));
		}
		if (newUser.getUserRole() != null && newUser.getRealtorDetail() == null) {
			if (newUser.getUserRole().getId() == UserRolesEnum.REALTOR
			        .getRoleId()) {
				newUser.setRealtorDetail(new RealtorDetail());
			}

		}

		LOG.debug("Done parsing, Setting a new random password");
		newUser.setPassword(generateRandomPassword());
		newUser.setStatus(1);
		if (newUser.getInternalUserDetail() != null) {
			if (newUser.getInternalUserDetail().getInternaUserRoleMaster()
			        .getId() == 1)
				newUser.getInternalUserDetail().setManager(newUser);
			newUser.getInternalUserDetail().setActiveInternal(
			        ActiveInternalEnum.ACTIVE);
		}
		LOG.debug("Saving the user to the database");
		Integer userID = null;
		try {
			userID = userProfileDao.saveUserWithDetails(newUser);
		} catch (DatabaseException de) {
			LOG.error("database exception");
			throw new DatabaseException("Email Already present in database");
		}

		LOG.debug("Saved, sending the email");
		try {
			sendNewUserEmail(newUser);
		} catch (InvalidInputException | UndeliveredEmailException e) {
			// TODO: Need to handle this and try a resend, since password will
			// not be stored
			LOG.error("Error sending email, proceeding with the email flow");
		}

		userVO.setPassword(newUser.getPassword());
		// reset this value so that two objects are not created
		userVO.setCustomerDetail(null);
		userVO.setId(userID);
		return userVO;

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
			user.getInternalUserDetail().setActiveInternal(
			        ActiveInternalEnum.DELETED);

			userProfileDao.updateInternalUserDetail(user);

		} else {
			throw new InputValidationException(new GenericErrorCode(
			        ServiceCodes.USER_PROFILE_SERVICE.getServiceID(),
			        ErrorConstants.LOAN_MANAGER_DELETE_ERROR),
			        ErrorConstants.LOAN_MANAGER_DELETE_ERROR);
		}

	}

	@Override
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
	@Transactional
	public UserVO saveUser(UserVO userVO) {

		// 1st create a entry in the customer details table
		// use that id to put in the user table .

		Integer userID = userProfileDao.saveCustomerDetails(User
		        .convertFromVOToEntity(userVO));
		User user = null;
		if (userID != null && userID > 0)
			user = userProfileDao.findInternalUser(userID);

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

	@Override
	@Transactional
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
		        && !csvRow[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.LM
		                .toString())
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

		if (csvRow[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.LM
		        .toString())) {
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
		if (rowData[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.LM
		        .toString())) {

			userRoleVO.setId(UserRolesEnum.INTERNAL.getRoleId());
			userRoleVO.setRoleCd(UserRolesEnum.INTERNAL.toString());
			userVO.setUserRole(userRoleVO);

			InternalUserDetailVO internalUserDetailVO = new InternalUserDetailVO();
			internalUserDetailVO
			        .setInternalUserRoleMasterVO(new InternalUserRoleMasterVO());
			internalUserDetailVO.getInternalUserRoleMasterVO().setId(
			        UserRolesEnum.LM.getRoleId());
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
			internalUserStateMapping.setStateLookup(stateLookupDao.findStateLookupByStateCode(stateCode));
			internalUserStateMapping.setUser(user);
			internalUserStateMappingDao.save(internalUserStateMapping);
		}

	}

	@Override
	@Transactional
	public JsonObject parseCsvAndAddUsers(MultipartFile file)
	        throws IOException, InvalidInputException,
	        UndeliveredEmailException, NoRecordsFetchedException {

		Reader reader = new InputStreamReader(file.getInputStream());
		CSVReader csvReader = new CSVReader(reader, ',',
		        CSVParser.DEFAULT_QUOTE_CHARACTER, 1);
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
		if(errorList.size()==0){
			errors.addProperty("success", "CSV was uploaded successfully");
		}else{
			errors.add("errors", errorList);	
		}
		
		return errors;
	}

	@Override
	@Transactional
	public UserVO registerCustomer(LoanAppFormVO loaAppFormVO)
	        throws FatalException {

		try {
			// CustomerEnagagement customerEnagagement =
			// userVO.getCustomerEnagagement();
			UserVO userVO = loaAppFormVO.getUser();

			userVO.setUsername(userVO.getEmailId().split(":")[0]);
			userVO.setEmailId(userVO.getEmailId().split(":")[0]);
			userVO.setUserRole(new UserRoleVO(UserRolesEnum.CUSTOMER));
			userVO.setCustomerDetail(new CustomerDetailVO());
			// String password = userVO.getPassword();
			// UserVO userVOObj= userProfileService.saveUser(userVO);
			UserVO userVOObj = null;
			LoanVO loanVO = null;

			LOG.info("calling createNewUserAndSendMail" + userVO.getEmailId());
			userVOObj = this.createNewUserAndSendMail(userVO);
			LOG.info("Email send successful"+userVO.getEmailId());
			// insert a record in the loan table also
			loanVO = new LoanVO();

			loanVO.setUser(userVOObj);
			loanVO.setLmEmail(loaAppFormVO.getLoanMangerEmail());
			loanVO.setRealtorEmail(loaAppFormVO.getRealtorEmail());

			loanVO.setCreatedDate(new Date(System.currentTimeMillis()));
			loanVO.setModifiedDate(new Date(System.currentTimeMillis()));

			// Currently hardcoding to refinance, this has to come from UI
			// TODO: Add LoanTypeMaster dynamically based on option selected
			LoanTypeMasterVO loanTypeMasterVO = null;
			if (loaAppFormVO.getLoanType() != null) {
				if (loaAppFormVO.getLoanType().getLoanTypeCd().equalsIgnoreCase("REF")) {
					LOG.info("REF Path"+loaAppFormVO.getLoanType());
					loanTypeMasterVO = new LoanTypeMasterVO(LoanTypeMasterEnum.REF);
					loanTypeMasterVO.setDescription("Refinance");
					loanTypeMasterVO.setLoanTypeCd("REF");
					loanVO.setLoanType(loanTypeMasterVO);
				} else {
					
					loanTypeMasterVO = new LoanTypeMasterVO(LoanTypeMasterEnum.PUR);
					loanTypeMasterVO.setDescription("Purchase");
					loanTypeMasterVO.setLoanTypeCd("PUR");
					loanVO.setLoanType(loanTypeMasterVO);
				}
			} else {
				LOG.info("loan type is NONE");
				loanTypeMasterVO = new LoanTypeMasterVO(LoanTypeMasterEnum.NONE);
				loanVO.setLoanType(loanTypeMasterVO);
			}


			loanVO.setLoanType(loanTypeMasterVO);

			if (loaAppFormVO.getPropertyTypeMaster() != null) {
				loanVO.setUserZipCode(loaAppFormVO.getPropertyTypeMaster()
				        .getHomeZipCode());
			}

			LOG.info("Creatign loan");
			loanVO = loanService.createLoan(loanVO);
			LOG.info("Done  loan creation"+loanVO.getId());
			workflowCoreService.createWorkflow(new WorkflowVO(loanVO.getId()));
			LOG.info("Done  loan creation");
			userVOObj.setDefaultLoanId(loanVO.getId());
			// create a record in the loanAppForm table

			LoanAppFormVO loanAppFormVO = new LoanAppFormVO();

			loanAppFormVO.setUser(userVOObj);
			loanAppFormVO.setLoan(loanVO);
			loanAppFormVO.setLoanAppFormCompletionStatus(new Float(0.0f));

			PropertyTypeMasterVO propertyTypeMasterVO = new PropertyTypeMasterVO();

			if (loaAppFormVO.getPropertyTypeMaster() != null) {
				propertyTypeMasterVO.setHomeZipCode(loaAppFormVO
				        .getPropertyTypeMaster().getHomeZipCode());
				propertyTypeMasterVO.setHomeWorthToday(loaAppFormVO
				        .getPropertyTypeMaster().getHomeWorthToday());
				propertyTypeMasterVO.setPropertyInsuranceCost(loaAppFormVO
				        .getPropertyTypeMaster().getPropertyInsuranceCost());
				propertyTypeMasterVO.setPropertyTaxesPaid(loaAppFormVO
				        .getPropertyTypeMaster().getPropertyTaxesPaid());
				propertyTypeMasterVO.setPropTaxMonthlyOryearly(loaAppFormVO
				        .getPropertyTypeMaster().getPropTaxMonthlyOryearly());
				propertyTypeMasterVO.setPropInsMonthlyOryearly(loaAppFormVO
				        .getPropertyTypeMaster().getPropInsMonthlyOryearly());
				propertyTypeMasterVO.setPropertyTypeCd(loaAppFormVO
				        .getPropertyTypeMaster().getPropertyTypeCd());
				propertyTypeMasterVO.setResidenceTypeCd(loaAppFormVO
				        .getPropertyTypeMaster().getResidenceTypeCd());

			}

			loanAppFormVO.setPropertyTypeMaster(propertyTypeMasterVO);

			RefinanceVO refinanceVO = new RefinanceVO();
			if (loaAppFormVO.getRefinancedetails() != null) {
				LOG.info("Creating refinance ");
				refinanceVO.setRefinanceOption(loaAppFormVO
				        .getRefinancedetails().getRefinanceOption());
				refinanceVO.setMortgageyearsleft(loaAppFormVO
				        .getRefinancedetails().getMortgageyearsleft());
				refinanceVO.setCashTakeOut(loaAppFormVO.getRefinancedetails()
				        .getCashTakeOut());
				refinanceVO.setCurrentMortgageBalance(loaAppFormVO
				        .getRefinancedetails().getCurrentMortgageBalance());
				refinanceVO.setCurrentMortgagePayment(loaAppFormVO
				        .getRefinancedetails().getCurrentMortgagePayment());
				refinanceVO.setIncludeTaxes(loaAppFormVO.getRefinancedetails()
				        .isIncludeTaxes());
			}

			loanAppFormVO.setRefinancedetails(refinanceVO);

			PurchaseDetailsVO purchaseDetailsVO = new PurchaseDetailsVO();
			if (loaAppFormVO.getPurchaseDetails() != null) {
				LOG.info("Creating getPurchaseDetails ");
				purchaseDetailsVO.setLivingSituation(loaAppFormVO
				        .getPurchaseDetails().getLivingSituation());
				purchaseDetailsVO.setHousePrice(loaAppFormVO
				        .getPurchaseDetails().getHousePrice());
				purchaseDetailsVO.setLoanAmount(loaAppFormVO
				        .getPurchaseDetails().getLoanAmount());
				purchaseDetailsVO.setTaxAndInsuranceInLoanAmt(loaAppFormVO
				        .getPurchaseDetails().isTaxAndInsuranceInLoanAmt());
				purchaseDetailsVO.setEstimatedPrice(loaAppFormVO
				        .getPurchaseDetails().getEstimatedPrice());
				purchaseDetailsVO.setBuyhomeZipPri(loaAppFormVO
				        .getPurchaseDetails().getBuyhomeZipPri());
			}

			loanAppFormVO.setPurchaseDetails(purchaseDetailsVO);

			loanAppFormVO.setLoanType(loaAppFormVO.getLoanType());
			loanAppFormVO.setMonthlyRent(loaAppFormVO.getMonthlyRent());

			// if(customerEnagagement.getLoanType().equalsIgnoreCase("REF")){
			// loanAppFormVO.setLoanType(new
			// LoanTypeMasterVO(LoanTypeMasterEnum.REF));
			// }
			LOG.info("loanAppFormService.create(loanAppFormVO)");
			loanAppFormService.create(loanAppFormVO);

			LOG.info("Created Loan app form  ");
			return userVOObj;
		} catch (Exception e) {
			LOG.error("User registration failed. Generating an alert"
			        + loaAppFormVO);
			throw new FatalException("Error in User registration", e);
		}
	}

	@Override
	public void crateWorkflowItems(int defaultLoanId) throws Exception {
		// TODO Auto-generated method stub
		workflowCoreService.createWorkflow(new WorkflowVO(defaultLoanId));
	}

	@Override
	public void updateCustomerScore(CustomerDetail customerDetail) {
		userProfileDao.updateCustomerScore(customerDetail);

	}

	@Override
	public void updateCustomerSpouseScore(
	        CustomerSpouseDetail customerSpouseDetail) {
		userProfileDao.updateCustomerSpouseScore(customerSpouseDetail);

	}

	@Override
	@Transactional
	// @CacheEvict(cacheManager = "ehCacheManager", allEntries = true)
	// @CacheEvict(allEntries = true)
	public Integer updateLQBUsercred(UserVO userVO) throws Exception {

		User user = User.convertFromVOToEntity(userVO);
		return userProfileDao.updateLqbProfile(user);

	}

	@Override
	public CustomerDetail getCustomerDetail(int id) {
		// TODO Auto-generated method stub
		User user = (User) userProfileDao.load(User.class, id);
		return user.getCustomerDetail();
	}

	@Override
	@Transactional
	public void resetPassword(User user) throws InvalidInputException,
	        UndeliveredEmailException {
		LOG.info("function to generate random password and save");
		user.setEmailEncryptionToken(nexeraUtility.encryptEmailAddress(user
		        .getEmailId()));

		user.setTokenGeneratedTime(utils.convertCurrentDateToUtc());
		updateTokenDetails(user);
		UserVO userVO = User.convertFromEntityToVO(user);
		LOG.info("Sending reset password to the user");
		sendResetLinkToUser(userVO);
	}

	private void sendResetLinkToUser(UserVO user) throws InvalidInputException,
	        UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_FORGOT_YOUR_PASSWORD);

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		substitutions.put("-username-", new String[] { user.getEmailId() });
		String uniqueURL = baseUrl + "reset.do?reference="
		        + user.getEmailEncryptionToken();
		substitutions.put("-passwordurl-", new String[] { uniqueURL });
		recipientVO.setEmailID(user.getEmailId());
		emailEntity.setRecipients(new ArrayList<EmailRecipientVO>(Arrays
		        .asList(recipientVO)));
		emailEntity.setSenderEmailId(CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Please reset your password");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		sendGridEmailService.sendMail(emailEntity);
	}

	@Override
	public void addDefaultLM(UserVO userVO) {
		// Get loan manager Id
		;
		userProfileDao.updateLMID(userVO.getId(), utils.getLoggedInUser()
		        .getId());
	}

	@Override
	// @Cacheable(value = "lqbAuthToken")
	public String getLQBUrl(Integer userId, Integer loanId) {

		LOG.info("user id of this user is : " + userId);
		// By default the URL will be the landing page of LQB, if the user has
		// provided the details, it will take the user to the loan specific page
		String url = lqbDefaultUrl;
		try {
			UserVO userVO = findUser(userId);
			if (userVO != null) {
				String lqbUsername = userVO.getInternalUserDetail()
				        .getLqbUsername().replaceAll("[^\\x00-\\x7F]", "");
				String lqbPassword = userVO.getInternalUserDetail()
				        .getLqbPassword().replaceAll("[^\\x00-\\x7F]", "");
				if (lqbUsername != null && lqbPassword != null) {
					JSONObject authOperationObject = createAuthObject(
					        WebServiceOperations.OP_NAME_AUTH_GET_USER_AUTH_TICET,
					        lqbUsername, lqbPassword);
					LOG.debug("Invoking LQB service to fetch user authentication ticket ");
					String authTicketJson = lqbInvoker
					        .invokeRestSpringParseObjForAuth(authOperationObject
					                .toString());
					if (!authTicketJson.contains("Access Denied")) {
						String sTicket = authTicketJson;
						LoanVO loanVO = getLoanById(loanId);
						if (loanVO != null) {
							url = getUrlByTicket(sTicket, loanVO.getLqbFileId());
							LOG.debug("Url received for this user/loan " + url);

						} else {
							LOG.error("Loan Id Entered is invalid ");
						}
					} else {
						LOG.error("Ticket Not Generated For This User ");
					}
				} else {
					LOG.error("LQBUsername or Password are not valid ");
				}
			} else {
				LOG.error("User not found with this user id");

			}

		} catch (InputValidationException e) {
			LOG.error("error and message is : " + e.getMessage());

		} catch (Exception e) {
			LOG.error("error and message is : " + e.getMessage());
		}
		return url == null ? lqbDefaultUrl : url;

	}

	private LoanVO getLoanById(int loanId) {
		LOG.debug("Inside method getLoanById ");
		return loanService.getLoanByID(loanId);
	}

	public String getUrlByTicket(String sTicket, String lqbLoanNumber) {
		String url = null;
		LOG.debug("Invoking LQB service to fetch URL for this user ");
		JSONObject appViewOperationObject = createUrlObject(
		        WebServiceOperations.OP_NAME_APP_VIEW_GET_VIEW_LOAN_URL,
		        sTicket, lqbLoanNumber);
		JSONObject urlJSON = lqbInvoker
		        .invokeRestSpringParseObjForAppView(appViewOperationObject
		                .toString());
		if (!urlJSON.isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {

			url = urlJSON
			        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
			url = "https://secure.lendersoffice.com" + url;
		} else {
			LOG.error("Unable to fetch the url ");
		}
		return url;
	}

	public JSONObject createUrlObject(String opName, String sTicket,
	        String sLoanName) {
		JSONObject json = new JSONObject();
		try {
			json.put(WebServiceMethodParameters.PARAMETER_S_TICKET, sTicket);
			json.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NAME,
			        sLoanName);
			json.put("opName", opName);
		} catch (JSONException e) {
			LOG.error("Invalid Json String ");
			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
	}

	public JSONObject createAuthObject(String opName, String userName,
	        String password) {
		JSONObject json = new JSONObject();
		try {
			json.put(WebServiceMethodParameters.PARAMETER_USERNAME, userName);
			json.put(WebServiceMethodParameters.PARAMETER_PASSWORD, password);
			json.put("opName", opName);
		} catch (JSONException e) {
			LOG.error("Invalid Json String ");
			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
	}

	@Override
	public List<String> getDefaultUsers(String userName) {
		return userProfileDao.getDefaultUsers(userName);
	}

	@Override
	public User findUserByToken(String userToken) {

		return userProfileDao.findByToken(userToken);
	}

	@Override
	public User validateRegistrationLink(String userToken, int clientRawOffset)
	        throws InvalidInputException {
		User userDetail = findUserByToken(userToken);
		if (userDetail == null) {
			throw new InvalidInputException("Invalid URL");
		}
		if (utils.hasLinkExpired(userDetail.getTokenGeneratedTime(),
		        clientRawOffset)) {
			throw new InvalidInputException(
			        "Token Expired - Please use the Reset password link to generate again");
		}
		return userDetail;
	}

	@Override
	@Transactional
	public void updateTokenDetails(User user) {
		userProfileDao.updateTokenDetails(user);
		return;
	}

	@Override
	@Transactional(readOnly = true)
	public UserVO findByUserName(String userName) throws DatabaseException,
	        NoRecordsFetchedException {
		// TODO Auto-generated method stub
		return User.convertFromEntityToVO(userProfileDao
		        .getUserByUserName(userName));
	}

	@Override
	@Transactional
    public InternalUserStateMappingVO updateInternalUserStateMapping(InternalUserStateMappingVO inputVo) {
	    
	    return InternalUserStateMapping.convertFromEntityToVO(userProfileDao.updateInternalUserStateMapping(inputVo));
    }

	@Override
    public InternalUserStateMappingVO deleteInternalUserStateMapping(
            InternalUserStateMappingVO inputVo) {
	    
		return InternalUserStateMapping.convertFromEntityToVO(userProfileDao.deleteInternalUserStateMapping(inputVo));
    }
}