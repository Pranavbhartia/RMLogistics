package com.nexera.workflow.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.workflow.Constants.WorkflowConstants;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.bean.WorkflowTaskConfigMaster;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.exception.FatalException;
import com.nexera.workflow.manager.CacheManager;
import com.nexera.workflow.manager.WorkflowManager;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.utils.Util;

@Component
@Scope(value = "prototype")
public class EngineTrigger {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EngineTrigger.class);
	private ExecutorService executorService;

	@Autowired
	CacheManager cacheManager;

	@Autowired
	WorkflowService workflowService;

	@Autowired
	private ApplicationContext applicationContext;

	private WorkflowExec loanManagerWorkflowExec;

	private WorkflowExec customerWorkflowExec;

	Map<WorkflowItemMaster, WorkflowItemExec> customerMap = new HashMap<WorkflowItemMaster, WorkflowItemExec>();

	Map<WorkflowItemMaster, WorkflowItemExec> loanManagerMap = new HashMap<WorkflowItemMaster, WorkflowItemExec>();

	@Transactional
	public Map<String, Integer> triggerWorkFlow() {
		LOGGER.debug("Triggering a workflow ");
		Map<String, Integer> workflowMap = new HashMap<String, Integer>();
		WorkflowMaster customerWorkflowMaster = workflowService
		        .getWorkFlowByWorkFlowType(WorkflowConstants.CUSTOMER_WORKFLOW_TYPE);
		WorkflowMaster loanManagerWorkflowMaster = workflowService
		        .getWorkFlowByWorkFlowType(WorkflowConstants.LOAN_MANAGER_WORKFLOW_TYPE);

		customerWorkflowExec = workflowService
		        .setWorkflowIntoExecution(customerWorkflowMaster);

		loanManagerWorkflowExec = workflowService
		        .setWorkflowIntoExecution(loanManagerWorkflowMaster);

		LOGGER.debug("Trigger Customer Workflow ");
		workflowMap.put(WorkflowConstants.CUSTOMER_WORKFLOW_TYPE,
		        triggerWorkflow(customerWorkflowExec, customerWorkflowMaster));

		LOGGER.debug("Trigger Loan Manager Workflow ");
		workflowMap.put(
		        WorkflowConstants.LOAN_MANAGER_WORKFLOW_TYPE,
		        triggerWorkflow(loanManagerWorkflowExec,
		                loanManagerWorkflowMaster));

		customerMap.clear();
		loanManagerMap.clear();

		return workflowMap;
	}

	public Integer triggerWorkflow(WorkflowExec workflowExec,
	        WorkflowMaster workflowMaster) {
		LOGGER.debug("Setting workflow master into execution ");

		List<WorkflowItemMaster> workflowItemMasterList = workflowMaster
		        .getWorkflowItemMasterList();
		if (workflowItemMasterList != null) {
			for (WorkflowItemMaster workflowItemMaster : workflowItemMasterList) {
				if (workflowService
				        .checkIfOnSuccessOfAnotherItem(workflowItemMaster)) {
					addWorkflowItemInItsCorrespondingMap(workflowMaster,
					        workflowItemMaster, null);
					LOGGER.debug("Check to avoid duplicate entries ");
					continue;
				} else {
					if (!workflowItemMaster.getChildWorkflowItemMasterList()
					        .isEmpty()) {
						LOGGER.debug("Putting the parent into exeuction first ");
						WorkflowItemExec parentWorkflowItemExec = putGeneralWorkflowItemIntoExecution(
						        workflowExec, workflowItemMaster);
						for (WorkflowItemMaster childItemMaster : workflowItemMaster
						        .getChildWorkflowItemMasterList()) {
							if (workflowService
							        .checkIfOnSuccessOfAnotherItem(childItemMaster)) {
								LOGGER.debug("Checking whether it is an on success of another item, to avoid duplicates ");
								addWorkflowItemInItsCorrespondingMap(
								        workflowMaster, childItemMaster,
								        parentWorkflowItemExec);
								continue;
							} else {
								LOGGER.debug("First Checking Whether it has an on success item or not ");
								if (childItemMaster.getOnSuccess() != null) {
									WorkflowItemMaster successItem = childItemMaster
									        .getOnSuccess();
									WorkflowItemExec successWorkflowItemExec = null;
									if (checkWhetherItBelongsToThisWorkflow(
									        workflowMaster, successItem,
									        parentWorkflowItemExec)) {
										LOGGER.debug("Both Success and its triggerer resides in the same workflow ");
										successWorkflowItemExec = putChildWorkflowItemIntoExecution(
										        workflowExec, successItem,
										        parentWorkflowItemExec);
										LOGGER.debug("Adding the trigger into execution ");
										WorkflowItemExec triggererItem = putChildWorkflowItemIntoExecution(
										        workflowExec, childItemMaster,
										        parentWorkflowItemExec);
										LOGGER.debug("Setting its on sucess item  ");
										triggererItem
										        .setOnSuccessItem(successWorkflowItemExec);
										workflowService
										        .updateWorkflowItemExecutionStatus(triggererItem);

									} else {

										LOGGER.debug("Add workflow item in its corresponding map ");
										addWorkflowItemInItsCorrespondingMap(
										        workflowMaster, successItem,
										        parentWorkflowItemExec);
										LOGGER.debug("Success and its associate resides in different workflow ");
										if (successItem
										        .getParentWorkflowMaster()
										        .getWorkflowType()
										        .equalsIgnoreCase(
										                WorkflowConstants.CUSTOMER_WORKFLOW_TYPE)) {
											for (Map.Entry<WorkflowItemMaster, WorkflowItemExec> entry : customerMap
											        .entrySet()) {
												WorkflowItemMaster workflowItem = entry
												        .getKey();
												if (workflowItem.getId() == successItem
												        .getId()) {
													LOGGER.debug("This workflowitem resides in the map, setting it into execution now ");
													if (customerWorkflowExec != null) {
														successWorkflowItemExec = putChildWorkflowItemIntoExecution(
														        customerWorkflowExec,
														        successItem,
														        entry.getValue());
													} else {
														LOGGER.error("Customer workflow not intiated");
													}
													LOGGER.debug("Adding the trigger into execution ");
													WorkflowItemExec triggererItem = putChildWorkflowItemIntoExecution(
													        workflowExec,
													        childItemMaster,
													        parentWorkflowItemExec);
													LOGGER.debug("Setting its on sucess item  ");
													triggererItem
													        .setOnSuccessItem(successWorkflowItemExec);
													workflowService
													        .updateWorkflowItemExecutionStatus(triggererItem);
												}
											}
										} else {
											for (Map.Entry<WorkflowItemMaster, WorkflowItemExec> entry : loanManagerMap
											        .entrySet()) {
												WorkflowItemMaster workflowItem = entry
												        .getKey();
												if (workflowItem.getId() == successItem
												        .getId()) {
													LOGGER.debug("This workflowitem resides in the map, setting it into execution now ");
													if (loanManagerWorkflowExec != null) {
														successWorkflowItemExec = putChildWorkflowItemIntoExecution(
														        loanManagerWorkflowExec,
														        successItem,
														        entry.getValue());
													} else {
														LOGGER.error("Loan Manager workflow not intiated ");

													}

													LOGGER.debug("Adding the trigger into execution ");
													WorkflowItemExec triggererItem = putChildWorkflowItemIntoExecution(
													        workflowExec,
													        childItemMaster,
													        parentWorkflowItemExec);
													LOGGER.debug("Setting its on sucess item  ");
													triggererItem
													        .setOnSuccessItem(successWorkflowItemExec);
													workflowService
													        .updateWorkflowItemExecutionStatus(triggererItem);
												}
											}
										}
									}

								} else {
									LOGGER.debug("Doesnt Have a Success Workflow Item associated, hence putting the item into execution ");
									putChildWorkflowItemIntoExecution(
									        workflowExec, childItemMaster,
									        parentWorkflowItemExec);
								}
							}
						}

					} else {
						if (workflowItemMaster.getParentWorkflowItemMaster() == null) {
							LOGGER.debug("Add only if it doesnt have a parent, this is to avoid duplicate copies of child items");
							if (workflowItemMaster.getOnSuccess() != null) {
								WorkflowItemMaster successItem = workflowItemMaster
								        .getOnSuccess();
								WorkflowItemExec successWorkflowItemExec = null;
								if (checkWhetherItBelongsToThisWorkflow(
								        workflowMaster, successItem, null)) {
									LOGGER.debug("Both Success and its triggerer resides in the same workflow ");
									putGeneralWorkflowItemIntoExecution(
									        workflowExec, workflowItemMaster);
								} else {

									LOGGER.debug("Adding workflowitem in its respective map ");
									addWorkflowItemInItsCorrespondingMap(
									        workflowMaster, workflowItemMaster,
									        null);
									LOGGER.debug("Success and its associate resides in different workflow ");
									if (successItem
									        .getParentWorkflowMaster()
									        .getWorkflowType()
									        .equalsIgnoreCase(
									                WorkflowConstants.CUSTOMER_WORKFLOW_TYPE)) {
										for (Map.Entry<WorkflowItemMaster, WorkflowItemExec> entry : customerMap
										        .entrySet()) {
											WorkflowItemMaster workflowItem = entry
											        .getKey();
											if (workflowItem.getId() == successItem
											        .getId()) {
												LOGGER.debug("This workflowitem resides in the map, setting it into execution now ");
												if (customerWorkflowExec != null) {
													successWorkflowItemExec = putChildWorkflowItemIntoExecution(
													        customerWorkflowExec,
													        successItem,
													        entry.getValue());
												} else {
													LOGGER.error("Customer workflow not intiated, adding back customer ");

												}
												LOGGER.debug("Adding the trigger into execution ");
												WorkflowItemExec triggererItem = putGeneralWorkflowItemIntoExecution(
												        workflowExec,
												        workflowItemMaster);
												LOGGER.debug("Setting its on sucess item  ");
												triggererItem
												        .setOnSuccessItem(successWorkflowItemExec);
												workflowService
												        .updateWorkflowItemExecutionStatus(triggererItem);
											}
										}
									} else {
										for (Map.Entry<WorkflowItemMaster, WorkflowItemExec> entry : loanManagerMap
										        .entrySet()) {
											WorkflowItemMaster workflowItem = entry
											        .getKey();
											if (workflowItem.getId() == successItem
											        .getId()) {
												LOGGER.debug("This workflowitem resides in the map, setting it into execution now ");
												if (customerWorkflowExec != null) {
													successWorkflowItemExec = putChildWorkflowItemIntoExecution(
													        customerWorkflowExec,
													        successItem,
													        entry.getValue());
												} else {
													LOGGER.error("Loan Manager workflow not intiated");

												}
												LOGGER.debug("Adding the trigger into execution ");
												WorkflowItemExec triggererItem = putGeneralWorkflowItemIntoExecution(
												        workflowExec,
												        workflowItemMaster);
												LOGGER.debug("Setting its on sucess item  ");
												triggererItem
												        .setOnSuccessItem(successWorkflowItemExec);
												workflowService
												        .updateWorkflowItemExecutionStatus(triggererItem);
											}
										}
									}
								}
							} else {
								putGeneralWorkflowItemIntoExecution(
								        workflowExec, workflowItemMaster);
							}
						}
					}
				}
			}
		}
		return workflowExec.getId();
	}

	public WorkflowItemExec putGeneralWorkflowItemIntoExecution(
	        WorkflowExec workflowExec, WorkflowItemMaster workflowItemMaster) {
		return workflowService.setWorkflowItemIntoExecution(workflowExec,
		        workflowItemMaster, null);
	}

	public WorkflowItemExec putChildWorkflowItemIntoExecution(
	        WorkflowExec workflowExec, WorkflowItemMaster workflowItemMaster,
	        WorkflowItemExec parentWorkflowItemExec) {
		return workflowService.setWorkflowItemIntoExecution(workflowExec,
		        workflowItemMaster, parentWorkflowItemExec);

	}

	public Integer triggerLoanManagerWorkflow(WorkflowMaster workflowMaster) {
		LOGGER.debug("Setting workflow master into execution ");
		WorkflowExec workflowExec = workflowService
		        .setWorkflowIntoExecution(workflowMaster);
		return workflowExec.getId();
	}

	private boolean checkWhetherItBelongsToThisWorkflow(

	WorkflowMaster workflowMaster, WorkflowItemMaster workflowItemMaster,
	        WorkflowItemExec parentWorkflowItemExec) {

		if (workflowItemMaster.getParentWorkflowMaster().getWorkflowType()
		        .equalsIgnoreCase(workflowMaster.getWorkflowType())) {
			return true;
		} else {
			return false;
		}
	}

	private void addWorkflowItemInItsCorrespondingMap(
	        WorkflowMaster workflowMaster,
	        WorkflowItemMaster workflowItemMaster,
	        WorkflowItemExec parentWorkflowItemExec) {
		int count = 0;
		if (workflowItemMaster.getParentWorkflowMaster().getWorkflowType()
		        .equalsIgnoreCase(WorkflowConstants.LOAN_MANAGER_WORKFLOW_TYPE)) {
			for (Map.Entry<WorkflowItemMaster, WorkflowItemExec> entry : loanManagerMap
			        .entrySet()) {
				WorkflowItemMaster workflowItem = entry.getKey();
				if (workflowItem.getId() == workflowItemMaster.getId()) {
					count = 1;
				}
			}
			if (count == 0) {
				loanManagerMap.put(workflowItemMaster, parentWorkflowItemExec);
			}

		} else {

			for (Map.Entry<WorkflowItemMaster, WorkflowItemExec> entry : customerMap
			        .entrySet()) {
				WorkflowItemMaster workflowItem = entry.getKey();
				if (workflowItem.getId() == workflowItemMaster.getId()) {
					count = 1;
				}
			}
			if (count == 0) {
				customerMap.put(workflowItemMaster, parentWorkflowItemExec);
			}

		}

	}

	public String startWorkFlowItemExecution(int workflowItemExecutionId) {
		String result = null;
		LOGGER.debug("Inside method startWorkFlowItemExecution ");
		Future<String> future = null;
		executorService = cacheManager.initializePool();

		WorkflowItemExec workflowItemExecution = workflowService
		        .getWorkflowExecById(workflowItemExecutionId);
		if (workflowItemExecution != null) {
			LOGGER.debug("Updating workflow master status if its not updated ");
			WorkflowExec workflowExec = workflowItemExecution
			        .getParentWorkflow();
			if (workflowExec.getStatus().equals(
			        WorkItemStatus.NOT_STARTED.getStatus())) {
				workflowExec.setStatus(WorkItemStatus.STARTED.getStatus());
				workflowService.updateWorkflowExecStatus(workflowExec);
			}
			if (workflowItemExecution.getParentWorkflowItemExec() != null) {
				LOGGER.debug("It does have a parent, its a child entry ");
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

				LOGGER.debug("Updating workflow item execution status  to started");
				if (workflowItemExecution.getStatus().equalsIgnoreCase(
				        WorkItemStatus.NOT_STARTED.getStatus())) {
					workflowItemExecution.setStatus(WorkItemStatus.STARTED
					        .getStatus());
					workflowService
					        .updateWorkflowItemExecutionStatus(workflowItemExecution);
					WorkflowManager workflowManager = applicationContext
					        .getBean(WorkflowManager.class);
					workflowManager.setWorkflowItemExec(workflowItemExecution);
					future = executorService.submit(workflowManager);

					executorService.shutdown();
					try {
						executorService.awaitTermination(Long.MAX_VALUE,
						        TimeUnit.NANOSECONDS);
					} catch (InterruptedException e) {
						LOGGER.error("Exception caught while terminating executor "
						        + e.getMessage());
						throw new FatalException(
						        "Exception caught while terminating executor "
						                + e.getMessage());
					}
				}
				LOGGER.debug("Checking whether the parents all workflow items are executed ");
				WorkflowItemExec parentEWorkflowItemExec = workflowItemExecution
				        .getParentWorkflowItemExec();
				List<WorkflowItemExec> childWorkflowItemExecList = workflowService
				        .getWorkflowItemListByParentWorkflowExecItem(parentEWorkflowItemExec);
				int count = 0;
				for (WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList) {
					if (childWorkflowItemExec.getStatus().equalsIgnoreCase(
					        WorkItemStatus.COMPLETED.getStatus())) {
						count = count + 1;
					}
				}
				if (count == childWorkflowItemExecList.size()) {
					LOGGER.debug("All child items are complete, Updating the parent ");
					parentEWorkflowItemExec.setStatus(WorkItemStatus.COMPLETED
					        .getStatus());
					workflowService
					        .updateWorkflowItemExecutionStatus(parentEWorkflowItemExec);
				}

			} else {
				LOGGER.debug("Doesnt have a parent, can be the parent or can be independent ");
				List<WorkflowItemExec> childWorkflowItemExecList = workflowService
				        .getWorkflowItemListByParentWorkflowExecItem(workflowItemExecution);
				if (!childWorkflowItemExecList.isEmpty()) {
					LOGGER.debug(" The item id belongs to parent "
					        + workflowItemExecution);
					LOGGER.debug("Updating the workflow item execution status to started ");
					workflowItemExecution.setStatus(WorkItemStatus.STARTED
					        .getStatus());
					workflowService
					        .updateWorkflowItemExecutionStatus(workflowItemExecution);
					for (WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList) {
						LOGGER.debug("Starting all child threads together ");
						LOGGER.debug("Updating the child workflow item execution status to started ");
						if (!childWorkflowItemExec.getStatus()
						        .equalsIgnoreCase(
						                WorkItemStatus.COMPLETED.getStatus())) {
							childWorkflowItemExec
							        .setStatus(WorkItemStatus.STARTED
							                .getStatus());

							workflowService
							        .updateWorkflowItemExecutionStatus(childWorkflowItemExec);
							WorkflowManager workflowManager = applicationContext
							        .getBean(WorkflowManager.class);
							workflowManager
							        .setWorkflowItemExec(childWorkflowItemExec);
							executorService.submit(workflowManager);
						}
					}
					executorService.shutdown();
					try {
						executorService.awaitTermination(Long.MAX_VALUE,
						        TimeUnit.NANOSECONDS);
					} catch (InterruptedException e) {
						LOGGER.error("Exception caught while terminating executor "
						        + e.getMessage());
						throw new FatalException(
						        "Exception caught while terminating executor "
						                + e.getMessage());
					}

					int count = 0;
					for (WorkflowItemExec childWorkflowItemExec : childWorkflowItemExecList) {
						if (childWorkflowItemExec.getStatus().equalsIgnoreCase(
						        WorkItemStatus.COMPLETED.getStatus())) {
							count = count + 1;
						}
					}
					if (count == childWorkflowItemExecList.size()) {
						LOGGER.debug("All child items are complete ");
						if (!workflowItemExecution.getStatus()
						        .equalsIgnoreCase(
						                WorkItemStatus.COMPLETED.getStatus())) {
							executorService = cacheManager.initializePool();
							LOGGER.debug(" Triggering the parent");
							WorkflowManager workflowManager = applicationContext
							        .getBean(WorkflowManager.class);
							workflowManager
							        .setWorkflowItemExec(workflowItemExecution);
							future = executorService.submit(workflowManager);
							executorService.shutdown();
							try {
								executorService.awaitTermination(
								        Long.MAX_VALUE, TimeUnit.NANOSECONDS);
							} catch (InterruptedException e) {
								LOGGER.error("Exception caught while terminating executor "
								        + e.getMessage());
								throw new FatalException(
								        "Exception caught while terminating executor "
								                + e.getMessage());
							}
						}

					}

				} else {
					LOGGER.debug("Independent execution");
					if (!workflowItemExecution.getStatus().equalsIgnoreCase(
					        WorkItemStatus.COMPLETED.getStatus())) {
						workflowItemExecution.setStatus(WorkItemStatus.STARTED
						        .getStatus());
						workflowService
						        .updateWorkflowItemExecutionStatus(workflowItemExecution);
						WorkflowManager workflowManager = applicationContext
						        .getBean(WorkflowManager.class);
						workflowManager
						        .setWorkflowItemExec(workflowItemExecution);
						future = executorService.submit(workflowManager);
						executorService.shutdown();
						try {
							executorService.awaitTermination(Long.MAX_VALUE,
							        TimeUnit.NANOSECONDS);
						} catch (InterruptedException e) {
							LOGGER.error("Exception caught while terminating executor "
							        + e.getMessage());
						}
					}
				}
			}
		}
		return "";

	}

	public String getRenderStateInfoOfItem(int workflowItemExecId) {
		LOGGER.debug("Inside method getRenderStaetInfoOfItem ");
		WorkflowItemExec workflowItemExec = workflowService
		        .getWorkflowExecById(workflowItemExecId);
		if (workflowItemExec != null) {
			String output = reflectionExecuteMethod(workflowItemExec,
			        WorkflowConstants.RENDER_STATE_INFO_METHOD);
			return output;
		}
		return null;
	}

	public String checkStatus(int workflowItemExecId) {
		LOGGER.debug("Inside method checkStatus ");
		WorkflowItemExec workflowItemExec = workflowService
		        .getWorkflowExecById(workflowItemExecId);
		if (workflowItemExec != null) {
			String output = reflectionExecuteMethod(workflowItemExec,
			        WorkflowConstants.CHECK_STATUS_METHOD);
			return output;
		}
		return null;
	}

	public String invokeActionMethod(int workflowItemExecId) {
		LOGGER.debug("Inside method invokeActionMethod ");
		WorkflowItemExec workflowItemExec = workflowService
		        .getWorkflowExecById(workflowItemExecId);
		if (workflowItemExec != null) {
			String output = reflectionExecuteMethod(workflowItemExec,
			        WorkflowConstants.INVOKE_ACTION_METHOD);
			return output;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String reflectionExecuteMethod(WorkflowItemExec workflowItemExec,
	        String methodName) {

		HashMap<String, Object> params = new HashMap<String, Object>();
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
				params.putAll(systemParamMap);
				if (workflowItemExec.getParams() != null) {
					String jsonParamString = workflowItemExec.getParams();
					itemParamMap = Util.convertJsonToMap(jsonParamString);
					params.putAll(itemParamMap);
				}

			}

			LOGGER.debug("Calling java reflection api to invoke method with specified params ");
			try {
				Class classToLoad = Class.forName(className);
				Object obj = applicationContext.getBean(classToLoad);

				Method method = classToLoad.getDeclaredMethod(methodName,
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

	@SuppressWarnings("unchecked")
	public List<WorkflowItemExec> getWorkflowItemExecByWorkflowMasterExec(
	        int workflowexecId) {
		LOGGER.debug("Inside method getWorkflowItemExecByWorkflowMasterExec");
		WorkflowExec workflowExec = workflowService
		        .getWorkflowExecFromId(workflowexecId);
		if (workflowExec != null) {
			return workflowExec.getWorkflowItems();
		}
		return Collections.EMPTY_LIST;
	}

	public void changeStateOfWorkflowItemExec(int workflowItemExecId,
	        String status) {
		WorkflowItemExec workflowItemExecution = workflowService
		        .getWorkflowExecById(workflowItemExecId);
		workflowItemExecution.setStatus(status);
		workflowService
		        .updateWorkflowItemExecutionStatus(workflowItemExecution);
	}
	/*
	 * public static void main(String[] args) { String json =
	 * "{\"workflowType\":\"LM_WF_ALL\"}"; ApplicationContext applicationContext
	 * = new ClassPathXmlApplicationContext(
	 * "spring\\workflow-engine-core-context.xml"); EngineTrigger engTrigger =
	 * (EngineTrigger) applicationContext .getBean(EngineTrigger.class);
	 * engTrigger.triggerWorkFlow(json); //
	 * engTrigger.startWorkFlowItemExecution( 74 );
	 * 
	 * }
	 */
}
