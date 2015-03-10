package com.nexera.mongo.dao;

import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.nexera.common.vo.mongo.MongoMessagesVO;

/**
 * DAO class to store and retrieve message from mongodb
 *
 */
@Component
public interface MongoMessageDAO {
	
	/**
	 * Saves a particular message in mongo
	 * @param message
	 * @return
	 */
	String save(MongoMessagesVO message);

	/**
	 * Finds a message with a particular mongo id
	 * @param id
	 * @return
	 */
	MongoMessagesVO find(String id);

	/**
	 * Finds messages for all ids
	 * @param messageIds
	 * @return
	 */
	Map<String, MongoMessagesVO> findMany(Set<String> messageIds);

	/**
	 * Removes the mongo document of a particular id
	 * @param id
	 */
	void remove(String id);
}
