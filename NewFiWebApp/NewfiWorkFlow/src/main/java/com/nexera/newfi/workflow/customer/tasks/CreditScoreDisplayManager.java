package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class CreditScoreDisplayManager implements IWorkflowTaskExecutor {

	@Autowired
	private EngineTrigger engineTrigger;

	@Autowired
	private LoanAppFormDao loanAppFormDao;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO check credit score columns in loan table
		int userId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		LoanAppForm loanAppForm = loanAppFormDao.findByuserID(userId);
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
