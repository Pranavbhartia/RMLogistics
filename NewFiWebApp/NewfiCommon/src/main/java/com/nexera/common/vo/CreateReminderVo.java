package com.nexera.common.vo;

import com.nexera.common.enums.MilestoneNotificationTypes;

/*
 * @author charu
 */
public class CreateReminderVo {
	private MilestoneNotificationTypes notificationType;
	private Integer loanId;
	private Integer userID;
	private Integer workflowItemExecutionId;
	private String prevMilestoneKey;
	private String notificationReminderContent;
	private boolean forCustomer;

	public CreateReminderVo(MilestoneNotificationTypes notificationType,
	        Integer loanId, Integer workflowItemExecutionId,
	        String prevMilestoneKey, String notificationReminderContent) {
		this.notificationType = notificationType;
		this.loanId = loanId;
		this.workflowItemExecutionId = workflowItemExecutionId;
		this.prevMilestoneKey = prevMilestoneKey;
		this.notificationReminderContent = notificationReminderContent;
	}

	public CreateReminderVo(MilestoneNotificationTypes notificationType,
	        int loanId, String notificationReminderContent) {
		this.notificationType = notificationType;
		this.loanId = loanId;
		this.notificationReminderContent = notificationReminderContent;
	}

	public CreateReminderVo() {

	}

	public MilestoneNotificationTypes getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(MilestoneNotificationTypes notificationType) {
		this.notificationType = notificationType;
	}

	public String getPrevMilestoneKey() {
		return prevMilestoneKey;
	}

	public void setPrevMilestoneKey(String prevMilestoneKey) {
		this.prevMilestoneKey = prevMilestoneKey;
	}

	public String getNotificationReminderContent() {
		return notificationReminderContent;
	}

	public void setNotificationReminderContent(
	        String notificationReminderContent) {
		this.notificationReminderContent = notificationReminderContent;
	}

	public boolean isForCustomer() {
		return forCustomer;
	}

	public void setForCustomer(boolean forCustomer) {
		this.forCustomer = forCustomer;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getWorkflowItemExecutionId() {
		return workflowItemExecutionId;
	}

	public void setWorkflowItemExecutionId(Integer workflowItemExecutionId) {
		this.workflowItemExecutionId = workflowItemExecutionId;
	}

}
