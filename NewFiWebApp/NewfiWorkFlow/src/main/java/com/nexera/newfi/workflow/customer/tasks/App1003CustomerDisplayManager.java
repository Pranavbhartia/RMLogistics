package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.tasks.NexeraWorkflowTask;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class App1003CustomerDisplayManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	private LoanDao loanDao;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private LoanAppFormService loanAppFormService;

	@Autowired
	private WorkflowService workflowService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(App1003CustomerDisplayManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute ");
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		LOG.debug("Status is " + status);
		String returnStatus = null;
		if (status.equals(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED
		        .getLosStatusID() + "")) {
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
		}
		return returnStatus;
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
		LOG.debug("Inside method invokeAction ");
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("inside method updateReminder");
		return null;
	}

}
