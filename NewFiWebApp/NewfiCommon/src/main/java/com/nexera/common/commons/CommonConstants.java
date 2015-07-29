package com.nexera.common.commons;

/**
 * Holds application level constants
 */

public interface CommonConstants {

	/**
	 * Property file constants
	 */

	/**
	 * Default constants
	 */
	public static final Integer RANDOM_PASSWORD_LENGTH = 8;
	public static final int SYSTEM_ADMIN_ID = 1;

	public static final float PROFILE_STATUS_WEIGHTAGE = (100f / 3f);
	public static final String CSV_UPLOAD_SUCCESS = "CSV file uploaded successfully";
	/**
	 * Entity status
	 */
	// Inactive status of a user
	public static final Integer STATUS_INACTIVE = 0;

	// Active status of a user
	public static final Integer STATUS_ACTIVE = 1;

	public static final Integer PAGINATION_SIZE = 5;

	/*
	 * CSV constants
	 */
	public static final int CSV_COLUMN_LENGTH = 12;
	public static final String ERROR_LINE_NUMBER = "lineNumber";
	public static final String ERROR_CSV_LINE = "csvLine";
	public static final String ERROR_MESSAGE = "message";
	public static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	public static final String NAME_REGEX = "[a-zA-Z]+";

	/*
	 * Csv cloumn constants
	 */
	public static final int FNAME_COLUMN = 0;
	public static final int LNAME_COLUMN = 1;

	public static final int EMAIL_COLUMN = 2;
	public static final int ROLE_COLUMN = 3;
	/* public static final int LICENSE_INFO_COLUMN = 4; */
	public static final int CITY_COLUMN = 4;
	public static final int STATE_COLUMN = 5;
	public static final int ZIPCODE_COLUMN = 6;
	public static final int SECONDARY_PHONE_COLUMN = 7;
	public static final int SECONDARY_EMAIL_COLUMN = 8;
	public static final int DATE_OF_BIRTH_COLUMN = 9;
	public static final int PROFILE_LINK_COLUMN = 10;
	public static final int STATE_CODE_COLUMN = 11;
	public static final String STATE_CODE_STRING_SEPARATOR = ",";

	public static final String DEFAULT_FROM_ADDRESS = "newfiportal";

	public static final String MESSAGE_PROPERTIES_FILE = "displaymessage.properties";
	public static final String LABEL_PROPERTIES_FILE = "message.properties";
	public static final String CONFIG_PROPERTIES_FILE = "config.properties";
	public static final String APPLICATION_PROPERTIES_FILE = "application.properties";
	public static final String DEFAULT_LOCALE = "en_US";
	public static final int DEFAULT_LOAN_ID = 0;

	/**
	 * Braintree constants
	 */
	public static final Integer SANDBOX_MODE_TRUE = 1;
	public static final String SUCCESS_KEY = "success";
	public static final String MESSAGE_KEY = "message";
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	public static final int TRANSACTION_STATUS_ENABLED = 1;
	public static final int TRANSACTION_STATUS_DISABLED = 0;
	public static final int TRANSACTION_STATUS_FAILED = 2;

	/*
	 * SendGrid constants
	 */
	public static final String SENDER_NAME = "Team newfi";

	public static final String SENDER_EMAIL_ID = "@loan.newfi.com";

	public static final String SENDER_DEFAULT_USER_NAME = "support";

	public static final String SMS_DEFAULT_TEXT = "Please login to newfi";

	public static final String RAREMILE_SUPPORT_EMAIL_ID = "";

	public static final String EMAIL_FOOTER = "Thanks and Regards," + "<br>"
	        + "Newfi Team";

	public static final String SENDER_DOMAIN = "@loan.newfi.com";
	public static final String FORGET_PASSWORD_SUCCESS_MESSAGE = "Please check your mail for reset password";

	/*
	 * Constants for email subjects
	 */
	public static final String NOTE_SUBJECT = "Newfi Message from ";

	public static final String ANONYMOUS_USER = "User name unavailable";

	/*
	 * 
	 */
	public static final String SENDER_NAME_REGEX = SENDER_NAME + " <";
	public static final String SENDER_DOMAIN_REGEX = SENDER_DOMAIN + ">";

	public static final Integer SYSTEM_USER_USERID = 1;

