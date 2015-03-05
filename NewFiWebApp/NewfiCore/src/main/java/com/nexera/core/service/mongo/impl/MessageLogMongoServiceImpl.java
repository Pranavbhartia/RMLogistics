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
import com.nexera.core.service.mongo.MessageLogMongoService;

@Component
public class MessageLogMongoServiceImpl implements MessageLogMongoService {

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
		return createDummyValues();
		// return null;
	}

	private MongoMessageHierarchyVO createDummyValues() {
		MongoMessageHierarchyVO hierarchyVO = new MongoMessageHierarchyVO();
		
		
		List<MongoMessagesVO> messageList = new ArrayList<MongoMessagesVO>();
		MongoMessagesVO messagesVO = null;
		for(int i=0;i<10;i++){
			 messagesVO = new MongoMessagesVO();
			 messagesVO.setBody("Random mesage: " + i);
			 
			 messageList.add(messagesVO);
			 messagesVO.setId(String.valueOf(i));
			 
		}
		hierarchyVO.setMessageList(messageList);

		List<MessageId> messageIds = hierarchyVO.createMessageIdVO();
		MessageId messageId = null;
		for(int i=0;i<10;i++){
			messageId = new MessageId();
			messageId.setMessageId(String.valueOf(i));
			for(int j=0;j<2;j++){
				messageId = new MessageId();
				messageId.setMessageId(String.valueOf(i));
				List<MessageId> list = messageId.createMessageIdVO();
			}
			messageIds.add(messageId);
		}
		
		messageId.setMessageId("Reply 1");
		
		//hierarchyVO.addToMessageId
		return hierarchyVO;
	}

}
