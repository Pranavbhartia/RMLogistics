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
	        .getLogger(UWStatusManager.class);

	@Autowired
	private LoanService loanService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		String completedStatus = null;
		if (status.equals(String.valueOf(LOSLoanStatus.LQB_STATUS_FUNDED
		        .getLosStatusID()))) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.loanFundedMessage);
			sendEmail(objectMap);
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED
		                .getLosStatusID()))) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.loanSuspendedMessage);
			sendEmail(objectMap);
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status
		        .equals(String.valueOf(LOSLoanStatus.LQB_STATUS_LOAN_DENIED
		                .getLosStatusID()))) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.loanDeclinedMessage);
			sendEmail(objectMap);
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN
		                .getLosStatusID()))) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.loanFundedMessage);
			sendEmail(objectMap);
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED
		                .getLosStatusID()))) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.loanArchivedMessage);
			sendEmail(objectMap);
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		}
		return completedStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		String returnString = "";
		try {

			Loan loan = new Loan();
			loan.setId(Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.LOAN_CLOSURE.getMilestoneKey());
			if (mileStone != null && mileStone.getComments() != null) {
				returnString = mileStone.getComments().toString();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			returnString = "";
		}
		return returnString;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

}
