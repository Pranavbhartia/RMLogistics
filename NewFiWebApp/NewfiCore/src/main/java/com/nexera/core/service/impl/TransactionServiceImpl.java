package com.nexera.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.TransactionDetailsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.vo.LoanVO;
import com.nexera.core.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionDetailsDao transactionDetailsDao;

	@Transactional
	@Override
	public LoanApplicationFee findByLoan(LoanVO loanVo) {
		Loan loan = new Loan();
		loan.setId(loanVo.getId());
		return transactionDetailsDao.findByLoan(loan);
	}
}
