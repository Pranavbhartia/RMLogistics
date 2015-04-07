package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.NotificationService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AlertManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	NotificationService notificationService;
	@Autowired
	private EngineTrigger engineTrigger;

	public String execute(HashMap<String, Object> objectMap) {

		makeANote(
		        Integer.parseInt(objectMap.get(
		                WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
		        LoanStatus.initialContactMadeMessage);
		sendEmail(objectMap);
		return WorkItemStatus.COMPLETED.getStatus();
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {

		return WorkflowDisplayConstants.ALERT_MANAGER_TEXT;
	}

	public String checkStatus(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get("loanID").toString());
		String status = null;
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                WorkflowDisplayConstants.CUSTOM_NOTIFICATION, null);
		if (notificationList.size() > 0) {
			int workflowItemExecId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
			        WorkItemStatus.PENDING.getStatus());
			status = WorkItemStatus.PENDING.getStatus();
		}
		return status;
	}

	@Override
	public String invokeAction(HashMap<String, Object> objectMap) {
		HashMap<String, Object> notification = (HashMap<String, Object>) objectMap
		        .get(WorkflowDisplayConstants.NOTIFICATION_VO_KEY_NAME);
		ObjectMapper mapper = new ObjectMapper();
		NotificationVO notificationVO = mapper.convertValue(notification,
		        NotificationVO.class);

		notificationService.createNotification(notificationVO);
		return String.valueOf(notificationVO.getId());
	}

}
