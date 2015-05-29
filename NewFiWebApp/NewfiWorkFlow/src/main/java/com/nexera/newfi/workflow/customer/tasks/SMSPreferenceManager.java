package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class SMSPreferenceManager implements IWorkflowTaskExecutor {
	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private ApplicationContext applicationContext;

	private static final Logger LOG = LoggerFactory
	        .getLogger(SMSPreferenceManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method renderStateInfo");
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		int userId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		UserVO userVo = userProfileService.findUser(userId);
		if (userVo.getMobileAlertsPreference() != null) {
			int workflowItemExecId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			EngineTrigger engineTrigger = applicationContext
			        .getBean(EngineTrigger.class);
			engineTrigger.startWorkFlowItemExecution(workflowItemExecId);
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
			        WorkItemStatus.COMPLETED.getStatus());
			return WorkItemStatus.COMPLETED.getStatus();
		}
		return null;

	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method invokeAction");
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method updateReminder");
		return null;
	}

}
