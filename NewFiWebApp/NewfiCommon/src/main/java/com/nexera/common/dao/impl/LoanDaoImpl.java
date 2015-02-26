package com.nexera.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;

@Component
@Transactional
public class LoanDaoImpl extends GenericDaoImpl implements LoanDao {

	@Override
	public List<Loan> getLoansOfUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("user", user));

		return criteria.list();
	}

	@Override
	public boolean addToLoanTeam(Loan loan, User user, User addedBy) {

		LoanTeam loanTeam = new LoanTeam();
		loanTeam.setUser(user);
		loanTeam.setAssignedBy(addedBy);
		loanTeam.setLoan(loan);
		loanTeam.setActive(true);
		loanTeam.setAssignedOn(new Date());

		Integer id = (Integer) this.save(loanTeam);

		if (id != null)
			return true;
		else
			return false;
	}

	@Override
	public boolean removeFromLoanTeam(Loan loan, User user) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("loan", loan));
		LoanTeam loanTeam = (LoanTeam) criteria.uniqueResult();

		if (loanTeam != null) {
			loanTeam.setActive(false);
			this.update(loanTeam);
			return true;
		}

		return false;
	}

	@Override
	public List<User> retreiveLoanTeam(Loan loan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Loan> retreiveLoansAsManager(User loanManager) {
		// TODO Auto-generated method stub
		return null;
	}
}
