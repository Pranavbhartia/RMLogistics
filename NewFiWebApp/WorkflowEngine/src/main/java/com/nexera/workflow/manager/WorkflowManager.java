/**
 * 
 */
package com.nexera.workflow.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.workflow.bean.WorkflowItemExec;
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

    private ExecutorService executorService;
    private int poolSize = 5;
    private WorkflowItemExec workflowItemExec;

    @Autowired
    WorkflowService workflowService;


    /**
      * 
      */
    public WorkflowManager()
    {
        LOGGER.debug( "Intializing thread pool for thread manager " );
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        executorService = Executors.newFixedThreadPool( poolSize, threadFactory );
    }


    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        LOGGER.debug( "Inside run method " );


    }


    /**
     * @return the workflowItemExec
     */
    public WorkflowItemExec getWorkflowItemExec()
    {
        return workflowItemExec;
    }


    /**
     * @param workflowItemExec the workflowItemExec to set
     */
    public void setWorkflowItemExec( WorkflowItemExec workflowItemExec )
    {
        this.workflowItemExec = workflowItemExec;
    }


}
