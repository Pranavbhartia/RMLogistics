package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class Application1003Manager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(WorkflowDisplayConstants.STATUS_KEY)
				.toString();
		if (status.equals(ApplicationStatus.initiated)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.initiatedMessage);
		} else if (status.equals(ApplicationStatus.submitted)) {
			// TODO check if we get timestamp for take a note from LQB
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.submittedMessage);
			sendEmail(objectMap);
		}
		return null;
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

}
