package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.TransactionDetailsDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.User;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class PaymentManager implements IWorkflowTaskExecutor {
	@Autowired
	private EngineTrigger engineTrigger;

	@Autowired
	private LoanDao loanDao;

	private TransactionDetailsDao transactionDetailsDao;

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
		// TODO check payment status
		int userId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		User user = new User();
		user.setId(userId);
		Loan loan = loanDao.getActiveLoanOfUser(user);
		LoanApplicationFee loanApplicationFee = transactionDetailsDao
				.findByLoan(loan);
		if (loanApplicationFee.getPaymentDate() != null) {
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
