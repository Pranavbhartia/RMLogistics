package com.nexera.core.helper.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.CommunicationLogConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.MessageTypeEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.MessageVO.FileVO;
import com.nexera.common.vo.MessageVO.MessageUserVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.MasterDataService;
import com.nexera.core.service.MessageService;
import com.nexera.core.service.UserProfileService;

@Component
@DependsOn(value = "needsListServiceImpl")
public class MessageServiceHelperImpl implements MessageServiceHelper {

	private static final String NEW_LINE_CHARACTER = "{0}\n > {1}";

	private static final String ITEMS_REMOVED_MESSAGE = "{0}\n\nFollowing items were removed from the list";

	private static final String ITEMS_ADDED_MESSAGE = "{0}\n\nFollowing items were added to the list";

	private static final Logger LOG = LoggerFactory
	        .getLogger(MessageServiceHelperImpl.class);

	@Autowired
	MessageService messageService;

	@Autowired
	LoanService loanService;

	@Autowired
	MasterDataService masterDataService;

	@Autowired
	UserProfileDao userProfileDao;

	@Autowired
	Utils utils;

	@Autowired
	UserProfileService userProfileService;

	@Override
	@Async
	public void saveMessage(MessageVO messagesVO, String messageType,
	        boolean sendEmail) {
		LOG.debug("Helper save message called");
		String messageID;
		try {
			if (messagesVO.getLoanId() != 0) {
				LoanVO loan = loanService.getLoanByID(messagesVO.getLoanId());
				messagesVO.setLoanEmail(loan.getLoanEmailId());
			}
			messageID = messageService.saveMessage(messagesVO, messageType,
			        sendEmail);
			LOG.debug("Helper save message succeeded. With messageID: "
			        + messageID);
		} catch (FatalException | NonFatalException e) {
			LOG.error("error while saving the message: " + messagesVO);
			// TODO: Write in Error table
		}

	}

