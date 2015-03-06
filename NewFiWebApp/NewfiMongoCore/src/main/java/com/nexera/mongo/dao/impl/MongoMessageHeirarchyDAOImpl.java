package com.nexera.mongo.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.mongo.dao.MongoMessageHeirarchyDAO;
import com.nexera.mongo.entity.MongoMessageHeirarchy;
import com.nexera.mongo.util.MongoConnector;
import com.nexera.mongo.util.MongoConstants;
import com.nexera.mongo.util.MongoUtil;

/**
 * @author Samarth Bhargav
 * 
 */

public class MongoMessageHeirarchyDAOImpl implements MongoMessageHeirarchyDAO {

	private DBCollection collection;

	public MongoMessageHeirarchyDAOImpl() {
		this.collection = MongoConnector
				.getCollection(MongoConstants.MESSAGE_HEIRARCHY_COLLECTION);
	}

	protected DBObject toDBObject(MongoMessageHeirarchy m) {
		BasicDBObject obj = new BasicDBObject();
		obj.put("loan_id", m.getLoanId());
		obj.put("type", m.getMessageType());
		obj.put("date", m.getDate());
		// Never put in a null list
		obj.put("messages", m.getMessages() == null ? Collections.emptyList()
				: m.getMessages());
		obj.put("role_list", m.getRoleList() == null ? Collections.emptyList()
				: m.getRoleList());
		obj.put("user_list", m.getUserList() == null ? Collections.emptyList()
				: m.getUserList());
		return obj;
	}

	protected MongoMessageHeirarchy toMessageHeirarchy(DBObject o) {
		MongoMessageHeirarchy m = new MongoMessageHeirarchy();
		m.setDate((Date) o.get("date"));
		m.setLoanId((Long) o.get("loan_id"));
		m.setMessages(MongoUtil.dbListToStringList((BasicDBList) o
				.get("messages")));
		m.setMessageType(MongoUtil.safeToString(o.get("type")));
		m.setRoleList(MongoUtil.dbListToStringList((BasicDBList) o
				.get("role_list")));
		m.setUserList(MongoUtil.dbListToLongList((BasicDBList) o
				.get("user_list")));
		return m;
	}

	protected DBObject constructQuery(MongoQueryVO query) {
		BasicDBObject q = new BasicDBObject();
		q.put("loan_id", query.getLoanId());
		q.put("$or", Arrays.asList(
				new BasicDBObject("role_list", query.getRoleName()),
				new BasicDBObject("user_list", query.getUserId())));
		if (query.getMessageType() != null) {
			q.put("type", query.getMessageType());
		}
		return q;
	}

	public void save(MongoMessageHeirarchy mh) {
		this.collection.insert(toDBObject(mh));
	}

	public MongoMessageHeirarchy findLatestByMessageId(String messageId) {
		DBObject query = QueryBuilder.start("messages").is(messageId).get();
		DBCursor cursor = this.collection.find(query)
				.sort(new BasicDBObject("date", -1)).limit(1);
		try {
			if (cursor.hasNext()) {
				return toMessageHeirarchy(cursor.next());
			} else {
				return null;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public List<MongoMessageHeirarchy> findBy(MongoQueryVO mongoQueryVO) {
        int limit = mongoQueryVO.getNumberOfRecords();
        int skip = mongoQueryVO.getPageNumber() * limit;
        DBCursor cursor = this.collection.find(constructQuery(mongoQueryVO))
                .sort(new BasicDBObject("date", -1)).skip(skip).limit(limit);
        List<MongoMessageHeirarchy> list = new ArrayList<MongoMessageHeirarchy>();
        try {
            while (cursor.hasNext()) {
                list.add(toMessageHeirarchy(cursor.next()));
            }
            return list;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
