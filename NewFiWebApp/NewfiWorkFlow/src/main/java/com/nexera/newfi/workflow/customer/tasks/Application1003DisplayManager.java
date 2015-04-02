package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.newfi.workflow.tasks.NexeraWorkflowTask;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class Application1003DisplayManager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {

	@Autowired
	private LoanDao loanDao;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
				WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		return null;
	}

}
