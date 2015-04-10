package com.nexera.newfi.workflow.tasks;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.TransactionService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
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

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
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
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.SYS_EDU_NOTIFICATION_TYPE;
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

}
