/**
 * 
 */
package com.nexera.mongo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Component;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.mongo.dao.MongoMessageDAO;
import com.nexera.mongo.util.MongoConstants;

/**
 * @author Samarth Bhargav
 * 
 */

@Component
public class MongoMessageDAOImpl implements MongoMessageDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	
	/**
	 * Saves a particular message in mongo
	 * @param message
	 * @return
	 */
	public String save(MongoMessagesVO message) {
		mongoTemplate.insert(message, MongoConstants.MESSAGE_COLLECTION);
		return message.getId();
	}
	
	/**
	 * Finds a message with a particular mongo id
	 * @param id
	 * @return
	 */
	public MongoMessagesVO find(String id) {
		
		return mongoTemplate.findOne(new BasicQuery(new BasicDBObject(MongoConstants.MONGO_ID, id)), MongoMessagesVO.class, MongoConstants.MESSAGE_COLLECTION);
	}
	
	/**
	 * Removes the mongo document of a particular id
	 * @param id
	 */
	public void remove(String id) {
		mongoTemplate.remove(new BasicQuery(new BasicDBObject(MongoConstants.MONGO_ID,id)), MongoMessagesVO.class, MongoConstants.MESSAGE_COLLECTION);
	}
	
	/**
	 * Finds messages for all ids
	 * @param messageIds
	 * @return
	 */
	public Map<String, MongoMessagesVO> findMany(Set<String> ids) {
		List<ObjectId> objectIds = new ArrayList<ObjectId>();
		for (String id : ids) {
			objectIds.add(new ObjectId(id));
		}
		Map<String, MongoMessagesVO> idMap = new HashMap<String, MongoMessagesVO>();
		DBObject query = QueryBuilder.start(MongoConstants.MONGO_ID).in(objectIds).get();
		List<MongoMessagesVO> messages = mongoTemplate.find(new BasicQuery(query), MongoMessagesVO.class, MongoConstants.MESSAGE_COLLECTION);
		
		for( MongoMessagesVO messagesVO : messages){
			idMap.put(messagesVO.getId(), messagesVO);
		}
		
		return idMap;
	}
}
