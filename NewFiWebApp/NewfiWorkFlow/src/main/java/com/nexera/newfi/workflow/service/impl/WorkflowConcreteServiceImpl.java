package com.nexera.newfi.workflow.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanTurnAroundTimeVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.UserProfileService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;

@Component
public class WorkflowConcreteServiceImpl implements IWorkflowService {
	private static final Logger LOG = LoggerFactory
	        .getLogger(WorkflowConcreteServiceImpl.class);
	@Autowired
	private LoanAppFormService loanAppFormService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	UserProfileService userProfileService;
	@Autowired
	private NeedsListService needsListService;
	@Autowired
	Utils utils;

	@Override
	public String getJsonStringOfMap(HashMap<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sw.toString();
	}

	@Override
	public LoanAppForm getLoanAppFormDetails(Loan loan) {
		return loanAppFormService.findByLoan(loan);
	}

	public String updateLMReminder(CreateReminderVo createReminderVo) {
		WorkflowItemExec currMilestone = workflowService
		        .getWorkflowExecById(createReminderVo
		                .getWorkflowItemExecutionId());
		if (currMilestone.getStatus().equals(
		        WorkItemStatus.NOT_STARTED.getStatus())) {
			LoanVO loanVO = loanService.getLoanByID(createReminderVo
			        .getLoanId());
			if (createReminderVo.isForCustomer())
				createReminderVo.setUserID(loanVO.getUser() != null ? loanVO
				        .getUser().getId() : 0);
			WorkflowExec workflowExec = new WorkflowExec();
			workflowExec.setId(loanVO.getLoanManagerWorkflowID());
			WorkflowItemMaster workflowItemMaster = workflowService
			        .getWorkflowByType(createReminderVo.getPrevMilestoneKey());
			WorkflowItemExec prevMilestone = workflowService
			        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			if (prevMilestone.getStatus().equals(
			        WorkItemStatus.COMPLETED.getStatus())) {
				sendReminder(createReminderVo, currMilestone, prevMilestone);
			}
		} else {
			dismissReadNotifications(createReminderVo.getLoanId(),
			        createReminderVo.getNotificationType());
		}

		return null;
	}

