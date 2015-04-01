package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
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

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		if (status.equals(LoanStatus.initiated)) {
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.initiatedMessage);
		} else if (status.equals(LoanStatus.submitted)) {
			// TODO check if we get timestamp for take a note from LQB
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.submittedMessage);
			sendEmail(objectMap);
			// TODO check if this is the right place to call this
			createAlertForDisclosureDue(objectMap);
		}
		return null;
	}

	private void createAlertForDisclosureDue(HashMap<String, Object> objectMap) {
		String notificationType = WorkflowConstants.DISCLOSURE_AVAIL_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		List<NotificationVO> notificationList = notificationService
				.findNotificationTypeListForLoan(loanId, notificationType, null);
		if (notificationList.size() == 0
				|| notificationList.get(0).getRead() == true) {
			NotificationVO notificationVO = new NotificationVO(loanId,
					notificationType,
					WorkflowConstants.DISCLOSURE_AVAIL_NOTIFICATION_CONTENT);
			List<UserRolesEnum> userRoles=new ArrayList<UserRolesEnum>();
			userRoles.add(UserRolesEnum.INTERNAL);
			List<InternalUserRolesEum> internalUserRoles=new ArrayList<InternalUserRolesEum>();
			internalUserRoles.add(InternalUserRolesEum.LM);
			notificationService.createRoleBasedNotification(notificationVO,
					userRoles, internalUserRoles);
		}
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


	public String updateReminder(HashMap<String, Object> objectMap) {
		return null;
	}

}
