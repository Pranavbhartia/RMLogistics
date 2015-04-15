package com.nexera.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.ExceptionMasterDao;
import com.nexera.common.dao.ExceptionMasterExecutionDao;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.ExceptionMasterExecution;
import com.nexera.core.service.ExceptionService;

@Component
public class ExceptionServiceImpl implements ExceptionService {

	@Autowired
	ExceptionMasterDao exceptionMasterDao;

	@Autowired
	ExceptionMasterExecutionDao exceptionMasterExecutionDao;

	@Override
	@Transactional(readOnly = true)
	public ExceptionMaster getExceptionMasterByType(String exceptionType) {
		return exceptionMasterDao.getExceptionMasterByType(exceptionType);

	}

	@Override
	@Transactional
	public ExceptionMasterExecution putExceptionMasterIntoExecution(
	        ExceptionMasterExecution exceptionMasterExecution) {
		Integer exceptionMasterExecutionId = (Integer) exceptionMasterExecutionDao
		        .save(exceptionMasterExecution);
		exceptionMasterExecution.setId(exceptionMasterExecutionId);
		return exceptionMasterExecution;

	}
}
