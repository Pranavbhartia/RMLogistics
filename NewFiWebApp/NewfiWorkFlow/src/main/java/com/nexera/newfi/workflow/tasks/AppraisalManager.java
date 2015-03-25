package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.newfi.workflow.customer.tasks.AppraisalDisplayManager;
import com.nexera.workflow.enums.Milestones;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class AppraisalManager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(AppraisalManager.class);

	@Autowired
	private LoanService loanService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get("status").toString();
		if (status.equals(ApplicationStatus.appraisalOrdered)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.appraisalOrderedMessage);
			sendEmail(objectMap);
		} else if (status.equals(ApplicationStatus.appraisalPending)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.appraisalPendingMessage);
			sendEmail(objectMap);
		}else if (status.equals(ApplicationStatus.appraisalReceived)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.appraisalReceivedMessage);
			sendEmail(objectMap);
		}
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		try{
			Loan loan=new Loan();
			loan.setId(Integer.parseInt(inputMap.get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
			LoanMilestoneMaster loanMilestoneMaster=new LoanMilestoneMaster();
			loanMilestoneMaster.setId(Milestones.APPRAISAL.getMilestoneID());
			LoanMilestone mileStone=loanService.findLoanMileStoneByLoan(loan, loanMilestoneMaster);
			return mileStone.getComments().toString();
		}catch(Exception e){
			LOG.error(e.getMessage());
			return "";
		}
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

}
