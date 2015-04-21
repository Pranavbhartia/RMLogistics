package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class DisclosuresDisplayManager implements IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;

	@Autowired
	private NeedsListService needsListService;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private EngineTrigger engineTrigger;

	private static final Logger LOG = LoggerFactory
	        .getLogger(DisclosuresDisplayManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method renderStateInfo");
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		return iWorkflowService.getRenderInfoForDisclosure(loanId);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		int workflowItemExecutionId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		WorkflowItemExec currentItem = new WorkflowItemExec();
		currentItem.setId(workflowItemExecutionId);
		WorkflowItemExec wfItemExec = workflowService
		        .getWorkflowItemExecBySucessItemID(currentItem);
		if (wfItemExec.getStatus().equals(WorkItemStatus.STARTED.getStatus())) {
			engineTrigger.changeStateOfWorkflowItemExec(
			        workflowItemExecutionId, wfItemExec.getStatus());
		}
		return wfItemExec.getStatus();
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
