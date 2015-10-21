package com.nexera.common.enums;

public enum LoanLCStates {

	Lead(1, "Lead"), LoanOpen(2, "Loan Open"), PreQualified(3, "Pre-Qualified"), Registered(
	        4, "Registered"), PreProcessing(5, "Pre-Processing"), Processing(6,
	        "Processing"), Submitted(7, "Submitted"), PreApproved(8,
	        "Pre-Approved"), Approved(9, "Approved"), ConditionReview(10,
	        "Condition Review"), DocsOrdered(11, "Docs Ordered"), DocsTitle(12,
	        "Docs in Title"), DocsSigned(13, "Docs Signed"), Funded(14, "Funded"), Canceled(
	        15, "Canceled"), Suspended(16, "Suspended"), Denied(17, "Denied"), Withdrawn(
	        18, "Withdrawn"),Application(19,"Application");
	public int getLcStateID() {
		return lcStateID;
	}

	public void setLcStateID(int lcStateID) {
		this.lcStateID = lcStateID;
	}

	public String getLcStateKey() {
		return lcStateKey;
	}

	public void setLcStateKey(String lcStateKey) {
		this.lcStateKey = lcStateKey;
	}

	private LoanLCStates(int lcStateID, String lcStateKey) {
		this.lcStateID = lcStateID;
		this.lcStateKey = lcStateKey;
	}

	private int lcStateID;
	private String lcStateKey;
}