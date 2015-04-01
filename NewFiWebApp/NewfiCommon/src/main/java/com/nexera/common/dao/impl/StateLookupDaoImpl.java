package com.nexera.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.nexera.common.dao.StateLookupDao;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;

@Component
@Transactional
public class StateLookupDaoImpl extends GenericDaoImpl implements StateLookupDao{
	
	private static final Logger LOG = LoggerFactory
	        .getLogger(StateLookupDaoImpl.class);
	        		
	@Override
	public StateLookup findStateLookupByStateCode(String stateCode) throws NoRecordsFetchedException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(StateLookup.class);
			criteria.add(Restrictions.eq("statecode", stateCode));
			Object obj = criteria.uniqueResult();
			if (obj == null) {
				throw new NoRecordsFetchedException("Record for statecode : " + stateCode + " not found in database");
			}
			StateLookup lookup = (StateLookup) obj;
			return lookup;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in findStateLookupByStateCode() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in findStateLookupByStateCode() ",
			        hibernateException);
		}
	}

}
