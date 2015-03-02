package com.nexera.core.service;

import java.util.HashMap;
import java.util.List;

import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.vo.LoanNeedsListVO;
import com.nexera.common.vo.ManagerNeedVo;

public interface NeedsListService {

	public HashMap<String, Object> getLoansNeedsList(int loanId) throws Exception;
	
	public int saveLoanNeeds(int loanId,List<Integer> needsList);
	

	public List<LoanNeedsListVO> getLoanNeedsList(Integer loanId); 
	
	public Integer getLoanNeedListIdByFileId(Integer fileId);

	public List<ManagerNeedVo> getNeedsListMaster(boolean isCustom);
	
	public int saveCustomNeed(NeedsListMaster need);

}
