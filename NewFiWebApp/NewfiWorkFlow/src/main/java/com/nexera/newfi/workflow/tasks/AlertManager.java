package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.NotificationService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AlertManager implements IWorkflowTaskExecutor {

	@Autowired
	NotificationService notificationService;

	public String execute(HashMap<String, Object> objectMap) {
		return null;
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {

		return WorkflowDisplayConstants.ALERT_MANAGER_TEXT;
	}

	public String checkStatus(HashMap<String, Object> inputMap) {

		return WorkflowDisplayConstants.ALERT_MANAGER_TEXT;
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
