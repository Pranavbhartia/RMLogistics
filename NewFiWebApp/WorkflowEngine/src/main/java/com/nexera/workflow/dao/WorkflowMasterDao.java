package com.nexera.workflow.dao;

import com.nexera.workflow.bean.WorkflowMaster;


public interface WorkflowMasterDao extends GenericDao
{

    /**
     * @param workflowType
     * @return
     */
    WorkflowMaster findWorkflowByType( String workflowType );


}
