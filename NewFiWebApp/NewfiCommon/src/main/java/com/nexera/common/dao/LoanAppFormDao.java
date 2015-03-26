package com.nexera.common.dao;

import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.vo.LoanAppFormVO;

public interface LoanAppFormDao extends GenericDao {

	public LoanAppForm findById(int appFormId);
	public LoanAppForm findByuserID(int userid);
	
	public LoanAppForm saveLoanAppFormWithDetails(LoanAppForm loanAppForm);
	
	public LoanAppForm findLoanAppForm(Integer loanAppFormID);
	
	public LoanAppForm find(LoanAppForm loanAppForm);

}
