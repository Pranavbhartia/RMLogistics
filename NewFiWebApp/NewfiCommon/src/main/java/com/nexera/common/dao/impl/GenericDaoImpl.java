/**
 * This implementation class provides methods for core db change.
 * It contains methods for load , update or delete of records. 
 * @author RareMile
 * @version 1.0
 */
package com.nexera.common.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.GenericDao;
import com.nexera.common.exception.DatabaseException;

@Component
public class GenericDaoImpl implements GenericDao {

	private static final int BATCH_SIZE = 5;

	@Autowired
	protected SessionFactory sessionFactory;

	@Override
    public Object save(Object obj) throws DatabaseException {

		Session session = sessionFactory.getCurrentSession();

		try {
			return session.save(obj);

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	@Override
    public void saveOrUpdate(Object obj) throws DatabaseException {

		Session session = sessionFactory.getCurrentSession();

		try {
			session.saveOrUpdate(obj);

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	@Override
    public void update(Object obj) throws DatabaseException {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(obj);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	/**
	 * Use this method to load a plain object, i.e a class without any
	 * associations. Load does not initialise the associations of a object.
	 */
	@Override
    public Object load(@SuppressWarnings("rawtypes") Class cls, Integer id)
	        throws DatabaseException {

		Session session = sessionFactory.getCurrentSession();
		Object obj = null;
		try {
			obj = session.get(cls, id);
			return obj;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}


    public Object load(@SuppressWarnings("rawtypes") Class cls, Object compositeKey)
	        throws DatabaseException {

		Session session = sessionFactory.getCurrentSession();
		Object obj = null;
		try {
			obj = session.get(cls, (Serializable) compositeKey);
			return obj;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}
	
	/**
	 * Use this method to load a plain object, i.e a class without any
	 * associations. Load does not initialise the associations of a object.
	 */
	@Override
    public List loadAll(@SuppressWarnings("rawtypes") Class cls)
	        throws DatabaseException {

		Session session = sessionFactory.getCurrentSession();

		try {
			//
			return session.createCriteria(cls).setCacheable(Boolean.TRUE)
			        .list();

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	/**
	 * This method take namedQuery and params as parameters , execute namedQuery
	 * query by passing params key , value and return List of required object.
	 * 
	 * @param String
	 *            - namedQuery
	 * @return Map - params
	 * @return List of objects
	 * @throws DatabaseException
	 * @throws NewsPortalException
	 *             exception
	 */

	@Override
    public List executeNamedQuery(final String namedQuery, final Map params)
	        throws DatabaseException {
		final Session session = sessionFactory.getCurrentSession();
		try {
			final Query query = session.getNamedQuery(namedQuery);
			if (params != null) {
				query.setProperties(params);
			}
			query.setCacheable(Boolean.TRUE);

			return query.list();

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	@Override
    @Transactional
	public void saveAll(List data) throws DatabaseException {
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();
			for (int i = 0; i < data.size(); i++) {
				Object object = data.get(i);
				session.save(object);
				if (i != 0 && i % BATCH_SIZE == 0)
					session.flush();

			}

			session.flush();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

}
