package com.nexera.core.batchprocessor;

import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
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
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.core.manager.ThreadManager;
import com.nexera.core.service.BatchService;
import com.nexera.core.service.LoanService;

@DisallowConcurrentExecution
public class LoanBatchProcessor extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(LoanBatchProcessor.class);

	@Autowired
	private LoanService loanService;

	@Autowired
	private ApplicationContext applicationContext;

	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private BatchService batchService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
	        throws JobExecutionException {

		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LOGGER.debug("Triggering the Quartz Schedular ");
		BatchJobMaster batchJobMaster = getBatchJobMasterById(2);
		if (batchJobMaster != null) {
			if (batchJobMaster.getStatus() == CommonConstants.STATUS_ACTIVE) {
				taskExecutor = getTaskExecutor();
				BatchJobExecution batchJobExecution = putBatchIntoExecution(batchJobMaster);
				try {
					List<Loan> loanList = loanService.getLoansInActiveStatus();
					if (loanList != null) {
						for (Loan loan : loanList) {
							if (loan.getLqbFileId() != null) {
								ThreadManager threadManager = applicationContext
								        .getBean(ThreadManager.class);
								threadManager
								        .setLoanMilestoneMasterList(getLoanMilestoneMasterList());
								threadManager.setLoan(loan);
								taskExecutor.execute(threadManager);
							}
						}
						taskExecutor.shutdown();
					}
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

	private List<LoanMilestoneMaster> getLoanMilestoneMasterList() {
		LOGGER.debug("Inside method getLoanMilestoneMasterList ");

		return loanService.getLoanMilestoneMasterList();
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
