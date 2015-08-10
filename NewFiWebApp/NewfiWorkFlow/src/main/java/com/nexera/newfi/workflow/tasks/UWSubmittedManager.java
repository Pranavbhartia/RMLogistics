package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class UWSubmittedManager implements IWorkflowTaskExecutor{
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
		return WorkItemStatus.COMPLETED.getStatus();
	}

	private void dismissSystemEduNotification(HashMap<String, Object> objectMap) {
		
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		return null;
	}
}
