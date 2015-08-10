package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.nexera.newfi.workflow.tasks.NexeraWorkflowTask;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class UWSubmittedDisplayManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Override
	public String execute(HashMap<String, Object> objectMap) {

		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
