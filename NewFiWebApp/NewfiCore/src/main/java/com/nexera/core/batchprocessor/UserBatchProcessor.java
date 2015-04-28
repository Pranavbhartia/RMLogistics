package com.nexera.core.batchprocessor;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.entity.BatchJobExecution;
import com.nexera.common.entity.BatchJobMaster;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.User;
import com.nexera.core.manager.UserManager;
import com.nexera.core.service.BatchService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.NexeraUtility;

public class UserBatchProcessor extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(UserBatchProcessor.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private NexeraUtility nexeraUtility;

	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private BatchService batchService;

	@Autowired
	private UserProfileService userProfileService;

	private ExceptionMaster exceptionMaster;

	@Override
	protected void executeInternal(JobExecutionContext context)
	        throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LOGGER.debug("Triggering the Quartz Schedular ");
		loadExceptionMaster();
		BatchJobMaster batchJobMaster = getBatchJobMasterById(3);
		if (batchJobMaster != null) {
			if (batchJobMaster.getStatus() == CommonConstants.STATUS_ACTIVE) {
				taskExecutor = getTaskExecutor();
				BatchJobExecution batchJobExecution = putBatchIntoExecution(batchJobMaster);
				try {
					List<User> userList = userProfileService
					        .fetchAllActiveUsers();
					for (User user : userList) {
						if (user.getTokenGeneratedTime() != null) {
							UserManager userManager = applicationContext
							        .getBean(UserManager.class);
							userManager.setUser(user);
							userManager.setExceptionMaster(exceptionMaster);
							taskExecutor.execute(userManager);
						}
					}
					taskExecutor.shutdown();
				} finally {
					LOGGER.debug("Updating the end time for this batch job ");
					updateBatchJobExecution(batchJobExecution);
				}
			} else {
				LOGGER.debug("Batch Jobs Not Running ");
			}
		}

	}

	private void updateBatchJobExecution(BatchJobExecution batchJobExecution) {
		batchJobExecution.setDateLastRunEndtime(new Date());
		batchService.updateBatchJobExecution(batchJobExecution);

	}

	private BatchJobExecution putBatchIntoExecution(
	        BatchJobMaster batchJobMaster) {
		BatchJobExecution batchJobExecution = new BatchJobExecution();
		batchJobExecution.setBatchJobMaster(batchJobMaster);
		batchJobExecution
		        .setComments("Loan Batch Processor Has Been Put Into Execution ");
		batchJobExecution.setDateLastRunStartTime(new Date());
		return batchService.putBatchIntoExecution(batchJobExecution);
	}

	private BatchJobMaster getBatchJobMasterById(int batchJobId) {
		LOGGER.debug("Inside method getBatchJobMasterById ");
		return batchService.getBatchJobMasterById(batchJobId);
	}

	private ExceptionMaster loadExceptionMaster() {

		if (exceptionMaster == null) {
			LOGGER.debug("Loading Loan ExceptionMaster ");
			exceptionMaster = nexeraUtility
			        .getExceptionMasterByType(CoreCommonConstants.EXCEPTION_TYPE_LOAN_BATCH);

		}
		return exceptionMaster;

	}

	private ThreadPoolTaskExecutor getTaskExecutor() {
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.initialize();
		taskExecutor.setCorePoolSize(3);
		taskExecutor.setMaxPoolSize(8);
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAwaitTerminationSeconds(Integer.MAX_VALUE);
		return taskExecutor;

	}

}
