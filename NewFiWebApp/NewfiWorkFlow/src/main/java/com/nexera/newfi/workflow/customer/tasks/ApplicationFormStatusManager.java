package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class ApplicationFormStatusManager implements IWorkflowTaskExecutor {

	@Autowired
	private EngineTrigger engineTrigger;

	@Autowired
	private LoanAppFormService loanAppFormService;

	private static final Logger LOG = LoggerFactory
	        .getLogger(ApplicationFormStatusManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method renderStateInfo");
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		Loan loan = new Loan();
		loan.setId(loanId);
		LoanAppForm loanAppForm = loanAppFormService.findByLoan(loan);
		if (loanAppForm != null) {
			LOG.debug("Found a loanAppForm  associated with this loan");
			return loanAppForm.getLoanAppFormCompletionStatus() + "";
		}
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method invokeAction");
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method updateReminder");
		return null;
	}

}
