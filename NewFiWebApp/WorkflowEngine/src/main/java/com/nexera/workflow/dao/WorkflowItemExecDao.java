package com.nexera.workflow.dao;

import java.util.List;

import com.nexera.workflow.bean.WorkflowItemExec;


public interface WorkflowItemExecDao extends GenericDao
{

    /**
     * @param workflowItemExecution
     * @return
     */
    List<WorkflowItemExec> getWorkflowItemListByparentWorkflowExecItem( WorkflowItemExec workflowItemExecution );


}
