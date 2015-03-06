/**
 * 
 */
package com.nexera.mongo.dao;

import java.util.List;

import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.mongo.entity.MongoMessageHeirarchy;

/**
 * @author Samarth Bhargav
 * 
 */
public interface MongoMessageHeirarchyDAO {
	void save(MongoMessageHeirarchy mh);

	MongoMessageHeirarchy findLatestByMessageId(String messageid);

	List<MongoMessageHeirarchy> findBy(MongoQueryVO mongoQueryVO);
}
