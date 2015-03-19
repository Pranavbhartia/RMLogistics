package com.nexera.core.batchprocessor;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EmailBatchProcessor extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EmailBatchProcessor.class);

	@Autowired
	private ThreadPoolTaskExecutor emailTaskExecutor;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
	        throws JobExecutionException {
		LOGGER.debug("Code inside this will get triggered every 1 min ");

	}

}
