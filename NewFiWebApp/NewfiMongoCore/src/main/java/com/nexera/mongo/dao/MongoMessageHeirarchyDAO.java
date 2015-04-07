/**
 * 
 */
package com.nexera.mongo.dao;

import org.springframework.stereotype.Component;

import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.mongo.entity.MongoMessageHeirarchy;
import com.nexera.mongo.entity.MongoQueryResult;

/**
 * @author Samarth Bhargav
 * 
 */

/**
 * Dao class for the mongo message hierarchy collection
 * 
 */
@Component
public interface MongoMessageHeirarchyDAO {

	/**
	 * Saves a particular message hierarchy document into the collection
	 * 
	 * @param mh
	 */
	void save(MongoMessageHeirarchy mh);

	/**
	 * Returns the hierarchy document for a message id
	 * 
	 * @param messageid
	 * @return
	 */
	MongoMessageHeirarchy findLatestByMessageId(String messageid);

	/**
	 * Fetches the mongo hierarchies for a particular query
	 * 
	 * @param mongoQueryVO
	 * @return
	 */
	MongoQueryResult findBy(MongoQueryVO mongoQueryVO);
}
