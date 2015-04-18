package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class UWStatusManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(UWStatusManager.class);

	@Autowired
	private LoanService loanService;
	@Autowired
	private IWorkflowService iWorkflowService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		String message = "";
		String milestoneStatus = null;

		if (status.equals(LOSLoanStatus.LQB_STATUS_IN_UNDERWRITING
		        .getLosStatusID() + "")) {
			message = LoanStatus.inUnderwritingMessage;
			milestoneStatus = WorkItemStatus.STARTED.getStatus();
		} else if (status.equals(LOSLoanStatus.LQB_STATUS_CLEAR_TO_CLOSE
		        .getLosStatusID() + "")) {
			message = LoanStatus.underwritingClearToCloseMessage;
			milestoneStatus = WorkItemStatus.COMPLETED.getStatus();
		}
		// TODO add alert code here
		if (!message.equals("")) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        message);
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        message);
			sendEmail(objectMap);
		}

		return milestoneStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		try {
			Loan loan = new Loan();
			loan.setId(Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.UW.getMilestoneKey());
			return mileStone.getComments().toString();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return "";
		}
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// DO Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.UW_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_LOCK_RATE;
		String notificationReminderContent = WorkflowConstants.UW_NOTIFICATION__NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		iWorkflowService.updateLMReminder(createReminderVo);
		return null;
	}

}
