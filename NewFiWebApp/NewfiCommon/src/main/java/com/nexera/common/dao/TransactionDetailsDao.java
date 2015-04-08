package com.nexera.common.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.TransactionDetails;

/**
 * The Dao class for the transaction details table.
 * @author karthik
 *
 */
@Component
public interface TransactionDetailsDao extends GenericDao {
	public LoanApplicationFee findByLoan(Loan loan);

	public List<TransactionDetails> findActiveTransactionsByLoan(Loan loan);
}
