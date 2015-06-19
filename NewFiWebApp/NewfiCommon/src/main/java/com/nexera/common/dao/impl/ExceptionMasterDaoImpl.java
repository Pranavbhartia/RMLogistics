package com.nexera.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.ExceptionMasterDao;
import com.nexera.common.entity.ExceptionMaster;

@Component
public class ExceptionMasterDaoImpl extends GenericDaoImpl implements
        ExceptionMasterDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(ExceptionMasterDaoImpl.class);

	@Override
	public ExceptionMaster getExceptionMasterByType(String exceptionType) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ExceptionMaster.class);
		criteria.add(Restrictions.eq("exceptionType", exceptionType));
		return (ExceptionMaster) criteria.uniqueResult();
	}

}