	public void dismissReadNotifications(int loanID,
	        MilestoneNotificationTypes noticationType) {
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanID,
		                noticationType.getNotificationTypeName(), true);
		for (NotificationVO notificationVO : notificationList) {
			notificationService.dismissNotification(notificationVO.getId());
		}
	}

	private void sendReminder(CreateReminderVo createReminderVo,
	        WorkflowItemExec currMilestone, WorkflowItemExec prevMilestone) {

		long noOfDays = (prevMilestone.getEndTime().getTime() - new Date()
		        .getTime()) / (1000 * 60 * 60 * 24);

		LoanTurnAroundTimeVO loanTurnAroundTimeVO = loanService
		        .retrieveTurnAroundTimeByLoan(createReminderVo.getLoanId(),
		                currMilestone.getParentWorkflowItemExec().getId());
		long turnaroundTime = loanTurnAroundTimeVO.getHours();

		if (noOfDays >= turnaroundTime) {
			createAlertOfType(createReminderVo);
		}
	}

	public void createAlertOfType(CreateReminderVo createReminderVo) {
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(createReminderVo.getLoanId(),
		                createReminderVo.getNotificationType()
		                        .getNotificationTypeName(), null);
		if (notificationList.size() == 0
		        || notificationList.get(0).getRead() == true) {
			NotificationVO notificationVO = new NotificationVO(
			        createReminderVo.getLoanId(), createReminderVo
			                .getNotificationType().getNotificationTypeName(),
			        createReminderVo.getNotificationReminderContent());
			if (!createReminderVo.isForCustomer())
				createNotificationForLM(notificationVO);
			else {
				notificationVO.setCreatedForID(createReminderVo.getUserID());
				notificationVO.setTimeOffset(new Date().getTimezoneOffset());
				notificationService.createNotification(notificationVO);
			}
		}
	}

	private void createNotificationForLM(NotificationVO notificationVO) {
		List<UserRolesEnum> userRoles = new ArrayList<UserRolesEnum>();
		userRoles.add(UserRolesEnum.INTERNAL);
		List<InternalUserRolesEum> internalUserRoles = new ArrayList<InternalUserRolesEum>();
		internalUserRoles.add(InternalUserRolesEum.LM);
		notificationService.createRoleBasedNotification(notificationVO,
		        userRoles, internalUserRoles);
	}

	public void sendReminder(CreateReminderVo createReminderVo,
	        int currMilestoneID, int prevMilestoneID) {
		WorkflowItemExec currMilestone = workflowService
		        .getWorkflowExecById(createReminderVo
		                .getWorkflowItemExecutionId());
		LoanVO loanVO = loanService.getLoanByID(createReminderVo.getLoanId());
		WorkflowExec workflowExec = new WorkflowExec();
		workflowExec.setId(loanVO.getLoanManagerWorkflowID());
		WorkflowItemMaster workflowItemMaster = workflowService
		        .getWorkflowByType(createReminderVo.getPrevMilestoneKey());
		WorkflowItemExec prevMilestone = workflowService
		        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
		sendReminder(createReminderVo, currMilestone, prevMilestone);
	}

	public String updateNexeraMilestone(int loanId, int masterMileStoneId,
	        String comments) {
		String status = null;
		try {
			Loan loan = new Loan(loanId);
			LoanMilestone mileStone = new LoanMilestone();
			mileStone.setLoan(loan);
			LoanMilestoneMaster loanMilestoneMaster = new LoanMilestoneMaster();
			loanMilestoneMaster.setId(masterMileStoneId);
			mileStone.setLoanMilestoneMaster(loanMilestoneMaster);
			mileStone.setComments(comments);
			loanService.saveLoanMilestone(mileStone);
			status = WorkItemStatus.COMPLETED.getStatus();
			return status;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return status;
		}
	}

	public String getNexeraMilestoneComments(int loanId, Milestones milestone) {
		String comments = null;
		Loan loan = new Loan(loanId);
		LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
		        milestone.getMilestoneKey());
		if (mileStone != null)
			comments = mileStone.getComments().toString();
		return comments;
	}

	@Override
	public String getCreditDisplayScore(int userID) {
		String creditDisplay = "";
		UserVO user = userProfileService.findUser(userID);
		if (user.getCustomerDetail() != null) {
			creditDisplay = utils.constrtCreditScore(CustomerDetail
			        .convertFromVOToEntity(user.getCustomerDetail()));
		}

		return creditDisplay;
	}

	@Override
	public String getRenderInfoForDisclosure(int loanID) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Loan loan = new Loan(loanID);
		LoanMilestone loanMilestone = loanService.findLoanMileStoneByLoan(loan,
		        Milestones.DISCLOSURE.getMilestoneKey());
		String status = loanMilestone.getComments().toString();
		map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
		        status);
		MasterNeedsEnum needURLItem = null;
		if (status.equals(LoanStatus.disclosureAvail)) {
			needURLItem = MasterNeedsEnum.Disclsoure_Available;
		} else if (status.equals(LoanStatus.disclosureSigned)) {
			needURLItem = MasterNeedsEnum.Signed_Disclosure;
		}
		if (needURLItem != null) {
			NeedsListMaster needsListMaster = new NeedsListMaster();
			needsListMaster.setId(Integer.parseInt(needURLItem.getIndx()));
			LoanNeedsList loanNeedsList = needsListService.findNeedForLoan(
			        loan, needsListMaster);
			// TODO check column path
			if (loanNeedsList != null
			        && loanNeedsList.getUploadFileId() != null) {
				map.put(WorkflowDisplayConstants.RESPONSE_URL_KEY,
				        loanNeedsList.getUploadFileId().getS3path());
			}
		}
		return getJsonStringOfMap(map);
	}
}
