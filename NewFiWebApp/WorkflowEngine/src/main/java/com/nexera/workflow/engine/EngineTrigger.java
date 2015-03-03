package com.nexera.workflow.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nexera.workflow.Constants.Status;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.exception.FatalException;
import com.nexera.workflow.manager.CacheManager;
import com.nexera.workflow.manager.WorkflowManager;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.vo.WorkflowVO;


@Component
@Scope ( value = "prototype")
public class EngineTrigger
{

    private static final Logger LOGGER = LoggerFactory.getLogger( EngineTrigger.class );
    private ExecutorService executorService;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    WorkflowManager workflowManager;


    public Integer triggerWorkFlow( String workflowJsonString )
    {
        LOGGER.debug( "Triggering a workflow " );
        WorkflowMaster workflowMaster = null;
        Gson gson = new Gson();
        WorkflowVO workflowVO = gson.fromJson( workflowJsonString, WorkflowVO.class );
        if ( workflowVO != null ) {
            String workflowType = workflowVO.getWorkflowType();
            workflowMaster = workflowService.getWorkFlowByWorkFlowType( workflowType );
            if ( workflowMaster != null ) {
                LOGGER.debug( "Workflow found for this workflowtype " + workflowMaster.getWorkflowType() );
                WorkflowExec workflowExec = workflowService.setWorkflowIntoExecution( workflowMaster );
                LOGGER.debug( "Workflow has been put into execution , Getting all items associated with workflow " );
                List<WorkflowItemMaster> workflowItemMasterList = workflowService
                    .getWorkflowItemMasterListByWorkflowMaster( workflowMaster );
                for ( WorkflowItemMaster workflowItemMaster : workflowItemMasterList ) {
                    LOGGER.debug( "Initializing all workflow items " );
                    //TODO test this 
                    if ( !workflowService.checkIfOnSuccessOfAnotherItem( workflowItemMaster ) ) {
                        if ( !workflowItemMaster.getChildWorkflowItemMasterList().isEmpty() ) {
                            WorkflowItemExec workflowItemExec = workflowService.setWorkflowItemIntoExecution( workflowExec,
                                workflowItemMaster, null );
                            for ( WorkflowItemMaster childworkflowItemMaster : workflowItemMaster
                                .getChildWorkflowItemMasterList() ) {
                                LOGGER.debug( "In this case will add parent workflow item execution id " );
                                if ( !workflowService.checkIfOnSuccessOfAnotherItem( childworkflowItemMaster ) ) {
                                    workflowService.setWorkflowItemIntoExecution( workflowExec, childworkflowItemMaster,
                                        workflowItemExec );
                                }
                            }

                        } else {
                            if ( workflowItemMaster.getParentWorkflowItemMaster() == null )
                                workflowService.setWorkflowItemIntoExecution( workflowExec, workflowItemMaster, null );
                        }
                    }

                }
                return workflowMaster.getId();
            }
        }
        return 0;

    }


