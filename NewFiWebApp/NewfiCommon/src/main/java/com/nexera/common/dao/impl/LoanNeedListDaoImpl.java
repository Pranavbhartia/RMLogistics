package com.nexera.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanNeedListDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;

@Component
public class LoanNeedListDaoImpl extends GenericDaoImpl implements
        LoanNeedListDao {

	@Override
	public LoanNeedsList findLoanNeedsList(Loan loan,
	        NeedsListMaster needsListMaster) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanNeedsList.class);
		criteria.add(Restrictions.eq("loan", loan));
		criteria.add(Restrictions.eq("needsListMaster", needsListMaster));

		return (LoanNeedsList) criteria.uniqueResult();
	}
}
