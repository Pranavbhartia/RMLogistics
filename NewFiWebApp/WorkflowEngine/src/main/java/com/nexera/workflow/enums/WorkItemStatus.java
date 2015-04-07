package com.nexera.workflow.enums;

public enum WorkItemStatus {
	NOT_STARTED("0"), STARTED("1"), PENDING("2"), COMPLETED("3");

	private String status;

	WorkItemStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static WorkItemStatus getWorkItemStatus(String strStatus) {
		WorkItemStatus workItemStatus = null;
		for (WorkItemStatus ws : WorkItemStatus.values()) {
			if (ws.status.equalsIgnoreCase(strStatus)) {
				workItemStatus = ws;
				break;
			}
		}
		return workItemStatus;
	}
}
