package com.nexera.newfi.workflow.tasks;

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
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AppraisalManager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
			.getLogger(AppraisalManager.class);

	@Autowired
	private LoanService loanService;
	@Autowired
	private WorkflowService workflowService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		makeANote(
				Integer.parseInt(objectMap.get(
						WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
				LoanStatus.appraisalReceivedMessage);
		sendEmail(objectMap);
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		try {
			String status=null;
			int workflowItemExecutionId = Integer.parseInt(inputMap.get(
					WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			WorkflowItemExec currMilestone = workflowService
					.getWorkflowExecById(workflowItemExecutionId);
			if(!currMilestone.getStatus().equals(WorkItemStatus.NOT_STARTED.getStatus()))
				status = "Pending";
			Loan loan = new Loan();
			loan.setId(Integer.parseInt(inputMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
					Milestones.APPRAISAL.getMilestoneKey());
			if (mileStone != null)
				status = mileStone.getComments().toString();
			return status;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return "";
		}
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

}
