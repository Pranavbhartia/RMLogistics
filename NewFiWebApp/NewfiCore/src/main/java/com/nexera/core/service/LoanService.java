package com.nexera.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LeadsDashBoardVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanLockRateVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTurnAroundTimeVO;
import com.nexera.common.vo.LoanUserSearchVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.SortingLoanListVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserLoanStatus;
import com.nexera.common.vo.UserVO;

public interface LoanService {

	public List<LoanVO> getLoansOfUser(UserVO user);

	public void sendRateLocked(Integer loanId) throws InvalidInputException,
	        UndeliveredEmailException;

	public LoanVO getLoanByID(Integer loanID);

	public boolean addToLoanTeam(LoanVO loan, UserVO user);

	public boolean removeFromLoanTeam(LoanVO loan, UserVO user);

	public boolean removeFromLoanTeam(LoanVO loan,
	        TitleCompanyMasterVO titleCompany);

	public boolean removeFromLoanTeam(LoanVO loan,
	        HomeOwnersInsuranceMasterVO homeOwnersInsurance);

	public List<UserVO> retreiveLoanTeam(LoanVO loan);

	public List<UserVO> retreiveLoanManagers(LoanVO loanVO);

	public List<LoanVO> retreiveLoansAsManager(UserVO loanManager);

	public LoanVO getActiveLoanOfUser(UserVO user);

	public LoanDashboardVO retrieveDashboard(UserVO user);

	public LoanDashboardVO retrieveDashboardForWorkLoans(UserVO user);

	public LoanDashboardVO retrieveDashboardForArchiveLoans(UserVO user);

	public List<Loan> getAllActiveLoan();

	public Loan fetchLoanById(Integer loanId);

	public void saveWorkflowInfo(int loanID, int customerWorkflowID,
	        int loanManagerWFID);

	public LoanVO findWorkflowInfoById(int loanID);

	public List<Loan> getAllLoans();

	public List<Loan> getLoansInActiveStatus();

	// TODO added for loan rest service
	public LoanVO createLoan(LoanVO loanVO);

	public Loan completeLoanModel(LoanVO loanVO);

	public List<TitleCompanyMasterVO> findTitleCompanyByName(
	        TitleCompanyMasterVO titleCompany);

	List<HomeOwnersInsuranceMasterVO> findHomeOwnInsByName(
	        HomeOwnersInsuranceMasterVO homeOwnInsVO);

	public LoanNeedsList fetchByNeedId(Integer needId, Integer loanId);

	public TitleCompanyMasterVO addTitleCompany(TitleCompanyMasterVO vo);

	public HomeOwnersInsuranceMasterVO addHomeOwnInsCompany(
	        HomeOwnersInsuranceMasterVO vo);

	TitleCompanyMasterVO addToLoanTeam(LoanVO loan,
	        TitleCompanyMasterVO titleCompany, UserVO addedBy);

	HomeOwnersInsuranceMasterVO addToLoanTeam(LoanVO loan,
	        HomeOwnersInsuranceMasterVO homeOwnersInsurance, UserVO addedBy);

	public LoanMilestone findLoanMileStoneByLoan(Loan loan,
	        String loanMilestoneMAsterName);

	public List<LoanMilestoneMaster> getLoanMilestoneByLoanType(
	        LoanTypeMaster loanTypeMaster);

	public LoanMilestone saveLoanMilestone(LoanMilestone loanMilestone);

	LoanTeamListVO getLoanTeamListForLoan(LoanVO loanVO);

	Loan parseLoanModel(LoanVO loanVO);

	LoanCustomerVO retrieveDashboard(UserVO userVO, LoanVO loanVO);

	LoansProgressStatusVO getLoansProgressForUser(Integer userId);

	UploadedFilesList fetchUploadedFromLoanNeedId(Integer loanNeedId);

	public void updateLoanMilestone(LoanMilestone loanMilestone);

	TitleCompanyMasterVO findTitleCompanyOfLoan(LoanVO loan);

	HomeOwnersInsuranceMasterVO findHomeOwnersInsuranceCompanyOfLoan(LoanVO loan);

