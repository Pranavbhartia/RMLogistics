package com.nexera.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.NewFieldTypeMunger;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.MessageUtils;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.dao.InternalUserStateMappingDao;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.StateLookupDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.InternalUserDetail;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.RealtorDetail;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.ActiveInternalEnum;
import com.nexera.common.enums.DisplayMessageType;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.LoanTypeMasterEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
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
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;
import com.nexera.common.vo.InternalUserStateMappingVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.PropertyTypeMasterVO;
import com.nexera.common.vo.PurchaseDetailsVO;
import com.nexera.common.vo.RealtorDetailVO;
import com.nexera.common.vo.RefinanceVO;
import com.nexera.common.vo.UpdatePasswordVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.InternalUserStateMappingService;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.LqbInterface;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.WorkflowCoreService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.workflow.vo.WorkflowVO;

@Component("userProfileServiceImpl")
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
	private SendEmailService sendEmailService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private MessageUtils messageUtils;

	private SecureRandom randomGenerator;

	@Autowired
	NexeraUtility nexeraUtility;

	@Autowired
	private LoanService loanService;

	@Autowired
	private MessageServiceHelper messageServiceHelper;

	@Autowired
	WorkflowCoreService workflowCoreService;

	@Autowired
	private Utils utils;

	@Autowired
	protected LoanAppFormService loanAppFormService;

	@Autowired
	private InternalUserStateMappingService internalUserStateMappingService;

	@Autowired
	private ApplicationContext applicationContext;
	


	@Value("${lqb.defaulturl}")
	private String lqbDefaultUrl;

	@Value("${cryptic.key}")
	private String crypticKey;

	@Value("${profile.url}")
	private String baseUrl;
	
	@Value("${recepients}")
	private String recepientEmailID;
	
	private static final Logger LOG = LoggerFactory
	        .getLogger(UserProfileServiceImpl.class);

	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	@Override
	@Transactional(readOnly = true)
	public UserVO findUser(Integer userid) {

		User user = userProfileDao.findByUserId(userid);
		UserVO userListVO = User.convertFromEntityToVO(user);

		if (null != userListVO.getInternalUserDetail()) {
			try {
				userListVO.getInternalUserDetail().setLqbUsername(
				        nexeraUtility.decrypt(salt, crypticKey, userListVO
				                .getInternalUserDetail().getLqbUsername()));
				userListVO.getInternalUserDetail().setLqbPassword(
				        nexeraUtility.decrypt(salt, crypticKey, userListVO
				                .getInternalUserDetail().getLqbPassword()));
			} catch (InvalidKeyException | NoSuchAlgorithmException
			        | InvalidKeySpecException | NoSuchPaddingException
			        | InvalidAlgorithmParameterException
			        | IllegalBlockSizeException | BadPaddingException
			        | IOException e) {

				LOG.error("Exception in FindUser for: " + userid, e);
			}
		}
		return userListVO;
	}

	@Override
	@Transactional
	public Integer updateUserStatus(UserVO userVO) {
		Integer result = userProfileDao.updateUserStatus(User
		        .convertFromVOToEntity(userVO));
		return result;
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

		/*
		 * if(userVO.getInternalUserStateMappingVOs()!=null){
		 * internalUserStateMappingService
		 * .saveOrUpdateUserStates(userVO.getInternalUserStateMappingVOs()); }
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
			userProfileDao.updateLoginTime(
			        new Date(System.currentTimeMillis()),
			        updatePasswordVO.getUserId());
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
	public void verifyEmail(int userId) {
		userProfileDao.verifyEmail(userId);
		LoanVO loan = loanService.getActiveLoanOfUser(new UserVO(userId));
		//NEXNF-628
		/*if (loan != null && loan.getId() != 0) {
			dismissAlert(MilestoneNotificationTypes.VERIFY_EMAIL_ALERT,
			        loan.getId(), null);
			// Dismiss Alert
		}*/
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

	@Override
	public void createAlert(
	        MilestoneNotificationTypes mileStoneNotificationType, int loanId,
	        String notificationContent) {
		MilestoneNotificationTypes notificationType = mileStoneNotificationType;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, notificationContent);

		LOG.debug("Inside method createAlertOfType");
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(createReminderVo.getLoanId(),
		                createReminderVo.getNotificationType()
		                        .getNotificationTypeName(), true);
		if (notificationList.size() == 0) {
			LOG.debug("Creating new notification "
			        + createReminderVo.getNotificationType());
			NotificationVO notificationVO = new NotificationVO(
			        createReminderVo.getLoanId(), createReminderVo
			                .getNotificationType().getNotificationTypeName(),
			        createReminderVo.getNotificationReminderContent());
			LOG.debug("This notification of for loanmanager");
			notificationVO.setCreatedForID(createReminderVo.getUserID());
			notificationVO.setTimeOffset(new Date().getTimezoneOffset());
			notificationService.createNotification(notificationVO);

		}
	}

	private void sendNewUserEmail(User user) throws InvalidInputException,
	        UndeliveredEmailException {
		String subject = "Welcome to newfi";
		EmailVO emailEntity = new EmailVO();

		Template template = null;
		if (user.getUserRole() != null
		        && user.getUserRole().getId() == UserRolesEnum.REALTOR
		                .getRoleId()) {
			template = templateService
			        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI_REALTOR);
			subject = "Invitation to the newfi team";
		} else {
			template = templateService
			        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI);
		}
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		substitutions.put("-username-", new String[] { user.getEmailId() });

		String uniqueURL = baseUrl + "reset.do?reference="
		        + user.getEmailEncryptionToken()
		        + "&verifyEmailPath=verifyEmail";

		substitutions.put("-baseUrl-", new String[] { baseUrl });
		substitutions.put("-passwordurl-", new String[] { uniqueURL });

		emailEntity.setSenderEmailId(user.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(subject);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();
		ccList.add(user.getUsername() + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);
		sendEmailService.sendUnverifiedEmailToCustomer(emailEntity, user,
		        template);

	}

	@Override
	public void sendContactAlert(UserVO user) throws InvalidInputException,
	        UndeliveredEmailException {
		String subject = "NEWFI ALERT: New Lead With No Products";
		EmailVO emailEntity = new EmailVO();

		Template template = null;

		template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NEW_LEAD_NO_PRODUCTS);
		String[] tokens = user.getEmailId().split(":");
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		if (tokens != null) {
			substitutions.put("-email-", new String[] { tokens[0] });
		}
		emailEntity.setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(subject);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
