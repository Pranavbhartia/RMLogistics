package com.nexera.core.service;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.vo.LoanAppFormVO;

public interface LoanAppFormService {

	public void save(LoanAppFormVO loaAppFormVO);

	public LoanAppForm create(LoanAppFormVO loaAppFormVO);
	
	public LoanAppForm findByLoan(Loan loan);
	public LoanAppForm findByuserID(int userid);
}
