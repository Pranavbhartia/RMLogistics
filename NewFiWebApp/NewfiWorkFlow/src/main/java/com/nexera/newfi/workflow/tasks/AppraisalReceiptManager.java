package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoadConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.Milestones;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;
@Component
public class AppraisalReceiptManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private LoanService loanService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(AppraisalReceiptManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String returnStatus = "";
		String mileStoneStatus = null;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		if (String.valueOf(LoadConstants.LQB_APPRAISAL_RECEIVED).equals(status)) {
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
			mileStoneStatus = "Appraisal Received";
		}
		if (mileStoneStatus != null) {
			LOG.debug("Updating Milestone for Appraisal As  " + mileStoneStatus);
			Loan loan = new Loan(loanId);
			LoanMilestone lm = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.APPRAISAL.getMilestoneKey());
			if (lm == null || ( lm != null && !mileStoneStatus.equals(lm.getComments()))) {
				iWorkflowService.updateNexeraMilestone(loanId,
				        Milestones.APPRAISAL.getMilestoneID(), mileStoneStatus);
			}
		}
		return returnStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
