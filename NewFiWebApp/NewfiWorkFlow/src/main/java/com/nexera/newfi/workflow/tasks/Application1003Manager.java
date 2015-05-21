package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
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
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		LOG.info("Executing Concrete class with Application1003Manager with "
		        + status);
		String returnStatus = null;
		if (status.equals(String
		        .valueOf(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED
		                .getLosStatusID()))) {
			int loanID = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			makeANote(loanID, LoanStatus.submittedMessage);
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        Milestones.App1003.getMilestoneKey());
			sendEmail(objectMap, CommonConstants.SUBJECT_APPLICATION_SUBMITTED);
			createAlertForDisclosureDue(objectMap);
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
			LOG.info("Saving Loan as INprogres");
			loanService.saveLoanProgress(loanID, new LoanProgressStatusMaster(
			        LoanProgressStatusMasterEnum.IN_PROGRESS));
		}
		return returnStatus;
	}
	public void sendEmail(HashMap<String, Object> objectMap, String subject) {
		if (objectMap != null) {
			LoanVO loanVO = loanService
			        .getLoanByID(Integer.parseInt(objectMap.get(
			                WorkflowDisplayConstants.LOAN_ID_KEY_NAME)
			                .toString()));
			if (loanVO != null) {
				String emailTemplateKey = objectMap.get(
				        WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME)
				        .toString();
				String emailTemplate = WorkflowDisplayConstants.EMAIL_TEMPLATE_DEFAULT_ID;
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
				emailEntity.setSenderEmailId("web@newfi.com");
				emailEntity.setSenderName("Newfi System");
				if (subject == null) {
					emailEntity.setSubject(CommonConstants.SUBJECT_DEFAULT);
				} else {
					emailEntity.setSubject(subject);
				}
				emailEntity.setTokenMap(substitutions);
				emailEntity.setTemplateId(emailTemplate);
				try {
					sendEmailService.sendEmailForLoanManagers(emailEntity,
					        loanVO.getId());
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
			        .retrieveTurnAroundTimeByLoan(loanId,
			                currMilestone.getWorkflowItemMaster().getId());
			long turnaroundTime = loanTurnAroundTimeVO.getHours();
			
			
			String content=WorkflowConstants.DISCLOSURE_AVAIL_NOTIFICATION_CONTENT;
			content=content.replace("72", ""+turnaroundTime);
			
			NotificationVO notificationVO = new NotificationVO(loanId,
			        notificationType.getNotificationTypeName(),
			        content);
			
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
