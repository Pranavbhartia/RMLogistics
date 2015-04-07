package com.nexera.core.service;

import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.vo.LoanVO;

public interface TransactionService {
	public LoanApplicationFee findByLoan(LoanVO loanVo);
}
