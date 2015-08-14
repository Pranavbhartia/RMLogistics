package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;
@Component
public class UWReviewedManager implements IWorkflowTaskExecutor{
	@Autowired
	private LoanService loanService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	private NotificationService notificationService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// Do Nothing
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		loanService.saveLoanProgress(loanId, new LoanProgressStatusMaster(
		        LoanProgressStatusMasterEnum.DECLINED));
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
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