    public String startWorkFlowItemExecution( int workflowItemExecutionId )
    {
        LOGGER.debug( "Inside method startWorkFlowItemExecution " );
        if ( !cacheManager.isInitialized() ) {
            executorService = cacheManager.initializePool();
        }


        WorkflowItemExec workflowItemExecution = workflowService.getWorkflowExecById( workflowItemExecutionId );
        if ( workflowItemExecution != null ) {
            LOGGER.debug( "Updating workflow master status if its not updated " );
            WorkflowExec workflowExec = workflowItemExecution.getParentWorkflow();
            if ( !workflowExec.getStatus().equals( Status.STARTED.getStatus() ) ) {
                workflowExec.setStatus( Status.STARTED.getStatus() );
                workflowService.updateWorkflowExecStatus( workflowExec );
            }
            if ( workflowItemExecution.getParentWorkflowItemExec() != null ) {
                LOGGER.debug( "It does have a parent, its a child entry " );
                WorkflowItemExec parentWorkflowItemExec = workflowItemExecution.getParentWorkflowItemExec();
                if ( parentWorkflowItemExec.getStatus().equalsIgnoreCase( Status.NOT_STARTED.getStatus() ) ) {
                    LOGGER.debug( "Updating the parent workflow item status to started " );
                    parentWorkflowItemExec.setStatus( Status.STARTED.getStatus() );
                    workflowService.updateWorkflowItemExecutionStatus( parentWorkflowItemExec );
                }

                LOGGER.debug( "Updating workflow item execution status  to started" );
                workflowItemExecution.setStatus( Status.STARTED.getStatus() );
                workflowService.updateWorkflowItemExecutionStatus( workflowItemExecution );
                workflowManager.setWorkflowItemExec( workflowItemExecution );
                executorService.execute( workflowManager );

                executorService.shutdown();
                try {
                    executorService.awaitTermination( Long.MAX_VALUE, TimeUnit.NANOSECONDS );
                } catch ( InterruptedException e ) {
                    LOGGER.error( "Exception caught while terminating executor " + e.getMessage() );
                    throw new FatalException( "Exception caught while terminating executor " + e.getMessage() );
                }

                LOGGER.debug( "Checking whether the parents all workflow items are executed " );
                WorkflowItemExec parentEWorkflowItemExec = workflowItemExecution.getParentWorkflowItemExec();
                List<WorkflowItemExec> childWorkflowItemExecList = workflowService
                    .getWorkflowItemListByParentWorkflowExecItem( parentEWorkflowItemExec );
                int count = 0;
                for ( WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList ) {
                    if ( childWorkflowItemExec.getStatus().equalsIgnoreCase( Status.COMPLETED.getStatus() ) ) {
                        count = count + 1;
                    }
                }
                if ( count == childWorkflowItemExecList.size() ) {
                    LOGGER.debug( "All child items are complete, Updating the parent " );
                    parentEWorkflowItemExec.setStatus( Status.COMPLETED.getStatus() );
                    workflowService.updateWorkflowItemExecutionStatus( parentEWorkflowItemExec );
                }

            } else {
                LOGGER.debug( "Doesnt have a parent, can be the parent or can be independent " );
                List<WorkflowItemExec> childWorkflowItemExecList = workflowService
                    .getWorkflowItemListByParentWorkflowExecItem( workflowItemExecution );
                if ( childWorkflowItemExecList != null ) {
                    LOGGER.debug( " The item id belongs to parent " + workflowItemExecution );
                    LOGGER.debug( "Updating the workflow item execution status to started " );
                    workflowItemExecution.setStatus( Status.STARTED.getStatus() );
                    workflowService.updateWorkflowItemExecutionStatus( workflowItemExecution );
                    for ( WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList ) {
                        LOGGER.debug( "Starting all child threads together " );
                        LOGGER.debug( "Updating the child workflow item execution status to started " );
                        childWorkflowItemExec.setStatus( Status.STARTED.getStatus() );
                        workflowService.updateWorkflowItemExecutionStatus( childWorkflowItemExec );
                        workflowManager.setWorkflowItemExec( childWorkflowItemExec );
                        executorService.execute( workflowManager );
                    }
                    executorService.shutdown();
                    try {
                        executorService.awaitTermination( Long.MAX_VALUE, TimeUnit.NANOSECONDS );
                    } catch ( InterruptedException e ) {
                        LOGGER.error( "Exception caught while terminating executor " + e.getMessage() );
                        throw new FatalException( "Exception caught while terminating executor " + e.getMessage() );
                    }

                    int count = 0;
                    for ( WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList ) {
                        if ( childWorkflowItemExec.getStatus().equalsIgnoreCase( Status.COMPLETED.getStatus() ) ) {
                            count = count + 1;
                        }
                    }
                    if ( count == childWorkflowItemExecList.size() ) {
                        LOGGER.debug( "All child items are complete, Updating the parent " );
                        workflowItemExecution.setStatus( Status.COMPLETED.getStatus() );
                        workflowService.updateWorkflowItemExecutionStatus( workflowItemExecution );
                    }


                } else {
                    LOGGER.debug( "Independent execution" );
                    workflowItemExecution.setStatus( Status.STARTED.getStatus() );
                    workflowService.updateWorkflowItemExecutionStatus( workflowItemExecution );
                    workflowManager.setWorkflowItemExec( workflowItemExecution );
                    executorService.execute( workflowManager );
                    executorService.shutdown();
                    try {
                        executorService.awaitTermination( Long.MAX_VALUE, TimeUnit.NANOSECONDS );
                    } catch ( InterruptedException e ) {
                        LOGGER.error( "Exception caught while terminating executor " + e.getMessage() );
                        throw new FatalException( "Exception caught while terminating executor " + e.getMessage() );
                    }

                }
            }
        }

        return null;
    }


    public List<WorkflowItemExec> getWorkflowItemExecByWorkflowMasterExec( int workflowexecId )
    {
        LOGGER.debug( "Inside method getWorkflowItemExecByWorkflowMasterExec" );
        WorkflowExec workflowExec = workflowService.getWorkflowExecFromId( workflowexecId );
        if ( workflowExec != null ) {
            return workflowExec.getWorkflowItems();
        }
        return new ArrayList<WorkflowItemExec>();
    }


    public static void main( String[] args )
    {
        String json = "{\"workflowType\":\"LM_WF_ALL\"}";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext( "spring\\core-context.xml" );
        EngineTrigger engTrigger = (EngineTrigger) applicationContext.getBean( EngineTrigger.class );
        engTrigger.triggerWorkFlow( json );
        // engTrigger.startWorkFlowItemExecution( 74 );


    }
}
