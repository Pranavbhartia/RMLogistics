package com.nexera.newfi.workflow.tasks;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.NotificationService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class AlertManager implements IWorkflowTaskExecutor {

	@Autowired
	NotificationService notificationService;

	public String execute(Object... objects) {

		int loanID = Integer.parseInt(objects[0].toString());
		NotificationVO notificationVO = (NotificationVO) objects[1];
		notificationService.createNotification(notificationVO);
		return null;
	}

	public Object renderState(Object input) {

		return null;
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		Object[] objectParams = new Object[1];
		objectParams[0] = ""; // loanID is a parameter.

		return null;
	}

}
