package com.nexera.mongo.dao.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Component;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.mongo.dao.MongoMessageHeirarchyDAO;
import com.nexera.mongo.entity.MongoMessageHeirarchy;
import com.nexera.mongo.util.MongoConstants;

/**
 * @author Samarth Bhargav
 * 
 */
@Component
public class MongoMessageHeirarchyDAOImpl implements MongoMessageHeirarchyDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	protected DBObject constructQuery(MongoQueryVO query) {
		BasicDBObject q = new BasicDBObject();
		q.put("loanId", query.getLoanId());
		q.put("$or", Arrays.asList(
		        new BasicDBObject("roleList", query.getRoleName()),
		        new BasicDBObject("userList", query.getUserId())));
		if (query.getMessageType() != null) {
			q.put("messageType", query.getMessageType());
		}
		return q;
	}

	/**
	 * Saves a particular message hierarchy document into the collection
	 * 
	 * @param mh
	 */
	public void save(MongoMessageHeirarchy mh) {
		mongoTemplate.insert(mh, MongoConstants.MESSAGE_HEIRARCHY_COLLECTION);
	}

	/**
	 * Returns the hierarchy document for a message id
	 * 
	 * @param messageid
	 * @return
	 */
	public MongoMessageHeirarchy findLatestByMessageId(String messageId) {
		// TODO: Change Method name
		// TODO Add Test Case
		DBObject queryObject = QueryBuilder.start("lastMessage").is(messageId)
		        .get();
		BasicQuery query = new BasicQuery(queryObject);
		query.with(new Sort(Direction.DESC, "date"));

		MongoMessageHeirarchy messageHeirarchy = mongoTemplate.findOne(query,
		        MongoMessageHeirarchy.class,
		        MongoConstants.MESSAGE_HEIRARCHY_COLLECTION);
		return messageHeirarchy;
	}

	/**
	 * Fetches the mongo hierarchies for a particular query
	 * 
	 * @param mongoQueryVO
	 * @return
	 */
	public List<MongoMessageHeirarchy> findBy(MongoQueryVO mongoQueryVO) {
		int limit = mongoQueryVO.getNumberOfRecords();
		int skip = mongoQueryVO.getPageNumber() * limit;

		BasicQuery query = new BasicQuery(constructQuery(mongoQueryVO));
		query.with(new Sort(Direction.DESC, "date"));
		query.skip(skip);
		query.limit(limit);

		List<MongoMessageHeirarchy> messageHeirarchies = mongoTemplate.find(
		        query, MongoMessageHeirarchy.class,
		        MongoConstants.MESSAGE_HEIRARCHY_COLLECTION);

		return messageHeirarchies;
	}

}
