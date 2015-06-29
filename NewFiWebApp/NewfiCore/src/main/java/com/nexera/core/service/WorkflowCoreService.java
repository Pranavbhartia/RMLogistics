package com.nexera.core.service;

import java.util.List;
import java.util.Map;

import com.nexera.common.entity.Loan;
import com.nexera.common.vo.LoanMilestoneVO;
import com.nexera.workflow.vo.WorkflowItemExecVO;
import com.nexera.workflow.vo.WorkflowVO;

public interface WorkflowCoreService {

	public void createWorkflow(WorkflowVO workflowVO) throws Exception;

	public void changeWorkItemState(WorkflowItemExecVO workItem);

	public List<WorkflowItemExecVO> getWorkflowItems(int workflowId);

	public void completeWorkflowItem(Loan loan, Map<String, Object> map,
	        String workflowName);

	public List<LoanMilestoneVO> getMilestones(int loanId);
}
