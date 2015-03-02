package com.nexera.workflow.service;

import java.util.List;

import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowMaster;


public interface WorkflowService
{


    WorkflowMaster getWorkFlowByWorkFlowType( String workflowType );


    WorkflowExec setWorkflowIntoExecution( WorkflowMaster workflowMaster );


    WorkflowItemExec setWorkflowItemIntoExecution( WorkflowExec parentWorkflowMaster, WorkflowItemMaster workflowItemMaster,
        WorkflowItemExec parentWorkflowItemExec );


    List<WorkflowItemMaster> getWorkflowItemMasterListByWorkflowMaster( WorkflowMaster workflowMaster );


    WorkflowItemExec getWorkflowExecById( int workflowexecId );


    void updateWorkflowExecStatus( WorkflowExec parentWorkflow );


    void updateWorkflowItemExecutionStatus( WorkflowItemExec workflowItemExecution );


    List<WorkflowItemExec> getWorkflowItemListByParentWorkflowExecItem( WorkflowItemExec workflowItemExecution );


    Boolean checkIfOnSuccessOfAnotherItem( WorkflowItemMaster workflowItemMaster );


}
