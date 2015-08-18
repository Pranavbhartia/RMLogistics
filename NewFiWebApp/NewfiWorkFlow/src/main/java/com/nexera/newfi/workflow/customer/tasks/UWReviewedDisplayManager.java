package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.newfi.workflow.tasks.NexeraWorkflowTask;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class UWReviewedDisplayManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private IWorkflowService iWorkflowService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {

		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String closureDate = iWorkflowService.getUWMilestoneDates(loanId,
		        LOSLoanStatus.LQB_STATUS_APPROVED);
		return closureDate;
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
