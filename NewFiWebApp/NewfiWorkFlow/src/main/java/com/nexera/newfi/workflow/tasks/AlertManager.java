package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AlertManager implements IWorkflowTaskExecutor {

	@Autowired
	NotificationService notificationService;

	public String execute(HashMap<String, Object> objectMap) {

		int loanID = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String notificationVOStr = objectMap.get(
		        WorkflowDisplayConstants.NOTIFICATION_VO_KEY_NAME).toString();
		Gson gson = new Gson();
		NotificationVO notificationVO = gson.fromJson(notificationVOStr,
		        NotificationVO.class);
		notificationService.createNotification(notificationVO);
		return String.valueOf(loanID);
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {

		return WorkflowDisplayConstants.ALERT_MANAGER_TEXT;
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		Object[] objectParams = new Object[1];
		objectParams[0] = ""; // loanID is a parameter.

		return null;
	}

}
