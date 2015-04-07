package com.nexera.core.batchprocessor;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nexera.common.entity.Loan;
import com.nexera.core.service.LoanService;

public class CheckLockRateStatusBatch extends  QuartzJobBean{

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckLockRateStatusBatch.class);
	
	@Autowired
	private LoanService loanService;
	
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("Started batch execution for CheckLockRateStatus");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		updateRateLocedInNeeds();
	}
	
	
	private void updateRateLocedInNeeds(){
		List<Loan> activeLoans = loanService.getAllActiveLoan();
		for (Loan loan : activeLoans) {
			LOGGER.info("Getting the lock rate of the Loan ");
			if(loan.getIsRateLocked()){
				
			}
		}
	}

	
}