	/*
	 * Loan Appform Constants
	 */
	public static final String PROPERTY_TYPE_SINGLE_FAMILY_RESIDENCE_VALUE = "0";
	public static final String PROPERTY_TYPE_CONDO = "1";
	public static final String PROPERTY_TYPE_MULTI_UNIT = "2";
	public static final String PROPERTY_TYPE_MOBILE_MANUFACTURE = "3";

	public static final String RESIDENCE_TYPE_PRIMARY_RESIDENCE = "0";
	public static final String RESIDENCE_TYPE_VACATION_HOME = "1";
	public static final String RESIDENCE_TYPE_INVESTMENT_PROPERTY = "2";

	public static final double LOAN_AMOUNT_THRESHOLD = 417000;

	public static final Integer DEFAULT_APPLICATION_FEE = 500;

	public static final int CSFPR = 395;
	public static final int JSFPR = 595;
	public static final int CINV = 495;
	public static final int JINV = 695;
	public static final int CMF = 595;
	public static final int JMF = 695;

	/*
	 * Credit score - For UI
	 */
	public static final String EQ = "EQ-";
	public static final String TU = "TU-";
	public static final String EX = "EX-";

	public static final String UNKNOWN_SCORE = "?";
	public static final String CREDIT_SCORE_SEPARATOR = "|";

	/* Template Key Names */

	public static final String TEMPLATE_KEY_NAME_PAYMENT = "PAYMENT_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_NEW_USER = "NEW_USER_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_NEW_NOTE = "NEW_NOTE_TEMPLATE";
	public static final String TEMPLATE_KEY_NAME_PAYMENT_UNSUCCESSFUL = "PAYMENT_UNSUCCESSFUL_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_TEST_TEMPLATE = "TEST_EMAIL_TEMPLATE_ID";
	
	public static final String TEMPLATE_KEY_NAME_NEW_LEAD_NO_PRODUCTS = "NEW_LEAD_NO_PRODUCTS";
	public static final String TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI = "WELCOME_TO_NEWFI_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI_REALTOR = "WELCOME_TO_NEWFI_REALTOR_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI_TITLE_COMPANY = "WELCOME_TO_NEWFI_TITLE_COMPANY_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI_HOME_OWNER_INSURANCE_COMPANY = "WELCOME_TO_NEWFI_HOME_OWNER_INSURANCE_COMPANY_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_CREATED_ACCOUNT_PASSWORD_NOT_UPDATED = "CREATED_ACCOUNT_PASSWORD_NOT_UPDATED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_FORGOT_YOUR_PASSWORD = "FORGOT_YOUR_PASSWORD_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_GET_TO_KNOW_NEWFI = "GET_TO_KNOW_NEWFI_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_DRIP_RATE_ALERTS = "DRIP_RATE_ALERTS_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_APPLICATION_NOT_COMPLETE = "APPLICATION_NOT_COMPLETE_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_APPLICATION_NOT_YET_COMPLETED = "APPLICATION_NOT_YET_COMPLETED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_APPLICATION_NOT_YET_COMPLETED_3 = "APPLICATION_NOT_YET_COMPLETED_3_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_APPLICATION_FINISHED = "APPLICATION_FINISHED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_NO_PRODUCTS_AVAILABLE = "NO_PRODUCTS_AVAILABLE_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_NO_PRODUCTS_AVAILABLE_LOAN_MANAGER = "NO_PRODUCTS_AVAILABLE_LOAN_MANAGER_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_FILE_INACTIVITY = "FILE_INACTIVITY_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_CREDIT_INFO = "CREDIT_INFO_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_RATE_LOCK_REQUESTED = "RATE_LOCK_REQUESTED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_RATES_LOCKED = "RATES_LOCKED_TEMPLATE_ID";
	// NEXNF-424 : Dont send Appriasl ordered email when app fee is paid in
	// newfi
	// public static final String TEMPLATE_KEY_NAME_APPRAISAL_ORDERED =
	// "APPRAISAL_ORDERED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_APPLICATION_FEE_PAID = "APPLICATION_FEE_PAID_TEMPLATE_ID";
	// NEXNF-415 : Removing Appraisal email calls
	// public static final String TEMPLATE_KEY_NAME_APPRAISAL_ORDERED_PURCHASE =
	// "APPRAISAL_ORDERED_PURCHASE_TEMPLATE_ID";
	// public static final String TEMPLATE_KEY_NAME_APPRAISAL_ORDERED_REFINANCE
	// = "APPRAISAL_ORDERED_REFINANCE_TEMPLATE_ID";
	// public static final String TEMPLATE_KEY_NAME_APPRAISAL_RECEIVED =
	// "APPRAISAL_RECEIVED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_PRUCHASE_AND_REFINANCE_REQUEST = "PRUCHASE_AND_REFINANCE_REQUEST_TEMPLATE_ID";
	/*
	 * public static final String TEMPLATE_KEY_NAME_DISCLOSURES_AVAILABLE =
	 * "DISCLOSURES_AVAILABLE_TEMPLATE_ID"; public static final String
	 * TEMPLATE_KEY_NAME_DISCLOSURES_ARE_COMPLETE =
	 * "DISCLOSURES_ARE_COMPLETE_TEMPLATE_ID";
	 */
	public static final String TEMPLATE_KEY_NAME_INITIAL_NEEDS_LIST_SET = "INITIAL_NEEDS_LIST_SET_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_NEEDS_LIST_UPDATED = "NEEDS_LIST_UPDATED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_7_DAYS_AGED_DOCUMENT = "7_DAYS_AGED_DOCUMENT_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_LOAN_PREAPPROVED_PRUCHASE = "LOAN_PREAPPROVED_PRUCHASE_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_LOAN_APPROVED_WITH_CONDITIONS = "LOAN_APPROVED_WITH_CONDITIONS_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_LOAN_SUSPENDED = "LOAN_SUSPENDED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_LOAN_DECLINED = "LOAN_DECLINED_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_LOAN_CLEAR_TO_CLOSE = "LOAN_CLEAR_TO_CLOSE_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_FINAL_DOCS_SENT = "FINAL_DOCS_SENT_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_DOCS_ASSIGNED_TO_FUNDER = "DOCS_ASSIGNED_TO_FUNDER_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_PRE_QUAL_LETTER = "PRE_QUAL_LETTER_TEMPLATE_ID";
	public static final String TEMPLATE_KEY_NAME_NEW_CUSTOMER_ALERT = "NEW_CUSTOMER_ALERT";
	public static final String PURCHASE_CONTRACT = "Purchase Contract Including Addendums and Counter-offers";
	public static final String TEMPLATE_KEY_NAME_DOCUMENT_TYPE_ASSIGNMENT_FAILURE = "DOCUMENT_TYPE_ASSIGNMENT_FAILURE";
	

