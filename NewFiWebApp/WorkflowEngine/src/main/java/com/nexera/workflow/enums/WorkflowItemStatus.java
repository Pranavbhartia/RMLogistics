package com.nexera.workflow.enums;

public enum WorkflowItemStatus {
	NOT_STARTED("0"), IN_PROGRESS("1"), COMPLETED("2");

	private final String statusValue;

	private WorkflowItemStatus(String s) {
		statusValue = s;
	}

	public WorkflowItemStatus getItemStatus(String strStatus) {
		WorkflowItemStatus wfStatusResult = WorkflowItemStatus.NOT_STARTED;
		for (WorkflowItemStatus wfStatus : WorkflowItemStatus.values()) {
			if (strStatus != null && wfStatus.statusValue.equals(strStatus)) {
				wfStatusResult = wfStatus;
				break;
			}
		}
		return wfStatusResult;
	}

	public String getStatusValue() {
		return statusValue;
	}
	
	
}
