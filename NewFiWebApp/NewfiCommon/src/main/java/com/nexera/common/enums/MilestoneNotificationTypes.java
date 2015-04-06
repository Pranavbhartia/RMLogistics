package com.nexera.common.enums;

public enum MilestoneNotificationTypes {
	CREDIT_SCORE_NOTIFICATION_TYPE("CREDIT_SCORE_NOTIFICATION");
	MilestoneNotificationTypes(String notificationTypeName) {
		this.notificationTypeName = notificationTypeName;
	}

	public String getNotificationTypeName() {
		return notificationTypeName;
	}

	private String notificationTypeName;
}
