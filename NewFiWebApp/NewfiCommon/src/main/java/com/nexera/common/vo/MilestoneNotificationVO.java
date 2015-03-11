package com.nexera.common.vo;

public class MilestoneNotificationVO {

	private int workflowItemId;
	private int loanId;

	private NotificationVO notificationVO;

	public int getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(int workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public NotificationVO getNotificationVO() {
		return notificationVO;
	}

	public void setNotificationVO(NotificationVO notificationVO) {
		this.notificationVO = notificationVO;
	}
}
