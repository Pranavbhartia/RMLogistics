package com.nexera.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.User;


@Component
@Transactional
public class LoanDaoImpl extends GenericDaoImpl implements LoanDao {

	@Override
	public List<Loan> getLoansOfUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addToLoanTeam(Loan loan,User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFromLoanTeam(Loan loan,User user) {
		// TODO Auto-generated method stub
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
