/**
 * 
 */
package com.nexera.workflow.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.workflow.Constants.Status;
import com.nexera.workflow.Constants.WorkflowConstants;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowTaskConfigMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.exception.FatalException;
import com.nexera.workflow.service.WorkflowService;

/**
 * @author Utsav
 *
 */
@Component
@Scope(value = "prototype")
public class WorkflowManager implements Runnable {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(WorkflowManager.class);

	private WorkflowItemExec workflowItemExec;
	private String methodName = "execute";

	private Object[] params;
	@Autowired
	private WorkflowService workflowService;

	/**
      * 
      */
	public WorkflowManager() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOGGER.debug("Inside run method ");

		startWorkFlowItemExecution(getWorkflowItemExec().getId());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String executeMethod(WorkflowItemMaster workflowItemMaster) {
		Object[] params = null;
		String result = null;
		Object[] itemSpecificParams = getParams();
		WorkflowTaskConfigMaster workflowTaskConfigMaster = workflowItemMaster
		        .getTask();
		if (workflowTaskConfigMaster != null) {
			LOGGER.debug("Will call the respective method of this workflow item ");
			String className = workflowTaskConfigMaster.getClassName();
			String systemSpecificParameter = workflowTaskConfigMaster
			        .getParams();
			if(systemSpecificParameter == null)
			{
				//TODO temporary solution
				systemSpecificParameter = "";
			}
			String[] systemSpecificParams = systemSpecificParameter.split(",");
			if (systemSpecificParams[0] == "") {
				params = itemSpecificParams;
			} else {
				if (itemSpecificParams == null) {
					params = systemSpecificParams;
				} else {
					params = systemSpecificParams;
					int i = params.length;
					for (Object itemParam : itemSpecificParams) {
						LOGGER.debug("Appending the input parameters after system specific parameters ");
						params[i++] = itemParam;
					}
				}
			}

			LOGGER.debug("Calling java reflection api to invoke method with specified params ");
			try {
				
				ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				        "spring\\workflow-engine-core-context.xml");
				EngineTrigger engTrigger = (EngineTrigger) applicationContext
				        .getBean(EngineTrigger.class);
				
				Class classToLoad = Class.forName(className);
				Object obj = classToLoad.newInstance();
			
				Method method = classToLoad.getDeclaredMethod(methodName,
				        new Class[] { Object[].class });
				result = (String) method.invoke(obj, new Object[] { params });

			} catch (ClassNotFoundException e) {
				LOGGER.debug("Class Not Found " + e.getMessage());
				throw new FatalException("Specified Class Not Found "
				        + e.getMessage());
			} catch (InstantiationException e) {
				LOGGER.debug("Cannot instantiate object of this class "
				        + e.getMessage());
				throw new FatalException(
				        "Cannot instantiate object of this class"
				                + e.getMessage());
			} catch (IllegalAccessException e) {
				LOGGER.debug("Unable to access the object" + e.getMessage());
				throw new FatalException("Unable to access the object "
				        + e.getMessage());
			} catch (NoSuchMethodException e) {
				LOGGER.error("Method not found for this class "
				        + e.getMessage());
				throw new FatalException("Method not found for this class "
				        + e.getMessage());
			} catch (SecurityException e) {
				LOGGER.error("Security Contrainsts " + e.getMessage());
				throw new FatalException(" Security Contrainsts "
				        + e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("Arguments passed are not valid for this method "
				        + e.getMessage());
				throw new FatalException(
				        "Arguments passed are not valid for this method "
				                + e.getMessage());
			} catch (InvocationTargetException e) {
				LOGGER.error("Unable to invoke the method " + e.getMessage());
				throw new FatalException("Unable to invoke the method "
				        + e.getMessage());
			}
		}

		return result;

	}

	public void startWorkFlowItemExecution(int workflowItemExecutionId) {

		WorkflowItemExec workflowItemExecution = workflowService
		        .getWorkflowExecById(workflowItemExecutionId);
		if (workflowItemExecution != null) {
			LOGGER.debug("Updating workflow master status if its not updated ");
			WorkflowItemMaster workflowItemMaster = workflowItemExecution
			        .getWorkflowItemMaster();
			String result = executeMethod(workflowItemMaster);

			if (result.equalsIgnoreCase(WorkflowConstants.SUCCESS)) {

				LOGGER.debug("Updating workflowitem master to completed ");
				workflowItemExecution.setStatus(Status.COMPLETED.getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(workflowItemExecution);
				LOGGER.debug("Checking if it has an onSuccess item to execute ");
				if (workflowItemMaster.getOnSuccess() != null) {
					WorkflowItemMaster successWorkflowItemMaster = workflowItemMaster
					        .getOnSuccess();
					WorkflowItemExec successWorkflowItemExec = workflowService
					        .setWorkflowItemIntoExecution(workflowItemExecution
					                .getParentWorkflow(),
					                successWorkflowItemMaster,
					                workflowItemExecution
					                        .getParentWorkflowItemExec());
					startWorkFlowItemExecution(successWorkflowItemExec.getId());

				}

			} else if (result.equalsIgnoreCase(WorkflowConstants.FAILURE)) {

				LOGGER.debug("Updating workflowitem master to completed ");
				workflowItemExecution.setStatus(Status.COMPLETED.getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(workflowItemExecution);
				LOGGER.debug("Checking if it has an onFailure item to execute ");
				// TODO test this Might Have issues regarding parent of success
				// workflow item
				if (workflowItemMaster.getOnFailure() != null) {
					WorkflowItemMaster failureWorkflowItemMaster = workflowItemMaster
					        .getOnFailure();
					WorkflowItemExec failureWorkflowItemExec = workflowService
					        .setWorkflowItemIntoExecution(workflowItemExecution
					                .getParentWorkflow(),
					                failureWorkflowItemMaster,
					                workflowItemExecution
					                        .getParentWorkflowItemExec());
					startWorkFlowItemExecution(failureWorkflowItemExec.getId());
				}

			} else if (result.equalsIgnoreCase(WorkflowConstants.PENDING)) {
				workflowItemExecution.setStatus(Status.PENDING.getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(workflowItemExecution);
			} else {
				LOGGER.error("Invalid state returned ");
				throw new FatalException("Invalid state returned ");
			}

		}

	}

	/**
	 * @return the workflowItemExec
	 */
	public WorkflowItemExec getWorkflowItemExec() {
		return workflowItemExec;
	}

	/**
	 * @param workflowItemExec
	 *            the workflowItemExec to set
	 */
	public void setWorkflowItemExec(WorkflowItemExec workflowItemExec) {
		this.workflowItemExec = workflowItemExec;
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

}
