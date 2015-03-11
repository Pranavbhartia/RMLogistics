package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.UserVO;

public interface LoanService {

	public List<LoanVO> getLoansOfUser(UserVO user);

	public LoanVO getLoanByID(Integer loanID);

	public boolean addToLoanTeam(LoanVO loan,UserVO user);

	public boolean removeFromLoanTeam(LoanVO loan,UserVO user);

	public List<UserVO> retreiveLoanTeam(LoanVO loan);

	public List<LoanVO> retreiveLoansAsManager(UserVO loanManager);
	
	public LoanVO getActiveLoanOfUser(UserVO user);

	LoanDashboardVO retrieveDashboard(UserVO user);

	LoanTeamListVO getLoanTeamListForLoan(LoanVO loan);

    LoansProgressStatusVO getLoansProgressForUser( Integer i );

    public UploadedFilesList fetchUploadedFromLoanNeedId(Integer loanNeedId);

    Loan parseLoanModel(LoanVO loanVO);
    public void saveWorkflowInfo(int loanID, int customerWorkflowID, int loanManagerWFID) ;


}
