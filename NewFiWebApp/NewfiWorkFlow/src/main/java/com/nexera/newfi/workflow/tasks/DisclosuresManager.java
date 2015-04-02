package com.nexera.newfi.workflow.tasks;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.NotificationService;
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

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME)
				.toString();
		boolean flag=false;
		int loanId=Integer.parseInt(objectMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String message="";
		String returnStatus = "";
		if (status.equals(LoanStatus.disclosureAvail)) {
			message=LoanStatus.disclosureAvailMessage;
			flag = true;
			returnStatus = WorkItemStatus.STARTED.getStatus();
		} else if (status.equals(LoanStatus.disclosureViewed)) {
			message=LoanStatus.disclosureViewedMessage;
			flag = true;
			returnStatus = WorkItemStatus.STARTED.getStatus();
		} else if (status.equals(LoanStatus.disclosureSigned)) {
			message = LoanStatus.disclosureSignedMessage;
			flag = true;
			returnStatus = WorkItemStatus.COMPLETED.getStatus();

			// Have to add need for appraisal
			NeedsListMaster appraisalMasterNeed = needsListService
					.fetchNeedListMasterByType(MasterNeedsEnum.Appraisal_Report
							.getIdentifier());
			List<NeedsListMaster> masterNeedsList = new ArrayList<NeedsListMaster>();
			masterNeedsList.add(appraisalMasterNeed);
			needsListService.saveMasterNeedsForLoan(loanId, masterNeedsList);

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
		HashMap<String, Object> map = new HashMap<String, Object>();
		Loan loan = new Loan();
		loan.setId(Integer.parseInt(objectMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		LoanMilestone loanMilestone = loanService.findLoanMileStoneByLoan(loan,
				Milestones.DISCLOSURE.getMilestoneKey());
		String status = loanMilestone.getComments().toString();
		// TODO check Status Mapping
		// String DisplayStat="";
		if (status.equals(LoanStatus.disclosureAvail)
				|| status.equals(LoanStatus.disclosureViewed)
				|| status.equals(LoanStatus.disclosureSigned)) {
			NeedsListMaster needsListMaster = new NeedsListMaster();
			needsListMaster
					.setId(Integer
							.parseInt(MasterNeedsEnum.System_Disclosure_Need
									.getIndx()));
			LoanNeedsList loanNeedsList = needsListService.findNeedForLoan(
					loan, needsListMaster);
			map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
					status);
			// TODO check column path
			map.put(WorkflowDisplayConstants.RESPONSE_URL_KEY, loanNeedsList
					.getUploadFileId().getS3path());


		} else {
			map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
					status);
		}
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

	private void dismissDisclosureDueAlerts(HashMap<String, Object> objectMap) {
		String notificationType = WorkflowConstants.DISCLOSURE_AVAIL_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		List<NotificationVO> notificationList = notificationService
				.findNotificationTypeListForLoan(loanId, notificationType, true);
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
