package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;

public interface LoanDao extends GenericDao {

	List<Loan> getLoansOfUser(User user);

	boolean addToLoanTeam(User user);

	boolean removeFromLoanTeam(User user);

	List<LoanTeam> retreiveLoanTeam(Loan loan);

	List<LoanTeam> retreiveLoansAsManager(User loanManager);
}
