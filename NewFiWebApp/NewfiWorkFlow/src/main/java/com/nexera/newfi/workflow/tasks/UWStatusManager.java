package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class UWStatusManager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get("status").toString();
		String message = "";
		if (status.equals(ApplicationStatus.inUnderwriting)) {
			message = ApplicationStatus.inUnderwritingMessage;
		} else if (status
				.equals(ApplicationStatus.underwritingObservationsReceived)) {
			message = ApplicationStatus.underwritingObservationsReceivedMessage;
		} else if (status.equals(ApplicationStatus.underwritingSubmitted)) {
			message = ApplicationStatus.underwritingSubmittedMessage;
		} else if (status.equals(ApplicationStatus.approvedWithConditions)) {
			message = ApplicationStatus.approvedWithConditionsMessage;
		} else if (status.equals(ApplicationStatus.underwritingApproved)) {
			message = ApplicationStatus.underwritingApprovedMessage;
		}
		//TODO add alert code here
		if (!message.equals("")) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					message);
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
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		return null;
	}

}
