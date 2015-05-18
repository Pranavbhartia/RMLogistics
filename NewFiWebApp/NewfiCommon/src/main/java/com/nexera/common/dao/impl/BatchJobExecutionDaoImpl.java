package com.nexera.common.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.BatchJobExecutionDao;
import com.nexera.common.entity.BatchJobExecution;

@Component
public class BatchJobExecutionDaoImpl extends GenericDaoImpl implements
        BatchJobExecutionDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(BatchJobExecutionDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public BatchJobExecution getLastUpdatedRecord(int batchjobId) {
		LOG.debug("Inside method getLastUpdatedRecord");
		Session session = sessionFactory.getCurrentSession();

		Query query = session
		        .createQuery("from BatchJobExecution bj where bj.batchJobMaster.id = :batch_job_id order by bj.id DESC");
		query.setParameter("batch_job_id", batchjobId);
		query.setMaxResults(1);
		BatchJobExecution last = (BatchJobExecution) query.uniqueResult();
		return last;
	}

}
