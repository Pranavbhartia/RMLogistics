package com.nexera.common.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;

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

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("loan", loan));
		List<LoanTeam> team = criteria.list();

		if (team != null && team.size() > 0) {
			List<User> userList = new ArrayList<User>();
			for (LoanTeam loanTeam : team) {
				User user = loanTeam.getUser();
				if (user != null)
					Hibernate.initialize(user.getUserRole());
				userList.add(user);
			}

			return userList;
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<Loan> retreiveLoansAsManager(User loanManager) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user", loanManager));
		List<LoanTeam> team = criteria.list();

		if (team != null && team.size() > 0) {
			List<Loan> loanList = new ArrayList<Loan>();
			for (LoanTeam loanTeam : team) {
				Loan loan = loanTeam.getLoan();
				loanList.add(loan);
			}
			return loanList;

		}

		return Collections.EMPTY_LIST;
	}

	@Override
	public LoanAppForm getLoanAppForm(Integer loanId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanAppForm.class);
			Loan loan = new Loan();
			loan.setId(loanId);
			criteria.add(Restrictions.eq("loan", loan));
			LoanAppForm appForm = (LoanAppForm) criteria.uniqueResult();
			return appForm;
		} catch (HibernateException hibernateException) {

			throw new DatabaseException(
					"Exception caught in fetchUsersBySimilarEmailId() ",
					hibernateException);
		}
	}
}
