package com.nexera.common.dao;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;



public interface LoanMilestoneDao extends GenericDao
{
	public LoanMilestone getLqbLoanStatus(Loan laon);
}
