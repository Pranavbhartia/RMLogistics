package com.nexera.workflow.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.dao.WorkflowExecDao;
import com.nexera.workflow.dao.WorkflowMasterDao;
import com.nexera.workflow.service.WorkflowService;


@Component
public class WorkflowServiceImpl implements WorkflowService
{

    @Autowired
    WorkflowMasterDao workflowMasterDao;

    @Autowired
    WorkflowExecDao workflowExecDao;

    private static final Logger LOGGER = LoggerFactory.getLogger( WorkflowServiceImpl.class );


    /* (non-Javadoc)
     * @see com.nexera.workflow.service.WorkflowService#getWorkFlowByWorkFlowType()
     */
    @Override
    public WorkflowMaster getWorkFlowByWorkFlowType( String workflowType )
    {
        LOGGER.debug( "Inside method getWorkFlowByWorkFlowType " );
        WorkflowMaster workflowMaster = workflowMasterDao.findWorkflowByType( workflowType );
        return workflowMaster;
    }


    /* (non-Javadoc)
     * @see com.nexera.workflow.service.WorkflowService#setWorkflowIntoExecution(com.nexera.workflow.bean.WorkflowMaster)
     */
    @Override
    public WorkflowExec setWorkflowIntoExecution( WorkflowMaster workflowMaster )
    {
        LOGGER.debug( "Inside method setWorkflowIntoExecution " );
        WorkflowExec workflowExec = new WorkflowExec();
        workflowExec.setCreatedByID( workflowMaster.getCreatedBy() );
        workflowExec.setActive( true );
        workflowExec.setCreatedTime( new Date() );
        workflowExec.setWorkflowMaster( workflowMaster );
        workflowExec.setStatus( "Initialized" );

        workflowExec = (WorkflowExec) workflowExecDao.save( workflowExec );
        return workflowExec;
    }


}
