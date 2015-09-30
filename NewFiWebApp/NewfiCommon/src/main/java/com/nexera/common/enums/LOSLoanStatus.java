package com.nexera.common.enums;

public enum LOSLoanStatus {

	LQB_STATUS_LEAD_NEW(12, "New Loan"), LQB_STATUS_LEAD_CANCELED(15,
	        "Lead Cancelled"), LQB_STATUS_LEAD_DECLINED(16, "Declined"), LQB_STATUS_LEAD_OTHER(
	        17), LQB_STATUS_LOAN_OPEN(0,"Loan Open", 1), LQB_STATUS_PRE_QUAL(1,
	        "Pre Qualified"), LQB_STATUS_REGISTERED(3, "Registered", 2), LQB_STATUS_PRE_PROCESSING(
	        29), LQB_STATUS_PROCESSING(22, "Processing"), LQB_STATUS_DOCUMENT_CHECK(30), LQB_STATUS_DOCUMENT_CHECK_FAILED(
	        31), LQB_STATUS_LOAN_SUBMITTED(28, "Submitted" , 3), LQB_STATUS_PRE_UNDERWRITING(
	        32), LQB_STATUS_IN_UNDERWRITING(13, "In Underwriting", 1), LQB_STATUS_PRE_APPROVED(
	        2), LQB_STATUS_APPROVED(4, "Approved"), LQB_STATUS_CONDITION_REVIEW(
	        33,"Condition Review"), LQB_STATUS_FINAL_UNDER_WRITING(23), LQB_STATUS_PRE_DOC_QC(34), LQB_STATUS_CLEAR_TO_CLOSE(
	        21, "Clear to close", 2), LQB_STATUS_DOCS_ORDERED(35,
	        "Docs Ordered", 1), LQB_STATUS_DOCS_DRAWN(36), LQB_STATUS_DOCS_OUT(
	        5, "Docs Out", 2), LQB_STATUS_DOCS_BACK(24), LQB_STATUS_FUNDING_CONDITIONS(
	        25), LQB_STATUS_FUNDED(6, "Funded", 3), LQB_STATUS_RECORDED(19), LQB_STATUS_FINAL_DOCS(
	        26), LQB_STATUS_LOAN_CLOSED(11, "Closed", 6), LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW(
	        40), LQB_STATUS_IN_PURCHASE_REVIEW(41), LQB_STATUS_PRE_PURCHASE_CONDITIONS(
	        42), LQB_STATUS_SUBMITTED_FOR_FINAL_PURCHASE_REVIEW(43), LQB_STATUS_IN_FINAL_PURCHASE_REVIEW(
	        44), LQB_STATUS_CLEAR_TO_PURCHASE(45), LQB_STATUS_LOAN_PURCHASED(46), LQB_STATUS_READY_FOR_SALE(
	        39), LQB_STATUS_LOAN_SHIPPED(20), LQB_STATUS_INVESTOR_CONDITIONS(37), LQB_STATUS_INVESTOR_CONDITIONS_SENT(
	        38), LQB_STATUS_LOAN_SOLD(27), LQB_STATUS_COUNTER_OFFER_APPROVED(47), LQB_STATUS_LOAN_ON_HOLD(
	        7), LQB_STATUS_LOAN_CANCELED(9), LQB_STATUS_LOAN_SUSPENDED(8,
	        "Suspended", 4), LQB_STATUS_LOAN_DENIED(10, "Denied", 4), LQB_STATUS_LOAN_WITHDRAWN(
	        48, "Withdrawn", 5), LQB_STATUS_LOAN_ARCHIVED(49, "Archived", 6), LQB_STATUS_LOAN_OTHER(
	        18), LQB_STATUS_LOAN_WEB_CONSUMER(14);
	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	private int losStatusID;
	private String displayStatus;
	private int order;

	private LOSLoanStatus(int losStatusID) {
		this.losStatusID = losStatusID;
		this.displayStatus = String.valueOf(losStatusID);
		this.order = 0;
	}

	private LOSLoanStatus(int losStatusID, String displayString) {
		this.losStatusID = losStatusID;
		this.displayStatus = displayString;
		this.order = 0;

	}

	private LOSLoanStatus(int losStatusID, String displayString, int order) {
		this.losStatusID = losStatusID;
		this.displayStatus = displayString;
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public int getLosStatusID() {
		return losStatusID;
	}

	public static LOSLoanStatus getLOSStatus(int inputID) {
		LOSLoanStatus losStatus = LOSLoanStatus.LQB_STATUS_LOAN_OPEN;
		for (LOSLoanStatus ls : LOSLoanStatus.values()) {
			if (ls.losStatusID == inputID) {
				losStatus = ls;
				break;
			}
		}
		return losStatus;
	}

	public static LOSLoanStatus getLOSStatus(String displayString) {
		LOSLoanStatus losStatus = LOSLoanStatus.LQB_STATUS_LOAN_OPEN;
		for (LOSLoanStatus ls : LOSLoanStatus.values()) {
			if (ls.displayStatus.equalsIgnoreCase(displayString)) {
				losStatus = ls;
				break;
			}
		}
		return losStatus;
	}
}
