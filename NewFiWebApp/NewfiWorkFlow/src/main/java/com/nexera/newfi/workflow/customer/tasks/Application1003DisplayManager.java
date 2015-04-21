package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.tasks.NexeraWorkflowTask;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class Application1003DisplayManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	private LoanDao loanDao;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private LoanService loanService;

	private static final Logger LOG = LoggerFactory
	        .getLogger(Application1003DisplayManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute ");
		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		LOG.debug("Status is " + status);
		String returnStatus = null;
		if (status.equals(LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED
		        .getLosStatusID() + "")) {
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
		}
		Integer loanID = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		createAlertForAgentAddition(loanID);
		return returnStatus;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus ");
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method invokeAction ");
		return null;
	}

	private void createAlertForAgentAddition(int loanId) {
		LOG.debug("Inside method createAlertForAgentAddition ");
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.TEAM_ADD_NOTIFICATION_TYPE;

		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), null);
		if (notificationList.size() == 0
		        || notificationList.get(0).getRead() == true) {
			LOG.debug("Creating new notification for "
			        + notificationType.getNotificationTypeName());
			LoanVO loanVO = new LoanVO(loanId);
			boolean agentFound = false;
			ExtendedLoanTeamVO extendedLoanTeamVO = loanService
			        .findExtendedLoanTeam(loanVO);
			if (extendedLoanTeamVO != null
			        && extendedLoanTeamVO.getUsers() != null) {
				for (UserVO userVO : extendedLoanTeamVO.getUsers()) {
					if (userVO.getUserRole() != null
					        && userVO
					                .getUserRole()
					                .getRoleCd()
					                .equalsIgnoreCase(
					                        UserRolesEnum.REALTOR.getName())) {
						LOG.debug("Agent found ");
						agentFound = true;
						break;

					}
				}
			}
			if (!agentFound) {
				LOG.debug("Not able to find any agent associated ");
				NotificationVO notificationVO = new NotificationVO(loanId,
				        notificationType.getNotificationTypeName(),
				        WorkflowConstants.AGENT_ADD_NOTIFICATION_CONTENT);
				LOG.debug("Creating new notification to add agent ");
				notificationService.createNotificationAsync(notificationVO);
			}
		}
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("inside method updateReminder");
		return null;
	}

}
