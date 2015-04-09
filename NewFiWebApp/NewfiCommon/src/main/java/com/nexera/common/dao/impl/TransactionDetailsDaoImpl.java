package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.dao.TransactionDetailsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.exception.DatabaseException;

/**
 * The Dao class for the transaction details table.
 * 
 * @author karthik
 *
 */

@Component
public class TransactionDetailsDaoImpl extends GenericDaoImpl implements
        TransactionDetailsDao {

	@Override
	public LoanApplicationFee findByLoan(Loan loan) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session
			        .createCriteria(LoanApplicationFee.class);
			criteria.add(Restrictions.eq("loan", loan));
			LoanApplicationFee loanApplicationFee = (LoanApplicationFee) criteria
			        .uniqueResult();
			return loanApplicationFee;
		} catch (HibernateException hibernateException) {
			throw new DatabaseException("Exception caught in findByLoan() ",
			        hibernateException);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionDetails> findActiveTransactionsByLoan(Loan loan) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(TransactionDetails.class);
		criteria.add(Restrictions.eq("loan", loan));
		criteria.add(Restrictions.eq("status",
		        CommonConstants.TRANSACTION_STATUS_ENABLED));
		return criteria.list();
	}

}
