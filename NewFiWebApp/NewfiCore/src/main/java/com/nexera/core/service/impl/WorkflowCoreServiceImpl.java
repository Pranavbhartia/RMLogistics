package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.WorkflowCoreService;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.vo.WorkflowItemExecVO;
import com.nexera.workflow.vo.WorkflowVO;

@Component
public class WorkflowCoreServiceImpl implements WorkflowCoreService {
	@Autowired
	EngineTrigger engineTrigger;

	@Autowired
	LoanService loanService;

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void createWorkflow(WorkflowVO workflowVO) throws Exception {
		Gson gson = new Gson();

		workflowVO.setWorkflowType(WorkflowConstants.CUSTOMER_WORKFLOW_TYPE);
		int customerWFID = engineTrigger.triggerWorkFlow(gson
		        .toJson(workflowVO));

		workflowVO
		        .setWorkflowType(WorkflowConstants.LOAN_MANAGER_WORKFLOW_TYPE);
		int loanManagerWFID = engineTrigger.triggerWorkFlow(gson
		        .toJson(workflowVO));
		loanService.saveWorkflowInfo(workflowVO.getLoanID(), customerWFID,
		        loanManagerWFID);

	}

	@Override
	public void changeWorkItemState(WorkflowItemExecVO workItem) {

		engineTrigger.changeStateOfWorkflowItemExec(workItem.getId(),
		        workItem.getStatus());

	}

	@Override
	public List<WorkflowItemExecVO> getWorkflowItems(int workflowId) {
		List<WorkflowItemExec> list = engineTrigger
		        .getWorkflowItemExecByWorkflowMasterExec(workflowId);
		List<WorkflowItemExecVO> volist = new ArrayList<WorkflowItemExecVO>();
		for (WorkflowItemExec workflowItem : list) {
			WorkflowItemExecVO workflowItemVO = new WorkflowItemExecVO();
			volist.add(workflowItemVO.convertToVO(workflowItem));
		}
		return volist;

	}

}
