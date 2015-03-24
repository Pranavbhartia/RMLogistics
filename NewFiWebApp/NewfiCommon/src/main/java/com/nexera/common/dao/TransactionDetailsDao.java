package com.nexera.common.dao;

import org.springframework.stereotype.Component;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;

/**
 * The Dao class for the transaction details table.
 * @author karthik
 *
 */
@Component
public interface TransactionDetailsDao extends GenericDao {
	public LoanApplicationFee findByLoan(Loan loan);
}
