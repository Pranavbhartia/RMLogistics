package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NeededItemScoreVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class NeededItemsManager implements IWorkflowTaskExecutor {

	@Autowired
	NeedsListService needsListService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private NotificationService notificationService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(NeededItemsManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LOG.debug("Render State info of NeededItemsManager" + loanId);
		// Make a service call to get the number of needed items' assigned
		// against the
		NeededItemScoreVO neededItemScoreVO = needsListService
		        .getNeededItemsScore(loanId);
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(neededItemScoreVO.getTotalSubmittedItem() + " out of "
		        + neededItemScoreVO.getNeededItemRequired());
		needsListService.createOrDismissNeedsAlert(loanId);
		return new Gson().toJson(neededItemScoreVO);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		NeededItemScoreVO neededItemScoreVO = needsListService
		        .getNeededItemsScore(loanId);
		String status = null;
		if (neededItemScoreVO.getTotalSubmittedItem() > 0
		        || neededItemScoreVO.getNeededItemRequired() > 0) {

			// Needed list created by Loan Manager
			status = WorkItemStatus.PENDING.getStatus();
			LOG.debug("Making needs list as Pending" + status);
			LoanVO loanVO = loanService.getLoanByID(loanId);
			WorkflowExec workflowExec = new WorkflowExec();
			workflowExec.setId(loanVO.getLoanManagerWorkflowID());
			WorkflowItemMaster workflowItemMaster = workflowService
			        .getWorkflowByType(WorkflowConstants.WORKFLOW_ITEM_NEEDS_STATUS);
			WorkflowItemExec managerNeedItem = workflowService
			        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			workflowExec.setId(loanVO.getCustomerWorkflowID());
			workflowItemMaster = workflowService
			        .getWorkflowByType(WorkflowConstants.WORKFLOW_CUST_ITEM_NEEDS_STATUS);
			if (neededItemScoreVO.getTotalSubmittedItem() >= neededItemScoreVO
			        .getNeededItemRequired()) { // If needs fullfilled -
				                                // complete it
				status = WorkItemStatus.COMPLETED.getStatus();
				LOG.debug("Making needs list as " + status);
				engineTrigger.startWorkFlowItemExecution(managerNeedItem
				        .getId());
			} else { // if in case a new need had come in - make it PEnding
				     // again
				status = WorkItemStatus.PENDING.getStatus();
				LOG.debug("Making needs list as " + status);
				WorkflowItemExec custNeedItem = workflowService
				        .getWorkflowItemExecByType(workflowExec,
				                workflowItemMaster);
				engineTrigger.changeStateOfWorkflowItemExec(
				        managerNeedItem.getId(), status);
				engineTrigger.changeStateOfWorkflowItemExec(
				        custNeedItem.getId(), status);
			}
		}
		return status;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("updateReminder of Needs items " + objectMap);
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.NEEDED_ITEMS_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_1003_COMPLETE;
		String notificationReminderContent = WorkflowConstants.NEEDED_ITEMS_NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		NeededItemScoreVO neededItemScoreVO = needsListService
		        .getNeededItemsScore(loanId);
		if (neededItemScoreVO.getNeededItemRequired() <= 0) {
			LOG.debug("Seding out reminders ince intial needs not created  ");
			LoanVO loanVO = loanService.getLoanByID(createReminderVo
			        .getLoanId());
			WorkflowExec workflowExec = new WorkflowExec();
			workflowExec.setId(loanVO.getLoanManagerWorkflowID());
			WorkflowItemMaster workflowItemMaster = workflowService
			        .getWorkflowByType(createReminderVo.getPrevMilestoneKey());
			WorkflowItemExec prevMilestone = workflowService
			        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			iWorkflowService.sendReminder(createReminderVo,
			        createReminderVo.getWorkflowItemExecutionId(),
			        prevMilestone.getId());

		} else {
			notificationService.dismissReadNotifications(
			        createReminderVo.getLoanId(), notificationType);
		}
		return null;
	}

}
