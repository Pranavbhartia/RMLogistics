package com.nexera.mongo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.mongo.dao.MongoMessageDAO;
import com.nexera.mongo.dao.MongoMessageHeirarchyDAO;
import com.nexera.mongo.entity.MongoMessageHeirarchy;
import com.nexera.mongo.service.MongoCoreMessageService;

@Component
public class MongoCoreMessageServiceImpl implements MongoCoreMessageService {
	
	@Autowired
	private MongoMessageDAO messageDAO;
	
	@Autowired
	private MongoMessageHeirarchyDAO heriarchyDAO;
	
	private static final Logger LOG = LoggerFactory
	        .getLogger(MongoCoreMessageServiceImpl.class);

	/**
	 * Saves a message into mongo and returns id
	 * @param messagesVO
	 * @return
	 * @throws FatalException
	 * @throws NonFatalException
	 */
	public String saveMessage(MongoMessagesVO messagesVO)
			throws FatalException, NonFatalException {
		String id = messageDAO.save(messagesVO);
 
		// If Parent ID is null, then it is a new message, and not part of a
		// chain
		if (messagesVO.getParentId() == null) {
			MongoMessageHeirarchy mh = new MongoMessageHeirarchy();
			mh.setDate(new Date(System.currentTimeMillis()));
			mh.setLoanId(messagesVO.getLoanId());
			mh.setMessages(Arrays.asList(id));
			mh.setMessageType(messagesVO.getMessageType());
			mh.setUserList(messagesVO.getUserList());
			heriarchyDAO.save(mh);
		} else {
			// It is not a new message
			// fetch the oldest hierarchy, add a new record containing this one
			MongoMessageHeirarchy mh = heriarchyDAO
					.findLatestByMessageId(messagesVO.getParentId());
			if (mh == null) {
				// This should never happen
				throw new FatalException("Inconsistent Database state");
			}
			// Add the new ID to the end of the list
			mh.getMessages().add(id);
			// Update the date
			mh.setDate(new Date(System.currentTimeMillis()));
			// Save the new record
			heriarchyDAO.save(mh);
		}
		return id;
	}

	/**
	 * Fetches messages and constructs hierarchy from mongo based on the query
	 * @param mongoQueryVO
	 * @return
	 * @throws FatalException
	 * @throws NonFatalException
	 */
	public MongoMessageHierarchyVO getMessages(MongoQueryVO mongoQueryVO)
			throws FatalException, NonFatalException {
		LOG.info("MongoCoreMessageServiceImpl : getMessages method called.");
		
		// First, find the ordered Heirarchy
		List<MongoMessageHeirarchy> mhList = heriarchyDAO
				.findBy(mongoQueryVO);
		
		LOG.info(" Message size retrieved : " + mhList.size());

		Set<String> messageIds = new HashSet<String>();
		List<List<String>> threadList = new ArrayList<List<String>>();

		for (MongoMessageHeirarchy mh : mhList) {
			List<String> mids = mh.getMessages();
			messageIds.addAll(mids);
			threadList.add(mids);
		}
		// Find all the messages
		Map<String, MongoMessagesVO> idMap = messageDAO
				.findMany(messageIds);

		// Construct the Hierarchy object
		MongoMessageHierarchyVO mhvo = new MongoMessageHierarchyVO();
		mhvo.setMessageIds(threadList);

		for (Entry<String, MongoMessagesVO> entry : idMap.entrySet()) {
			mhvo.setMongoMessages(entry.getKey(), entry.getValue());
		}
		
		LOG.info("MongoCoreMessageServiceImpl : end of method getMessages.");
		return mhvo;
	}

}
