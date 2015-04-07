package com.nexera.newfi.workflow.service;

import java.util.HashMap;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.CreateReminderVo;

public interface IWorkflowService {

	String getJsonStringOfMap(HashMap<String, Object> map);

	LoanAppForm getLoanAppFormDetails(Loan loan);

	public String updateLMReminder(CreateReminderVo createReminderVo);

	public String updateNexeraMilestone(int loanId, int masterMileStoneId,
	        String comments);

	public String getNexeraMilestoneComments(int loanId, Milestones milestone);

	public void sendReminder(CreateReminderVo createReminderVo,
	        int currMilestoneID, int prevMilestoneID);

	public void dismissReadNotifications(int loanID,
	        MilestoneNotificationTypes noticationType);
}
