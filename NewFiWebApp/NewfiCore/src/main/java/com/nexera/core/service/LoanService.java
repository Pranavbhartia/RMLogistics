package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserVO;

public interface LoanService {

	public List<LoanVO> getLoansOfUser(UserVO user);

	public LoanVO getLoanByID(Integer loanID);

	public boolean addToLoanTeam(LoanVO loan, UserVO user);

	public boolean removeFromLoanTeam(LoanVO loan, UserVO user);

	public List<UserVO> retreiveLoanTeam(LoanVO loan);

	public List<LoanVO> retreiveLoansAsManager(UserVO loanManager);

	public LoanVO getActiveLoanOfUser(UserVO user);

	public LoanDashboardVO retrieveDashboard(UserVO user);

	public LoanDashboardVO retrieveDashboardForWorkLoans(UserVO user);

	public LoanDashboardVO retrieveDashboardForMyLoans(UserVO user);

	public LoanDashboardVO retrieveDashboardForArchiveLoans(UserVO user);

	LoanTeamListVO getLoanTeamListForLoan(LoanVO loan);

	LoansProgressStatusVO getLoansProgressForUser(Integer i);

	public UploadedFilesList fetchUploadedFromLoanNeedId(Integer loanNeedId);

	Loan parseLoanModel(LoanVO loanVO);

	LoanCustomerVO retrieveDashboard(UserVO userVO, LoanVO loanVO);

	public void saveWorkflowInfo(int loanID, int customerWorkflowID,
	        int loanManagerWFID);

	public LoanVO findWorkflowInfoById(int loanID);

	public List<Loan> getAllLoans();

	// TODO added for loan rest service
	public LoanVO createLoan(LoanVO loanVO);

	public Loan completeLoanModel(LoanVO loanVO);

	public List<TitleCompanyMasterVO> findTitleCompanyByName(
	        TitleCompanyMasterVO titleCompany);

	List<HomeOwnersInsuranceMasterVO> findHomeOwnInsByName(
	        HomeOwnersInsuranceMasterVO homeOwnInsVO);

	public LoanNeedsList fetchByNeedId(Integer needId);

	public TitleCompanyMasterVO addTitleCompany(TitleCompanyMasterVO vo);

	public HomeOwnersInsuranceMasterVO addHomeOwnInsCompany(
	        HomeOwnersInsuranceMasterVO vo);

	TitleCompanyMasterVO addToLoanTeam(LoanVO loan, TitleCompanyMasterVO titleCompany,
	        UserVO addedBy);

	HomeOwnersInsuranceMasterVO addToLoanTeam(LoanVO loan,
	        HomeOwnersInsuranceMasterVO homeOwnersInsurance, UserVO addedBy);

	public LoanMilestone findLoanMileStoneByLoan(Loan loan,
	        LoanMilestoneMaster loanMilestoneMaster);

	public List<LoanMilestoneMaster> getLoanMilestoneByLoanType(
	        LoanTypeMaster loanTypeMaster);
}
