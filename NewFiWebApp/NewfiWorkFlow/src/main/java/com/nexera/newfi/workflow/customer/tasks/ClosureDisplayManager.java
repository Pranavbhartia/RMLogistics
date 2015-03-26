package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.newfi.workflow.tasks.UWStatusManager;
import com.nexera.workflow.enums.Milestones;
import com.nexera.workflow.task.IWorkflowTaskExecutor;
@Component
public class ClosureDisplayManager implements IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(ClosureDisplayManager.class);

	@Autowired
	private LoanService loanService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		try{
			Loan loan=new Loan();
			loan.setId(Integer.parseInt(inputMap.get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestoneMaster loanMilestoneMaster=new LoanMilestoneMaster();
			loanMilestoneMaster.setId(Milestones.LOAN_CLOSURE.getMilestoneID());
			LoanMilestone mileStone=loanService.findLoanMileStoneByLoan(loan, loanMilestoneMaster);
			return mileStone.getComments().toString();
		}catch(Exception e){
			LOG.error(e.getMessage());
			return "";
		}
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
