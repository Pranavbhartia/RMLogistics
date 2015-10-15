package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoadConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class UWReviewedManager implements IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private NotificationService notificationService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// Do Nothing
		String returnStatus = null;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		if (status != null) {
			//This concrete class will be called for Approved, Denied and Suspened - only when it s denied
			if ((status.equals(LOSLoanStatus.LQB_STATUS_LOAN_DENIED
			        .getLosStatusID() + ""))) {
				loanService.saveLoanProgress(loanId,
				        new LoanProgressStatusMaster(
				                LoanProgressStatusMasterEnum.DECLINED));
			}
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
		}
		return returnStatus;

	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String closureDate = iWorkflowService.getUWMilestoneDates(loanId,
		        LOSLoanStatus.LQB_STATUS_APPROVED);
		return closureDate;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		return null;
	}

	private void dismissSystemEduNotification(HashMap<String, Object> objectMap) {

	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		return null;
	}
}