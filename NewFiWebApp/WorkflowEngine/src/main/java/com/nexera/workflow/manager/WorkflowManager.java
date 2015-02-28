/**
 * 
 */
package com.nexera.workflow.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.service.WorkflowService;


/**
 * @author Utsav
 *
 */
@Component
@Scope ( value = "prototype")
public class WorkflowManager implements Runnable
{

    private static final Logger LOGGER = LoggerFactory.getLogger( WorkflowManager.class );

    private WorkflowMaster workflowMaster;

    @Autowired
    WorkflowService workflowService;


    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        LOGGER.debug( "Inside run method " );
        WorkflowExec workflowExec = workflowService.setWorkflowIntoExecution( getWorkflowMaster() );

    }


    /**
     * @return the workflowMaster
     */
    public WorkflowMaster getWorkflowMaster()
    {
        return workflowMaster;
    }


    /**
     * @param workflowMaster the workflowMaster to set
     */
    public void setWorkflowMaster( WorkflowMaster workflowMaster )
    {
        this.workflowMaster = workflowMaster;
    }

}
