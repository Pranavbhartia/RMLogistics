/**
 * This interface provides methods for core db change.
 * It contains methods for load , update or delete of records. * 
 * @author Raremile
 * @version 1.0
 */
package com.nexera.common.dao;

import java.util.List;
import java.util.Map;

import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;

public interface GenericDao {

	public Object save(Object obj) throws DatabaseException;
	
	public void saveOrUpdate(Object obj) throws DatabaseException ;

	public void update(Object obj) throws DatabaseException;

	public Object load(Class cls, Integer id)
			throws DatabaseException;
	
	public Object load(@SuppressWarnings("rawtypes") Class cls, Object compositeKey)
		        throws DatabaseException ;

	public List loadAll(Class cls) throws DatabaseException;

	public List executeNamedQuery(final String namedQuery, final Map params)
			throws DatabaseException;
	
	public void saveAll(List data) throws DatabaseException;


	
}
