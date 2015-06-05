package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
	private ApplicationContext applicationContext;

	private static final Logger LOG = LoggerFactory
	        .getLogger(AlertManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug(" Executing concrete class " + objectMap);
		/*
		 * makeANote( Integer.parseInt(objectMap.get(
		 * WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
		 * LoanStatus.initialContactMadeMessage)
		 */;
		objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
		        LoanStatus.initialContactMadeMessage);		
		//Removing the Send Email call from here - Since no email should be sent out in this case
		//sendEmail(objectMap, null);
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.debug("Rendering State "
		        + inputMap.get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME));
		return WorkflowDisplayConstants.ALERT_MANAGER_TEXT;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get("loanID").toString());
		LOG.debug("Checking State " + loanId);
		String status = null;
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                WorkflowDisplayConstants.CUSTOM_NOTIFICATION, null);
		if (notificationList.size() > 0) {
			LOG.debug("Alert manager : Marking as "
			        + WorkItemStatus.PENDING.getStatus());
			int workflowItemExecId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			EngineTrigger engineTrigger = applicationContext
			        .getBean(EngineTrigger.class);
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

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		// Do Nothing - since this does NOT need a Reminder.
		return null;
	}

}
