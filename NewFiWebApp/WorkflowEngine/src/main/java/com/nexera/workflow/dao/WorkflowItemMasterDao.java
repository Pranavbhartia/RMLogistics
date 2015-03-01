package com.nexera.workflow.dao;

import java.util.List;

import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowMaster;


public interface WorkflowItemMasterDao extends GenericDao
{

    /**
     * @param workflowMaster
     * @return
     */
    List<WorkflowItemMaster> getWorkflowItemMasterListByWorkflowMaster( WorkflowMaster workflowMaster );


}
