package com.nexera.newfi.workflow.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class LoanTeamManager implements IWorkflowTaskExecutor {

	@Autowired
	LoanService loanService;

	public String execute(Object[] inputs) {
		int loanID = Integer.parseInt(inputs[0].toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		int userID = Integer.parseInt(inputs[1].toString());
		UserVO userVO = new UserVO();
		userVO.setId(userID);
		loanService.addToLoanTeam(loanVO, userVO);
		return null;
	}

	public String renderStateInfo(String[] inputs) {
		Integer loanId = Integer.parseInt(inputs[0]);
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanId);
		List<UserVO> loanTeam = loanService.retreiveLoanTeam(loanVO);
		Gson gson = new Gson();
		return gson.toJson(loanTeam);
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

}
