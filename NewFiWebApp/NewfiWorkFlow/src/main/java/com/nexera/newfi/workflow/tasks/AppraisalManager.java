package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class AppraisalManager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get("status").toString();
		if (status.equals(ApplicationStatus.appraisalOrdered)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.appraisalOrderedMessage);
			sendEmail(objectMap);
		} else if (status.equals(ApplicationStatus.appraisalPending)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.appraisalPendingMessage);
			sendEmail(objectMap);
		}else if (status.equals(ApplicationStatus.appraisalReceived)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.appraisalReceivedMessage);
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
