package com.nexera.newfi.workflow.service;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.CreateReminderVo;

public interface IWorkflowService {

	LoanAppForm getLoanAppFormDetails(Loan loan);

	public String updateLMReminder(CreateReminderVo createReminderVo);

	public String getNexeraMilestoneComments(int loanId, Milestones milestone);

	public void sendReminder(CreateReminderVo createReminderVo,
	        int currMilestoneID, int prevMilestoneID);

	public void createAlertOfType(CreateReminderVo createReminderVo);

	public String getCreditDisplayScore(int userID);

	public String getRenderInfoForDisclosure(int loanID);

	public String getRenderInfoForApplicationFee(int loanID);

	public String getRenderInfoFor1003(int loanID, int userID);

	public String getRenderInfoForAppraisal(int loanID);

	public String getDisclosureURL(int loanID, MasterNeedsEnum needItem);

	public String getUWMilestoneDates(int loanID, LOSLoanStatus loanStatus);

	public void cleanupDisclosureMilestones(int loanID);

	public String getAppraisalMilestoneDates(int loanID, String status);
}
