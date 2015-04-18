package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.Milestones;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.TransactionService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class PaymentManager implements IWorkflowTaskExecutor {
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private LoanService loanService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private IWorkflowService iWorkflowService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(PaymentManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {

		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		String status = LoanStatus.APP_PAYMENT_CLICK_TO_PAY;
		try {
			Loan loan = new Loan();
			loan.setId(Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.APP_FEE.getMilestoneKey());
			if (mileStone != null && mileStone.getComments() != null) {
				status = mileStone.getComments().toString();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			status = "";
		}
		return status;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		String returnStatus = null;
		Loan loan = new Loan();
		loan.setId(Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		int workflowItemExecutionId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
		        Milestones.APP_FEE.getMilestoneKey());
		if (mileStone != null && mileStone.getComments() != null) {
			if (mileStone.getComments().equals(LoanStatus.APP_PAYMENT_PENDING)) {
				engineTrigger.changeStateOfWorkflowItemExec(
				        workflowItemExecutionId,
				        WorkItemStatus.STARTED.getStatus());
				returnStatus = WorkItemStatus.STARTED.getStatus();
			}
		}
		return returnStatus;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
