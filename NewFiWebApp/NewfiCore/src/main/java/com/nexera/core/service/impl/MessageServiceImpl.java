package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.Utils;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.MessageHierarchyVO;
import com.nexera.common.vo.MessageQueryVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.MessageVO.MessageUserVO;
import com.nexera.common.vo.UserRoleNameImageVO;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.core.service.MessageService;
import com.nexera.mongo.service.MongoCoreMessageService;
import com.nexera.mongo.service.impl.MongoCoreMessageServiceImpl;

@Component
public class MessageServiceImpl implements MessageService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(MessageServiceImpl.class);

	@Autowired
	UserProfileDao userProfileDao;

	@Autowired
	Utils utils;

	@Override
	public String saveMessage(MessageVO messagesVO, String messageType)
	        throws FatalException, NonFatalException {

		MongoMessagesVO mongoMessagesVO = new MongoMessagesVO();
		LOG.debug("Messaege from UI: " + messagesVO);
		mongoMessagesVO.setBody(messagesVO.getMessage());
		mongoMessagesVO.setCreatedBy(new Long(messagesVO.getCreatedUser()
		        .getUserID()));
		mongoMessagesVO.setParentId(messagesVO.getParentId());
		mongoMessagesVO.setLoanId(Long.valueOf(messagesVO.getLoanId()));
		mongoMessagesVO.setMessageType(messageType);
		mongoMessagesVO.setRoleName(messagesVO.getCreatedUser().getRoleName());
		List<Long> userAccessList = getUserIds(messagesVO.getOtherUsers());
		List<String> userRoleList = getUserRoles(messagesVO.getOtherUsers());
		userAccessList.add(new Long(messagesVO.getCreatedUser().getUserID()));
		userRoleList.add(messagesVO.getCreatedUser().getRoleName());
		mongoMessagesVO.setUserList(userAccessList);
		mongoMessagesVO.setRoleList(userRoleList);
		// TODO: Take care of GMT conversion
		mongoMessagesVO.setCreatedDate(new Date(System.currentTimeMillis()));

		MongoCoreMessageService mongoMessageService = new MongoCoreMessageServiceImpl();
		LOG.debug("Saving Mongo message: " + mongoMessagesVO);
		return mongoMessageService.saveMessage(mongoMessagesVO);

	}

	private List<String> getUserRoles(List<MessageUserVO> otherUsers) {
		List<String> userroleList = new ArrayList<String>();
		for (MessageUserVO messageUserVO : otherUsers) {
			userroleList.add(messageUserVO.getRoleName());
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
		MongoCoreMessageService mongoMessageService = new MongoCoreMessageServiceImpl();
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

		messageVO
		        .setCreatedDate(utils
		                .getDateInUserLocaleFormatted(mongoMessagesVO
		                        .getCreatedDate()));
		messageVO.setParentId(mongoMessagesVO.getParentId());
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
}
