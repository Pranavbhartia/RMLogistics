package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.Milestones;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.LoanService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LoanClosureManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(LoanClosureManager.class);

	@Autowired
	private LoanService loanService;
	@Autowired
	Utils utils;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String subject = null;
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		String completedStatus = null;
		String displayMessage = "";
		if (status.equals(String.valueOf(LOSLoanStatus.LQB_STATUS_FUNDED
		        .getLosStatusID()))) {
			displayMessage = LoanStatus.loanFundedMessage;
			subject = CommonConstants.SUBJECT_LOAN_FUNDED;
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED
		                .getLosStatusID()))) {
			objectMap.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
			        CommonConstants.TEMPLATE_KEY_NAME_LOAN_SUSPENDED);
			displayMessage = LoanStatus.loanSuspendedMessage;
			subject = CommonConstants.SUBJECT_LOAN_SUSPENDED;
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status
		        .equals(String.valueOf(LOSLoanStatus.LQB_STATUS_LOAN_DENIED
		                .getLosStatusID()))) {
			displayMessage = LoanStatus.loanDeclinedMessage;
			objectMap.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
			        CommonConstants.TEMPLATE_KEY_NAME_LOAN_DECLINED);
			subject = CommonConstants.SUBJECT_LOAN_DECLINED;
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN
		                .getLosStatusID()))) {
			displayMessage = LoanStatus.loanWithdrawnMessage;
			subject = CommonConstants.SUBJECT_LOAN_WITHDRAWN;
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
			int loanId = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			loanService.saveLoanProgress(loanId, new LoanProgressStatusMaster(
			        LoanProgressStatusMasterEnum.WITHDRAWN));
			LOG.debug(" Withdrawn : Updating Loan LC State for " + loanId + "as " + WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN).getLcStateKey());
			loanService.updateLoanLCState(loanId,WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN));
			
		} else if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED
		                .getLosStatusID()))) {
			displayMessage = LoanStatus.loanArchivedMessage;
			completedStatus = WorkItemStatus.COMPLETED.getStatus();
			subject = CommonConstants.SUBJECT_LOAN_ARCHIVED;
		}
		else if (status.equals(String.valueOf(LOSLoanStatus.LQB_STATUS_LOAN_CANCELED
		        .getLosStatusID()))) {				
			int loanId = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			LOG.debug("Making loan as cancelled"+loanId);
			loanService.saveLoanProgress(loanId, new LoanProgressStatusMaster(
			        LoanProgressStatusMasterEnum.SMCLOSED));
			LOG.debug(" cancelled : Updating Loan LC State for " + loanId + "as " + WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_LOAN_CANCELED).getLcStateKey());
			loanService.updateLoanLCState(loanId,WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_LOAN_CANCELED));
		} 
		if (status != null && !status.isEmpty()) {
			/*
			 * makeANote(Integer.parseInt(objectMap.get(
			 * WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			 * displayMessage)
			 */;
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        displayMessage);
		}
		if (objectMap != null
		        && objectMap
		                .get(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME) != null) {
			sendEmailToInternalUsers(objectMap, subject);
		}
		return completedStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		StringBuffer returnString = new StringBuffer();

		try {
			/*LOG.debug("Getting the closure Status");
			Loan loan = new Loan();
			loan.setId(Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.LOAN_CLOSURE.getMilestoneKey());
			if (mileStone != null && mileStone.getComments() != null) {
				returnString.append(mileStone.getComments());
			}
			if (mileStone != null && mileStone.getStatusUpdateTime() != null) {
				returnString.append(" "
				        + utils.getDateAndTimeForDisplay(mileStone
				                .getStatusUpdateTime()));
			}*/
		} catch (Exception e) {
			LOG.error(e.getMessage());

		}
		return returnString.toString();
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing :
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		// Do Nothing : No Reminders
		return null;
	}

}
