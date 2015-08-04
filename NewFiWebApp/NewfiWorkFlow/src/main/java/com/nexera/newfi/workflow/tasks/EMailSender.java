package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.newfi.workflow.service.IWorkflowService;
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
	@Autowired
	private IWorkflowService iWorkflowService;

	// private SendEmailService sendEmailService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// Call the Email Sender here.
		LOG.debug("Sending System Education Email");
		if (objectMap != null) {
			int loanId = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        LoanStatus.sysEduMessage);
			objectMap.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
			        CommonConstants.TEMPLATE_KEY_NAME_GET_TO_KNOW_NEWFI);
			/*sendEmailForCustomer(objectMap,
			        CommonConstants.SUBJECT_GETTING_TO_KNOW_NEWFI);*/
			/* makeANote(loanId, LoanStatus.sysEduMessage) */;
		}
		return WorkItemStatus.COMPLETED.getStatus();
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {

		return "";
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
		LOG.debug(" Updating Reminder " + objectMap);
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.SYS_EDU_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_INITIAL_CONTACT;
		String notificationReminderContent = WorkflowConstants.SYS_EDU_NOTIFICATION_CONTENT;
		LOG.debug(" prevMilestoneKey is " + prevMilestoneKey);
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		iWorkflowService.updateLMReminder(createReminderVo);
		return null;
	}
}
