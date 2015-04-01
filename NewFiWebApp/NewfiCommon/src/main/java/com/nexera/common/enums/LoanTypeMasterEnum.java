package com.nexera.common.enums;

public enum LoanTypeMasterEnum {
	PUR("PUR", 1),  REF("REF", 2), REFCO(
	        "REFCO", 3);

	private final String statusName;
	private final int statusId;

	private LoanTypeMasterEnum(String s, int roleId) {
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
