package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.core.service.LoanService;
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

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		makeANote(
		        Integer.parseInt(objectMap.get(
		                WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
		        LoanStatus.creditScoreMessage);
		sendEmail(objectMap);

		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		int userID = (Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString()));

		// TO Remove------ start
		map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
		        iWorkflowService.getCreditDisplayScore(userID));
		map.put(WorkflowDisplayConstants.RESPONSE_URL_KEY,
		        "http://www.lendingqb.com/");
		// TO Remove------ end

		// TODO confirm where credit score and url will be stored
		/*
		 * if (loanAppForm.getCreditStatus() != null &&
		 * !loanAppForm.getCreditStatus().trim().equals("")) {
		 * map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
		 * loanAppForm.getCreditStatus()); // TODO check column path
		 * map.put(WorkflowDisplayConstants.RESPONSE_URL_KEY,
		 * loanAppForm.getCreditStatusUrl()); }
		 */
		return iWorkflowService.getJsonStringOfMap(map);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
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
		iWorkflowService.updateLMReminder(createReminderVo);
		return null;
	}
}
