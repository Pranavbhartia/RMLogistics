package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.tasks.NexeraWorkflowTask;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class Application1003DisplayManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	private LoanDao loanDao;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private LoanAppFormService loanAppFormService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private WorkflowService workflowService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(Application1003DisplayManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute ");
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		LOG.debug("Status is " + status);
		String returnStatus = null;
		if (status.equals(WorkflowDisplayConstants.APP_FORM_COMPLETE)) {
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
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
		LOG.debug("Inside method checkStatus ");
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		Loan loan = new Loan();
		loan.setId(loanId);
		LoanAppForm loanAppForm = loanAppFormService.findByLoan(loan);
		if (loanAppForm != null
		        && loanAppForm.getLoanAppFormCompletionStatus() == 100f) {
			LOG.debug("Found a loanAppForm  associated with this loan and is found to be completed...");
			int workflowItemExecutionId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			inputMap.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
			        WorkflowDisplayConstants.APP_FORM_COMPLETE);
			// This means Brain Tree has approved the fee payment
			String params = Utils.convertMapToJson(inputMap);
			workflowService.saveParamsInExecTable(workflowItemExecutionId,
			        params);
			EngineTrigger engineTrigger = applicationContext
			        .getBean(EngineTrigger.class);
			engineTrigger.startWorkFlowItemExecution(workflowItemExecutionId);
			return WorkItemStatus.COMPLETED.getStatus();
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method invokeAction ");
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("inside method updateReminder");
		return null;
	}

}
