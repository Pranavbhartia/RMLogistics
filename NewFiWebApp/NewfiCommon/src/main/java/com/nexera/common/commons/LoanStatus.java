package com.nexera.common.commons;

public interface LoanStatus {
	String inUnderwritingMessage = "In Underwriting";
	String underwritingClearToCloseMessage = "Approved With Conditions";
	String initialContactMadeMessage = "Initial Contact Made";
	String submittedMessage = "Submitted";
	String applicationSubmittedMessage = "Application";
	String appraisalReceivedMessage = "Appraisal Received";
	String creditScoreMessage = "Credit information received";
	String disclosureAvailMessage = "Disclosures are available";

	String disclosureSignedMessage = "Disclosures Signed";
	String sysEduMessage = "System Education Message";
	String loancClosedMessage = "Loan Closed";
	String loanDeclinedMessage = "Loan Declined";
	String loanFundedMessage = "Loan Funded";
	String loanWithdrawnMessage = "Loan Withdrawn";
	String disclosureAvail = "Available";

	String disclosureSigned = "Signed";
	String appraisalAvailable = "Received";
	String teamMemberAddedMessage = "New Team Member Added";
	String titleCompanyAddedMessage = "New Title Company Added";
	String HMInsCompanyAddedMessage = "New Home Owner Insurance Company Added";

	String loanSuspendedMessage = "Loan Suspended";

	String loanArchivedMessage = "Loan Archived";

	String LM_Decision_Note_Message = "LM Decision Note Message";

	String APP_PAYMENT_SUCCESS = "Success";

	String APP_PAYMENT_FAILURE = "Failure";

	String APP_PAYMENT_OVERDUE = "Over due";
	String APP_PAYMENT_NOT_INITIATED = "Not Initiated";
	String APP_PAYMENT_CLICK_TO_PAY = "Click here to pay";
	String APP_PAYMENT_CANT_PAY_YET = "Disclosures required";
	String APP_PAYMENT_PENDING = "Pending Verification";

	String paymentPendingStatusMessage = "Pending Verification";

	String paymentSuccessStatusMessage = "Payment Made";
	String paymentFailureStatusMessage = "Failure";

	String ratesLocked = "Rates Locked";

	String noProductFound = "Not Products Found";
	
	String ratesLockedRequested="Rate Lock Requested by User";
}
