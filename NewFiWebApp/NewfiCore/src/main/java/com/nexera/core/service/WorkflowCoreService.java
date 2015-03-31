package com.nexera.core.service;

import java.util.List;

import com.nexera.workflow.vo.WorkflowItemExecVO;
import com.nexera.workflow.vo.WorkflowVO;

public interface WorkflowCoreService {

	public void createWorkflow(WorkflowVO workflowVO) throws Exception;

	public void changeWorkItemState(WorkflowItemExecVO workItem);

	public List<WorkflowItemExecVO> getWorkflowItems(int workflowId);
}
