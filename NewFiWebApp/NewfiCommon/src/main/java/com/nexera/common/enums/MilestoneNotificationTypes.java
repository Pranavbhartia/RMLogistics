package com.nexera.common.enums;

public enum MilestoneNotificationTypes {
	CREDIT_SCORE_NOTIFICATION_TYPE("CREDIT_SCORE_NOTIFICATION"), SYS_EDU_NOTIFICATION_TYPE(
	        "SYS_EDU_NOTIFICATION"), DISCLOSURE_AVAIL_NOTIFICATION_TYPE(
	        "DISCLOSURE_AVAIL_NOTIFICATION"), NEEDED_ITEMS_NOTIFICATION_TYPE(
	        "NEEDED_ITEMS_NOTIFICATION"), APP_FEE_NOTIFICATION_TYPE(
	        "APP_FEE_NOTIFICATION"), APP_FEE_NOTIFICATION_OVERDUE_TYPE(
	        "APP_FEE_NOTIFICATION_OVERDUE"), LOCK_RATE_CUST_NOTIFICATION_TYPE(
	        "LOCK_RATE_CUST_NOTIFICATION"), LOCK_RATE_NOTIFICATION_TYPE(
	        "LOCK_NOTIFICATION"), PURCHASE_DOCUMENT_EXPIRATION_NOTIFICATION_TYPE(
	        "PURCHASE_DOC_NOTIFICATION"), APPRAISAL_NOTIFICATION_TYPE(
	        "APPRAISAL_NOTIFICATION"), UW_NOTIFICATION_TYPE("UW_NOTIFICATION"), TEAM_ADD_NOTIFICATION_TYPE(
	        "TEAM_ADD_NOTIFICATION");
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
