package com.nexera.workflow.dao;

import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowMaster;

public interface WorkflowMasterDao extends GenericDao {

	/**
	 * @param workflowType
	 * @return
	 */
	WorkflowMaster findWorkflowByType(String workflowType);

	WorkflowItemMaster getWorkflowByType(String workflowType);

	public WorkflowItemExec getWorkflowItemExecByType(
	        WorkflowExec workflowExec, WorkflowItemMaster workflowItemMaster);

	public WorkflowItemExec getWorkflowItemExecBySuccessItemID(
	        WorkflowItemExec successItem);
}
