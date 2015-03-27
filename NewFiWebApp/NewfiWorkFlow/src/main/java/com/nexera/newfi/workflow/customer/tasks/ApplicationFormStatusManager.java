package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class ApplicationFormStatusManager implements IWorkflowTaskExecutor {

	@Autowired
	private EngineTrigger engineTrigger;

	@Autowired
	private LoanAppFormService loanAppFormService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		Loan loan = new Loan();
		loan.setId(loanId);
		LoanAppForm loanAppForm = loanAppFormService.findByLoan(loan);
		if (loanAppForm != null) {
			return loanAppForm.getLoanAppFormCompletionStatus() + "";
		}
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		int userId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		LoanAppForm loanAppForm = loanAppFormService.findByuserID(userId);

		if (loanAppForm.getLoanAppFormCompletionStatus() == 100) {
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

}
