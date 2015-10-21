package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.core.service.LoanService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class ClosureStatusManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	private LoanService loanService;
	
	private static final Logger LOG = LoggerFactory
	        .getLogger(ClosureStatusManager.class);
	

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();

		int loanId = Integer.parseInt(objectMap.get(
		          WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		if (status.equals(String.valueOf(LOSLoanStatus.LQB_STATUS_FUNDED
		        .getLosStatusID()))) {
			LOG.debug(" Funded : Updating Loan LC State for " + loanId + "as " + WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_FUNDED).getLcStateKey());
			loanService.updateLoanLCState(loanId,WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_FUNDED));
			
			loanService.saveLoanProgress(loanId, new LoanProgressStatusMaster(LoanProgressStatusMasterEnum.SMCLOSED));
		}
		else if (status.equals(String.valueOf(LOSLoanStatus.LQB_STATUS_DOCS_ORDERED
		        .getLosStatusID()))) {
			LOG.debug(" Docs Ordered : Updating Loan LC State for " + loanId + "as " + WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_DOCS_ORDERED).getLcStateKey());
			loanService.updateLoanLCState(loanId,WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_DOCS_ORDERED));
			
		}
		else if (status.equals(String.valueOf(LOSLoanStatus.LQB_STATUS_DOCS_OUT
		        .getLosStatusID()))) {
			LOG.debug(" Docs Out : Updating Loan LC State for " + loanId + "as " + WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_DOCS_OUT).getLcStateKey());
			loanService.updateLoanLCState(loanId,WorkflowConstants.STATUS_LC_STATE_LOOKUP.get(LOSLoanStatus.LQB_STATUS_DOCS_OUT));
			
		}
		String returnStatus = WorkItemStatus.COMPLETED.getStatus();
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
