package com.nexera.core.service;

import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.ExceptionMasterExecution;

public interface ExceptionService {

	ExceptionMaster getExceptionMasterByType(String exceptionType);

	ExceptionMasterExecution putExceptionMasterIntoExecution(
	        ExceptionMasterExecution exceptionMasterExecution);

}
