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

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class EMailSender extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
			.getLogger(EMailSender.class);
	@Autowired
	private SendGridEmailService sendGridEmailService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private LoanService loanService;

	// private SendEmailService sendEmailService;

	public String execute(HashMap<String, Object> objectMap) {
		// Call the Email Sender here.
		if (objectMap != null) {
			int loanId = Integer.parseInt(objectMap.get(
					WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			LoanVO loanVo = loanService.getLoanByID(loanId);
			String emailTemplate = objectMap.get(
					WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME)
					.toString();
			LOG.debug("Template name is " + emailTemplate);

			EmailVO emailEntity = new EmailVO();
			List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
			for (UserVO userVo : loanVo.getLoanTeam()) {
				EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
				emailRecipientVO.setEmailID(userVo.getEmailId());
				emailRecipientVO.setRecipientName(userVo.getDisplayName());
				recipients.add(emailRecipientVO);
			}

			emailEntity.setSenderEmailId("web@newfi.com");
			emailEntity.setRecipients(recipients);

			emailEntity.setSenderName("Newfi System");
			emailEntity.setSubject("Nexera Newfi Portal");
			Map<String, String[]> substitutions = new HashMap<String, String[]>();
			substitutions.put(
					"-name-",
							new String[] { WorkflowDisplayConstants.EMAIL_RECPIENT_NAME });
			// emailEntity.setTemplateBased(true);
			emailEntity.setTokenMap(substitutions);
			emailEntity.setTemplateId(emailTemplate);

			// sendEmailService.sendMail(emailEntity, false);
			sendGridEmailService.sendAsyncMail(emailEntity);
			makeANote(loanId, LoanStatus.sysEduMessage);

		}
		return WorkflowConstants.SUCCESS;
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {

		return "";
	}

	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		String notificationType = WorkflowConstants.SYS_EDU_NOTIFICATION_TYPE;
		int loanId=Integer.parseInt(objectMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
				WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		WorkflowItemExec sysEduMilestone = workflowService
				.getWorkflowExecById(workflowItemExecutionId);
		if (sysEduMilestone.getStatus().equals(
				WorkItemStatus.NOT_STARTED.getStatus())) {
			LoanVO loanVO = loanService.getLoanByID(loanId);
			WorkflowExec workflowExec = new WorkflowExec();
			workflowExec.setId(loanVO.getLoanManagerWorkflowID());
			WorkflowItemMaster workflowItemMaster = workflowService
					.getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_INITIAL_CONTACT);
			WorkflowItemExec workflowitemexec = workflowService
					.getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			if (workflowitemexec.getStatus().equals(
					WorkItemStatus.COMPLETED.getStatus())) {
				long noOfDays = (workflowitemexec.getEndTime().getTime() - new Date()
						.getTime()) / (1000 * 60 * 60 * 24);
				long turnaroundTime=70;
				if (noOfDays > turnaroundTime) {
					List<NotificationVO> notificationList = notificationService
							.findNotificationTypeListForLoan(loanId,
									notificationType, null);
					if (notificationList.size() == 0
							|| notificationList.get(0).getRead() == true) {
						NotificationVO notificationVO = new NotificationVO(
								loanId, notificationType,
								WorkflowConstants.SYS_EDU_NOTIFICATION_CONTENT);
						notificationService.createNotification(notificationVO);
					}
				}
			}
		} else {
			List<NotificationVO> notificationList = notificationService
					.findNotificationTypeListForLoan(loanId, notificationType,
							true);
			for (NotificationVO notificationVO : notificationList) {
				notificationService.dismissNotification(notificationVO.getId());
			}
		}

		return null;
	}
}
