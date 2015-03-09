/**
 * 
 */
package com.nexera.mongo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteConcern;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.common.vo.mongo.MongoMessagesVO.FileVO;
import com.nexera.mongo.dao.MongoMessageDAO;
import com.nexera.mongo.util.MongoConnector;
import com.nexera.mongo.util.MongoConstants;
import com.nexera.mongo.util.MongoUtil;

/**
 * @author Samarth Bhargav
 * 
 */


public class MongoMessageDAOImpl implements MongoMessageDAO {

	private DBCollection collection;

	protected static DBObject toDBObject(FileVO f) {
		BasicDBObject o = new BasicDBObject();
		o.put("fname", f.getFileName());
		o.put("url", f.getUrl());
		return o;
	}

	protected static FileVO toFVOObject(DBObject o) {
		FileVO f = new FileVO();
		f.setFileName(MongoUtil.safeToString(o.get("fname")));
		f.setUrl(MongoUtil.safeToString(o.get("url")));
		return f;
	}

	protected static DBObject toDBObject(MongoMessagesVO m) {
		BasicDBObject obj = new BasicDBObject();
		obj.put("body", m.getBody());
		obj.put("created_by", m.getCreatedBy());
		obj.put("created_date", m.getCreatedDate());
		FileVO[] fileVO = m.getLinks();
		BasicDBList fvoList = new BasicDBList();
		if (fileVO != null) {
			for (FileVO f : fileVO) {
				fvoList.add(toDBObject(f));
			}
		}
		obj.put("links", fvoList);
		obj.put("loan_id", m.getLoanId());
		obj.put("parent", m.getParentId());
		obj.put("type", m.getMessageType());
		obj.put("role_list", m.getRoleList());
		obj.put("role_name", m.getRoleName());
		obj.put("user_list", m.getUserList());
		return obj;
	}

	protected static MongoMessagesVO toMessage(DBObject o) {
		if (o == null) {
			return null;
		}
		MongoMessagesVO m = new MongoMessagesVO();
		m.setId(o.get("_id").toString());
		m.setBody(MongoUtil.safeToString(o.get("body")));
		m.setCreatedBy((Long) o.get("created_by"));
		m.setCreatedDate((Date) o.get("created_date"));
		m.setLoanId((Long) o.get("loan_id"));
		m.setParentId(MongoUtil.safeToString(o.get("parent")));
		m.setMessageType(MongoUtil.safeToString(o.get("type")));

		BasicDBList list = (BasicDBList) o.get("links");
		List<FileVO> fvoList = new ArrayList<MongoMessagesVO.FileVO>();
		for (Object fvo : list) {
			fvoList.add(toFVOObject((DBObject) fvo));
		}
		m.setLinks(fvoList.toArray(new FileVO[] {}));

		m.setRoleList(MongoUtil.dbListToStringList((BasicDBList) o
				.get("role_list")));
		m.setRoleName(MongoUtil.safeToString(o.get("role_name")));
		m.setUserList(MongoUtil.dbListToLongList((BasicDBList) o
				.get("user_list")));
		return m;
	}

	public MongoMessageDAOImpl() {
		this.collection = MongoConnector
				.getCollection(MongoConstants.MESSAGE_COLLECTION);
	}

	public String save(MongoMessagesVO message) {
		DBObject doc = toDBObject(message);
		this.collection.insert(doc, WriteConcern.ACKNOWLEDGED);
		ObjectId id = (ObjectId) doc.get("_id");
		return id.toString();
	}

	public MongoMessagesVO find(String id) {
		return toMessage(this.collection.findOne(new BasicDBObject("_id",
				new ObjectId(id))));
	}

	public void remove(String id) {
		this.collection.remove(new BasicDBObject("_id", new ObjectId(id)));
	}

	public Map<String, MongoMessagesVO> findMany(Set<String> ids) {
		List<ObjectId> objectIds = new ArrayList<ObjectId>();
		for (String id : ids) {
			objectIds.add(new ObjectId(id));
		}
		Map<String, MongoMessagesVO> idMap = new HashMap<String, MongoMessagesVO>();
		DBObject query = QueryBuilder.start("_id").in(objectIds).get();
		DBCursor cursor = this.collection.find(query);
		try {
			while (cursor.hasNext()) {
				MongoMessagesVO vo = toMessage(cursor.next());
				idMap.put(vo.getId(), vo);
			}
			return idMap;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}
