package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoadConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.LoanTypeMasterEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AppraisalManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private WorkflowService workflowService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(AppraisalManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Execute Appraisal Manager " + objectMap);

		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String returnStatus = "";
		String mileStoneStatus = null;
		// NEXNF-415 : Removing Appraisal email calls
		if (LoanStatus.appraisalAvailable.equals(status)
		        || String.valueOf(LoadConstants.LQB_APPRAISAL_ORDER).endsWith(
		                status)) {
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
			mileStoneStatus = LoanStatus.appraisalAvailable;
			/*
			 * makeANote(Integer.parseInt(objectMap.get(
			 * WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			 * LoanStatus.appraisalReceivedMessage)
			 */;
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        LoanStatus.appraisalReceivedMessage);
		}
		if (mileStoneStatus != null) {
			LOG.debug("Updating Milestone for Appraisal As  " + mileStoneStatus);
			Loan loan = new Loan(loanId);
			LoanMilestone lm = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.APPRAISAL.getMilestoneKey());
			if (lm == null) {
				loanService.updateNexeraMilestone(loanId,
				        Milestones.APPRAISAL.getMilestoneID(), mileStoneStatus);
			}
		}
		return returnStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {

		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("Updating Reminders for Appraisal " + objectMap);
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.APPRAISAL_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_APP_FEE;
		String notificationReminderContent = WorkflowConstants.APPRAISAL_NOTIFICATION__NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		iWorkflowService.updateLMReminder(createReminderVo);

		return null;
	}

}
