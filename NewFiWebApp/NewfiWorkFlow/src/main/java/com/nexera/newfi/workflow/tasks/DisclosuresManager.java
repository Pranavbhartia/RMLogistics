package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class DisclosuresManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	private LoanService loanService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private NeedsListService needsListService;
	@Autowired
	private IWorkflowService iWorkflowService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		boolean flag = false;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String message = "";
		String returnStatus = "";
		String mileStoneStatus = null;
		if (status.equals(LoanStatus.disclosureAvail)) {
			message = LoanStatus.disclosureAvailMessage;
			flag = true;
			returnStatus = WorkItemStatus.STARTED.getStatus();
			mileStoneStatus = LoanStatus.disclosureAvail;
		} else if (status.equals(LoanStatus.disclosureSigned)) {
			message = LoanStatus.disclosureSignedMessage;
			flag = true;
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
			mileStoneStatus = LoanStatus.disclosureSigned;
			// Have to add need for appraisal
			NeedsListMaster appraisalMasterNeed = needsListService
			        .fetchNeedListMasterByType(MasterNeedsEnum.Appraisal_Report
			                .getIdentifier());
			List<NeedsListMaster> masterNeedsList = new ArrayList<NeedsListMaster>();
			masterNeedsList.add(appraisalMasterNeed);
			needsListService.saveMasterNeedsForLoan(loanId, masterNeedsList);
			createAppilcationPaymentAlert(objectMap);
		}
		if (mileStoneStatus != null) {

			iWorkflowService.updateNexeraMilestone(loanId,
			        Milestones.DISCLOSURE.getMilestoneID(), mileStoneStatus);
		}
		if (flag) {
			makeANote(loanId, message);
			sendEmail(objectMap);
			// Dismiss any DISCLOSURE_AVAIL_NOTIFICATION_TYPE alerts
			dismissDisclosureDueAlerts(objectMap);
			return returnStatus;
		}
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> objectMap) {
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		return iWorkflowService.getRenderInfoForDisclosure(loanId);
	}

	private void createAppilcationPaymentAlert(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.APP_FEE_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());

		String notificationReminderContent = WorkflowConstants.APP_FEE__NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, notificationReminderContent);
		iWorkflowService.createAlertOfType(createReminderVo);
	}

	private void dismissDisclosureDueAlerts(HashMap<String, Object> objectMap) {
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.DISCLOSURE_AVAIL_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), true);
		for (NotificationVO notificationVO : notificationList) {
			notificationService.dismissNotification(notificationVO.getId());
		}
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
