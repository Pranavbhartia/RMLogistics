package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LoanTeamManager implements IWorkflowTaskExecutor {

	@Autowired
	LoanService loanService;

	public String execute(HashMap<String, Object> objectMap) {
		int loanID = Integer.parseInt(objectMap
		        .get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		int userID = Integer.parseInt(objectMap
		        .get(WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		UserVO userVO = new UserVO();
		userVO.setId(userID);
		loanService.addToLoanTeam(loanVO, userVO);
		return null;
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {
		
		Integer loanIdInt=(Integer) inputMap
		        .get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME);
		System.out.println("Loan id : "+inputMap
		        .get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME));
		int loanID = Integer.parseInt(inputMap
		        .get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		List<UserVO> loanTeam = loanService.retreiveLoanTeam(loanVO);
		Gson gson = new Gson();
		return gson.toJson(loanTeam);
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

}
