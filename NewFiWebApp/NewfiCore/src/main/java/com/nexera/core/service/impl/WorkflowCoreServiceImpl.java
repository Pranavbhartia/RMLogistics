package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	LoanService loanService;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	@Transactional
	public void createWorkflow(WorkflowVO workflowVO) throws Exception {
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		Map<String, Integer> map = engineTrigger.triggerWorkFlow();

		loanService.saveWorkflowInfo(workflowVO.getLoanID(),
		        map.get(WorkflowConstants.CUSTOMER_WORKFLOW_TYPE),
		        map.get(WorkflowConstants.LOAN_MANAGER_WORKFLOW_TYPE));

	}

	@Override
	public void changeWorkItemState(WorkflowItemExecVO workItem) {
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		engineTrigger.changeStateOfWorkflowItemExec(workItem.getId(),
		        workItem.getStatus());

	}

	@Override
	public List<WorkflowItemExecVO> getWorkflowItems(int workflowId) {
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
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
