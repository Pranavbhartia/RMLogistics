package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOG = LoggerFactory
	        .getLogger(DisclosuresManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Executing Disclosures Manager" + objectMap);
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
			LOG.debug("Making Disclosure Managers as " + mileStoneStatus
			        + "Saving Appraisal Need");

			needsListService.saveMasterNeedsForLoan(loanId, masterNeedsList);
			LOG.debug("Saved Appraiasl Need");
			createAppilcationPaymentAlert(objectMap);
		}
		if (mileStoneStatus != null) {
			LOG.debug("Updating MS entries for " + loanId
			        + " mileStoneStatus as " + mileStoneStatus);
			iWorkflowService.updateNexeraMilestone(loanId,
			        Milestones.DISCLOSURE.getMilestoneID(), mileStoneStatus);
		}
		if (flag) {
			LOG.debug("Sending Note from Disclosures Manager" + loanId
			        + " Status as " + returnStatus);
			makeANote(loanId, message);
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
			        message);
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
		LOG.debug("Creating Application Payment Alert " + objectMap);
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.APP_FEE_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String notificationReminderContent = WorkflowConstants.APP_FEE__NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, notificationReminderContent);
		iWorkflowService.createAlertOfType(createReminderVo);
	}

	private void dismissDisclosureDueAlerts(HashMap<String, Object> objectMap) {
		LOG.debug(" Dimiss Disclosure Due Alerts" + objectMap);
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
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothiing
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// Do Nothing - No need to generate any Reminder..
		return null;
	}

}
