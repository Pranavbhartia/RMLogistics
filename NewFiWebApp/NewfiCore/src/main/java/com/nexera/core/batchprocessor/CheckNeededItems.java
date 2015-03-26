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
import com.nexera.common.vo.NeededItemScoreVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;

public class CheckNeededItems extends QuartzJobBean{

	private final static Logger LOGGER = LoggerFactory.getLogger(CheckNeededItems.class);

	@Autowired
	private LoanService loanService;
	
	@Autowired
	private NeedsListService needsListService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("Starting batch to check for required documetns : CheckNeededItems ");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		updateCompleteNeededDocuments();
	}
	
	
	private void updateCompleteNeededDocuments(){
		List<Loan> activeLoans = loanService.getAllActiveLoan();
		for (Loan loan : activeLoans) {
			LOGGER.info("Getting deatils for loan" + loan.getId());
			NeededItemScoreVO itemScoreVO  = needsListService.getNeededItemsScore(loan.getId());
			
			if(itemScoreVO.getNeededItemRequired() == itemScoreVO.getTotalSubmittedItem()){
				//todo : call work flow service
			}
		}
	}

}
