package com.nexera.core.service;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.vo.LoanAppFormVO;

public interface LoanAppFormService {

	public void save(LoanAppFormVO loaAppFormVO);

	public LoanAppFormVO create(LoanAppFormVO loaAppFormVO);


	public LoanAppFormVO find(LoanAppFormVO loanAppFormVO);

	
	public LoanAppForm findByLoan(Loan loan);
	public LoanAppForm findByuserID(int userid);

}
