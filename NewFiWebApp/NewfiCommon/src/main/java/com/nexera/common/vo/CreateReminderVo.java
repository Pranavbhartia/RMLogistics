package com.nexera.common.vo;

import com.nexera.common.enums.MilestoneNotificationTypes;

/*
 * @author charu
 */
public class CreateReminderVo {
	private MilestoneNotificationTypes notificationType;
	private int loanId;
	private int userID;
	private int workflowItemExecutionId;
	private String prevMilestoneKey;
	private String notificationReminderContent;
	private boolean forCustomer;
	public CreateReminderVo(MilestoneNotificationTypes notificationType,
	        int loanId,
			int workflowItemExecutionId, String prevMilestoneKey,
			String notificationReminderContent) {
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

	public int getUserID() {
	    return userID;
    }

	public void setUserID(int userID) {
	    this.userID = userID;
    }

	public boolean isForCustomer() {
	    return forCustomer;
    }

	public void setForCustomer(boolean forCustomer) {
	    this.forCustomer = forCustomer;
    }

}
