package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LockRatesManager implements IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	Utils utils;
	private static final Logger LOG = LoggerFactory
	        .getLogger(LockRatesManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.info("Getting State of Rate Locked");
		HashMap<String, Object> map = new HashMap<String, Object>();
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = loanService.getLoanByID(loanId);
		String apr = "";
		if (loanVO.getLockStatus().equalsIgnoreCase(
		        CoreCommonConstants.RATE_LOCKED)) {
			if (loanVO.getLockStatus().equalsIgnoreCase(
			        CoreCommonConstants.RATE_LOCKED)) {
				map.put(WorkflowDisplayConstants.RESPONSE_LOCKED_RATE_KEY,
						loanVO.getLockedRate());
				map.put(WorkflowDisplayConstants.RESPONSE_LOCK_EXPIRATION_KEY,utils.getDateAndTimeForDisplay(loanVO.getLockExpirationDate()));
				map.put(WorkflowDisplayConstants.RESPONSE_LOCKED_DATA_KEY,loanVO.getLockedRateData());
				return utils.getJsonStringOfMap(map);
		}	
		}
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.info("checkStatus Rate Locked");
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		int userId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		UserVO user = new UserVO(userId);
		LoanVO loan = loanService.getActiveLoanOfUser(user);
		if (loan.getLockStatus().equalsIgnoreCase(
		        CoreCommonConstants.RATE_LOCKED)) {
			LOG.info("Chaning Status to Completeed");

			int workflowItemExecId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			engineTrigger.startWorkFlowItemExecution(workflowItemExecId);
			return WorkItemStatus.COMPLETED.getStatus();
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {

		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.LOCK_RATE_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());

		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_APP_FEE;
		LOG.debug("LockRatesManager : Updating Reminder" + loanId
		        + " with Prev Mielstone as " + prevMilestoneKey);
		String notificationReminderContent = WorkflowConstants.LOCK_RATE__NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		iWorkflowService.updateLMReminder(createReminderVo);
		return null;
	}
}
