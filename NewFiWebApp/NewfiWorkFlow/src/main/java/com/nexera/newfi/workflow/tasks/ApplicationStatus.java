package com.nexera.newfi.workflow.tasks;

public interface ApplicationStatus {

	String initiated = "customerInitiates";
	String initiatedMessage = "Application Initiated";
	String submitted = "applicationSubmitted";
	String submittedMessage = "1003 submitted";
	String disclosureAvail = "available";
	String disclosureAvailMessage = "Disclosures are available";
	String disclosureViewed = "Viewed";
	String disclosureViewedMessage = "Disclosures Viewed";
	String disclosureSigned = "Signed";
	String disclosureSignedMessage = "Disclosures Signed";
	String appraisalOrdered = "Appraisal Ordered";
	String appraisalOrderedMessage = "Appraisal Ordered";
	String appraisalPending = "Appraisal Pending";
	String appraisalPendingMessage = "Appraisal Pending";
	String appraisalReceived = "Appraisal Received";
	String appraisalReceivedMessage = "Appraisal Received";
	String inUnderwriting = "In Underwriting";
	String inUnderwritingMessage = "In Underwriting";
	String underwritingObservationsReceived = "Underwriting observations Received";
	String underwritingObservationsReceivedMessage = "Underwriting observations Received";
	String underwritingSubmitted = "Underwriting Submitted";
	String underwritingSubmittedMessage = "Underwriting Submitted";
	String approvedWithConditions = "Approved With Conditions";
	String approvedWithConditionsMessage = "Approved With Conditions";
	String underwritingApproved = "Approved With Conditions";
	String underwritingApprovedMessage = "Approved With Conditions";
	String loanClosed = "Loan Closed";
	String loancClosedMessage = "Loan Closed";
	String loanDeclined = "Loan Declined";
	String loanDeclinedMessage = "Loan Declined";
	String loanFunded = "Loan Declined";
	String loanFundedMessage = "Loan Funded";
}
