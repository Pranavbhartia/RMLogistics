package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanTurnAroundTimeDao;
import com.nexera.common.entity.LoanTurnAroundTime;
import com.nexera.common.entity.MileStoneTurnAroundTime;

@Component
public class LoanTurnAroundTimeDaoImpl extends GenericDaoImpl implements
		LoanTurnAroundTimeDao {

	@Override
	public void saveAllLoanTurnAroundTimeForLoan(
			List<LoanTurnAroundTime> turnAroundTimes) {
		this.saveAll(turnAroundTimes);

	}

	@Override
	public LoanTurnAroundTime loadLoanTurnAroundByLoanAndWorkitem(
			Integer loanId, Integer workFlowItemId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTurnAroundTime.class);

		criteria.add(Restrictions.eq("workflowItemMaster.id", workFlowItemId));

		criteria.add(Restrictions.eq("loan.id", loanId));
		return (LoanTurnAroundTime) criteria.uniqueResult();
	}

}
