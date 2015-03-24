package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.HomeOwnersInsuranceMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanStatusMaster;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.TitleCompanyMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.vo.LoanStatusMasterVO;
import com.nexera.common.vo.LoanTypeMasterVO;

public interface LoanDao extends GenericDao {

	public List<Loan> getLoansOfUser(User user);

	Loan getLoanWithDetails(Integer loanID);

	public Loan getLoanWorkflowDetails(Integer loanID);

	public boolean addToLoanTeam(Loan loan, User user, User addedBy);

	public boolean removeFromLoanTeam(Loan loan, User user);

	public List<User> retreiveLoanTeam(Loan loan);

	public List<Loan> retreiveLoansAsManager(User loanManager);

	public LoanAppForm getLoanAppForm(Integer loanId);

	public Loan getActiveLoanOfUser(User parseUserModel);

	public List<Loan> retrieveLoanForDashboard(User parseUserModel);

	public List<LoanTeam> getLoanTeamList(Loan loan);

	public List<Loan> getLoansForUser(Integer userId);

	public UploadedFilesList fetchUploadedFromLoanNeedId(Integer loanNeedId);

	public Integer getNeededItemsRequired(Integer loanId);

	public Integer getTotalNeededItem(Integer loanId);

	Loan retrieveLoanForDashboard(User parseUserModel, Loan loan);

	// TODO added for loan rest
	public List<LoanStatusMaster> getLoanStatusMaster(LoanStatusMasterVO loanVO);

	public List<LoanTypeMaster> getLoanTypeMater(
	        LoanTypeMasterVO loanTypeMaterVO);

	public List<TitleCompanyMaster> findTitleCompanyByName(
			TitleCompanyMaster titleCompany);

	public List<HomeOwnersInsuranceMaster> findHomeOwnInsByName(
			HomeOwnersInsuranceMaster parseHomeOwnInsMaster);
	
	

}
