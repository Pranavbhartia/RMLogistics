package com.nexera.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.TransactionDetailsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.vo.LoanVO;
import com.nexera.core.service.TransactionService;

@Component
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

	@Override
	@Transactional
	public List<TransactionDetails> getActiveTransactionsByLoan(Loan loan) {
		return transactionDetailsDao.findActiveTransactionsByLoan(loan);
	}
}
