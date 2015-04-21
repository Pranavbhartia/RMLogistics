package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LockYourRateManager implements IWorkflowTaskExecutor {
	@Autowired
	private LoanService loanService;
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private NotificationService notificationService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// DO Nothing
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = loanService.getLoanByID(loanId);
		if (loanVO != null)
			return loanVO.getLockedRate() + "";
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		int userId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		UserVO user = new UserVO();
		user.setId(userId);
		LoanVO loan = loanService.getActiveLoanOfUser(user);
		if (loan.getIsRateLocked()) {
			int workflowItemExecId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
			        WorkItemStatus.COMPLETED.getStatus());
			dismissAllLockYourRateAlert(loan.getId());
			return WorkItemStatus.COMPLETED.getStatus();
		}
		return null;
	}

	public void dismissAllLockYourRateAlert(int loanId) {
		notificationService.dismissReadNotifications(loanId,
		        MilestoneNotificationTypes.LOCK_RATE_CUST_NOTIFICATION_TYPE);
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.LOCK_RATE_CUST_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_APP_FEE;
		String notificationReminderContent = WorkflowConstants.LOCK_RATE_CUST_NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		iWorkflowService.updateLMReminder(createReminderVo);

		return null;
	}

}
