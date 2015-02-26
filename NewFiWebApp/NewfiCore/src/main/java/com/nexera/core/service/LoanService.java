package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;

public interface LoanService {

	List<Loan> getLoansOfUser(User user);

	Loan getLoanByID(Integer loanID);

	boolean addToLoanTeam(User user);

	boolean removeFromLoanTeam(User user);

	List<LoanTeam> retreiveLoanTeam(Loan loan);

}
