package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.User;

public interface LoanDao extends GenericDao {

	public List<Loan> getLoansOfUser(User user);

	public boolean addToLoanTeam(Loan loan,User user,User addedBy);

	public boolean removeFromLoanTeam(Loan loan,User user);

	public List<User> retreiveLoanTeam(Loan loan);

	public List<Loan> retreiveLoansAsManager(User loanManager);
	
    public LoanAppForm getLoanAppForm(Integer loanId);

	public Loan getActiveLoanOfUser(User parseUserModel);
}
