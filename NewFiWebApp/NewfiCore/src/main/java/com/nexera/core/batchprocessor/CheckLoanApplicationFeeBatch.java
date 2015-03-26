package com.nexera.core.batchprocessor;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CheckLoanApplicationFeeBatch extends QuartzJobBean{

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckLoanApplicationFeeBatch.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("Started job execution for CheckLoanApplicationFeeBatch");
		
	}

}
