package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.impl.LoanDaoImpl;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.enums.Milestones;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class UWStatusManager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(UWStatusManager.class);

	@Autowired
	private LoanService loanService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get("status").toString();
		String message = "";
		if (status.equals(ApplicationStatus.inUnderwriting)) {
			message = ApplicationStatus.inUnderwritingMessage;
		} else if (status
				.equals(ApplicationStatus.underwritingObservationsReceived)) {
			message = ApplicationStatus.underwritingObservationsReceivedMessage;
		} else if (status.equals(ApplicationStatus.underwritingSubmitted)) {
			message = ApplicationStatus.underwritingSubmittedMessage;
		} else if (status.equals(ApplicationStatus.approvedWithConditions)) {
			message = ApplicationStatus.approvedWithConditionsMessage;
		} else if (status.equals(ApplicationStatus.underwritingApproved)) {
			message = ApplicationStatus.underwritingApprovedMessage;
		}
		//TODO add alert code here
		if (!message.equals("")) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					message);
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
			loanMilestoneMaster.setId(Milestones.UW.getMilestoneID());
			LoanMilestone mileStone=loanService.findLoanMileStoneByLoan(loan, loanMilestoneMaster);
			return mileStone.getComments().toString();
		}catch(Exception e){
			LOG.error(e.getMessage());
			return "";
		}
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		return null;
	}

}
