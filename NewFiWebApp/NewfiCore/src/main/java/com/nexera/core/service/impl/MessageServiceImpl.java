package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.User;
import com.nexera.common.enums.EmailRecipientTypeEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.MessageHierarchyVO;
import com.nexera.common.vo.MessageQueryVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.MessageVO.FileVO;
import com.nexera.common.vo.MessageVO.MessageUserVO;
import com.nexera.common.vo.UserRoleNameImageVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.core.service.MessageService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.mongo.service.MongoCoreMessageService;

@Component
public class MessageServiceImpl implements MessageService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(MessageServiceImpl.class);

	@Autowired
	UserProfileDao userProfileDao;

	@Autowired
	Utils utils;

	@Autowired
	TemplateService templateService;

	@Autowired
	MongoCoreMessageService mongoMessageService;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Override
	public String saveMessage(MessageVO messagesVO, String messageType,
	        boolean sendEmail) throws FatalException, NonFatalException {

		MongoMessagesVO mongoMessagesVO = new MongoMessagesVO();
		LOG.debug("Messaege from UI: " + messagesVO);
		mongoMessagesVO.setBody(messagesVO.getMessage());
		mongoMessagesVO.setCreatedBy(new Long(messagesVO.getCreatedUser()
		        .getUserID()));
		mongoMessagesVO.setParentId(messagesVO.getParentId());
		mongoMessagesVO.setLoanId(messagesVO.getLoanId());
		mongoMessagesVO.setMessageType(messageType);
		mongoMessagesVO.setRoleName(messagesVO.getCreatedUser().getRoleName());
		List<Long> userAccessList = getUserIds(messagesVO.getOtherUsers());
		List<String> userRoleList = getUserRoles(messagesVO.getOtherUsers());
		userAccessList.add(new Long(messagesVO.getCreatedUser().getUserID()));
		userRoleList.add(messagesVO.getCreatedUser().getRoleName());
		mongoMessagesVO.setUserList(userAccessList);
		mongoMessagesVO.setRoleList(userRoleList);
		List<FileVO> fileList = messagesVO.getLinks();
		if (fileList != null && !fileList.isEmpty()) {
			mongoMessagesVO.setLinks(convertToMongoFileList(fileList));
		}

		// TODO: Take care of GMT conversion
		mongoMessagesVO.setCreatedDate(new Date(System.currentTimeMillis()));

		LOG.debug("Saving Mongo message: " + mongoMessagesVO);
		String mongoMessageId = mongoMessageService
		        .saveMessage(mongoMessagesVO);
		/*
		 * Invoke send Email service to notify the users about the take a note
		 * operation
		 */
		if (sendEmail) {
			sendEmail(messagesVO, mongoMessageId);
		}

		return mongoMessageId;

	}

	private void sendEmail(MessageVO messagesVO, String mongoMessageId) {

		EmailVO emailVO = new EmailVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NEW_NOTE);

		// Set to address, it will be all the users to whom the message is
		// targetted
		List<User> users = userProfileDao
		        .getEmailAddress(extractUserIds(messagesVO.getOtherUsers()));
		if (users == null || users.isEmpty()) {
			LOG.warn(
			        "A message was created with no target users. Ignoring send email",
			        messagesVO);
			return;
		}
		List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
		for (User user : users) {
			EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
			emailRecipientVO.setEmailID(user.getEmailId());
			emailRecipientVO.setRecipientName(user.getFirstName());
			emailRecipientVO.setRecipientTypeEnum(EmailRecipientTypeEnum.TO);
			recipients.add(emailRecipientVO);
			if (user.getCustomerDetail() != null
			        && user.getCustomerDetail().getSecEmailId() != null
			        && !user.getCustomerDetail().getSecEmailId().isEmpty()) {
				emailRecipientVO.setEmailID(user.getCustomerDetail()
				        .getSecEmailId());
				emailRecipientVO.setRecipientName(user.getFirstName());
				emailRecipientVO
				        .setRecipientTypeEnum(EmailRecipientTypeEnum.TO);
			}
		}
		emailVO.setRecipients(recipients);

		// Set the subject as New note taken in the system
		emailVO.setSubject(CommonConstants.NOTE_SUBJECT);

		// Set reply to as the messageId of mongomessageId
		emailVO.setSenderEmailId(utils.generateMessageIdFromAddress(
		        mongoMessageId, messagesVO.getLoanId()));
		emailVO.setSenderName(CommonConstants.SENDER_NAME);

		// Set body as the message written. Prefix that this user has sent the
		// message. Add it in the substitutions
		emailVO.setBody(messagesVO.getMessage());
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		if (messagesVO.getCreatedUser() != null) {
			substitutions.put("-name-", new String[] { messagesVO
			        .getCreatedUser().getUserName() });
		} else {
			substitutions.put("-name-",
			        new String[] { CommonConstants.ANONYMOUS_USER });
		}

		substitutions.put("-time-", new String[] { utils.getTimeInPST() });

		if (messagesVO.getLoanEmail() != null
		        && !messagesVO.getLoanEmail().isEmpty()) {
			substitutions.put("-loanFile-",
			        new String[] { String.valueOf(messagesVO.getLoanEmail()) });
		} else {
			substitutions.put("-loanFile-",
			        new String[] { String.valueOf(messagesVO.getLoanId()) });
		}
		emailVO.setTokenMap(substitutions);

		// Set template information
		emailVO.setTemplateBased(Boolean.TRUE);
		emailVO.setTemplateId(template.getValue());

		sendGridEmailService.sendAsyncMail(emailVO);

	}

	private List<Integer> extractUserIds(List<MessageUserVO> otherUsers) {
		List<Integer> usrIds = new ArrayList<Integer>();
		for (MessageUserVO user : otherUsers) {
			usrIds.add(user.getUserID());
		}
		return usrIds;

	}

	private com.nexera.common.vo.mongo.MongoMessagesVO.FileVO[] convertToMongoFileList(
	        List<FileVO> fileList) {
		// TODO Auto-generated method stub
		com.nexera.common.vo.mongo.MongoMessagesVO.FileVO arrayOfFiles[] = new com.nexera.common.vo.mongo.MongoMessagesVO.FileVO[fileList
		        .size()];
		int i = 0;
		for (FileVO fileVO : fileList) {
			com.nexera.common.vo.mongo.MongoMessagesVO.FileVO file = new com.nexera.common.vo.mongo.MongoMessagesVO.FileVO();
			file.setFileName(fileVO.getFileName());
			file.setUrl(fileVO.getUrl());
			arrayOfFiles[i] = file;
			i = i + 1;
		}
		return arrayOfFiles;
	}

	private List<String> getUserRoles(List<MessageUserVO> otherUsers) {
		List<String> userroleList = new ArrayList<String>();
		boolean internalUserPresent = Boolean.FALSE;
		for (MessageUserVO messageUserVO : otherUsers) {
			userroleList.add(messageUserVO.getRoleName());
			if (messageUserVO.getRoleName().equals(
			        UserRolesEnum.INTERNAL.getName())) {
				internalUserPresent = Boolean.TRUE;
			}
		}
		if (!internalUserPresent) {
			userroleList.add(UserRolesEnum.INTERNAL.getName());
			userroleList.add(UserRolesEnum.SM.getName());
		}
		return userroleList;
	}

	private List<Long> getUserIds(List<MessageUserVO> otherUsers) {

		List<Long> userIdList = new ArrayList<Long>();
		for (MessageUserVO messageUserVO : otherUsers) {
			userIdList.add(new Long(messageUserVO.getUserID()));
		}
		return userIdList;
	}

	@Override
	@Transactional(readOnly = true)
	public MessageHierarchyVO getMessages(MessageQueryVO queryVO)
	        throws FatalException, NonFatalException {
		// TODO Auto-generated method stub

		MongoQueryVO mongoQueryVO = new MongoQueryVO();

		mongoQueryVO.setLoanId(queryVO.getLoanId());
		mongoQueryVO.setUserId(queryVO.getUserId());
		mongoQueryVO.setPageNumber(queryVO.getPageNumber());
		mongoQueryVO.setNumberOfRecords(queryVO.getNumberOfRecords());
		mongoQueryVO.setRoleName(userProfileDao.findUserRoleForMongo(queryVO
		        .getUserId().intValue()));
		MongoMessageHierarchyVO mongoHierarchyVO = mongoMessageService
		        .getMessages(mongoQueryVO);

		MessageHierarchyVO messageHierarchyVO = constructMessageHierarchy(mongoHierarchyVO);
		return messageHierarchyVO;
	}

	private MessageHierarchyVO constructMessageHierarchy(
	        MongoMessageHierarchyVO mongoHierarchyVO) {
		List<List<MessageVO>> messageVOMasterList = new ArrayList<List<MessageVO>>();
		List<MessageVO> messageVOList;
		List<List<String>> messages = mongoHierarchyVO.getMessageIds();

		for (List<String> list : messages) {
			messageVOList = new ArrayList<MessageVO>();
			for (String messageId : list) {
				MongoMessagesVO mongoMessagesVO = mongoHierarchyVO
				        .getMongoMessagesVO(messageId);
				// TODO: Remove the int value when the userId changes are done

				MessageVO messageVO = createMessageVO(mongoMessagesVO);
				messageVOList.add(messageVO);
			}
			Collections.sort(messageVOList, new Comparator<MessageVO>() {
				@Override
				public int compare(MessageVO o1, MessageVO o2) {
					if (o1.getSortOrderDate() == null
					        || o2.getSortOrderDate() == null)
						return 0;
					return o2.getSortOrderDate().compareTo(
					        o1.getSortOrderDate());
				}
			});
			messageVOMasterList.add(messageVOList);
		}
		MessageHierarchyVO hierarchyVO = new MessageHierarchyVO();
		hierarchyVO.setMessageVOs(messageVOMasterList);
		return hierarchyVO;
	}

	private Collection<? extends Integer> convertToIntTempMethod(
	        List<Long> userList) {
		Set<Integer> integers = new TreeSet<Integer>();
		for (Long userId : userList) {
			integers.add(userId.intValue());
		}
		return integers;
	}

	private MessageVO createMessageVO(MongoMessagesVO mongoMessagesVO) {
		// TODO Auto-generated method stub
		MessageVO messageVO = new MessageVO();
		messageVO.setId(mongoMessagesVO.getId());
		messageVO.setMessage(mongoMessagesVO.getBody());
		List<UserRoleNameImageVO> nameImageVOs = new ArrayList<UserRoleNameImageVO>();
		UserRoleNameImageVO nameImageVO = userProfileDao
		        .findUserDetails(mongoMessagesVO.getCreatedBy().intValue());

		MessageUserVO createdUser = messageVO.createNewUserVO();
		createdUser.setImgUrl(nameImageVO.getImgPath());
		createdUser.setRoleName(nameImageVO.getUserRole());
		createdUser.setUserName(nameImageVO.getUserName());
		createdUser.setUserID(nameImageVO.getUserID());
		messageVO.setCreatedUser(createdUser);

		if (mongoMessagesVO.getLinks() != null
		        && mongoMessagesVO.getLinks().length != 0) {
			messageVO
			        .setLinks(convertMongoFileList(mongoMessagesVO.getLinks()));
		}

		messageVO
		        .setCreatedDate(utils
		                .getDateInUserLocaleFormatted(mongoMessagesVO
		                        .getCreatedDate()));
		messageVO.setParentId(mongoMessagesVO.getParentId());
		messageVO.setLoanId(mongoMessagesVO.getLoanId());
		messageVO.setSortOrderDate(mongoMessagesVO.getCreatedDate());
		List<UserRoleNameImageVO> nameList = userProfileDao
		        .finUserDetailsList(mongoMessagesVO.getUserList());
		List<MessageUserVO> userVOs = new ArrayList<MessageVO.MessageUserVO>();
		for (UserRoleNameImageVO userRoleNameImageVO : nameList) {
			createdUser = messageVO.createNewUserVO();
			createdUser.setImgUrl(userRoleNameImageVO.getImgPath());
			createdUser.setRoleName(userRoleNameImageVO.getUserRole());
			createdUser.setUserName(userRoleNameImageVO.getUserName());
			createdUser.setUserID(userRoleNameImageVO.getUserID());
			userVOs.add(createdUser);
		}
		messageVO.setOtherUsers(userVOs);
		return messageVO;
	}

	private List<FileVO> convertMongoFileList(
	        com.nexera.common.vo.mongo.MongoMessagesVO.FileVO[] links) {
		List<FileVO> fileVOs = new ArrayList<MessageVO.FileVO>();
		for (com.nexera.common.vo.mongo.MongoMessagesVO.FileVO fileVO : links) {
			FileVO vo = new FileVO();
			vo.setFileName(fileVO.getFileName());
			vo.setUrl(fileVO.getUrl());
			fileVOs.add(vo);
		}
		return fileVOs;
	}
}
