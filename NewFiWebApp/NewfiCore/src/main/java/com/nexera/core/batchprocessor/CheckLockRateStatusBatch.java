package com.nexera.core.batchprocessor;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class CheckLockRateStatusBatch extends  QuartzJobBean{

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckLockRateStatusBatch.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("Started batch execution for CheckLockRateStatus");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		updateRateLocedInNeeds();
	}
	
	
	private void updateRateLocedInNeeds(){
		
	}

	
}
