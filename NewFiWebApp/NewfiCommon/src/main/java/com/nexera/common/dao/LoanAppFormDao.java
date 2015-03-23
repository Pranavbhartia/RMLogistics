package com.nexera.common.dao;

import com.nexera.common.entity.LoanAppForm;

public interface LoanAppFormDao extends GenericDao {

	public LoanAppForm findById(int appFormId);
	public LoanAppForm findByuserID(int userid);

}
