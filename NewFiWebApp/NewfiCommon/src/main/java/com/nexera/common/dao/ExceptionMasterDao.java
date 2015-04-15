package com.nexera.common.dao;

import com.nexera.common.entity.ExceptionMaster;

public interface ExceptionMasterDao extends GenericDao {

	public ExceptionMaster getExceptionMasterByType(String exceptionType);
}
