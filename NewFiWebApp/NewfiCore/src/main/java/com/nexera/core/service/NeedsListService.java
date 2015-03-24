package com.nexera.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.vo.LoanNeedsListVO;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.common.vo.NeededItemScoreVO;

public interface NeedsListService {

	public HashMap<String, Object> getLoansNeedsList(int loanId) throws Exception;
	
	public int saveLoanNeeds(int loanId,List<Integer> needsList);
	
	public List<LoanNeedsListVO> getLoanNeedsList(Integer loanId); 
	
	public Map<String, List<LoanNeedsListVO>> getLoanNeedsMap(Integer loanId);
	
	public Integer getLoanNeedListIdByFileId(Integer fileId);

	public List<ManagerNeedVo> getNeedsListMaster(boolean isCustom);
	
	public NeededItemScoreVO getNeededItemsScore(Integer loanId);
	
	public int saveCustomNeed(NeedsListMaster need);

	public NeedsListMaster fetchNeedListMasterUsingID(Integer needId);
	
}
