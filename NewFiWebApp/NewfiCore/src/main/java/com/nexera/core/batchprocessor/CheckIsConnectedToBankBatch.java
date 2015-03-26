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

public class CheckIsConnectedToBankBatch extends QuartzJobBean{

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckIsConnectedToBankBatch.class);
	
	@Autowired
	private LoanService loanService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("Started process to check CheckIsConnectedToBankBatch");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		updateUserBankConnection();
	}
	
	
	private void updateUserBankConnection(){
		List<Loan> activealoans = loanService.getAllActiveLoan();
		for (Loan loan : activealoans) {
			Loan currentLoan = loanService.fetchLoanById(loan.getId());
			if(currentLoan.getIsBankConnected()!=null){
				//todo : 
				
			}
		} 
	}

}
