/**
 * 
 */
package com.nexera.workflow.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.workflow.Constants.WorkflowConstants;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowTaskConfigMaster;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.utils.Util;

/**
 * @author Utsav
 *
 */
@Component
@Scope(value = "prototype")
public class WorkflowManager implements Callable<String> {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(WorkflowManager.class);

	private WorkflowItemExec workflowItemExec;
	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private ApplicationContext applicationContext;

	Map<String, Object> params = new HashMap<String, Object>();

	/**
      * 
      */
	public WorkflowManager() {

	}

	public String startWorkFlowItemExecution(
	        WorkflowItemExec workflowItemExecution) {

		LOGGER.debug("If parent exist, put parent into execution ");
		changeStatusOfParentToStarted(workflowItemExecution);

		LOGGER.debug("Updating workflow master status if its not updated ");
		WorkflowItemMaster workflowItemMaster = workflowItemExecution
		        .getWorkflowItemMaster();
		String result = executeMethod(workflowItemMaster, workflowItemExecution);
		if (result != null) {
			if (result.equalsIgnoreCase(WorkItemStatus.COMPLETED.getStatus())) {

				LOGGER.debug("Updating workflowitem master to completed ");
				workflowItemExecution.setEndTime(new Date());
				workflowItemExecution.setStatus(WorkItemStatus.COMPLETED
				        .getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(workflowItemExecution);
				LOGGER.debug("Checking if it has an onSuccess item to execute ");
				changeStatusOfParentToCompleted(workflowItemExecution);
				if (workflowItemExecution.getOnSuccessItem() != null) {
					WorkflowItemExec succesItem = workflowItemExecution
					        .getOnSuccessItem();
					startWorkFlowItemExecution(succesItem);
				}				

			} else if (result.equalsIgnoreCase(WorkflowConstants.FAILURE)) {

				LOGGER.debug("Updating workflowitem master to completed ");
				workflowItemExecution.setStatus(WorkItemStatus.COMPLETED
				        .getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(workflowItemExecution);
				LOGGER.debug("Checking if it has an onFailure item to execute ");

			} else if (result.equalsIgnoreCase(WorkItemStatus.PENDING
			        .getStatus())) {
				workflowItemExecution.setStatus(WorkItemStatus.PENDING
				        .getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(workflowItemExecution);
			} else if (result.equalsIgnoreCase(WorkItemStatus.STARTED
			        .getStatus())) {
				workflowItemExecution.setStatus(WorkItemStatus.STARTED
				        .getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(workflowItemExecution);
			}
		} else {
			LOGGER.error("Invalid result received for this milestone "
			        + workflowItemExecution.getId());
		}

		return result;

	}

	private void changeStatusOfParentToCompleted(
	        WorkflowItemExec workflowItemExec) {
		WorkflowItemExec parentWorkflowItemExec = workflowItemExec
		        .getParentWorkflowItemExec();
		if (parentWorkflowItemExec != null) {
			List<WorkflowItemExec> childWorkflowItemExecList = workflowService
			        .getWorkflowItemListByParentWorkflowExecItem(parentWorkflowItemExec);
			int count = 0;
			for (WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList) {
				if (childWorkflowItemExec.getStatus().equalsIgnoreCase(
				        WorkItemStatus.COMPLETED.getStatus())) {
					count = count + 1;
				}
			}
			if (count == childWorkflowItemExecList.size()) {
				LOGGER.debug("All child items are complete, Updating the parent ");
				parentWorkflowItemExec.setStatus(WorkItemStatus.COMPLETED
				        .getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(parentWorkflowItemExec);
			}
		}
	}

	private void changeStatusOfParentToStarted(
	        WorkflowItemExec workflowItemExecution) {
		if (workflowItemExecution.getParentWorkflowItemExec() != null) {

			WorkflowItemExec parentWorkflowItemExec = workflowItemExecution
			        .getParentWorkflowItemExec();
			if (parentWorkflowItemExec.getStatus().equalsIgnoreCase(
			        WorkItemStatus.NOT_STARTED.getStatus())) {
				LOGGER.debug("Updating the parent workflow item status to started ");
				parentWorkflowItemExec.setStatus(WorkItemStatus.STARTED
				        .getStatus());
				workflowService
				        .updateWorkflowItemExecutionStatus(parentWorkflowItemExec);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String executeMethod(WorkflowItemMaster workflowItemMaster,
	        WorkflowItemExec workflowItemExec) {

		Map<String, Object> itemParamMap;
		Map<String, Object> systemParamMap;
		String result = null;
		WorkflowTaskConfigMaster workflowTaskConfigMaster = workflowItemExec
		        .getWorkflowItemMaster().getTask();
		if (workflowTaskConfigMaster != null) {
			LOGGER.debug("Will call the respective method of this workflow item ");
			String className = workflowTaskConfigMaster.getClassName();
			String systemSpecificParamsJson = workflowTaskConfigMaster
			        .getParams();
			systemParamMap = Util.convertJsonToMap(systemSpecificParamsJson);
			if (systemParamMap == null) {

				if (workflowItemExec.getParams() != null) {
					String jsonParamString = workflowItemExec.getParams();
					itemParamMap = Util.convertJsonToMap(jsonParamString);
					params.putAll(itemParamMap);
				}

			} else {

				if (workflowItemExec.getParams() != null) {
					String jsonParamString = workflowItemExec.getParams();
					itemParamMap = Util.convertJsonToMap(jsonParamString);
					params.putAll(itemParamMap);
				}
				params.putAll(systemParamMap);

			}

			LOGGER.debug("Calling java reflection api to invoke method with specified params ");
			try {
				Class classToLoad = Class.forName(className);
				Object obj = applicationContext.getBean(classToLoad);

				Method method = classToLoad.getDeclaredMethod(
				        WorkflowConstants.EXECUTE_METHOD,
				        new Class[] { HashMap.class });

				result = (String) method.invoke(obj, params);

			} catch (ClassNotFoundException e) {
				LOGGER.debug("Class Not Found " + e.getMessage());
			} catch (IllegalAccessException e) {
				LOGGER.debug("Unable to access the object" + e.getMessage());
			} catch (NoSuchMethodException e) {
				LOGGER.error("Method not found for this class "
				        + e.getMessage());
			} catch (SecurityException e) {
				LOGGER.error("Security Contrainsts " + e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("Arguments passed are not valid for this method "
				        + e.getMessage());
			} catch (InvocationTargetException e) {
				LOGGER.error("Unable to invoke the method " + e.getMessage());
			}
		}

		return result;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public String call() throws Exception {
		LOGGER.debug("Inside run method ");

		return startWorkFlowItemExecution(getWorkflowItemExec());
	}

}
