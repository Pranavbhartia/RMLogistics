package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.User;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LoanTeamManager implements IWorkflowTaskExecutor {

	@Autowired
	LoanService loanService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private Utils utils;

	public String execute(HashMap<String, Object> objectMap) {
		return invokeAction(objectMap);
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {

		int loanID = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		ExtendedLoanTeamVO extendedLoanTeamVO = loanService.findExtendedLoanTeam(loanVO);
		Gson gson = new Gson();
		return gson.toJson(extendedLoanTeamVO);
	}

	public String checkStatus(HashMap<String, Object> inputMap) {

		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> objectMap) {
		int loanID = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		// TODO-add way to add title company and home owners insurance as well
		Integer userID = null, titleCompanyID = null, homeOwnInsCompanyID = null;
		if (objectMap.containsKey(WorkflowDisplayConstants.USER_ID_KEY_NAME)) {
			Object userIDObj = objectMap
			        .get(WorkflowDisplayConstants.USER_ID_KEY_NAME);
			userID = Integer.parseInt(userIDObj.toString());
		}

		if (objectMap.containsKey(WorkflowDisplayConstants.TITLE_COMPANY_ID))
			titleCompanyID = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.TITLE_COMPANY_ID).toString());

		if (objectMap
		        .containsKey(WorkflowDisplayConstants.HOME_OWN_INS_COMP_ID))
			homeOwnInsCompanyID = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.HOME_OWN_INS_COMP_ID).toString());

		// return new Gson().toJson(user);
		if (userID != null && userID > 0) {
			UserVO userVO = new UserVO();
			userVO.setId(userID);
			loanService.addToLoanTeam(loanVO, userVO);
			UserVO user = userProfileService.loadInternalUser(userID);

			return new Gson().toJson(user);
		} else if (titleCompanyID != null && titleCompanyID > 0) {
			TitleCompanyMasterVO company = new TitleCompanyMasterVO();
			company.setId(titleCompanyID);
			company = loanService.addToLoanTeam(loanVO, company,
			        User.convertFromEntityToVO(utils.getLoggedInUser()));

			return new Gson().toJson(company);
		} else if (homeOwnInsCompanyID != null && homeOwnInsCompanyID > 0) {
			HomeOwnersInsuranceMasterVO company = new HomeOwnersInsuranceMasterVO();
			company.setId(homeOwnInsCompanyID);
			company = loanService.addToLoanTeam(loanVO, company,
			        User.convertFromEntityToVO(utils.getLoggedInUser()));
			return new Gson().toJson(company);
		} else
			return "Bad request";
	}
}
