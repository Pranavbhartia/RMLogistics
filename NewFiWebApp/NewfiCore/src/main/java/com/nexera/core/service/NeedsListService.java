package com.nexera.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.vo.LoanNeedsListVO;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.common.vo.NeededItemScoreVO;

public interface NeedsListService {

	public HashMap<String, Object> getLoansNeedsList(int loanId)
	        throws Exception;

	public int saveLoanNeeds(int loanId, List<Integer> needsList);

	public List<LoanNeedsListVO> getLoanNeedsList(Integer loanId);

	public Map<String, List<LoanNeedsListVO>> getLoanNeedsMap(Integer loanId);

	public Integer getLoanNeedListIdByFileId(Integer fileId);

	public List<ManagerNeedVo> getNeedsListMaster(boolean isCustom);

	public NeededItemScoreVO getNeededItemsScore(Integer loanId);

	public int saveCustomNeed(NeedsListMaster need);

	public NeedsListMaster fetchNeedListMasterUsingID(Integer needId);

	public LoanNeedsList findNeedForLoan(Loan loan,
	        NeedsListMaster needsListMaster);

	public List<LoanNeedsList> saveMasterNeedsForLoan(int loanId,
	        List<NeedsListMaster> masterNeeds);

	public NeedsListMaster fetchNeedListMasterByType(String needsListType);

	public UploadedFilesList fetchPurchaseDocumentBasedOnPurchaseContract(
	        Integer loanID);

	public String checkCreditReport(Integer loanID);

	public void initialNeedsListSetEmail(Integer loanID, List<Integer> addedList);

	public void updatedNeedsListEmail(Integer loanID, List<Integer> addedList,
	        List<Integer> removedList);
	
//	public void createInitilaNeedsList(Integer loanId);

	public void createOrDismissNeedsAlert(int loanId);
	
	public boolean checkNeedExist(String label,
	        String category,String description,User user,String lqbDocumentType);
}
