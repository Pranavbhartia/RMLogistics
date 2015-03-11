package com.nexera.common.vo;

public class MilestoneNotificationVO {

	private int milestoneId;
	private int loanId;

	private NotificationVO notificationVO;

	
	public int getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(int milestoneId) {
		this.milestoneId = milestoneId;
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
