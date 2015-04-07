package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
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
		iWorkflowService.dismissReadNotifications(loanId,
		        MilestoneNotificationTypes.LOCK_RATE_CUST_NOTIFICATION_TYPE);
	}
	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
