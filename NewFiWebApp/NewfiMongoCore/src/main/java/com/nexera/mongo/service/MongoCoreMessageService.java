package com.nexera.mongo.service;

import org.springframework.stereotype.Component;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.common.vo.mongo.MongoQueryVO;

/**
 * The service class for the communication logs in mongodb
 *
 */
@Component
public interface MongoCoreMessageService {
	
	/**
	 * Saves a message into mongo and returns id
	 * @param messagesVO
	 * @return
	 * @throws FatalException
	 * @throws NonFatalException
	 */
	public String saveMessage(MongoMessagesVO messagesVO)
			throws FatalException, NonFatalException;
	
	/**
	 * Fetches messages and constructs hierarchy from mongo based on the query
	 * @param mongoQueryVO
	 * @return
	 * @throws FatalException
	 * @throws NonFatalException
	 */
	public MongoMessageHierarchyVO getMessages(MongoQueryVO mongoQueryVO)
			throws FatalException, NonFatalException;
}
