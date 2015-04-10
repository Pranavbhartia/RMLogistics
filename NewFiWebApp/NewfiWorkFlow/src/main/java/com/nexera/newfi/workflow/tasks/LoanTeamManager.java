package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.EditLoanTeamVO;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.UserProfileService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LoanTeamManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	LoanService loanService;

	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private Utils utils;
	@Autowired
	private NotificationService notificationService;

	public String execute(HashMap<String, Object> objectMap) {
		return WorkItemStatus.COMPLETED.getStatus();
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {

		int loanID = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		ExtendedLoanTeamVO extendedLoanTeamVO = loanService
		        .findExtendedLoanTeam(loanVO);
		Gson gson = new Gson();
		return gson.toJson(extendedLoanTeamVO);
	}

	public String checkStatus(HashMap<String, Object> inputMap) {
		int loanID = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		ExtendedLoanTeamVO extendedLoanTeamVO = loanService
		        .findExtendedLoanTeam(loanVO);
		if (extendedLoanTeamVO.getUsers().size() > 1) {
			int workflowItemExecId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
			        WorkItemStatus.PENDING.getStatus());
			return WorkItemStatus.PENDING.getStatus();
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> objectMap) {
		int loanID = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanID);
		boolean agentAdded = false;
		Integer userID = null, titleCompanyID = null, homeOwnInsCompanyID = null;
		if (objectMap.containsKey(WorkflowDisplayConstants.USER_ID_KEY_NAME)) {
			Object userIDObj = objectMap
			        .get(WorkflowDisplayConstants.USER_ID_KEY_NAME);
			userID = Integer.parseInt(userIDObj.toString());
		}

		if (objectMap.containsKey(WorkflowDisplayConstants.TITLE_COMPANY_ID)) {
			titleCompanyID = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.TITLE_COMPANY_ID).toString());
		}
		if (objectMap
		        .containsKey(WorkflowDisplayConstants.HOME_OWN_INS_COMP_ID)) {
			homeOwnInsCompanyID = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.HOME_OWN_INS_COMP_ID).toString());
		}

		EditLoanTeamVO editLoanTeamVO = new EditLoanTeamVO();
		editLoanTeamVO.setUserID(userID);
		editLoanTeamVO.setLoanID(loanID);
		editLoanTeamVO.setHomeOwnInsCompanyID(homeOwnInsCompanyID);
		editLoanTeamVO.setTitleCompanyID(titleCompanyID);
		String message = null;
		if (userID != null && userID > 0) {
			UserVO user = new UserVO(userID);
			boolean result = loanService.addToLoanTeam(loanVO, user);
			if (result) {
				user = userProfileService.loadInternalUser(userID);
				if (user.getUserRole() != null
				        && user.getUserRole().getRoleCd()
				                .equals(UserRolesEnum.REALTOR.getName())) {
					agentAdded = true;
				}
			}

			editLoanTeamVO.setOperationResult(result);
			editLoanTeamVO.setUser(user);
			changeState(loanID);
			message = LoanStatus.teamMemberAddedMessage + " Name : "
			        + user.getDisplayName() + " Role : "
			        + user.getUserRole().getRoleDescription();
		} else if (titleCompanyID != null && titleCompanyID > 0) {
			TitleCompanyMasterVO company = new TitleCompanyMasterVO();
			company.setId(titleCompanyID);
			company = loanService.addToLoanTeam(loanVO, company,
			        User.convertFromEntityToVO(utils.getLoggedInUser()));
			editLoanTeamVO.setTitleCompany(company);
			editLoanTeamVO.setOperationResult(true);
			changeState(loanID);
			message = LoanStatus.titleCompanyAddedMessage + " Name : "
			        + company.getName();
		} else if (homeOwnInsCompanyID != null && homeOwnInsCompanyID > 0) {
			HomeOwnersInsuranceMasterVO company = new HomeOwnersInsuranceMasterVO();
			company.setId(homeOwnInsCompanyID);
			company = loanService.addToLoanTeam(loanVO, company,
			        User.convertFromEntityToVO(utils.getLoggedInUser()));
			editLoanTeamVO.setHomeOwnInsCompany(company);
			editLoanTeamVO.setOperationResult(true);
			changeState(loanID);
			message = LoanStatus.HMInsCompanyAddedMessage + " Name : "
			        + company.getName();
		} else {
			return "Bad request";
		}
		if (message != null) {
			makeANote(loanID, message);
		}
		if (agentAdded) {
			dismissAgentAddAlert(loanID);
		}
		return new Gson().toJson(editLoanTeamVO);
	}

	private void dismissAgentAddAlert(int loanId) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.TEAM_ADD_NOTIFICATION_TYPE;
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), true);
		for (NotificationVO notificationVO : notificationList) {
			notificationService.dismissNotification(notificationVO.getId());
		}
	}

	private void changeState(int loanID) {
		LoanVO loanVO = loanService.getLoanByID(loanID);
		WorkflowExec workflowExec = new WorkflowExec();
		workflowExec.setId(loanVO.getLoanManagerWorkflowID());
		WorkflowItemMaster workflowItemMaster = workflowService
		        .getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_TEAM_STATUS);
		WorkflowItemExec workflowitemexec = workflowService
		        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
		workflowExec.setId(loanVO.getCustomerWorkflowID());
		WorkflowItemMaster custWorkflowItemMaster = workflowService
		        .getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_MANAGE_TEAM);
		WorkflowItemExec custWorkflowitemexec = workflowService
		        .getWorkflowItemExecByType(workflowExec, custWorkflowItemMaster);
		engineTrigger.changeStateOfWorkflowItemExec(
		        custWorkflowitemexec.getId(),
		        WorkItemStatus.STARTED.getStatus());
		engineTrigger.changeStateOfWorkflowItemExec(workflowitemexec.getId(),
		        WorkItemStatus.STARTED.getStatus());
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
