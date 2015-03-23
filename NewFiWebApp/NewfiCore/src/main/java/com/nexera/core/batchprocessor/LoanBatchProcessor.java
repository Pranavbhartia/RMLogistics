package com.nexera.core.batchprocessor;

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

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.core.manager.ThreadManager;
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

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
	        throws JobExecutionException {

		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LOGGER.debug("Triggering the Quartz Schedular ");

		List<Loan> loanList = loanService.getAllLoans();
		if (loanList != null) {
			for (Loan loan : loanList) {
				LoanProgressStatusMaster loanProgessStatus = loan
				        .getLoanProgressStatus();
				String loanStatus = loanProgessStatus.getLoanProgressStatus();
				if ((!loanStatus
				        .equalsIgnoreCase(LoanStatusMaster.STATUS_CLOSED))
				        && (!loanStatus
				                .equalsIgnoreCase(LoanStatusMaster.STATUS_WITHDRAW))
				        && (!loanStatus
				                .equalsIgnoreCase(LoanStatusMaster.STATUS_DECLINED))) {
					ThreadManager threadManager = applicationContext
					        .getBean(ThreadManager.class);
					threadManager.setLoan(loan);
					taskExecutor.execute(threadManager);
				}
			}
		}
	}
}