	// Document type credit report.
	public static final String LQB_DOC_TYPE_CR = "CREDIT REPORT";

	public static final String FILE_DOWNLOAD_SERVLET = "readFileAsStream.do?uuid=";

	public static final String elapsedPrependStr = "due in";

	public static final String elapsedAppendStr = "hrs.";

	public static final String elapsedOverDueStr = "Overdue.";
	public static final String THUMBNAIL_PARAM = "&isThumb=0";
	public static final String EMAIL_EMPTY_FOOTER = ".";

	// Subjects
	public static final String SUBJECT_GETTING_TO_KNOW_NEWFI = "Getting to know Newfi";

	public static final String SUBJECT_DRIP_RATE_ALERTS = "Drip Rate Alerts";

	public static final String SUBJECT_CREATED_ACCOUNT_NOT_COMPLETED_APPLICATION = "Created Account - Not Completed Application";

	public static final String SUBJECT_NO_COMPLETED_APPLICATION = "No Completed Application";

	public static final String SUBJECT_NOT_COMPLETED_APPLICATION_EMAIL_3 = "Not Completed Application EMail 3";

	public static final String SUBJECT_APPLICATION_FINISHED = "Application Finished";

	public static final String SUBJECT_NO_PRODUCTS_AVAILABLE = "No Products Available";

	public static final String SUBJECT_NO_ACTION_FILE_INACTIVITY = "No Action - File Inactivity";

	public static final String SUBJECT_YOUR_CREDIT = "Your Credit";

	public static final String SUBJECT_RATE_LOCK_REQUESTED = "Rate Lock Requested";

	public static final String SUBJECT_RATES_LOCKED = "Rates Locked";

	public static final String SUBJECT_YOUR_APPLICATION_FEE_DUE = "Your Application Fee Due";

	public static final String SUBJECT_APPLICATION_FEE_PAID = "Application Fee Paid";

	public static final String SUBJECT_APPLICATION_FEE_PENDING = "Application Fee Pending";

	public static final String SUBJECT_APPLICATION_FEE_FAILED = "Application Fee Failed";

	public static final String SUBJECT_APPRAISAL_ORDERED_PURCHASE = "Appraisal Ordered - Purchase";

