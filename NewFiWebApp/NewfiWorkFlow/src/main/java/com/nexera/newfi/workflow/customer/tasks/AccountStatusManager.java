package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.core.service.UserProfileService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AccountStatusManager implements IWorkflowTaskExecutor {
	@Autowired
	private EngineTrigger engineTrigger;

	@Autowired
	private UserProfileService userProfileService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		int workflowItemExecId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		engineTrigger.startWorkFlowItemExecution(workflowItemExecId);
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
