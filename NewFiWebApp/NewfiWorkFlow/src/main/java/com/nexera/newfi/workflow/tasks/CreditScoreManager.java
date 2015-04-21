package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class CreditScoreManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	UserProfileService userProfileService;

	@Autowired
	NeedsListService needsListService;
	@Autowired
	Utils utils;
	private static final Logger LOG = LoggerFactory
	        .getLogger(CreditScoreManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Execute Concrete class : CreditScoreManager" + objectMap);
		makeANote(
		        Integer.parseInt(objectMap.get(
		                WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
		        LoanStatus.creditScoreMessage);
		objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
		        LoanStatus.creditScoreMessage);
		sendEmail(objectMap);
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		LOG.debug("Render State Info " + inputMap);
		int userID = (Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString()));
		int loanID = (Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		// TO Remove------ start
		map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
		        iWorkflowService.getCreditDisplayScore(userID));
		map.put(WorkflowDisplayConstants.RESPONSE_URL_KEY,
		        needsListService.checkCreditReport(loanID));
		// TO Remove------ end
		
		return utils.getJsonStringOfMap(map);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		// The credit scores come from LQB via Batch only - So Batch will keep
		// the WorkItem status clean
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.CREDIT_SCORE_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_1003_COMPLETE;
		String notificationReminderContent = WorkflowConstants.CREDIT_SCORE_NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		LOG.debug("Creating LM Reminder for ..Credit Score Manager for Prev MS :" +prevMilestoneKey);
		iWorkflowService.updateLMReminder(createReminderVo);
		return null;
	}
}
