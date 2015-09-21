package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.newfi.workflow.tasks.NexeraWorkflowTask;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AppraisalReceiptDisplayManager extends NexeraWorkflowTask
        implements IWorkflowTaskExecutor {
	@Autowired
	private IWorkflowService iWorkflowService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(AppraisalReceiptDisplayManager.class);
	private String STATUS_VALUE = "Appraisal Received";

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute ");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String appRecDate = iWorkflowService.getAppraisalMilestoneDates(loanId,
		        STATUS_VALUE);
		return appRecDate;
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
