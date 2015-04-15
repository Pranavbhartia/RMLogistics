package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
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

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> objectMap) {
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		return iWorkflowService.getRenderInfoForDisclosure(loanId);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
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
		// TODO Auto-generated method stub
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
