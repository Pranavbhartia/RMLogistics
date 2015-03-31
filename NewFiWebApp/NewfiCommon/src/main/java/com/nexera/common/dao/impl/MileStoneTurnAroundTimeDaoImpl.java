package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.MileStoneTurnAroundTimeDao;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.MileStoneTurnAroundTime;
import com.nexera.common.entity.User;
import com.nexera.common.entity.WorkflowItemMaster;

@Component
public class MileStoneTurnAroundTimeDaoImpl extends GenericDaoImpl implements
		MileStoneTurnAroundTimeDao {

	@Override
	public List<WorkflowItemMaster> loadAllData() {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WorkflowItemMaster.class);

		criteria.add(Restrictions.ne("displayTurnAroundOrder", -1));
		criteria.addOrder(Order.asc("displayTurnAroundOrder"));
		/*
		 * List<MileStoneTurnAroundTime> aroundTimes = this
		 * .loadAll(MileStoneTurnAroundTime.class); for (MileStoneTurnAroundTime
		 * mileStoneTurnAroundTime : aroundTimes) {
		 * Hibernate.initialize(mileStoneTurnAroundTime
		 * .getWorkflowItemMaster()); }
		 */

		// return aroundTimes;
		return criteria.list();
	}

	@Override
	public void saveOrUpdateMileStoneTurnAroundTime(
			List<MileStoneTurnAroundTime> stoneTurnAroundTimes) {
		for (MileStoneTurnAroundTime mileStoneTurnAroundTime : stoneTurnAroundTimes) {
			this.saveOrUpdate(mileStoneTurnAroundTime);
		}
	}

	@Override
	public MileStoneTurnAroundTime loadByWorkItem(Integer workflowItemMasterId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(MileStoneTurnAroundTime.class);

		criteria.add(Restrictions.eq("workflowItemMaster.id",
				workflowItemMasterId));
		return (MileStoneTurnAroundTime) criteria.uniqueResult();
	}

}
