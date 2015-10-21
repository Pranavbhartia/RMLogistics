package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.customer.tasks.App1003CustomerDisplayManager;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class UWSubmittedManager implements IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	IWorkflowService iworkflowService;
	@Autowired
	private NotificationService notificationService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(UWSubmittedManager.class);
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// Do Nothing
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		if (status.equals(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED
		        .getLosStatusID() + "")) {
			int loanId = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			//Update the Loan pRogress
			LOG.debug(" UW_Submitted : Updating Loan LC State for " + loanId + "as " + WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED).getLcStateKey());
			loanService.updateLoanLCState(loanId, WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED));
		}
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String closureDate = iworkflowService.getUWMilestoneDates(loanId,
		        LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED);
		return closureDate;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		return WorkItemStatus.COMPLETED.getStatus();
	}

	private void dismissSystemEduNotification(HashMap<String, Object> objectMap) {

	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		return null;
	}
}
