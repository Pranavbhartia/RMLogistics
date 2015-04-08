package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.vo.LoanVO;

public interface TransactionService {
	public LoanApplicationFee findByLoan(LoanVO loanVo);

	public List<TransactionDetails> getActiveTransactionsByLoan(Loan loan);
}
