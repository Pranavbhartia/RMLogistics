package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.dao.NeedsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.UploadedFilesList;
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
	public List<LoanNeedsList> getLoanNeedsList(int loanId)
	        throws NoRecordsFetchedException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanNeedsList.class);
			
			Loan loan = new Loan();
			loan.setId(loanId);
			
			criteria.createAlias("needsListMaster","nlm");
			
			criteria.add(Restrictions.eq("loan", loan));
			criteria.add(Restrictions.ne("nlm.label",
			        CommonConstants.LQB_DOC_TYPE_CR));
			
			List<LoanNeedsList> loanNeeds = criteria.list();
			return loanNeeds;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nexera.common.dao.NeedsDao#deleteLoanNeeds(int)
	 */
	@Override
	public void deleteLoanNeed(LoanNeedsList need) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(need);
	}

	@Override
	public void deleteLoanNeeds(int loanId) {
		try {
			List<LoanNeedsList> list = getLoanNeedsList(loanId);
			for (LoanNeedsList need : list)
				deleteLoanNeed(need);
			LOG.debug("delete successful");
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw re;
		} catch (NoRecordsFetchedException e) {

		}
	}

	@Override
	public Integer getLoanNeedListIdByFileId(Integer fileId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanNeedsList.class);
		criteria.createAlias("uploadFileId", "upFileId");
		criteria.add(Restrictions.eq("upFileId.id", fileId));
		LoanNeedsList loanNeedsList = (LoanNeedsList) criteria.uniqueResult();
		if (loanNeedsList != null) {
			LOG.info("loanNeedsList not empty");
			return loanNeedsList.getNeedsListMaster().getId();
		}
		LOG.info("loanNeedsList empty");
		return null;
	}

	@Override
	public List<NeedsListMaster> getMasterNeedsList(Boolean isCustom) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(NeedsListMaster.class);
			criteria.add(Restrictions.eq("isCustom", isCustom));
			List<NeedsListMaster> loanNeeds = criteria.list();
			return loanNeeds;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}
	}

	@Override
	public LoanNeedsList findNeedForLoan(Loan loan,
	        NeedsListMaster needsListMaster) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanNeedsList.class);
			criteria.add(Restrictions.eq("loan", loan));
			criteria.add(Restrictions.eq("needsListMaster", needsListMaster));
			List<LoanNeedsList> list = criteria.list();
			if (list.size() > 0)
				return list.get(0);
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}
		return null;
	}

	@Override
	public NeedsListMaster findNeedsListMasterByLabel(String label) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(NeedsListMaster.class);
			criteria.add(Restrictions.eq("label", label));
			List<NeedsListMaster> list = criteria.list();
			if (list.size() > 0)
				return list.get(0);
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}
		return null;
	}

	@Override
	public LoanNeedsList findLoanNeedByMaster(Loan loan,
	        NeedsListMaster needListMaster) {
		LoanNeedsList loanNeedsList = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanNeedsList.class);
			criteria.add(Restrictions.eq("loan", loan));
			criteria.add(Restrictions.eq("needsListMaster", needListMaster));
			List<LoanNeedsList> list = criteria.list();
			if (list.size() > 0)
				loanNeedsList = list.get(0);
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}
		return loanNeedsList;
	}

	@Override
	public String checkCreditReport(Integer loanID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UploadedFilesList.class);
		Loan loan = new Loan(loanID);
		criteria.add(Restrictions.eq("loan", loan));
		criteria.add(Restrictions.eq("documentType",
		        CommonConstants.LQB_DOC_TYPE_CR));
		criteria.addOrder(Order.desc("uploadedDate"));
		List<UploadedFilesList> filesLists = criteria.list();

		if (filesLists == null || filesLists.isEmpty()) {
			return null;
		}
		UploadedFilesList latestFile = filesLists.get(0);
		return latestFile.getUuidFileId();
	}
	
	@Override
    public NeedsListMaster getNeed(Integer needId) {
		NeedsListMaster needsListMaster;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NeedsListMaster.class);
		criteria.add(Restrictions.eq("id", needId));		
		needsListMaster =  (NeedsListMaster) criteria.uniqueResult();
		return needsListMaster;
    }
}