	@Override
	public void generateNeedListModificationMessage(int loanId,
	        User loggedInUser, List<Integer> addedList,
	        List<Integer> removedList, boolean sendEmail) {

		LoanTeamListVO teamList = loanService
		        .getLoanTeamListForLoan(new LoanVO(loanId));

		List<Integer> lookupList = new ArrayList<Integer>();
		lookupList.addAll(addedList);
		lookupList.addAll(removedList);
		Map<Integer, String> needListLookup = buildLookup(lookupList);

		MessageVO messageVO = new MessageVO();
		String message = new String(CommunicationLogConstants.MODIFY_NEED);

		messageVO.setLoanId(loanId);
		messageVO.setCreatedDate(utils.getDateInUserLocaleFormatted(new Date(
		        System.currentTimeMillis())));
		/*
		 * Set all users who will have access to this message
		 */
		List<LoanTeamVO> loanTeamVos = teamList.getLoanTeamList();
		if (loggedInUser.getUserRole().getRoleCd()
		        .equals(UserRolesEnum.CUSTOMER.getName())) {
			// get system User
			User usr = (User) userProfileDao.load(User.class, 1);
			loggedInUser = new User();
			loggedInUser.setId(usr.getId());
			loggedInUser.setUsername(usr.getUsername());
			loggedInUser.setPhotoImageUrl(usr.getPhotoImageUrl());
			loggedInUser.setFirstName(usr.getFirstName());
			loggedInUser.setLastName(usr.getLastName());
		}
		MessageUserVO createdUserVO = messageVO.createNewUserVO();
		createdUserVO.setUserID(loggedInUser.getId());
		createdUserVO.setImgUrl(loggedInUser.getPhotoImageUrl());
		createdUserVO.setUserName(loggedInUser.getFirstName() + " "
		        + loggedInUser.getLastName());
		message = message.replace(CommunicationLogConstants.USER,
		        loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
		try {
			String roleName = userProfileDao.findUserRoleForMongo(loggedInUser
			        .getId());
			createdUserVO.setRoleName(roleName);
		} catch (DatabaseException | NoRecordsFetchedException e) {
			// THIS SHOULD NEVER HAPPEN
			LOG.error("Trying to fetch a user who does not exist. ", e);
		}
		messageVO.setCreatedUser(createdUserVO);

		// createdUserVO.setRoleName(loggedInUser.getu);
		List<MessageUserVO> messageUserVOs = new ArrayList<MessageVO.MessageUserVO>();
		for (LoanTeamVO loanTeamVO : loanTeamVos) {
			UserVO userVo = loanTeamVO.getUser();
			if (userVo.getId() != loggedInUser.getId()) {
				MessageUserVO otherUser = messageVO.createNewUserVO();
				otherUser.setUserID(userVo.getId());
				otherUser.setUserName(userVo.getDisplayName());
				otherUser.setImgUrl(userVo.getPhotoImageUrl());
				try {
					otherUser.setRoleName(userProfileDao
					        .findUserRoleForMongo(userVo.getId()));
				} catch (DatabaseException | NoRecordsFetchedException e) {
					// THIS SHOULD NEVER HAPPEN
					LOG.error("Trying to fetch a user who does not exist. ", e);
				}
				messageUserVOs.add(otherUser);
			}
			if (UserRolesEnum.CUSTOMER.equalsName(userVo.getUserRole()
			        .getRoleCd())) {
				message = message.replace(CommunicationLogConstants.CUSTOMER,
				        userVo.getDisplayName());

			}

		}
		if (addedList != null && !addedList.isEmpty()) {
			message = MessageFormat.format(ITEMS_ADDED_MESSAGE, message);
		}
		for (Integer neededList : addedList) {
			String label = needListLookup.get(neededList);
			message = MessageFormat.format(NEW_LINE_CHARACTER, message, label);
		}
		if (removedList != null && !removedList.isEmpty()) {
			message = MessageFormat.format(ITEMS_REMOVED_MESSAGE, message);
		}
		for (Integer neededList : removedList) {
			String label = needListLookup.get(neededList);
			message = MessageFormat.format(NEW_LINE_CHARACTER, message, label);
		}
		messageVO.setMessage(message);
		messageVO.setOtherUsers(messageUserVOs);
		this.saveMessage(messageVO, MessageTypeEnum.NOTE.toString(), sendEmail);

	}

	public Map<Integer, String> buildLookup(List<Integer> lookupList) {
		List<NeedsListMaster> needsListMasters = masterDataService
		        .getNeedListMaster();
		Map<Integer, String> needListLookup = new HashMap<Integer, String>();
		for (NeedsListMaster needsListMaster : needsListMasters) {
			needListLookup.put(
			        needsListMaster.getId(),
			        needsListMaster.getLabel() + " - "
			                + needsListMaster.getDescription());
		}

		return needListLookup;

	}

	@Override
	@Async
	public void generateEmailDocumentMessage(int loanId, User loggedInUser,
	        String messageId, String noteText, List<FileVO> fileUrls,
	        boolean successFlag, boolean sendEmail, boolean systemGenerated,
	        List<String> mailerList, boolean entireTeam) {
		/*
		 * 1. Create messageVO object. 2. set senderUserId as the createdBy of
		 * the note. Permission will be all the users who are part of the loan
		 * team 3. set messageId as parent messageId if present 4. FileVO will
		 * contain the array of all the files 5. noteText is the text/body of
		 * the message. If attachment are present, we will create a default text
		 */
		MessageVO messageVO = new MessageVO();
		messageVO.setLoanId(loanId);
		messageVO.setCreatedDate(utils.getDateInUserLocaleFormatted(new Date(
		        System.currentTimeMillis())));
		String message = CommunicationLogConstants.DOCUMENT_UPLOAD;

		if (systemGenerated) {
			if (entireTeam) {
				setGlobalPermissionsToMessage(
				        loanId,
				        messageVO,
				        userProfileDao
				                .findByUserId(CommonConstants.SYSTEM_USER_USERID),
				        message);
			} else {

				setPermissionsToMessageBasedOnMailerList(
				        loanId,
				        messageVO,
				        userProfileDao
				                .findByUserId(CommonConstants.SYSTEM_USER_USERID),
				        message, mailerList);
			}
		} else {
			if (entireTeam) {
				setGlobalPermissionsToMessage(loanId, messageVO, loggedInUser,
				        message);
			} else {
				setPermissionsToMessageBasedOnMailerList(loanId, messageVO,
				        loggedInUser, message, mailerList);

			}
		}

		/*
		 * We will override the message either if the file list contains values
		 * or if successFlag is false. If successflag is false, then we will set
		 * the message based on the caller. The caller is expected to create the
		 * message accordingly
		 */
		/*
		 * Commented by UTSAV. To send the entire success/failure text if
		 * (fileUrls == null || fileUrls.isEmpty() || !successFlag) { }
		 */
		messageVO.setMessage(noteText);
		messageVO.setLinks(fileUrls);
		messageVO.setParentId(messageId);
		this.saveMessage(messageVO, MessageTypeEnum.EMAIL.toString(), sendEmail);

	}

	private void setPermissionsToMessageBasedOnMailerList(int loanId,
	        MessageVO messageVO, User loggedInUser, String message,
	        List<String> mailerList) {

		List<UserVO> userVOList = new ArrayList<>();
		for (String emailId : mailerList) {
			User user = userProfileService.findUserByMail(emailId);
			if (user != null) {
				UserVO userVO = User.convertFromEntityToVO(user);
				userVOList.add(userVO);
			}

		}

		List<User> salesManagerList = userProfileService.geAllSalesManagers();
		User salesManager = salesManagerList.get(0);
		boolean found = false;
		UserVO salesManagerVO = null;
		if (salesManager != null) {
			salesManagerVO = User.convertFromEntityToVO(salesManager);
			for (UserVO user : userVOList) {
				if (user.getEmailId().equalsIgnoreCase(
				        salesManagerVO.getEmailId())) {
					found = true;
				}

			}
		}

		if (!found) {
			if (salesManager != null) {
				userVOList.add(salesManagerVO);
			}
		}

		List<MessageUserVO> messageUserVOs = new ArrayList<MessageVO.MessageUserVO>();
		for (UserVO userVo : userVOList) {

			if (userVo.getId() != loggedInUser.getId()) {
				MessageUserVO otherUser = messageVO.createNewUserVO();
				otherUser.setUserID(userVo.getId());
				otherUser.setUserName(userVo.getDisplayName());
				otherUser.setImgUrl(userVo.getPhotoImageUrl());
				try {
					otherUser.setRoleName(userProfileDao
					        .findUserRoleForMongo(userVo.getId()));
				} catch (DatabaseException | NoRecordsFetchedException e) {
					// THIS SHOULD NEVER HAPPEN
					LOG.error("Trying to fetch a user who does not exist. ", e);
				}

				messageUserVOs.add(otherUser);
			} else {
				if (message != null) {
					message = message.replace(
					        CommunicationLogConstants.USER,
					        loggedInUser.getFirstName() + " "
					                + loggedInUser.getLastName());
				}

			}

		}

		MessageUserVO createdUserVO = messageVO.createNewUserVO();
		createdUserVO.setUserID(loggedInUser.getId());
		createdUserVO.setImgUrl(loggedInUser.getPhotoImageUrl());
		createdUserVO.setUserName(loggedInUser.getFirstName() + " "
		        + loggedInUser.getLastName());
		try {
			String roleName = userProfileDao.findUserRoleForMongo(loggedInUser
			        .getId());
			createdUserVO.setRoleName(roleName);
		} catch (DatabaseException | NoRecordsFetchedException e) {
			// THIS SHOULD NEVER HAPPEN
			LOG.error("Trying to fetch a user who does not exist. ", e);
		}
		messageVO.setCreatedUser(createdUserVO);
		messageVO.setMessage(message);
		messageVO.setOtherUsers(messageUserVOs);

	}

	private void setGlobalPermissionsToMessage(int loanId, MessageVO messageVO,
	        User loggedInUser, String message) {

		LoanTeamListVO teamList = loanService
		        .getLoanTeamListForLoan(new LoanVO(loanId));
		List<LoanTeamVO> loanTeamVos = teamList.getLoanTeamList();

		List<MessageUserVO> messageUserVOs = new ArrayList<MessageVO.MessageUserVO>();
		List<UserVO> userVOList = new ArrayList<UserVO>();
		boolean salesManagerFound = false;
		for (LoanTeamVO loanTeamVO : loanTeamVos) {
			UserVO userVO = loanTeamVO.getUser();

			if (userVO != null) {
				userVOList.add(userVO);
				if (userVO.getInternalUserDetail() != null) {
					if (userVO.getInternalUserDetail()
					        .getInternalUserRoleMasterVO().getId() == InternalUserRolesEum.SM
					        .getRoleId()) {
						salesManagerFound = true;
					}
				}
			}
		}

		if (!salesManagerFound) {
			List<User> userList = userProfileService.geAllSalesManagers();
			if (userList != null) {
				User user = userList.get(0);
				if (user != null) {
					UserVO userVO = User.convertFromEntityToVO(user);
					userVOList.add(userVO);
				}
			}

		}
		for (UserVO userVo : userVOList) {
			if (userVo.getId() != loggedInUser.getId()) {
				MessageUserVO otherUser = messageVO.createNewUserVO();
				otherUser.setUserID(userVo.getId());
				otherUser.setUserName(userVo.getDisplayName());
				otherUser.setImgUrl(userVo.getPhotoImageUrl());
				try {
					otherUser.setRoleName(userProfileDao
					        .findUserRoleForMongo(userVo.getId()));
				} catch (DatabaseException | NoRecordsFetchedException e) {
					// THIS SHOULD NEVER HAPPEN
					LOG.error("Trying to fetch a user who does not exist. ", e);
				}

				messageUserVOs.add(otherUser);
			} else {
				if (message != null) {
					message = message.replace(
					        CommunicationLogConstants.USER,
					        loggedInUser.getFirstName() + " "
					                + loggedInUser.getLastName());
				}

			}

		}

		MessageUserVO createdUserVO = messageVO.createNewUserVO();
		createdUserVO.setUserID(loggedInUser.getId());
		createdUserVO.setImgUrl(loggedInUser.getPhotoImageUrl());
		createdUserVO.setUserName(loggedInUser.getFirstName() + " "
		        + loggedInUser.getLastName());
		try {
			String roleName = userProfileDao.findUserRoleForMongo(loggedInUser
			        .getId());
			createdUserVO.setRoleName(roleName);
		} catch (DatabaseException | NoRecordsFetchedException e) {
			// THIS SHOULD NEVER HAPPEN
			LOG.error("Trying to fetch a user who does not exist. ", e);
		}
		messageVO.setCreatedUser(createdUserVO);
		messageVO.setMessage(message);
		messageVO.setOtherUsers(messageUserVOs);

	}

	@Override
	@Async
	public void generateWorkflowMessage(int loanId, String noteText,
	        boolean sendEmail) {

		MessageVO messageVO = new MessageVO();
		messageVO.setLoanId(loanId);
		messageVO.setCreatedDate(utils.getDateInUserLocaleFormatted(new Date(
		        System.currentTimeMillis())));
		setGlobalPermissionsToMessage(
		        loanId,
		        messageVO,
		        userProfileDao.findByUserId(CommonConstants.SYSTEM_USER_USERID),
		        null);
		messageVO.setMessage(noteText);
		this.saveMessage(messageVO, MessageTypeEnum.NOTE.toString(), sendEmail);
	}

	@Override
	public void generateWelcomeNote(User loggedInUser, int loanId) {

		MessageVO messageVO = new MessageVO();

		messageVO.setLoanId(loanId);
		messageVO.setCreatedDate(utils.getDateInUserLocaleFormatted(new Date(
		        System.currentTimeMillis())));
		setGlobalPermissionsToMessage(
		        loanId,
		        messageVO,
		        userProfileDao.findByUserId(CommonConstants.SYSTEM_USER_USERID),
		        null);
		String message = CommunicationLogConstants.WELCOME_USER;
		message = message.replace(CommunicationLogConstants.USER,
		        loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
		messageVO.setMessage(message);
		this.saveMessage(messageVO, MessageTypeEnum.NOTE.toString(), true);
	}

	@Override
	@Async
	public void checkIfUserFirstLogin(User loggedInUser, boolean isShopper) {
		// Since this is an async method, we catch generic exception and log the
		// error
		try {
			User user = userProfileDao.findByUserId(loggedInUser.getId());
			
			if (!isShopper) {
				userProfileDao.updateLoginTime(
				        new Date(System.currentTimeMillis()), user.getId());
			}
			
			if (user.getUserRole().getId() != UserRolesEnum.CUSTOMER
			        .getRoleId()) {
				// If the logged in user is customer generate a note, else
				// ignore
				return;
			}

			Date loginDate = user.getLastLoginDate();
			if (loginDate == null) {
				/*
				 * LoanVO loanVO = loanService.getActiveLoanOfUser(User
				 * .convertFromEntityToVO(loggedInUser));
				 * this.generateWelcomeNote(loggedInUser, loanVO.getId());
				 */
			}
		} catch (Exception ex) {
			LOG.error(
			        "There was an error in async method checkIfUserFirstLogin ",
			        ex);
		}
	}

	@Override
	@Async
	public void generatePrivateMessage(int loanId, String noteText,
	        User createdBy, boolean sendEmail) {
		MessageVO messageVO = new MessageVO();

		messageVO.setLoanId(loanId);
		messageVO.setCreatedDate(utils.getDateInUserLocaleFormatted(new Date(
		        System.currentTimeMillis())));
		setOnlyInternalUsersAccess(loanId, messageVO, createdBy, null,
		        Boolean.FALSE);

		messageVO.setMessage(noteText);
		this.saveMessage(messageVO, MessageTypeEnum.NOTE.toString(), sendEmail);

	}

	@Override
	@Async
	public void generateManagerMessage(int loanId, String noteText,
	        User createdBy, boolean sendEmail) {
		MessageVO messageVO = new MessageVO();

		messageVO.setLoanId(loanId);
		messageVO.setCreatedDate(utils.getDateInUserLocaleFormatted(new Date(
		        System.currentTimeMillis())));
		setOnlyInternalUsersAccess(loanId, messageVO, createdBy, null, true);

		messageVO.setMessage(noteText);
		this.saveMessage(messageVO, MessageTypeEnum.NOTE.toString(), true);

	}

	private void setOnlyInternalUsersAccess(int loanId, MessageVO messageVO,
	        User user, String message, boolean managerOnly) {

		LoanTeamListVO teamList = loanService
		        .getLoanTeamListForLoan(new LoanVO(loanId));

		List<LoanTeamVO> loanTeamVos = teamList.getLoanTeamList();

		List<MessageUserVO> messageUserVOs = new ArrayList<MessageVO.MessageUserVO>();
		User createdByUser = userProfileDao.findByUserId(user.getId());
		for (LoanTeamVO loanTeamVO : loanTeamVos) {
			UserVO userVo = loanTeamVO.getUser();
			if (isInternalUser(userVo, managerOnly)) {
				if (userVo.getId() != createdByUser.getId()) {
					MessageUserVO otherUser = messageVO.createNewUserVO();
					otherUser.setUserID(userVo.getId());
					otherUser.setUserName(userVo.getDisplayName());
					otherUser.setImgUrl(userVo.getPhotoImageUrl());
					try {
						otherUser.setRoleName(userProfileDao
						        .findUserRoleForMongo(userVo.getId()));
					} catch (DatabaseException | NoRecordsFetchedException e) {
						// THIS SHOULD NEVER HAPPEN
						LOG.error(
						        "Trying to fetch a user who does not exist. ",
						        e);
					}

					messageUserVOs.add(otherUser);
				}

			}

		}

		MessageUserVO createdUserVO = messageVO.createNewUserVO();
		createdUserVO.setUserID(createdByUser.getId());
		createdUserVO.setImgUrl(createdByUser.getPhotoImageUrl());
		createdUserVO.setUserName(createdByUser.getFirstName() + " "
		        + createdByUser.getLastName());
		try {
			String roleName = userProfileDao.findUserRoleForMongo(createdByUser
			        .getId());
			createdUserVO.setRoleName(roleName);
		} catch (DatabaseException | NoRecordsFetchedException e) {
			// THIS SHOULD NEVER HAPPEN
			LOG.error("Trying to fetch a user who does not exist. ", e);
		}
		messageVO.setCreatedUser(createdUserVO);
		messageVO.setMessage(message);
		messageVO.setOtherUsers(messageUserVOs);

	}

	private boolean isInternalUser(UserVO userVo, boolean managerOnly) {
		// TODO Auto-generated method stub
		String roleCode = userVo.getUserRole().getRoleCd();
		UserRolesEnum rolesEnum = UserRolesEnum.valueOf(roleCode);
		switch (rolesEnum) {
		case CUSTOMER:
			return false;
		case REALTOR:
			return false;
		case SYSTEM:
			return true;
		default:

			if (!managerOnly) {
				// If manager only flag is not set, means th message is visible
				// to all internal users
				return true;
			} else {
				// This means only admins and sales manager wil have access,
				// others will not
				if (userVo.getInternalUserDetail().getId() == UserRolesEnum.SM
				        .getRoleId())
					return true;
				return false;
			}

		}

	}
}
