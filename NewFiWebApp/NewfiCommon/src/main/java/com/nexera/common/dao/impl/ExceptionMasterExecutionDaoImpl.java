package com.nexera.common.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.ExceptionMasterExecutionDao;

@Component
public class ExceptionMasterExecutionDaoImpl extends GenericDaoImpl implements
        ExceptionMasterExecutionDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(ExceptionMasterExecutionDaoImpl.class);

}
