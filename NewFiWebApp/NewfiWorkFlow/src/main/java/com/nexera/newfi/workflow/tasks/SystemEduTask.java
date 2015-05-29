package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

/*
 * @author Charu Joshi
 */
@Component
public class SystemEduTask extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	private NotificationService notificationService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		int workflowItemExecutionId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		WorkflowItemExec workflowItemExecution = workflowService
		        .getWorkflowExecById(workflowItemExecutionId);
		if (workflowItemExecution != null) {
			WorkflowExec workflowExec = workflowItemExecution
			        .getParentWorkflow();
			if (!workflowExec.getStatus().equals(
			        WorkItemStatus.STARTED.getStatus())) {
				workflowExec.setStatus(WorkItemStatus.STARTED.getStatus());
				workflowService.updateWorkflowExecStatus(workflowExec);
			}
			workflowItemExecution.setStatus(WorkItemStatus.COMPLETED
			        .getStatus());
			workflowService
			        .updateWorkflowItemExecutionStatus(workflowItemExecution);

			if (workflowItemExecution.getParentWorkflowItemExec() != null) {
				WorkflowItemExec parentWorkflowItemExec = workflowItemExecution
				        .getParentWorkflowItemExec();
				if (parentWorkflowItemExec.getStatus().equalsIgnoreCase(
				        WorkItemStatus.NOT_STARTED.getStatus())) {
					parentWorkflowItemExec.setStatus(WorkItemStatus.STARTED
					        .getStatus());
					workflowService
					        .updateWorkflowItemExecutionStatus(parentWorkflowItemExec);
				}
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
					inputMap.put(WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME,
					        parentEWorkflowItemExec.getId());
					String params = Utils.convertMapToJson(inputMap);
					workflowService.saveParamsInExecTable(
					        parentEWorkflowItemExec.getId(), params);
					EngineTrigger engineTrigger = applicationContext
					        .getBean(EngineTrigger.class);
					engineTrigger
					        .startWorkFlowItemExecution(parentEWorkflowItemExec
					                .getId());
				}

			}
			// Dismiss any SYS_EDU_NOTIFICATION_TYPE notification
			dismissSystemEduNotification(inputMap);
		}
		return WorkItemStatus.COMPLETED.getStatus();
	}

	private void dismissSystemEduNotification(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.SYS_EDU_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), true);
		for (NotificationVO notificationVO : notificationList) {
			notificationService.dismissNotification(notificationVO.getId());
		}
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		return null;
	}

}
