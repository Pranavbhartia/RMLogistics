package com.nexera.common.enums;

public enum MilestoneNotificationTypes {
	CREDIT_SCORE_NOTIFICATION_TYPE("CREDIT_SCORE_NOTIFICATION"), SYS_EDU_NOTIFICATION_TYPE(
	        "SYS_EDU_NOTIFICATION"), DISCLOSURE_AVAIL_NOTIFICATION_TYPE(
	        "DISCLOSURE_AVAIL_NOTIFICATION");
	MilestoneNotificationTypes(String notificationTypeName) {
		this.notificationTypeName = notificationTypeName;
	}

	public String getNotificationTypeName() {
		return notificationTypeName;
	}

	private String notificationTypeName;

	public static MilestoneNotificationTypes getNotificationTypeByName(
	        String name) {
		MilestoneNotificationTypes notificationType = null;
		for (MilestoneNotificationTypes ms : MilestoneNotificationTypes
		        .values()) {
			if (name != null
			        && name.equalsIgnoreCase(ms.getNotificationTypeName())) {
				notificationType = ms;
				break;
			}
		}
		return notificationType;
	}
}