	ExtendedLoanTeamVO findExtendedLoanTeam(LoanVO loanVO);

	public LoanTurnAroundTimeVO retrieveTurnAroundTimeByLoan(Integer loanId,
	        Integer workFlowItemId);

	public LoanNeedsList fetchLoanNeedByFileId(
	        UploadedFilesList uploadedFileList);

	public void updateLoanNeedList(LoanNeedsList loanNeedList);

	public void saveAllLoanTurnAroundTimeForLoan(Integer loanId);

	public void assignNeedsToLoan(LoanNeedsList loanNeedsList);

	public LoanNeedsList findLoanNeedsList(Loan loan,
	        NeedsListMaster needsListMaster);

	public int getApplicationFee(int loanId) throws NoRecordsFetchedException,
	        InvalidInputException;

	public void updateLoan(Loan loan);

	public List<LoanMilestoneMaster> getLoanMilestoneMasterList();

	public UserLoanStatus getUserLoanStaus(LoanVO loanVO);

	public LoanDashboardVO searchUsers(LoanUserSearchVO searchVO);

	public void updateLoanAppFee(int loanId, BigDecimal newAppFee);

	public LoanAppFormVO retrieveLoanAppForm(UserVO userVO);

	public void setExpiryDateToPurchaseDocument(Integer loanId, Long date);

	public String saveLoanMilestone(int loanId, int masterMileStoneId,
	        String comments);

	public void saveLoanProgress(int loand,
	        LoanProgressStatusMaster progressValue);

	public void updateLoan(Integer loanId, Boolean rateLocked, String rateVo);

	public LoanVO wrapperCallForDashboard(Integer loanID);

	public void sendApplicationFinishedEmail(Loan loan);

	public void sendApplicationSubmitConfirmationMail(Integer loanId,
	        boolean sendMailToLM);

	public void createAlertForAgentAddition(int loanId);

	void createAlertForAgent(int loanId);

	public LoanVO getLoanByLoanEmailId(String string);

	public Boolean checkIfLoanHasSalesManager(Long loanId);

	/**
	 * @param loanID
	 * @throws InvalidInputException
	 * @throws UndeliveredEmailException
	 */
	void sendRateLockRequested(Integer loanID, LoanLockRateVO loanLockRateVO)
	        throws InvalidInputException, UndeliveredEmailException;

	public void sendNoproductsAvailableEmail(UserVO userVO, int loanID);

	public Loan getLoanByWorkflowItemExecId(int workflowItemExecId);

	public void saveCreditScoresForBorrower(Map<String, String> creditScoreMap,
	        Loan loan, ExceptionMaster exceptionMaster);

	/**
	 * @param userVO
	 * @param startLimit
	 * @param endLimit
	 * @return
	 */
	LoanDashboardVO retrieveDashboardForWorkLoans(UserVO userVO,
	        String startLimit, String endLimit);

	/**
	 * @param loanId
	 */
	void markLoanDeleted(int loanId);

	public int updateStatusInLoanTeam(UserVO user);

	public String updateNexeraMilestone(int loanId, int masterMileStoneId,
	        String comments);

	public void updateLoanModifiedDate(int loanId, Date modifiedDate);

	public void updateLoanLockDetails(int loanId, Date lockExpirationDate,
	        String lockedRate, String lockStatus);

	public boolean isCreditScoreValid(CustomerDetail customerDetail);

	public Integer updateLQBAmounts(Loan loan);

	public LoanMilestone findLoanMileStoneByCriteria(
	        LoanMilestone searchCriteria);
	
	public Integer updateLtv(Loan laon);
	
	public LeadsDashBoardVO retrieveDashboardForMyLeads(UserVO userVO,
	         String startLimit, String endLimit);
	public LeadsDashBoardVO retrieveDashboardForMyLeads(UserVO userVO);
	
	public LoanDashboardVO getLoanListSortedForMyloans(SortingLoanListVO list);
	
	public LoanDashboardVO getLoanListSortedForArchivedLoans(SortingLoanListVO list);

}
