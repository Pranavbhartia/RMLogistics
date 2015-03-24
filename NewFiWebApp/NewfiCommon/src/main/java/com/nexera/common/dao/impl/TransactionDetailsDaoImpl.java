package com.nexera.common.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.TransactionDetailsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.Notification;
import com.nexera.common.exception.DatabaseException;

/**
 * The Dao class for the transaction details table.
 * @author karthik
 *
 */

@Component
public class TransactionDetailsDaoImpl extends GenericDaoImpl implements TransactionDetailsDao {
	
	public LoanApplicationFee findByLoan(Loan loan){
		try{
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanApplicationFee.class);
			criteria.add(Restrictions.eq("loan", loan));
			LoanApplicationFee loanApplicationFee=(LoanApplicationFee) criteria.uniqueResult();
			return loanApplicationFee;	
		}catch (HibernateException hibernateException) {
			throw new DatabaseException(
					"Exception caught in fetchUsersBySimilarEmailId() ",
					hibernateException);
		}
	}
	
}
