package com.nexera.common.dao;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;

public interface LoanNeedListDao extends GenericDao {

	LoanNeedsList findLoanNeedsList(Loan loan, NeedsListMaster needsListMaster);

}