/*		EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
		emailRecipientVO.setEmailID("info@newfi.com");
		recipients.add(emailRecipientVO);*/
		EmailRecipientVO emailRecipientVO1 = new EmailRecipientVO();
		//emailRecipientVO1.setEmailID("pat@newfi.com");//to take from property file
		List<String> receipientList = new ArrayList<String>();
		receipientList = getRecepientsList(recepientEmailID);
		for(String recepient:receipientList){
			emailRecipientVO1 = new EmailRecipientVO();
			emailRecipientVO1.setEmailID(recepient);	
			recipients.add(emailRecipientVO1);
		}		
		
		//recipients.add(emailRecipientVO1);
		//emailEntity.setRecipients(recipients);
		emailEntity.setBody("---");
		sendEmailService.sendMail(emailEntity, true);

	}

	private List<String> getRecepientsList(String recepientEmailID2) {
		List<String> unprotectedUrls = new ArrayList<String>();
		if(recepientEmailID2.length() > 1){
			String[] unprotectedUrlsArray = recepientEmailID2.split(",");

		
			for (String url : unprotectedUrlsArray) {
				unprotectedUrls.add(url);
			}
			
		}else {
			unprotectedUrls.add(recepientEmailID2);
		}
		return unprotectedUrls;
	    
    }

	private void sendNewUserAlertEmail(User user, Integer loanID)
	        throws InvalidInputException, UndeliveredEmailException {
		String subject = CommonConstants.SUBJECT_NEW_LOAN_ALERT;
		EmailVO emailEntity = new EmailVO();

		Template template = null;

		template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NEW_CUSTOMER_ALERT);

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-custname-", new String[] { user.getFirstName()
		        + " " + user.getLastName() });
		substitutions.put("-url-", new String[] { baseUrl });
		emailEntity.setSenderEmailId(user.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(subject);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();
		ccList.add(user.getUsername() + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);
		sendEmailService.sendEmailForInternalUsersAndSM(emailEntity, loanID,
		        template);
	}

	private void sendEmailWithQuotes(UserVO user,
	        List<LqbTeaserRateVo> teaseRateDataList)
	        throws InvalidInputException, UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_DRIP_RATE_ALERTS);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();

		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		substitutions.put("-username-", new String[] { user.getEmailId() });

		substitutions.put("-url-", new String[] { baseUrl });

		substitutions.put("-lowestPeriodYear-",
		        new String[] { teaseRateDataList.get(0).getYearData() });
		substitutions.put("-lowestRate-",
		        new String[] { teaseRateDataList.get(0).getTeaserRate() });
		substitutions.put("-lowestApr-", new String[] { teaseRateDataList
		        .get(0).getAPR() });

		substitutions.put("-periodYear-",
		        new String[] { teaseRateDataList.get(1).getYearData() });
		substitutions.put("-rate-", new String[] { teaseRateDataList.get(1)
		        .getTeaserRate() });
		substitutions.put("-apr-", new String[] { teaseRateDataList.get(1)
		        .getAPR() });

		emailEntity.setSenderEmailId(user.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("You have been subscribed to Nexera");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();
		ccList.add(user.getUsername() + CommonConstants.SENDER_EMAIL_ID);

		emailEntity.setCCList(ccList);
		sendEmailService.sendUnverifiedEmailToCustomer(emailEntity, user,
		        template);
	}

	@Override
	@Transactional
	public User createNewUser(UserVO userVO) throws InvalidInputException,
	        UndeliveredEmailException, FatalException {
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
		userVO.setPassword(newUser.getPassword());
		userVO.setUsername(newUser.getUsername());
		// reset this value so that two objects are not created
		userVO.setCustomerDetail(null);
		userVO.setId(userID);
		userVO.setEmailId(newUser.getEmailId());
		userVO.setEmailEncryptionToken(newUser.getEmailEncryptionToken());
		return newUser;

	}

	@Override
	public void sendEmailToCustomer(User newUser) {
		LOG.debug("Saved, sending the email");
		try {
			sendNewUserEmail(newUser);

		} catch (InvalidInputException | UndeliveredEmailException e) {
			// TODO: Need to handle this and try a resend, since password will
			// not be stored
			LOG.error("Error sending email, proceeding with the email flow");
		}
		LOG.debug("sendNewUserEmail : sending the email done");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		randomGenerator = new SecureRandom();
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public boolean deleteUser(UserVO userVO) throws Exception {

		User user = User.convertFromVOToEntity(userVO);

		boolean canUserBeDeleted = loanDao.checkLoanDependency(user);
		if (canUserBeDeleted) {
			user.getInternalUserDetail().setActiveInternal(
			        ActiveInternalEnum.DELETED);

			userProfileDao.updateInternalUserDetail(user);

		} else {
			String userType = userVO.getInternalUserDetail().getInternalUserRoleMasterVO().getRoleDescription();
			
			throw new InputValidationException(new GenericErrorCode(
			        ServiceCodes.USER_PROFILE_SERVICE.getServiceID(),
			        ErrorConstants.LOAN_MANAGER_DELETE_ERROR+userType+" has active loans"),
			        ErrorConstants.LOAN_MANAGER_DELETE_ERROR+userType+" has active loans");
		}
		return canUserBeDeleted;
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

	private boolean checkStateCode(String stateCodewithLicence) {
		boolean status = false;

		try {
			String stateCode = stateCodewithLicence.split(":")[0];
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

		/*
		 * if (csvRow[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.REALTOR
		 * .toString())) { if (csvRow[CommonConstants.LICENSE_INFO_COLUMN] ==
		 * null || csvRow[CommonConstants.LICENSE_INFO_COLUMN].isEmpty()) {
		 * message = messageUtils.getDisplayMessage(
		 * DisplayMessageConstants.INVALID_LICENSE_INFO,
		 * DisplayMessageType.ERROR_MESSAGE).toString(); return message; } if
		 * (csvRow[CommonConstants.PROFILE_LINK_COLUMN] == null ||
		 * csvRow[CommonConstants.PROFILE_LINK_COLUMN].isEmpty()) { message =
		 * messageUtils.getDisplayMessage(
		 * DisplayMessageConstants.INVALID_PROFILE_URL,
		 * DisplayMessageType.ERROR_MESSAGE).toString(); return message; } }
		 */

		if (csvRow[CommonConstants.ROLE_COLUMN].equals(UserRolesEnum.LM
		        .toString())
		        || csvRow[CommonConstants.ROLE_COLUMN]
		                .equals(UserRolesEnum.REALTOR.toString())) {
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
			Date dat = validateDate(rowData[CommonConstants.DATE_OF_BIRTH_COLUMN]);
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			customerDetail.setDateOfBirth(df.format(dat));
			userVO.setCustomerDetail(customerDetail);
		} else {
			userRoleVO.setId(UserRolesEnum.REALTOR.getRoleId());
			userRoleVO.setRoleCd(UserRolesEnum.REALTOR.toString());
			userVO.setUserRole(userRoleVO);

			RealtorDetailVO realtorDetailVO = new RealtorDetailVO();
			/*
			 * realtorDetailVO
			 * .setLicenceInfo(rowData[CommonConstants.LICENSE_INFO_COLUMN]);
			 */

			realtorDetailVO
			        .setProfileUrl(rowData[CommonConstants.PROFILE_LINK_COLUMN]);
			userVO.setRealtorDetail(realtorDetailVO);
		}
		UserVO createdUserVo = userVO;
		User newUser = createNewUser(userVO);
		sendEmailToCustomer(newUser);
		return createdUserVo;
	}

	private void createUserStateMapping(UserVO userVO, String stateCodes)
	        throws NoRecordsFetchedException {

		String[] stateCodesList = stateCodes
		        .split(CommonConstants.STATE_CODE_STRING_SEPARATOR);

		InternalUserStateMapping internalUserStateMapping;
		User user = userProfileDao.findByUserId(userVO.getId());

		for (String stateCodeAndLicenceNo : stateCodesList) {

			String stateCode = stateCodeAndLicenceNo.split(":")[0];
			String licenseNumber = stateCodeAndLicenceNo.split(":")[1];
			internalUserStateMapping = new InternalUserStateMapping();
			internalUserStateMapping.setStateLookup(stateLookupDao
			        .findStateLookupByStateCode(stateCode));
			internalUserStateMapping.setLicenseNumber(licenseNumber);
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
		        CSVParser.DEFAULT_QUOTE_CHARACTER, 3);
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
		if (errorList.size() == 0) {
			errors.addProperty("success", "CSV was uploaded successfully");
		} else {
			errors.add("errors", errorList);
		}

		return errors;
	}

	@Override
	@Transactional
	public UserVO registerCustomer(LoanAppFormVO loaAppFormVO,
	        List<LqbTeaserRateVo> teaseRateDataList) throws FatalException {

		try {
			// CustomerEnagagement customerEnagagement =
			// userVO.getCustomerEnagagement();
			UserVO userVO = loaAppFormVO.getUser();

			userVO.setUsername(userVO.getEmailId().split(":")[0]);
			userVO.setEmailId(userVO.getEmailId().split(":")[0]);
			userVO.setUserRole(new UserRoleVO(UserRolesEnum.CUSTOMER));
			userVO.setCustomerDetail(new CustomerDetailVO());
			userVO.setStatus(1);
			// String password = userVO.getPassword();
			// UserVO userVOObj= userProfileService.saveUser(userVO);
			UserVO userVOObj = null;
			LoanVO loanVO = null;

			LOG.info("calling createNewUserAndSendMail" + userVO.getEmailId());
			userVOObj = userVO;
			User newUser = this.createNewUser(userVO);

			if (null != loaAppFormVO.getEmailQuote()
			        && loaAppFormVO.getEmailQuote()) {

				sendEmailWithQuotes(userVOObj, teaseRateDataList);
			}

			LOG.info("Successfully exceuted createNewUserAndSendMail");

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
				if (loaAppFormVO.getLoanType().getLoanTypeCd()
				        .equalsIgnoreCase("REF")) {
					LOG.info("In refinanace path........................................");
					loanTypeMasterVO = new LoanTypeMasterVO(
					        LoanTypeMasterEnum.REF);
					loanTypeMasterVO.setDescription("Refinance");
					loanTypeMasterVO.setLoanTypeCd("REF");
					loanVO.setLoanType(loanTypeMasterVO);
				} else {
					LOG.info("In purchase path........................................");
					loanTypeMasterVO = new LoanTypeMasterVO(
					        LoanTypeMasterEnum.PUR);
					loanTypeMasterVO.setDescription("Purchase");
					loanTypeMasterVO.setLoanTypeCd("PUR");
					loanVO.setLoanType(loanTypeMasterVO);
				}
			} else {
				LOG.info("loan type is NONE..................................................");
				loanTypeMasterVO = new LoanTypeMasterVO(LoanTypeMasterEnum.NONE);
				loanVO.setLoanType(loanTypeMasterVO);
			}

			// loanVO.setLoanType(loanTypeMasterVO);

			if (loaAppFormVO.getPropertyTypeMaster() != null) {
				loanVO.setUserZipCode(loaAppFormVO.getPropertyTypeMaster()
				        .getHomeZipCode());
			}

			loanVO = loanService.createLoan(loanVO);
			LOG.info("loan is created........................................................ ");

			workflowCoreService.createWorkflow(new WorkflowVO(loanVO.getId()));

			LOG.info("workflowCoreService is excecuted succefully.......................................... ");

			userVOObj.setDefaultLoanId(loanVO.getId());

			LoanAppFormVO loanAppFormVO = new LoanAppFormVO();
			LOG.info("loanapp form object created sec..................................................");
			loanAppFormVO.setUser(userVOObj);
			loanAppFormVO.setLoan(loanVO);
			loanAppFormVO.setLoanType(loanTypeMasterVO);

			loanAppFormVO.setLoanAppFormCompletionStatus(new Float(0.0f));

			PropertyTypeMasterVO propertyTypeMasterVO = new PropertyTypeMasterVO();

			LOG.info("convertion of propertyTypeMasterVO ...........................................");

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

			LOG.info("convertion of refinanceVO after purchase......................................");
			RefinanceVO refinanceVO = new RefinanceVO();
			if (loaAppFormVO.getRefinancedetails() != null) {
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
			LOG.info("after convertion of refinanceVO .............................................. ");
			PurchaseDetailsVO purchaseDetailsVO = new PurchaseDetailsVO();
			if (loaAppFormVO.getPurchaseDetails() != null) {
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

			// loanAppFormVO.setLoanType(loaAppFormVO.getLoanType());
			loanAppFormVO.setMonthlyRent(loaAppFormVO.getMonthlyRent());

			// if(customerEnagagement.getLoanType().equalsIgnoreCase("REF")){
			// loanAppFormVO.setLoanType(new
			// LoanTypeMasterVO(LoanTypeMasterEnum.REF));
			// }
			LOG.info("before calling create.......................................");
			loanAppFormService.create(loanAppFormVO);
			LOG.info("after creating a loanAppFormService.create(loanAppFormVO)");

			LOG.info("Creating Alert ");
			if (loanVO != null && loanVO.getId() != 0) {
				if (userVOObj != null) {
					UserRoleVO userRoleVO = userVOObj.getUserRole();
					if (userRoleVO != null) {
						if (userRoleVO.getId() == UserRolesEnum.CUSTOMER
						        .getRoleId()) {
							createAlert(
							        MilestoneNotificationTypes.WATCH_ALERT_NOTIFICATION_TYPE,
							        loanVO.getId(),
							        WorkflowConstants.WATCH_TUTORIAL_ALERT_NOTIFICATION_CONTENT);
							createAlert(
							        MilestoneNotificationTypes.COMPLETE_APPLICATION_NOTIFICATION_TYPE,
							        loanVO.getId(),
							        WorkflowConstants.COMPLETE_YOUR_APPLICATION_NOTIFICATION_CONTENT);
							//NEXNF-628
							/*createAlert(
							        MilestoneNotificationTypes.VERIFY_EMAIL_ALERT,
							        loanVO.getId(),
							        WorkflowConstants.VERIFY_EMAIL_NOTIFICATION_CONTENT);*/
							if (null != loanVO.getLoanType()
							        && null != loanVO.getLoanType()
							                .getLoanTypeCd()
							        && loanVO.getLoanType().getLoanTypeCd()
							                .equals("PUR"))
								loanService.createAlertForAgent(loanVO.getId());
							// code to send mail for no product found
							boolean noProductFound = false;
							if (null != teaseRateDataList
							        && teaseRateDataList.get(0) == null) {
								noProductFound = true;
								LOG.info("loan type is NONE..................................................");
								loanTypeMasterVO = loanVO.getLoanType();
								if (loanTypeMasterVO.getId() != LoanTypeMasterEnum.NONE
								        .getStatusId()) {
									int loanId = loanVO.getId();
									LOG.info(loanId + "-------------------");
									loanService.sendNoproductsAvailableEmail(
									        userVOObj, loanId);
									messageServiceHelper
									        .generatePrivateMessage(loanId,
									                LoanStatus.ratesLocked,
									                utils.getLoggedInUser(),
									                false);
								}
							}
						}
					}
				}
			}
			LOG.info("User registration complete, iniating workflow"
			        + userVOObj.getUsername());
			this.crateWorkflowItems(userVOObj.getDefaultLoanId());
			sendEmailToCustomer(newUser);
			sendNewUserAlertEmail(newUser, userVOObj.getDefaultLoanId());
			//
			return userVOObj;
		} catch (Exception e) {

			LOG.error("User registration failed. Generating an alert"
			        + loaAppFormVO);
			LOG.error(
			        "error while creating user in shopper registartion  creating user",
			        e);
			e.getCause().printStackTrace();
			throw new FatalException("Error in User registration");

		}
	}

	@Override
	public void dismissAlert(
	        MilestoneNotificationTypes mileStoneNotificationType, int loanId,
	        String notificationContent) {

		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                mileStoneNotificationType.getNotificationTypeName(),
		                true);
		for (NotificationVO notificationVO : notificationList) {
			notificationService.dismissNotification(notificationVO.getId());
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
	public Integer updateLQBUsercred(UserVO userVO)
	        throws InvalidInputException {
		String sTicket = null;
		Integer updateCount = 0;
		User user = User.convertFromVOToEntity(userVO);
		try {
			if (user.getInternalUserDetail() != null) {

				// Chck if any change in cred.
				User existingUserinDB = userProfileDao.findByUserId(userVO
				        .getId());
				if (existingUserinDB.getInternalUserDetail() != null
				        && existingUserinDB.getInternalUserDetail()
				                .getLqbUsername() != null
				        && existingUserinDB.getInternalUserDetail()
				                .getLqbPassword() != null
				        && nexeraUtility.decrypt(
				                salt,
				                crypticKey,
				                existingUserinDB.getInternalUserDetail()
				                        .getLqbUsername()).equalsIgnoreCase(
				                user.getInternalUserDetail().getLqbUsername())
				        && nexeraUtility.decrypt(
				                salt,
				                crypticKey,
				                existingUserinDB.getInternalUserDetail()
				                        .getLqbPassword()).equalsIgnoreCase(
				                user.getInternalUserDetail().getLqbPassword())) {
					throw new InvalidInputException(
					        ErrorConstants.LQB_CRED_ALREADY_SAVED);
				}
				LqbInterface lqbCacheInvoker = (LqbInterface) applicationContext
				        .getBean("lqbCacheInvoker");
				sTicket = lqbCacheInvoker.findSticket(nexeraUtility.encrypt(
				        salt, crypticKey, user.getInternalUserDetail()
				                .getLqbUsername()), nexeraUtility.encrypt(salt,
				        crypticKey, user.getInternalUserDetail()
				                .getLqbPassword()));

				InternalUserDetailVO internalUserDetailVO = userVO
				        .getInternalUserDetail();
				// Only if sTciket is valid
				if (sTicket != null && internalUserDetailVO != null) {

					user.getInternalUserDetail().setLqbAuthToken(sTicket);
					user.getInternalUserDetail().setLqbExpiryTime(
					        System.currentTimeMillis());
					String lqbUsername = user.getInternalUserDetail()
					        .getLqbUsername();
					String lqbPassword = user.getInternalUserDetail()
					        .getLqbPassword();
					if (lqbUsername != null && lqbPassword != null) {
						String encryptedlqbUserName = nexeraUtility.encrypt(
						        salt, crypticKey, lqbUsername);
						user.getInternalUserDetail().setLqbUsername(
						        encryptedlqbUserName);
						String encryptedLqbPassword = nexeraUtility.encrypt(
						        salt, crypticKey, lqbPassword);
						user.getInternalUserDetail().setLqbPassword(
						        encryptedLqbPassword);
						updateCount = userProfileDao.updateLqbProfile(user);
					}
				} else if (sTicket == null) {
					updateCount = 0;
					throw new InvalidInputException(
					        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
				}
			}
		} catch (InvalidKeyException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (NoSuchAlgorithmException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (InvalidKeySpecException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (NoSuchPaddingException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (InvalidAlgorithmParameterException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (UnsupportedEncodingException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (IllegalBlockSizeException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (BadPaddingException e) {
			throw new InvalidInputException(
			        ErrorConstants.LQB_ENCRYPTION_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateCount;

	}

	@Override
	@Transactional
	public Integer updateNMLSId(UserVO userVO) {
		User user = User.convertFromVOToEntity(userVO);
		return userProfileDao.updateNMLS(user);

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

	@Override
	@Transactional
	public void resendRegistrationDetails(User user)
	        throws InvalidInputException, UndeliveredEmailException {
		user.setEmailEncryptionToken(nexeraUtility.encryptEmailAddress(user
		        .getEmailId()));
		user.setTokenGeneratedTime(utils.convertCurrentDateToUtc());
		updateTokenDetails(user);
		LOG.info("Sending reset password to the user");
		sendNewUserEmail(user);
	}

	private void sendResetLinkToUser(UserVO user) throws InvalidInputException,
	        UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_FORGOT_YOUR_PASSWORD);

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() });
		substitutions.put("-username-", new String[] { user.getEmailId() });
		String uniqueURL = baseUrl + "reset.do?reference="
		        + user.getEmailEncryptionToken();
		substitutions.put("-passwordurl-", new String[] { uniqueURL });
		emailEntity.setSenderEmailId(user.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(CommonConstants.SUBJECT_RESET_PASSWORD);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();

		ccList.add(user.getUsername() + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);
		sendEmailService.sendUnverifiedEmailToCustomer(emailEntity, user,
		        template);
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

		/*
		 * Get the token from DB for this user. If token is valid, use it. Else
		 * get it from LQB
		 */
		String url = lqbDefaultUrl;
		try {
			UserVO userVO = findUser(userId);
			if (userVO != null && userVO.getInternalUserDetail() != null) {
				String lqbUsername = userVO.getInternalUserDetail()
				        .getLqbUsername();

				String lqbPassword = userVO.getInternalUserDetail()
				        .getLqbPassword();

				if (lqbUsername != null && lqbPassword != null) {
					lqbUsername = lqbUsername.replaceAll("[^\\x00-\\x7F]", "");
					lqbPassword = lqbPassword.replaceAll("[^\\x00-\\x7F]", "");

					String token = userVO.getInternalUserDetail()
					        .getLqbAuthToken();
					Long expiryTime = userVO.getInternalUserDetail()
					        .getLqbExpiryTime();
					if (token != null && expiryTime != null) {
						// This means that we have a token stored in the system
						// with an expiry time.

						/*
						 * Check if the token has expired. All tokens expire
						 * after 4 hours
						 */
						LOG.info("User has entered the credentials."
						        + lqbUsername);
						if (utils.hasTokenExpired(expiryTime)) {
							// Token has expired, hence generate a new one.
							LOG.info("Token expired for user: " + lqbUsername);
							token = getNewToken(lqbUsername, lqbPassword,
							        userVO.getInternalUserDetail().getId());
						}
						// Now, we have a token, either from DB or from LQB if
						// it had expired
						if (token != null) {
							LOG.info("Token that will be used." + token);
							url = getLQBUrlWithToken(token, loanId);
							return url;
						} else {
							LOG.error("Ticket Not Generated For This User ");
							return lqbDefaultUrl;
						}

					} else {
						// We have valid LQB credentials, but no token in the
						// system.
						LOG.info("No token in system for user: " + lqbUsername);
						token = getNewToken(lqbUsername, lqbPassword, userVO
						        .getInternalUserDetail().getId());
						if (token != null) {
							LOG.info("Token that will be used." + token);
							url = getLQBUrlWithToken(token, loanId);
							return url == null ? lqbDefaultUrl : url;
						} else {
							LOG.error("Ticket Not Generated For This User ");
							return lqbDefaultUrl;
						}

					}
				} else {
					LOG.info("User has not set LQB credentials. Send back defaul loan url for loan: "
					        + loanId);
					return lqbDefaultUrl;
				}
			} else {
				LOG.warn("This cannot happen, since internal user should exist for userId: "
				        + userId);
			}
		} catch (Exception e) {
			LOG.error("Error retrieving loan URL for loan " + loanId, e);
		}
		return lqbDefaultUrl;
	}

	private String getLQBUrlWithToken(String token, int loanId) {
		String sTicket = token;
		LoanVO loanVO = getLoanById(loanId);
		if (loanVO != null) {
			return getUrlByTicket(sTicket, loanVO.getLqbFileId());

		}
		LOG.error("Loan Id Entered is invalid ");

		return null;

	}

	private String getNewToken(String lqbUsername, String lqbPassword,
	        int internalUserId) {
		JSONObject authOperationObject = NexeraUtility.createAuthObject(
		        WebServiceOperations.OP_NAME_AUTH_GET_USER_AUTH_TICET,
		        lqbUsername, lqbPassword);
		LOG.debug("Invoking LQB service to fetch user authentication ticket ");
		String token = lqbInvoker
		        .invokeRestSpringParseObjForAuth(authOperationObject.toString());
		// Got back a token. Save it in DB, if token is valid
		if (token != null && token.contains("EncryptedTicket")) {
			userProfileDao.updateTokenDetails(internalUserId, token,
			        System.currentTimeMillis());
		} else {
			// If token is invalid return null
			return null;
		}

		return token;

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
		
		
		Boolean isLinkExpired=utils.hasLinkExpired(userDetail.getTokenGeneratedTime(),
		        clientRawOffset);
			
		if (isLinkExpired) {
			throw new InvalidInputException(
			        "Token Expired - Please use the Reset password link to generate again");
		}else{
			
			//TODO setting as email verified 
			if(!userDetail.getEmailVerified()){
				LOG.info("The user is verifying his email: set his verified to true");
				verifyEmail(userDetail.getId());
				LOG.info("Also dismiss alert for Verification");
			}
			
			//TODO updating the token as null after email is verified
			UserVO userVOUpdate = new UserVO();
			userVOUpdate.setId(userDetail.getId());
			userVOUpdate.setEmailEncryptionToken(null);
			updateTokenDetails(User
			        .convertFromVOToEntity(userVOUpdate));

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
	public InternalUserStateMappingVO updateInternalUserStateMapping(
	        InternalUserStateMappingVO inputVo) {

		return InternalUserStateMapping.convertFromEntityToVO(userProfileDao
		        .updateInternalUserStateMapping(inputVo));
	}

	@Override
	public InternalUserStateMappingVO deleteInternalUserStateMapping(
	        InternalUserStateMappingVO inputVo) {

		return InternalUserStateMapping.convertFromEntityToVO(userProfileDao
		        .deleteInternalUserStateMapping(inputVo));
	}

	@Override
	public Integer updateTutorialStatus(Integer id) throws Exception {
		return userProfileDao.updateTutorialStatus(id);

	}

	@Override
	public void updateLoginTime(Date date, int userId) {
		userProfileDao.updateLoginTime(new Date(System.currentTimeMillis()),
		        userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> geAllSalesManagers() {
		return userProfileDao.getAllSalesManagers();

	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findBySecondaryEmail(String fromAddressString) {
		return userProfileDao.getUserBySecondaryMail(fromAddressString);
	}

	@Override
	public void sendEmailPreQualification(LoanAppFormVO loaAppFormVO,
	        ByteArrayOutputStream byteArrayOutputStream)
	        throws InvalidInputException, UndeliveredEmailException {
		String subject = CommonConstants.PRE_QUALIFICATION_LETTER;
		EmailVO emailEntity = new EmailVO();

		Template template = null;

		template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_PRE_QUAL_LETTER);

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { loaAppFormVO.getUser()
		        .getFirstName() + " " + loaAppFormVO.getUser().getLastName() });

		emailEntity.setAttachmentStream(byteArrayOutputStream);
		emailEntity.setSenderEmailId(loaAppFormVO.getUser().getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(subject);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();
		ccList.add(loaAppFormVO.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);

		sendEmailService.sendEmailForTeam(emailEntity, loaAppFormVO.getLoan()
		        .getId(), template);

	}

	@Override
	@Transactional
	
	public void updateInternalUserDetails(InternalUserDetail internalUserDetail) {
		userProfileDao.update(internalUserDetail);
	}

	@Override
	@Transactional
    public boolean deleteUserEntries(UserVO userVO) throws Exception {
		boolean isSuccess = false;
		LOG.info("To delete/change the user status to 0 in loanTeam table......................");
		int rows ;
		userVO.setStatus(0);	
		rows = loanService.updateStatusInLoanTeam(userVO);
		
		if(rows == 0){
			LOG.info("The user is not associated in any team hence the no of updated rows returned o:::::"+rows);
		}

		LoanVO loanVO = loanService.getActiveLoanOfUser(userVO);
		if(loanVO!=null){
			  Loan loan = new Loan(loanVO.getId());

			  loanService.markLoanDeleted(loan.getId());
			  isSuccess = true;
			  

		}
		
		LOG.info("To delete/change the user status to -1 in internaluser table......................");
		
		boolean isInternalUserDeleted = false;
		//TO change the status of user in internal user table
		if (userVO.getUserRole().getId() == UserRolesEnum.INTERNAL
		        .getRoleId()) {
			isInternalUserDeleted = deleteUser(userVO);				

		}
		if (isInternalUserDeleted && isSuccess) {
			LOG.info("User deleted successfully"+userVO.getUserRole().getRoleDescription());
			isSuccess = true;
		}
		/*
		LOG.info("To delete/change the user status to 0 in loanTeam table......................");
		int rows ;
		userVO.setStatus(0);	
		rows = loanService.updateStatusInLoanTeam(userVO);
		
		if(rows == 0){
			LOG.info("The user is not associated in any team hence the no of updated rows returned o:::::"+rows);
		}*/
			
		

		LOG.info("To delete/change the user status to -1 in user table......................");
		//TO change status in user table
/*		userVO.setStatus(-1);
		Integer result = updateUserStatus(userVO);*/

		//Marking the loan status as DELETE in loanmilestone master
		/*if(result > 0){
			LoanVO loanVO = loanService.getActiveLoanOfUser(userVO);
			if(loanVO!=null){
				  Loan loan = new Loan(loanVO.getId());
				   LoanMilestone lm = loanService.findLoanMileStoneByLoan(loan,
				           Milestones.DELETE.getMilestoneKey());
				   if (lm == null) {
				    loanService.updateNexeraMilestone(loanVO.getId(),
				            Milestones.DELETE.getMilestoneID(),Milestones.DELETE.getMilestoneKey());
				   }
				   LoanProgressStatusMaster progressStatusMaster = new LoanProgressStatusMaster();
				   progressStatusMaster.setId(LoanProgressStatusMasterEnum.DELETED.getStatusId());
				   loan.setLoanProgressStatus(progressStatusMaster);
				   loanService.saveLoanProgress(loan.getId(),progressStatusMaster);
				 
				  }
			}else {
				LOG.info("There was an error while deletion of user and  no of rows returned is 0............"+result);
			}
				 
		
		if (result > 0 || rows > 0 || isInternalUserDeleted) {
			LOG.info("User deleted successfully"+userVO.getUserRole().getRoleDescription());
			isSuccess = true;
		}*/
		
		
		  return isSuccess;
	}
}