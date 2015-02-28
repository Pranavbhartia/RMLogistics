package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.NeedsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;

@Component
@Transactional
public class NeedsDaoImpl extends GenericDaoImpl implements NeedsDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(NeedsDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	
	
	@Override
	public List<LoanNeedsList> getLoanNeedsList(int loanId) throws NoRecordsFetchedException {
		try{
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanNeedsList.class);
			Loan loan=new Loan();
			loan.setId(loanId);
			criteria.add(Restrictions.eq("loan", loan));
			List<LoanNeedsList> loanNeeds=(List<LoanNeedsList>)criteria.list();
			return loanNeeds;	
		}catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
					hibernateException);
			throw new DatabaseException(
					"Exception caught in fetchUsersBySimilarEmailId() ",
					hibernateException);
		}
	}
	/* (non-Javadoc)
	 * @see com.nexera.common.dao.NeedsDao#deleteLoanNeeds(int)
	 */
	public void deleteLoanNeed(LoanNeedsList need){
		Session session = sessionFactory.getCurrentSession();
		session.delete(need);
	}
	@Override
	public void deleteLoanNeeds(int loanId) {
		try {
			List<LoanNeedsList> list = getLoanNeedsList(loanId);
		    for (LoanNeedsList need: list)
		    	deleteLoanNeed(need);
		    LOG.debug("delete successful");
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw re;
		} catch (NoRecordsFetchedException e) {
			
		}
	}
	@Override
	public List<NeedsListMaster> getMasterNeedsList(Boolean isCustom) {
		try{
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(NeedsListMaster.class);
			criteria.add(Restrictions.eq("isCustom", isCustom));
			List<NeedsListMaster> loanNeeds=(List<NeedsListMaster>)criteria.list();
			return loanNeeds;
		}catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
					hibernateException);
			throw new DatabaseException(
					"Exception caught in fetchUsersBySimilarEmailId() ",
					hibernateException);
		}
	}
}
