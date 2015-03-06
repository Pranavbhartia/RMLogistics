package com.nexera.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.MessageHierarchyVO;
import com.nexera.common.vo.MessageQueryVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.core.service.MessageService;
import com.nexera.core.service.mongo.MongoMessageService;

@Component
public class MessageServiceImpl implements MessageService {

	@Autowired
	MongoMessageService mongoMessageService;

	@Autowired
	UserProfileDao userProfileDao;

	@Override
	public String saveMessage(MessageVO messagesVO) throws FatalException,
	        NonFatalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageHierarchyVO getMessages(MessageQueryVO QueryVO)
	        throws FatalException, NonFatalException {
		// TODO Auto-generated method stub

		MongoQueryVO mongoQueryVO = new MongoQueryVO();

		MongoMessageHierarchyVO mongoHierarchyVO = mongoMessageService
		        .getMessages(mongoQueryVO);

		MessageHierarchyVO messageHierarchyVO = new MessageHierarchyVO();

		
		return null;
	}

	

}
