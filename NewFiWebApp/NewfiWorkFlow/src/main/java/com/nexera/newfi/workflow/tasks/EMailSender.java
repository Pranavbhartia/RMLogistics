package com.nexera.newfi.workflow.tasks;

import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class EMailSender implements IWorkflowTaskExecutor {

	public String execute(Object... objects) {
		// Call the Email Sender here.
		if (objects != null) {
			String emailTemplate = objects[0].toString();
		}
		return null;
	}

	public Object renderStateInfo(Object input) {
		
		return "";
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

}
