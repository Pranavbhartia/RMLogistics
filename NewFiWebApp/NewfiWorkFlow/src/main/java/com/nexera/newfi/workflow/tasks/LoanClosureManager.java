package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.Milestones;
import com.nexera.core.service.LoanService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LoanClosureManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(LoanClosureManager.class);

	@Autowired
	private LoanService loanService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		String completedStatus = null;
		String displayMessage = "";
		if (status.equals(String.valueOf(LOSLoanStatus.LQB_STATUS_FUNDED
		        .getLosStatusID()))) {
			displayMessage = LoanStatus.loanFundedMessage;

			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED
		                .getLosStatusID()))) {
			displayMessage = LoanStatus.loanSuspendedMessage;

			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status
		        .equals(String.valueOf(LOSLoanStatus.LQB_STATUS_LOAN_DENIED
		                .getLosStatusID()))) {
			displayMessage = LoanStatus.loanDeclinedMessage;

			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN
		                .getLosStatusID()))) {
			displayMessage = LoanStatus.loanFundedMessage;

			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED
		                .getLosStatusID()))) {
			displayMessage = LoanStatus.loanArchivedMessage;
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		}
		if (status != null && !status.isEmpty()) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        displayMessage);
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        displayMessage);
			sendEmail(objectMap);
		}
		return completedStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		StringBuffer returnString = new StringBuffer();

		try {
			LOG.debug("Getting the closure Status");
			Loan loan = new Loan();
			loan.setId(Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.LOAN_CLOSURE.getMilestoneKey());
			if (mileStone != null && mileStone.getComments() != null) {
				returnString.append(mileStone.getComments());
			}
			if (mileStone != null && mileStone.getStatusUpdateTime() != null) {
				returnString.append(" On " + mileStone.getStatusUpdateTime());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());

		}
		return returnString.toString();
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing : 
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// Do Nothing : No Reminders
		return null;
	}

}
