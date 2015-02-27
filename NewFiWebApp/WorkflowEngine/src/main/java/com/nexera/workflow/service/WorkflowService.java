package com.nexera.workflow.service;

import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowMaster;


public interface WorkflowService
{


    WorkflowMaster getWorkFlowByWorkFlowType( String workflowType );

   
    WorkflowExec setWorkflowIntoExecution( WorkflowMaster workflowMaster );


}
