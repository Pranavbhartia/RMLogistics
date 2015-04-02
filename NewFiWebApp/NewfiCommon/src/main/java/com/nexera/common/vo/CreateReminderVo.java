package com.nexera.common.vo;

/*
 * @author charu
 */
public class CreateReminderVo {
	private String notificationType;
	private int loanId;
	private int workflowItemExecutionId;
	private String prevMilestoneKey;
	private String notificationReminderContent;

	public CreateReminderVo(String notificationType, int loanId,
			int workflowItemExecutionId, String prevMilestoneKey,
			String notificationReminderContent) {
		this.notificationType = notificationType;
		this.loanId = loanId;
		this.workflowItemExecutionId = workflowItemExecutionId;
		this.prevMilestoneKey = prevMilestoneKey;
		this.notificationReminderContent = notificationReminderContent;
	}

	public CreateReminderVo() {

	}
	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int getWorkflowItemExecutionId() {
		return workflowItemExecutionId;
	}

	public void setWorkflowItemExecutionId(int workflowItemExecutionId) {
		this.workflowItemExecutionId = workflowItemExecutionId;
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

}