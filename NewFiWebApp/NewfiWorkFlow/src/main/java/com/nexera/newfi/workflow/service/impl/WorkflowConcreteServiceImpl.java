package com.nexera.newfi.workflow.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanTurnAroundTimeVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;

@Component
public class WorkflowConcreteServiceImpl implements IWorkflowService {

	@Autowired
	private LoanAppFormService loanAppFormService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private LoanService loanService;
	@Override
	public String getJsonStringOfMap(HashMap<String, Object> map) {
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

	@Override
	public LoanAppForm getLoanAppFormDetails(Loan loan) {
		return loanAppFormService.findByLoan(loan);
	}

	public String updateLMReminder(CreateReminderVo createReminderVo) {

		WorkflowItemExec currMilestone = workflowService
				.getWorkflowExecById(createReminderVo
						.getWorkflowItemExecutionId());
		if (currMilestone.getStatus().equals(
				WorkItemStatus.NOT_STARTED.getStatus())) {
			LoanVO loanVO = loanService.getLoanByID(createReminderVo
					.getLoanId());
			WorkflowExec workflowExec = new WorkflowExec();
			workflowExec.setId(loanVO.getLoanManagerWorkflowID());
			WorkflowItemMaster workflowItemMaster = workflowService
					.getWorkflowByType(createReminderVo.getPrevMilestoneKey());
			WorkflowItemExec prevMilestone = workflowService
					.getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			if (prevMilestone.getStatus().equals(
					WorkItemStatus.COMPLETED.getStatus())) {
				long noOfDays = (prevMilestone.getEndTime().getTime() - new Date()
						.getTime()) / (1000 * 60 * 60 * 24);

				LoanTurnAroundTimeVO loanTurnAroundTimeVO = loanService
						.retrieveTurnAroundTimeByLoan(createReminderVo
								.getLoanId(), currMilestone
								.getParentWorkflowItemExec().getId());
				long turnaroundTime = loanTurnAroundTimeVO.getHours();

				if (noOfDays >= turnaroundTime) {
					List<NotificationVO> notificationList = notificationService
							.findNotificationTypeListForLoan(
									createReminderVo.getLoanId(),
									createReminderVo.getNotificationType(),
									null);
					if (notificationList.size() == 0
							|| notificationList.get(0).getRead() == true) {
						NotificationVO notificationVO = new NotificationVO(
								createReminderVo.getLoanId(),
								createReminderVo.getNotificationType(),
								createReminderVo
										.getNotificationReminderContent());
						List<UserRolesEnum> userRoles = new ArrayList<UserRolesEnum>();
						userRoles.add(UserRolesEnum.INTERNAL);
						List<InternalUserRolesEum> internalUserRoles = new ArrayList<InternalUserRolesEum>();
						internalUserRoles.add(InternalUserRolesEum.LM);
						notificationService.createRoleBasedNotification(
								notificationVO, userRoles, internalUserRoles);
					}
				}
			}
		} else {
			List<NotificationVO> notificationList = notificationService
					.findNotificationTypeListForLoan(
							createReminderVo.getLoanId(),
							createReminderVo.getNotificationType(),
							true);
			for (NotificationVO notificationVO : notificationList) {
				notificationService.dismissNotification(notificationVO.getId());
			}
		}

		return null;
	}
}
