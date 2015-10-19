package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
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
	private LoanService loanService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	Utils utils;
	private static final Logger LOG = LoggerFactory
	        .getLogger(PaymentManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method renderStateInfo");
		Map<String, Object> map = new HashMap<String, Object>();
		String status = "";
		String paymentType = "";
		try {
			Loan loan = new Loan();
			int loanId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			loan.setId(loanId);
			LoanMilestone disclosureMS = loanService.findLoanMileStoneByLoan(
			        loan, Milestones.DISCLOSURE.getMilestoneKey());

			if (disclosureMS != null
			        && disclosureMS.getComments() != null
			        && disclosureMS.getComments().equals(
			                LoanStatus.disclosureSigned)) {
				// Show Click To pay only if Disclosures are signed
				status = LoanStatus.APP_PAYMENT_CLICK_TO_PAY;
			} else {
				status = LoanStatus.APP_PAYMENT_CANT_PAY_YET;
			}
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
			        Milestones.APP_FEE.getMilestoneKey());
			if (mileStone != null && mileStone.getComments() != null) {
				status = mileStone.getComments().toString();
			}
			Loan loanDetail  = loanService.fetchLoanById(loanId);
			try{	
					paymentType = loanDetail.getPaymentVendor();
			}
			catch(Exception e){
					LOG.error("Exception Caught " + e.getMessage());
					LOG.warn("Nothing found in  table for loanid: "+loanId);
					paymentType = null;
			}
			map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
			        status);
			map.put(WorkflowDisplayConstants.CUSTOMER_PAYMENT_TYPE,  paymentType);
			return utils.getJsonStringOfMap(map);
		} catch (Exception e) {
			LOG.error(e.getMessage());			
		}
		return utils.getJsonStringOfMap(map);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		String returnStatus = WorkItemStatus.NOT_STARTED.getStatus();
		Loan loan = new Loan();
		int loanID = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		loan.setId(loanID);
		// Fall Back Code:
		// If Disclosures is Signed and if no MS entry is made : make it here to
		// fix the issue
		// in NEXNF900

		int workflowItemExecutionId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
		        Milestones.APP_FEE.getMilestoneKey());
		if (mileStone != null && mileStone.getComments() != null) {
			if (mileStone.getComments().equals(LoanStatus.APP_PAYMENT_PENDING)) {
				EngineTrigger engineTrigger = applicationContext
				        .getBean(EngineTrigger.class);
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
		LOG.debug("Inside method invokeAction");
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method updateReminder");
		return null;
	}
}
