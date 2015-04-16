package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.Milestones;
import com.nexera.core.service.LoanService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class ClosureDisplayManager implements IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(ClosureDisplayManager.class);

	@Autowired
	private LoanService loanService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// Do NOthing
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		StringBuffer returnString = new StringBuffer();
		try {
			Loan loan = new Loan();
			loan.setId(Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.LOAN_CLOSURE.getMilestoneKey());
			if (mileStone != null && mileStone.getComments() != null) {
				returnString.append(mileStone.getComments().toString());
				returnString.append(" On " + mileStone.getStatusUpdateTime());

			}
		} catch (Exception e) {
			LOG.error(e.getMessage());

		}
		return returnString.toString();
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

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
