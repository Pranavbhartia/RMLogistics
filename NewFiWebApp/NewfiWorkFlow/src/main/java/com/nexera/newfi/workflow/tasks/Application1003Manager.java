package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.service.IWorkflowService;
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

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		String returnStatus = null;
		if (status.equals(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED
		        .getLosStatusID() + "")) {
			int loanID = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			makeANote(loanID, LoanStatus.submittedMessage);
			sendEmail(objectMap);
			createAlertForDisclosureDue(objectMap);
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
			loanService.saveLoanProgress(loanID, new LoanProgressStatusMaster(
			        LoanProgressStatusMasterEnum.IN_PROGRESS));
		}
		return returnStatus;
	}

	private void createAlertForDisclosureDue(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.DISCLOSURE_AVAIL_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), null);
		if (notificationList.size() == 0
		        || notificationList.get(0).getRead() == true) {
			NotificationVO notificationVO = new NotificationVO(loanId,
			        notificationType.getNotificationTypeName(),
			        WorkflowConstants.DISCLOSURE_AVAIL_NOTIFICATION_CONTENT);
			List<UserRolesEnum> userRoles = new ArrayList<UserRolesEnum>();
			userRoles.add(UserRolesEnum.INTERNAL);
			List<InternalUserRolesEum> internalUserRoles = new ArrayList<InternalUserRolesEum>();
			internalUserRoles.add(InternalUserRolesEum.LM);
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

	public String updateReminder(HashMap<String, Object> objectMap) {
		return null;
	}

}
