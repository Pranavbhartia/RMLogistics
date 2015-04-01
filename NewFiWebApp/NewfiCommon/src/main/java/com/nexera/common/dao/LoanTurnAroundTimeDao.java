package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.LoanTurnAroundTime;

public interface LoanTurnAroundTimeDao {

	public void saveAllLoanTurnAroundTimeForLoan(
			List<LoanTurnAroundTime> turnAroundTimes);

	public LoanTurnAroundTime loadLoanTurnAroundByLoanAndWorkitem(
			Integer loanId, Integer workFlowItemId);
}
