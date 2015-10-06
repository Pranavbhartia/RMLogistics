package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.exception.NoRecordsFetchedException;

public interface NeedsDao extends GenericDao {

	public List<LoanNeedsList> getLoanNeedsList(int loanId)
	        throws NoRecordsFetchedException;

	public void deleteLoanNeeds(int loanId);

	public void deleteLoanNeed(LoanNeedsList need);

	public Integer getLoanNeedListIdByFileId(Integer fileId);

	public List<NeedsListMaster> getMasterNeedsList(Boolean isCustom);

	public LoanNeedsList findNeedForLoan(Loan loan,
	        NeedsListMaster needsListMaster);

	public NeedsListMaster findNeedsListMasterByLabel(String label);

	public LoanNeedsList findLoanNeedByMaster(Loan loan,
	        NeedsListMaster needListMaster);

	public String checkCreditReport(Integer loanID);
	
	public NeedsListMaster  getNeed(Integer needId);
	
	public boolean checkNeedExist(String label,
	        String category,String description,User user,String lqbDocumentType);

}
