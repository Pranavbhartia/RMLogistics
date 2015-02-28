package com.nexera.common.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;

public interface NeedsDao extends GenericDao {

	public List<LoanNeedsList> getLoanNeedsList(int loanId) throws NoRecordsFetchedException ;
	public void deleteLoanNeeds(int loanId);
	public void deleteLoanNeed(LoanNeedsList need);
	public List<NeedsListMaster> getMasterNeedsList(Boolean isCustom);	
}
