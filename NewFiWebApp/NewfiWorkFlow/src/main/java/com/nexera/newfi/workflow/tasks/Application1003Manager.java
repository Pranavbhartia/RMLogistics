package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoadConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.LoanLCStates;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanTurnAroundTimeVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class Application1003Manager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private IWorkflowService iworkflowService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(Application1003Manager.class);
	@Autowired
	private SendEmailService sendEmailService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private Utils utils;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		LOG.info("Executing Concrete class with Application1003Manager with "
		        + status);
		String returnStatus = null;

		if (status.equals(String
		        .valueOf(LoadConstants.LQB_1003_INTERVIEW_DATE_UPDATED))) {
			int loanID = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        Milestones.App1003.getMilestoneKey());
			createAlertForDisclosureDue(objectMap);
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
			LOG.info("Saving Loan as INprogres");
			loanService.saveLoanProgress(loanID, new LoanProgressStatusMaster(
			        LoanProgressStatusMasterEnum.IN_PROGRESS));
			loanService.saveLoanMilestone(loanID,
			        Milestones.App1003.getMilestoneID(),
			        LoanStatus.applicationSubmittedMessage);
			loanService.updateLoanLCState(loanID, LoanLCStates.Application);
			Date interviewDate = utils.parseStringIntoDate(objectMap.get(
			        WorkflowDisplayConstants.INTERVIEW_DATE_KEY_NAME).toString());
			if (interviewDate != null) {
				loanService.updateInterviewDate(loanID, interviewDate);
			}
		}
		return returnStatus;
	}

	@Override
	public void sendEmail(HashMap<String, Object> objectMap, String subject) {
		if (objectMap != null) {
			LoanVO loanVO = loanService
			        .getLoanByID(Integer.parseInt(objectMap.get(
			                WorkflowDisplayConstants.LOAN_ID_KEY_NAME)
			                .toString()));
			String emailTemplateKey = objectMap.get(
			        WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME)
			        .toString();
			if (loanVO != null && emailTemplateKey != null) {
				String emailTemplate = "";
				Template template = templateService
				        .getTemplateByKey(emailTemplateKey);
				if (template != null) {
					LOG.info("Send Email Template Found " + template.getValue());
					emailTemplate = template.getValue();
				}
				EmailVO emailEntity = new EmailVO();
				String[] names = new String[1];
				names[0] = loanVO.getUser().getFirstName();

				Map<String, String[]> substitutions = new HashMap<String, String[]>();
				substitutions.put("-name-", names);
				substitutions = doTemplateSubstitutions(substitutions,
				        objectMap);
				emailEntity.setSenderEmailId(loanVO.getUser().getUsername()
				        + CommonConstants.SENDER_EMAIL_ID);
				emailEntity.setSenderName(CommonConstants.SENDER_NAME);
				if (subject == null) {
					emailEntity.setSubject(CommonConstants.SUBJECT_DEFAULT);
				} else {
					emailEntity.setSubject(subject);
				}
				emailEntity.setTokenMap(substitutions);
				emailEntity.setTemplateId(emailTemplate);
				String loanManagerUsername = null;
				LoanTeamListVO loanTeamList = loanService
				        .getLoanTeamListForLoan(loanVO);
				for (LoanTeamVO loanTeam : loanTeamList.getLoanTeamList()) {
					if (loanTeam.getUser() != null) {
						if (loanTeam.getUser().getInternalUserDetail() != null) {
							if (loanTeam.getUser().getInternalUserDetail()
							        .getInternalUserRoleMasterVO().getId() == InternalUserRolesEum.LM
							        .getRoleId()) {
								loanManagerUsername = loanTeam.getUser()
								        .getUsername();
							}
						}
					}
				}
				List<String> loanManagerccList = new ArrayList<String>();
				if (loanManagerUsername != null) {
					loanManagerccList
					        .add(CommonConstants.SENDER_DEFAULT_USER_NAME + "-"
					                + loanManagerUsername + "-"
					                + loanVO.getId()
					                + CommonConstants.SENDER_EMAIL_ID);

					emailEntity.setCCList(loanManagerccList);
				}
				try {
					sendEmailService.sendEmailForLoanManagers(emailEntity,
					        loanVO.getId(), template);
				} catch (InvalidInputException e) {
					LOG.error("Exception Caught " + e.getMessage());
				} catch (UndeliveredEmailException e) {
					LOG.error("Exception Caught " + e.getMessage());
				}
			}
		}
	}

	private void createAlertForDisclosureDue(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.DISCLOSURE_AVAIL_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LOG.info("Create Alert for Disclosure Due : for " + loanId);
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), true);
		if (notificationList.size() == 0) {
			LoanVO loanVO = loanService.getLoanByID(loanId);
			WorkflowExec workflowExec = new WorkflowExec();
			workflowExec.setId(loanVO.getLoanManagerWorkflowID());
			WorkflowItemMaster workflowItemMaster = workflowService
			        .getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_DISCLOSURE_STATUS);
			WorkflowItemExec currMilestone = workflowService
			        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			LoanTurnAroundTimeVO loanTurnAroundTimeVO = loanService
			        .retrieveTurnAroundTimeByLoan(loanId, currMilestone
			                .getWorkflowItemMaster().getId());
			String content = WorkflowConstants.DISCLOSURE_AVAIL_NOTIFICATION_CONTENT;

			if (loanTurnAroundTimeVO != null) {
				long turnaroundTime = loanTurnAroundTimeVO.getHours();
				content = content.replace("72", "" + turnaroundTime);
			}

			NotificationVO notificationVO = new NotificationVO(loanId,
			        notificationType.getNotificationTypeName(), content);

			List<UserRolesEnum> userRoles = new ArrayList<UserRolesEnum>();
			userRoles.add(UserRolesEnum.INTERNAL);
			List<InternalUserRolesEum> internalUserRoles = new ArrayList<InternalUserRolesEum>();
			internalUserRoles.add(InternalUserRolesEum.LM);
			LOG.info("Creating Alert for LM" + notificationVO);
			notificationService.createRoleBasedNotification(notificationVO,
			        userRoles, internalUserRoles);
		}
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int userID = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		return iworkflowService.getRenderInfoFor1003(loanId, userID);
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

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		return null;
	}

	@Override
	public Map<String, String[]> doTemplateSubstitutions(
	        Map<String, String[]> substitutions,
	        HashMap<String, Object> objectMap) {
		if (substitutions == null) {
			substitutions = new HashMap<String, String[]>();
		}
		String[] ary = new String[1];
		ary[0] = Milestones.App1003.getMilestoneKey();
		substitutions.put("-status-", ary);
		return substitutions;
	}

}
