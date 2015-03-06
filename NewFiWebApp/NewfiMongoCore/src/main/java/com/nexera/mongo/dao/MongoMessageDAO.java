package com.nexera.mongo.dao;

import java.util.Map;
import java.util.Set;

import com.nexera.common.vo.mongo.MongoMessagesVO;

public interface MongoMessageDAO {
	String save(MongoMessagesVO message);

	MongoMessagesVO find(String id);

	Map<String, MongoMessagesVO> findMany(Set<String> messageIds);

	void remove(String id);
}
