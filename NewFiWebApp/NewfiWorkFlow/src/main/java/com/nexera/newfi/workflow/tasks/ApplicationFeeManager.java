package com.nexera.newfi.workflow.tasks;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.TransactionService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class ApplicationFeeManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private EngineTrigger engineTrigger;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String returnStatus = null;
		if (objectMap != null) {
			int loanId = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			String status = objectMap.get(
			        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME)
			        .toString();
			if (status.equals(LoanStatus.APP_PAYMENT_SUCCESS)) {
				dismissAllPaymentAlerts(loanId);
				createAlertToLockRates(objectMap);

				returnStatus = WorkItemStatus.COMPLETED.getStatus();

			} else if (status.equals(LoanStatus.APP_PAYMENT_FAILURE)) {

				return WorkItemStatus.NOT_STARTED.getStatus();
			} else if (status.equals(LoanStatus.APP_PAYMENT_PENDING)) {
				returnStatus = WorkItemStatus.STARTED.getStatus();
			}
			if (status != null && !status.isEmpty()) {
				loanService.saveLoanMilestone(loanId,
				        Milestones.APP_FEE.getMilestoneID(),
				        LoanStatus.APP_PAYMENT_PENDING);
				makeANote(Integer.parseInt(objectMap.get(
				        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
				        status);
				sendEmail(objectMap);
			}

		}
		return returnStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// First Check if Payment Made
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		return iWorkflowService.getRenderInfoForApplicationFee(loanId);

	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// This code can be removed from CHeck Status
		// Since The Transaction and App Fee tables are updated only by Batch
		//Batch will take care to update the WorkflowItem as correct status
		// Keeping it for an additional fall back - but not required
		
		int loanID = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO(loanID);
		LoanApplicationFee loanApplicationFee = transactionService
		        .findByLoan(loanVO);
		int workflowItemExecId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		if (loanApplicationFee.getPaymentDate() != null) {
			inputMap.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
			        LoanStatus.APP_PAYMENT_SUCCESS);
			// This means Brain Tree has approved the fee payment
			String params = Utils.convertMapToJson(inputMap);
			workflowService.saveParamsInExecTable(workflowItemExecId, params);
			engineTrigger.startWorkFlowItemExecution(workflowItemExecId);
			return WorkItemStatus.COMPLETED.toString();
		}
		// If Payment is done on : NewFiWeb and not done
		// we should say Pending Approval
		List<TransactionDetails> transList = transactionService
		        .getActiveTransactionsByLoan(new Loan(loanID));
		if (transList != null && !transList.isEmpty()) {
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
			        WorkItemStatus.STARTED.toString());
			return WorkItemStatus.STARTED.toString();
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		BigDecimal newAppFee = new BigDecimal(inputMap.get(
		        WorkflowDisplayConstants.APP_FEE_CHANGE).toString());
		loanService.updateLoanAppFee(loanId, newAppFee);
		int userId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		User user = new User();
		user.setId(userId);
		makeANote(loanId, newAppFee.toString(), user);
		return newAppFee.toString();
	}

	private void makeANote(int loanId, String message, User createdBy) {
		messageServiceHelper.generatePrivateMessage(loanId, message, createdBy,
		        false);
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = loanService.getLoanByID(loanId);
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_DISCLOSURE_DISPLAY;
		WorkflowExec workflowExec = new WorkflowExec();
		workflowExec.setId(loanVO.getLoanManagerWorkflowID());
		WorkflowItemMaster workflowItemMaster = workflowService
		        .getWorkflowByType(prevMilestoneKey);
		WorkflowItemExec prevMilestone = workflowService
		        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
		if (prevMilestone.getStatus().equals(
		        WorkItemStatus.COMPLETED.getStatus())) {
			LoanApplicationFee loanApplicationFee = transactionService
			        .findByLoan(loanVO);
			if (loanApplicationFee.getPaymentDate() == null) {

				CreateReminderVo createReminderVo = new CreateReminderVo(
				        MilestoneNotificationTypes.APP_FEE_NOTIFICATION_OVERDUE_TYPE,
				        loanId,
				        WorkflowConstants.APP_FEE_OVERDUE_NOTIFICATION_CONTENT);
				createReminderVo.setForCustomer(true);
				iWorkflowService.sendReminder(createReminderVo,
				        workflowItemExecutionId, prevMilestone.getId());

			}
		}
		return null;
	}

	private void dismissAllPaymentAlerts(int loanID) {
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
}
