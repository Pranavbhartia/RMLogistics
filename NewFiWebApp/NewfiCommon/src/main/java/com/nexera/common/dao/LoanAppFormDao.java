package com.nexera.common.dao;

import org.hibernate.HibernateException;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.ZipCodeLookup;

public interface LoanAppFormDao extends GenericDao {

	public LoanAppForm findById(int appFormId);
	public LoanAppForm findByuserID(int userid);
	
	public LoanAppForm saveLoanAppFormWithDetails(LoanAppForm loanAppForm);
	
	public LoanAppForm findLoanAppForm(Integer loanAppFormID);
	

	public LoanAppForm find(LoanAppForm loanAppForm);

	public LoanAppForm findByLoan(Loan loan) ;
	public ZipCodeLookup findByZipCode(String zipCode) throws HibernateException, Exception;

	/**
	 * @param loanId
	 * @param lockedLoanData
	 */
	void updatelockedLoanData(Integer loanId, String lockedLoanData);


}
