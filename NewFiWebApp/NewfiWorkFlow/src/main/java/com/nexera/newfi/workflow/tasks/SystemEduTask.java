package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.core.service.LoanService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

/*
 * @author Charu Joshi
 */
@Component
public class SystemEduTask extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	WorkflowService workflowService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
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
		int workflowItemExecutionId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		WorkflowItemExec workflowItemExecution = workflowService
				.getWorkflowExecById(workflowItemExecutionId);
		if (workflowItemExecution != null) {
			WorkflowExec workflowExec = workflowItemExecution
					.getParentWorkflow();
			if (!workflowExec.getStatus().equals(
					WorkItemStatus.STARTED.getStatus())) {
				workflowExec.setStatus(WorkItemStatus.STARTED.getStatus());
				workflowService.updateWorkflowExecStatus(workflowExec);
			}
			// TODO check prev status
			workflowItemExecution.setStatus(WorkItemStatus.COMPLETED
					.getStatus());
			workflowService
					.updateWorkflowItemExecutionStatus(workflowItemExecution);
			
			if (workflowItemExecution.getParentWorkflowItemExec() != null) {
				WorkflowItemExec parentWorkflowItemExec = workflowItemExecution
						.getParentWorkflowItemExec();
				if (parentWorkflowItemExec.getStatus().equalsIgnoreCase(
						WorkItemStatus.NOT_STARTED.getStatus())) {
					parentWorkflowItemExec.setStatus(WorkItemStatus.STARTED
							.getStatus());
					workflowService
							.updateWorkflowItemExecutionStatus(parentWorkflowItemExec);
				}
				WorkflowItemExec parentEWorkflowItemExec = workflowItemExecution
						.getParentWorkflowItemExec();
				List<WorkflowItemExec> childWorkflowItemExecList = workflowService
						.getWorkflowItemListByParentWorkflowExecItem(parentEWorkflowItemExec);
				int count = 0;
				for (WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList) {
					if (childWorkflowItemExec.getStatus().equalsIgnoreCase(
							WorkItemStatus.COMPLETED.getStatus())) {
						count = count + 1;
					}
				}
				
				if (count == childWorkflowItemExecList.size()) {
					inputMap.put(WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME,
							parentEWorkflowItemExec.getId());
					String params = Utils.convertMapToJson(inputMap);
					workflowService.saveParamsInExecTable(
							parentEWorkflowItemExec.getId(), params);

					engineTrigger.startWorkFlowItemExecution(parentEWorkflowItemExec.getId());
				}

			}
		}
		return "success";
	}

}
