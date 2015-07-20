package com.nexera.common.enums;

public enum LoanProgressStatusMasterEnum {
	NEW_PROSPECT("NEW_PROSPECT", 1), LEAD("LEAD", 2), NEW_LOAN("NEW_LOAN", 3), IN_PROGRESS(
	        "IN_PROGRESS", 4), SMCLOSED("CLOSED", 5), WITHDRAWN("WITHDRAWN", 6), DECLINED(
	        "DECLINED", 7), DELETED("DELETED", 8);

	private final String statusName;
	private final int statusId;

	private LoanProgressStatusMasterEnum(String s, int roleId) {
		this.statusName = s;
		this.statusId = roleId;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : statusName.equals(otherName);
	}

	public int getStatusId() {
		return statusId;
	}

	public String toString() {
		return statusName;
	}

}
