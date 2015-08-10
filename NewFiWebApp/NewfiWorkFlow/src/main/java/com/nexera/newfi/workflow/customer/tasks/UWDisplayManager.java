package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.core.service.LoanService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class UWDisplayManager implements IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(UWDisplayManager.class);

	@Autowired
	private LoanService loanService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method invokeAction");
		return null;
	}

	@Override
    public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method updateReminder");
		return null;
	}

}
