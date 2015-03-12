package com.nexera.newfi.workflow.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class EMailSender implements IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(EMailSender.class);

	public String execute(Object[] objects) {
		// Call the Email Sender here.
		if (objects != null) {
			String emailTemplate = objects[0].toString();
			LOG.debug("Template name is " + emailTemplate);
		}
		return "";
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

	public String renderStateInfo(String[] inputs) {

		return "";
	}

}
