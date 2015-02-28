package com.nexera.core.service;

import java.util.List;

import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.UserVO;

public interface LoanService {

	List<LoanVO> getLoansOfUser(UserVO user);

	LoanVO getLoanByID(Integer loanID);

	boolean addToLoanTeam(LoanVO loan,UserVO user);

	boolean removeFromLoanTeam(LoanVO loan,UserVO user);

	List<UserVO> retreiveLoanTeam(LoanVO loan);

	List<LoanVO> retreiveLoansAsManager(UserVO loanManager);
	
	LoanDashboardVO retrieveDashboard(UserVO user);
	LoanTeamListVO getLoanTeamListForLoan(LoanVO loan);

    LoansProgressStatusVO getLoansProgressForUser( Integer i );


}
