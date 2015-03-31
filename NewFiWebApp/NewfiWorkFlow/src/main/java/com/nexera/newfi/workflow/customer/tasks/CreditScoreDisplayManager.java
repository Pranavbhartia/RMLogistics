package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class CreditScoreDisplayManager implements IWorkflowTaskExecutor {

	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Loan loan = new Loan();
		loan.setId(Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		LoanAppForm loanAppForm = iWorkflowService.getLoanAppFormDetails(loan);

		// To Remove ----start
		map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
				"EQ-?? | TU-?? | EX-??");
		// To Remove ----end

		// TODO confirm where credit score and url will be stored
		/*if (loanAppForm.getCreditStatus() != null
				&& !loanAppForm.getCreditStatus().trim().equals("")) {
			map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
					loanAppForm.getCreditStatus());
		}*/
		return iWorkflowService.getJsonStringOfMap(map);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO check credit score columns in loan table
		int userId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		// LoanAppForm loanAppForm = loanAppFormDao.findByuserID(userId);
		/*
		 * if(loanAppForm.getCreditStatus()!=null&&!loanAppForm.getCreditStatus()
		 * .trim().equals("")){ int
		 * workflowItemExecId=Integer.parseInt(inputMap.
		 * get("workflowItemExecId").toString());
		 * engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId, "3");
		 * return "3"; }
		 */
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
