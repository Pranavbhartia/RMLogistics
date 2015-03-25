package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class Application1003Manager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {
	

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get("status").toString();
		if (status.equals(ApplicationStatus.initiated)) {
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.initiatedMessage);
		} else if (status.equals(ApplicationStatus.submitted)) {
			// TODO check if we get timestamp for take a note from LQB
			makeANote(Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
					ApplicationStatus.submittedMessage);
			sendEmail(objectMap);
		}
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
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
