package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.TransactionService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class PaymentManager implements IWorkflowTaskExecutor {
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private LoanService loanService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		if (objectMap != null) {
			int loanId = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			String status = objectMap.get(
			        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME)
			        .toString();
			if (status.equals(LoanStatus.APP_PAYMENT_SUCCESS)) {
				dismissAllPaymentAlerts(loanId);
				createAlertToLockRates(objectMap);
				return WorkItemStatus.COMPLETED.getStatus();
			}
		}
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO check payment status
		int userId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		UserVO user = new UserVO();
		user.setId(userId);
		LoanVO loan = loanService.getActiveLoanOfUser(user);
		LoanApplicationFee loanApplicationFee = transactionService
				.findByLoan(loan);
		if (loanApplicationFee.getPaymentDate() != null) {
			int workflowItemExecId = Integer.parseInt(inputMap.get(
					WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			engineTrigger
					.changeStateOfWorkflowItemExec(workflowItemExecId, "3");
			return "3";
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	private void dismissAllPaymentAlerts(int loanID){
		iWorkflowService.dismissReadNotifications(loanID,
		        MilestoneNotificationTypes.APP_FEE_NOTIFICATION_OVERDUE_TYPE);
		iWorkflowService.dismissReadNotifications(loanID,
		        MilestoneNotificationTypes.APP_FEE_NOTIFICATION_TYPE);
	}

	private void createAlertToLockRates(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.LOCK_RATE_CUST_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String notificationReminderContent = WorkflowConstants.LOCK_RATE_CUST_NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, notificationReminderContent);
		createReminderVo.setForCustomer(true);
		iWorkflowService.createAlertOfType(createReminderVo);
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
