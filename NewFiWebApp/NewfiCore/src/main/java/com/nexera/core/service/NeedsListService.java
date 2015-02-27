package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.vo.ManagerNeedVo;

public interface NeedsListService {

	public List<ManagerNeedVo> getLoansNeedsList(int loanId) throws Exception;
	
	public int saveLoanNeeds(int loanId,List<Integer> needsList);
}
