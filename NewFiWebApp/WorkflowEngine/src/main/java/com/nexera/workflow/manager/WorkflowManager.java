/**
 * 
 */
package com.nexera.workflow.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.workflow.Constants.Status;
import com.nexera.workflow.Constants.WorkflowConstants;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowTaskConfigMaster;
import com.nexera.workflow.exception.FatalException;
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
    private String methodName = "execute";
    @Autowired
    WorkflowService workflowService;
    private WorkflowManager workflowManager;


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

        WorkflowItemMaster workflowItemMaster = getWorkflowItemExec().getWorkflowItemMaster();
        LOGGER.debug( "Updating workflowitem master to pending " );

        String result = executeMethod( workflowItemMaster );
        if ( result.equalsIgnoreCase( WorkflowConstants.SUCCESS ) ) {

            LOGGER.debug( "Updating workflowitem master to completed " );
            getWorkflowItemExec().setStatus( Status.COMPLETED.getStatus() );
            workflowService.updateWorkflowItemExecutionStatus( getWorkflowItemExec() );
            LOGGER.debug( "Checking if it has an onSuccess item to execute " );
            //TODO test this
            if ( workflowItemMaster.getOnSuccess() != null ) {
                WorkflowItemMaster successWorkflowItemMaster = workflowItemMaster.getOnSuccess();
                WorkflowItemExec successWorkflowItemExec = workflowService.setWorkflowItemIntoExecution( getWorkflowItemExec()
                    .getParentWorkflow(), successWorkflowItemMaster, getWorkflowItemExec().getParentWorkflowItemExec() );
                workflowManager.setWorkflowItemExec( successWorkflowItemExec );
                executorService.execute( workflowManager );

            }

        } else if ( result.equalsIgnoreCase( WorkflowConstants.FAILURE ) ) {

            LOGGER.debug( "Updating workflowitem master to completed " );
            getWorkflowItemExec().setStatus( Status.COMPLETED.getStatus() );
            workflowService.updateWorkflowItemExecutionStatus( getWorkflowItemExec() );
            LOGGER.debug( "Checking if it has an onFailure item to execute " );
            if ( workflowItemMaster.getOnFailure() != null ) {
                WorkflowItemMaster failureWorkflowItemMaster = workflowItemMaster.getOnFailure();
                WorkflowItemExec failureWorkflowItemExec = workflowService.setWorkflowItemIntoExecution( getWorkflowItemExec()
                    .getParentWorkflow(), failureWorkflowItemMaster, getWorkflowItemExec().getParentWorkflowItemExec() );
                workflowManager.setWorkflowItemExec( failureWorkflowItemExec );
                executorService.execute( workflowManager );

            }


        } else if ( result.equalsIgnoreCase( WorkflowConstants.PENDING ) ) {
            getWorkflowItemExec().setStatus( Status.PENDING.getStatus() );
            workflowService.updateWorkflowItemExecutionStatus( getWorkflowItemExec() );
        } else {
            LOGGER.error( "Invalid state returned " );
            throw new FatalException( "Invalid state returned " );
        }


    }


    @SuppressWarnings ( { "unchecked", "rawtypes" })
    private String executeMethod( WorkflowItemMaster workflowItemMaster )
    {
        String params = null;
        String result = null;
        String itemSpecificParams = workflowItemMaster.getParams();
        WorkflowTaskConfigMaster workflowTaskConfigMaster = workflowItemMaster.getTask();
        if ( workflowTaskConfigMaster != null ) {
            LOGGER.debug( "Will call the respective method of this workflow item " );
            String className = workflowTaskConfigMaster.getClassName();
            String systemSpecificParams = workflowTaskConfigMaster.getParams();
            if ( systemSpecificParams == null ) {
                params = itemSpecificParams;
            } else {
                if ( itemSpecificParams == null ) {
                    params = systemSpecificParams;
                } else {
                    params = systemSpecificParams.concat( "," ).concat( itemSpecificParams );
                    if ( params.endsWith( "," ) ) {
                        params = params.substring( 0, params.length() - 1 );
                    }
                }
            }

            LOGGER.debug( "Calling java reflection api to invoke method with specified params " );
            try {
                Class classToLoad = Class.forName( className );
                Object obj = classToLoad.newInstance();
                Class[] paramString = new Class[1];
                paramString[0] = String.class;
                Method method = classToLoad.getDeclaredMethod( methodName, paramString );
                result = (String) method.invoke( obj, params );

            } catch ( ClassNotFoundException e ) {
                LOGGER.debug( "Class Not Found " + e.getMessage() );
                throw new FatalException( "Specified Class Not Found " + e.getMessage() );
            } catch ( InstantiationException e ) {
                LOGGER.debug( "Cannot instantiate object of this class " + e.getMessage() );
                throw new FatalException( "Cannot instantiate object of this class" + e.getMessage() );
            } catch ( IllegalAccessException e ) {
                LOGGER.debug( "Unable to access the object" + e.getMessage() );
                throw new FatalException( "Unable to access the object " + e.getMessage() );
            } catch ( NoSuchMethodException e ) {
                LOGGER.error( "Method not found for this class " + e.getMessage() );
                throw new FatalException( "Method not found for this class " + e.getMessage() );
            } catch ( SecurityException e ) {
                LOGGER.error( "Security Contrainsts " + e.getMessage() );
                throw new FatalException( " Security Contrainsts " + e.getMessage() );
            } catch ( IllegalArgumentException e ) {
                LOGGER.error( "Arguments passed are not valid for this method " + e.getMessage() );
                throw new FatalException( "Arguments passed are not valid for this method " + e.getMessage() );
            } catch ( InvocationTargetException e ) {
                LOGGER.error( "Unable to invoke the method " + e.getMessage() );
                throw new FatalException( "Unable to invoke the method " + e.getMessage() );
            }
        }

        return result;

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
