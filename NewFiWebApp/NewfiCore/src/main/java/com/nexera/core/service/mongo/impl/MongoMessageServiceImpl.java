package com.nexera.core.service.mongo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO.MessageId;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.core.service.mongo.MongoMessageService;

@Component
public class MongoMessageServiceImpl implements MongoMessageService {

	@Override
	public String saveMessage(MongoMessagesVO messagesVO)
	        throws FatalException, NonFatalException {

		/*
		 * This method is expected to save the messageVO oject and return the
		 * mongo message ID.
		 */
		return null;
	}

	@Override
	public MongoMessageHierarchyVO getMessages(MongoQueryVO mongoQueryVO)
	        throws FatalException, NonFatalException {
		// TODO Auto-generated method stub
		/*
		 * This method should construct the hierarchy object based on the
		 * queryVO.
		 */
		MongoMessageHierarchyVO hierarchyVO =  createDummyValues();

		
		
		return hierarchyVO;

	}

	private MongoMessageHierarchyVO createDummyValues() {
		MongoMessageHierarchyVO hierarchyVO = new MongoMessageHierarchyVO();
		
		
		List<MongoMessagesVO> messageList = new ArrayList<MongoMessagesVO>();
		MongoMessagesVO messagesVO = null;
		for(int i=0;i<5;i++){
			 messagesVO = new MongoMessagesVO();
			 messagesVO.setBody("Random mesage: " + i);
			 
			 messageList.add(messagesVO);
			 messagesVO.setId(String.valueOf(i));
			 
		}
		hierarchyVO.setMessageList(messageList);

		List<MessageId> ids = hierarchyVO.createMessageIdVO();
		
		MessageId messageId = new MessageId();
		messageId.setMessageId("4");
		
		MessageId messageId2 = new MessageId();
		messageId2.setMessageId("2");
		messageId.createMessageIdVO().add(messageId2);
		
		MessageId messageId3 = new MessageId();
		messageId3.setMessageId("1");
		
		messageId2.createMessageIdVO().add(messageId3);
		
		
		ids.add(messageId);
		
		
		//hierarchyVO.addToMessageId
		return hierarchyVO;
	}

}
