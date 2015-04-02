package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NeededItemScoreVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class NeededItemsManager implements IWorkflowTaskExecutor {

	@Autowired
	NeedsListService needsListService;
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private LoanService loanService;

	public String execute(HashMap<String, Object> objectMap) {

		return null;
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		// Make a service call to get the number of needed items' assigned
		// against the
		NeededItemScoreVO neededItemScoreVO = needsListService
				.getNeededItemsScore(loanId);
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(neededItemScoreVO.getTotalSubmittedItem() + " out of "
				+ neededItemScoreVO.getNeededItemRequired());
		return new Gson().toJson(neededItemScoreVO);
	}

	public String checkStatus(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		NeededItemScoreVO neededItemScoreVO = needsListService
				.getNeededItemsScore(loanId);
		if (neededItemScoreVO.getTotalSubmittedItem() > 0) {
			String status = WorkItemStatus.PENDING.getStatus();
			if (neededItemScoreVO.getTotalSubmittedItem() >= neededItemScoreVO
					.getNeededItemRequired()) {
				status = WorkItemStatus.COMPLETED.getStatus();
			}
			LoanVO loanVO = loanService.getLoanByID(loanId);
			WorkflowExec workflowExec = new WorkflowExec();
			workflowExec.setId(loanVO.getLoanManagerWorkflowID());
			WorkflowItemMaster workflowItemMaster = workflowService
			        .getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_NEEDS_STATUS);
			WorkflowItemExec managerNeedItem = workflowService
			        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			
			workflowExec.setId(loanVO.getCustomerWorkflowID());
			workflowItemMaster = workflowService
			        .getWorkflowByType(WorkflowConstants.WORKFLOW_CUST_ITEM_NEEDS_STATUS);
			WorkflowItemExec CustNeedItem = workflowService
			        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);

			engineTrigger.changeStateOfWorkflowItemExec(
					managerNeedItem.getId(), status);
			engineTrigger.changeStateOfWorkflowItemExec(CustNeedItem.getId(),
					status);
			
			return status;
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
