package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.User;

public interface LoanDao extends GenericDao {
	List<Loan> getLoansOfUser(User user);
}
