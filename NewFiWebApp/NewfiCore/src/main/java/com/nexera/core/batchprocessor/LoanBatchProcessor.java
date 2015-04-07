package com.nexera.core.batchprocessor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.exception.FatalException;
import com.nexera.core.manager.ThreadManager;
import com.nexera.core.service.BatchService;
import com.nexera.core.service.LoanService;
import com.nexera.core.utility.LoanStatusMaster;

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
					List<Loan> loanList = loanService.getAllLoans();
					if (loanList != null) {
						for (Loan loan : loanList) {
							if (loan.getLqbFileId() != null) {
								LoanProgressStatusMaster loanProgessStatus = loan
								        .getLoanProgressStatus();
								if (loanProgessStatus != null) {
									String loanStatus = loanProgessStatus
									        .getLoanProgressStatus();
									if ((!loanStatus
									        .equalsIgnoreCase(LoanStatusMaster.STATUS_CLOSED))
									        && (!loanStatus
									                .equalsIgnoreCase(LoanStatusMaster.STATUS_WITHDRAW))
									        && (!loanStatus
									                .equalsIgnoreCase(LoanStatusMaster.STATUS_DECLINED))) {
										ThreadManager threadManager = applicationContext
										        .getBean(ThreadManager.class);
										threadManager
										        .setLoanMilestoneMasterList(getLoanMilestoneMasterByLoanType(loan));
										threadManager.setLoan(loan);
										taskExecutor.execute(threadManager);
									}
								}
							}
						}
						taskExecutor.shutdown();
						try {
							taskExecutor.getThreadPoolExecutor()
							        .awaitTermination(Long.MAX_VALUE,
							                TimeUnit.NANOSECONDS);
						} catch (InterruptedException e) {
							LOGGER.error("Exception caught while terminating executor "
							        + e.getMessage());
							throw new FatalException(
							        "Exception caught while terminating executor "
							                + e.getMessage());
						}
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

	private List<LoanMilestoneMaster> getLoanMilestoneMasterByLoanType(Loan loan) {
		LOGGER.debug("Inside method getLoanMilestoneMasterByLoan ");
		LoanTypeMaster loanTypeMaster = loan.getLoanType();
		return loanService.getLoanMilestoneByLoanType(loanTypeMaster);
	}

	private ThreadPoolTaskExecutor getTaskExecutor() {
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.initialize();
		taskExecutor.setCorePoolSize(3);
		taskExecutor.setMaxPoolSize(10);
		return taskExecutor;

	}
}
