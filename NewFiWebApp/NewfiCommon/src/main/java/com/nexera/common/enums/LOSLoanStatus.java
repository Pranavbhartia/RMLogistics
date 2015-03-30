package com.nexera.common.enums;

public enum LOSLoanStatus {
	LQB_STATUS_LEAD_NEW(12), LQB_STATUS_LEAD_CANCELED(15), LQB_STATUS_LEAD_DECLINED(
	        16), LQB_STATUS_LEAD_OTHER(17), LQB_STATUS_LOAN_OPEN(0), LQB_STATUS_PRE_QUAL(
	        1), LQB_STATUS_REGISTERED(3), LQB_STATUS_PRE_PROCESSING(29), LQB_STATUS_PROCESSING(
	        22), LQB_STATUS_DOCUMENT_CHECK(30), LQB_STATUS_DOCUMENT_CHECK_FAILED(
	        31), LQB_STATUS_LOAN_SUBMITTED(28), LQB_STATUS_PRE_UNDERWRITING(32), LQB_STATUS_IN_UNDERWRITING(
	        13), LQB_STATUS_PRE_APPROVED(2), LQB_STATUS_APPROVED(4), LQB_STATUS_CONDITION_REVIEW(
	        33), LQB_STATUS_FINAL_UNDER_WRITING(23), LQB_STATUS_PRE_DOC_QC(34), LQB_STATUS_CLEAR_TO_CLOSE(
	        21), LQB_STATUS_DOCS_ORDERED(35), LQB_STATUS_DOCS_DRAWN(36), LQB_STATUS_DOCS_OUT(
	        5), LQB_STATUS_DOCS_BACK(24), LQB_STATUS_FUNDING_CONDITIONS(25), LQB_STATUS_FUNDED(
	        6), LQB_STATUS_RECORDED(19), LQB_STATUS_FINAL_DOCS(26), LQB_STATUS_LOAN_CLOSED(
	        11), LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW(40), LQB_STATUS_IN_PURCHASE_REVIEW(
	        41), LQB_STATUS_PRE_PURCHASE_CONDITIONS(42), LQB_STATUS_SUBMITTED_FOR_FINAL_PURCHASE_REVIEW(
	        43), LQB_STATUS_IN_FINAL_PURCHASE_REVIEW(44), LQB_STATUS_CLEAR_TO_PURCHASE(
	        45), LQB_STATUS_LOAN_PURCHASED(46), LQB_STATUS_READY_FOR_SALE(39), LQB_STATUS_LOAN_SHIPPED(
	        20), LQB_STATUS_INVESTOR_CONDITIONS(37), LQB_STATUS_INVESTOR_CONDITIONS_SENT(
	        38), LQB_STATUS_LOAN_SOLD(27), LQB_STATUS_COUNTER_OFFER_APPROVED(47), LQB_STATUS_LOAN_ON_HOLD(
	        7), LQB_STATUS_LOAN_CANCELED(9), LQB_STATUS_LOAN_SUSPENDED(8), LQB_STATUS_LOAN_DENIED(
	        10), LQB_STATUS_LOAN_WITHDRAWN(48), LQB_STATUS_LOAN_ARCHIVED(49), LQB_STATUS_LOAN_OTHER(
	        18), LQB_STATUS_LOAN_WEB_CONSUMER(14);
	private int losStatusID;

	private LOSLoanStatus(int losStatusID) {
		this.losStatusID = losStatusID;

	}

	public int getLosStatusID() {
		return losStatusID;
	}

	public void setLosStatusID(int losStatusID) {
		this.losStatusID = losStatusID;
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
}