	public static final String SUBJECT_APPRAISAL_ORDERED_REFINANCE = "Appraisal Ordered - Refinance";

	public static final String SUBJECT_APPRAISAL_RECEIVED = "Appraisal Received";

	public static final String SUBJECT_PASSWORD_NOT_UPADTED = "Password not updated! Please update";

	public static final String SUBJECT_DISCLOSURE_AVAILABLE = "Disclosure Available";

	public static final String SUBJECT_DISCLOSURE_ARE_COMPLETE = "Disclosure Are Complete";

	public static final String SUBJECT_LOAN_HAS_BEEN_PREAPPROVED = "Loan has been preapproved - Purchase Only";

	public static final String SUBJECT_LOAN_APPROVED_WITH_CONDITIONS = "Loan Approved With Conditions";

	public static final String SUBJECT_LOAN_FUNDED = "Loan Funded";

	public static final String SUBJECT_LOAN_SUSPENDED = "Loan Suspended";

	public static final String SUBJECT_UPDATE_NEEDS_LIST = "Your needs list has been updated";

	public static final String SUBJECT_LOAN_DECLINED = "Loan Declined";

	public static final String SUBJECT_LOAN_WITHDRAWN = "Loan Withdrawn";

	public static final String SUBJECT_LOAN_ARCHIVED = "Loan Archived";

	public static final String SUBJECT_LOAN_IS_CLEAR_TO_CLOSE = "Loan Is Clear To Close";

	public static final String SUBJECT_DEFAULT = "Loan Status Updated From Newfi";

	public static final String SUBJECT_APPLICATION_SUBMITTED = "Application Has Been Submitted";

	public static final String SUBJECT_INITIAL_NEEDS_LIST_ARE_SET = "Initial Needs List For Your Newfi Loan";
	public static final String SUBJECT_TITLE_COMPANY = "Welcome to newfi as a Title Company";
	public static final String SUBJECT_HOME_INSUR_COMPANY = "Welcome to newfi as a Home Insurance Company";
	
	public static final String SUBJECT_DOCUMENT_TYPE_ASSIGNMENT_FAILURE = "Document Type Lqb Assignment Failure";
	

	public static final String SUBJECT_RESET_PASSWORD = "Reset newfi Password";

	public static final String CREATET_LOAN_TEMPLATE = "DIRECT-REFI-MASTER TEMPLATE";

	public static final String SLOANNUMBER = "sLoanNumber";

	public static final String SXMLQUERYMAP = "sXmlQueryMap";

	public static final String FORMAT = "format";

	public static final String OPNAME = "opName";

	public static final String LOANVO = "loanVO";

	public static final String SEND_EMAIL_TO_CUSTOMER_ONLY = "customerOnly";

	public static final String SEND_EMAIL_TO_TEAM = "team";

	public static final String SEND_EMAIL_TO_LOAN_MANAGERS = "loanManagersOnly";

	public static final String SEND_EMAIL_TO_SALES_MANGERS = "salesManagersOnly";

	public static final String SEND_EMAIL_TO_INTERNAL_USERS = "internalUsers";
	
	public static final String SEND_EMAIL_TO_LM_AND_SM = "LM_SM_Users";

	public static final String STICKET = "sTicket";

	public static final String PRE_QUALIFICATION_LETTER = "Pre-Qualification letter";
	public static final String SUBJECT_NEW_LOAN_ALERT = "NEWFI ALERT: New Lead";
	public static final String EXTRA_DOCUMENT = "Extra";

	// Marking page rate

	public static final String HOMEWORTHTODAY = "350000";
	public static final String LOANAMOUNT = "280000";
	public static final String STATEFROMAPI = "California";
	public static final String CITY = "Santa Clara";
	public static final String ZIPCODE = "94087";
	public static final String OCCTYPE = "0";
	public static final String SUBPROPTYPE = "0";
	public static final String LOANPURPOSE = "2";

	public static final String FILE_NAME_PREQUAL_LETTER = "newfi pre-qualification.pdf";
	public static final String DEFAULT_CREDIT_SCORE = "800";
	public static final String[] allowedStates = { "CA", "OR", "WA" };
	
	public static final String ZIPCODE_ISNOT_VALID = "Zip code is not valid";
	public static final String ZIPCODE_ISNOT_APPROVED ="Please enter a valid Zip Code in a newfi approved state: CA, OR or WA";
	public static final String ZIPCODE_VALID ="Valid ZipCode";

	
	
}
