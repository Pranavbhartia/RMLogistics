package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;

public interface LoanDao extends GenericDao {

	List<Loan> getLoansOfUser(User user);

	boolean addToLoanTeam(Loan loan,User user);

	boolean removeFromLoanTeam(Loan loan,User user);

	List<LoanTeam> retreiveLoanTeam(Loan loan);

	List<LoanTeam> retreiveLoansAsManager(User loanManager);
}
