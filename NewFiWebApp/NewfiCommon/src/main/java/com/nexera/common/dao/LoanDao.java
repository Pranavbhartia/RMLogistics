package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.HomeOwnersInsuranceMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanDetail;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.TitleCompanyMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.UserVO;


public interface LoanDao extends GenericDao
{

    public List<Loan> getLoansOfUser( User user );


    Loan getLoanWithDetails( Integer loanID );


    public Loan getLoanWorkflowDetails( Integer loanID );


    public boolean addToLoanTeam( Loan loan, User user, User addedBy );


    public boolean removeFromLoanTeam( Loan loan, User user );


    public boolean removeFromLoanTeam( Loan loan, HomeOwnersInsuranceMaster homeOwnIns );


    public boolean removeFromLoanTeam( Loan loan, TitleCompanyMaster titleCompany );


    public List<User> retreiveLoanTeam( Loan loan );


    public List<Loan> retreiveLoansAsManager( User loanManager );


    public LoanAppForm getLoanAppForm( Integer loanId );


    public Loan getActiveLoanOfUser( User parseUserModel );


    public List<Loan> retrieveLoanForDashboard( User parseUserModel );


    public List<LoanTeam> getLoanTeamList( Loan loan );


    public List<Loan> getLoansForUser( Integer userId );


    public UploadedFilesList fetchUploadedFromLoanNeedId( Integer loanNeedId );


    public Integer getNeededItemsRequired( Integer loanId );


    public Integer getTotalNeededItem( Integer loanId );


    Loan retrieveLoanForDashboard( User parseUserModel, Loan loan );


    public List<LoanTypeMaster> getLoanTypeMater( LoanTypeMasterVO loanTypeMaterVO );


    public List<TitleCompanyMaster> findTitleCompanyByName( TitleCompanyMaster titleCompany );


    public List<HomeOwnersInsuranceMaster> findHomeOwnInsByName( HomeOwnersInsuranceMaster parseHomeOwnInsMaster );


    List<Loan> retrieveLoanByProgressStatus( User parseUserModel, int loanProgressStatusId );


    public LoanNeedsList fetchByNeedId( Integer needId );


    public HomeOwnersInsuranceMaster addHomeOwnInsCompany( HomeOwnersInsuranceMaster homeOwnInsMaster );


    public TitleCompanyMaster addTitleCompany( TitleCompanyMaster titleCompanyMaster );


    boolean addToLoanTeam( Loan loan, HomeOwnersInsuranceMaster homeOwnersInsurance, User addedBy );


    boolean addToLoanTeam( Loan loan, TitleCompanyMaster titleCompany, User addedBy );


    public LoanMilestone findLoanMileStoneByLoan( Loan loan, String loanMilestoneMAsterName );


    public List<Loan> getAllActiveLoan();


    public List<Loan> retrieveLoanForDashboardForAdmin( User parseUserModel );


    public int retrieveUserRoleId( UserVO userVO );


    public TitleCompanyMaster findTitleCompanyOfLoan( Loan loan );


    public HomeOwnersInsuranceMaster findHomeOwnersInsuranceCompanyOfLoan( Loan loan );


    LoanDetail findLoanDetailOfLoan( Loan loan );


    public void updateLoanEmail( int loanId, String generateLoanEmail );


    public LoanNeedsList fetchLoanNeedsByFileId( UploadedFilesList uploadedFileList );
    
    public List<Loan> loanListBasedOnUser(User user);

}
