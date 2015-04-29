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
	public static final int CSV_COLUMN_LENGTH = 13;
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
	public static final int LICENSE_INFO_COLUMN = 4;
	public static final int CITY_COLUMN = 5;
	public static final int STATE_COLUMN = 6;
	public static final int ZIPCODE_COLUMN = 7;
	public static final int SECONDARY_PHONE_COLUMN = 8;
	public static final int SECONDARY_EMAIL_COLUMN = 9;
	public static final int DATE_OF_BIRTH_COLUMN = 10;
	public static final int PROFILE_LINK_COLUMN = 11;
	public static final int STATE_CODE_COLUMN = 12;
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
	public static final String SENDER_NAME = "NewFi Team";

	public static final String SENDER_EMAIL_ID = "support@loan.newfi.com";

	public static final String RAREMILE_SUPPORT_EMAIL_ID = "";

	public static final String EMAIL_FOOTER = "Thanks and Regards," + "<br>"
	        + "Newfi Team";

	public static final String SENDER_DOMAIN = "@loan.newfi.com";
	public static final String FORGET_PASSWORD_SUCCESS_MESSAGE = "Please Check your mail for reset password";

	/*
	 * Constants for email subjects
	 */
	public static final String NOTE_SUBJECT = "NewFi - You have a new note";

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
	public static final String TEMPLATE_WORKFLOW_COMPLETION = "TEMPLATE_WORKFLOW_COMPLETION";
	public static final String TEMPLATE_WELCOME_TO_NEWFI = "WELCOME_TO_NEWFI_TEMPLATE_ID";
	public static final String TEMPLATE_CREATED_ACCOUNT_PASSWORD_NOT_UPDATED = "CREATED_ACCOUNT_PASSWORD_NOT_UPDATED_TEMPLATE_ID";
	public static final String TEMPLATE_FORGOT_YOUR_PASSWORD = "FORGOT_YOUR_PASSWORD_TEMPLATE_ID";
	public static final String TEMPLATE_GET_TO_KNOW_NEWFI = "GET_TO_KNOW_NEWFI_TEMPLATE_ID";
	public static final String TEMPLATE_DRIP_RATE_ALERTS = "DRIP_RATE_ALERTS_TEMPLATE_ID";
	public static final String TEMPLATE_APPLICATION_NOT_COMPLETE = "APPLICATION_NOT_COMPLETE_TEMPLATE_ID";
	public static final String TEMPLATE_APPLICATION_NOT_YET_COMPLETED = "APPLICATION_NOT_YET_COMPLETED_TEMPLATE_ID";
	public static final String TEMPLATE_APPLICATION_NOT_YET_COMPLETED_3 = "APPLICATION_NOT_YET_COMPLETED_3_TEMPLATE_ID";
	public static final String TEMPLATE_APPLICATION_FINISHED = "APPLICATION_FINISHED_TEMPLATE_ID";
	public static final String TEMPLATE_NO_PRODUCTS_AVAILABLE = "NO_PRODUCTS_AVAILABLE_TEMPLATE_ID";
	public static final String TEMPLATE_FILE_INACTIVITY = "FILE_INACTIVITY_TEMPLATE_ID";
	public static final String TEMPLATE_CREDIT_INFO = "CREDIT_INFO_TEMPLATE_ID";
	public static final String TEMPLATE_RATE_LOCK_REQUESTED = "RATE_LOCK_REQUESTED_TEMPLATE_ID";
	public static final String TEMPLATE_RATES_LOCKED = "RATES_LOCKED_TEMPLATE_ID";
	public static final String TEMPLATE_APPRAISAL_ORDERED = "APPRAISAL_ORDERED_TEMPLATE_ID";
	public static final String TEMPLATE_APPLICATION_FEE_PAID = "APPLICATION_FEE_PAID_TEMPLATE_ID";
	public static final String TEMPLATE_APPRAISAL_ORDERED_PURCHASE = "APPRAISAL_ORDERED_PURCHASE_TEMPLATE_ID";
	public static final String TEMPLATE_APPRAISAL_ORDERED_REFINANCE = "APPRAISAL_ORDERED_REFINANCE_TEMPLATE_ID";
	public static final String TEMPLATE_APPRAISAL_RECEIVED = "APPRAISAL_RECEIVED_TEMPLATE_ID";
	public static final String TEMPLATE_PRUCHASE_AND_REFINANCE_REQUEST = "PRUCHASE_AND_REFINANCE_REQUEST_TEMPLATE_ID";
	public static final String TEMPLATE_DISCLOSURES_AVAILABLE = "DISCLOSURES_AVAILABLE_TEMPLATE_ID";
	public static final String TEMPLATE_DISCLOSURES_ARE_COMPLETE = "DISCLOSURES_ARE_COMPLETE_TEMPLATE_ID";
	public static final String TEMPLATE_INITIAL_NEEDS_LIST_SET = "INITIAL_NEEDS_LIST_SET_TEMPLATE_ID";
	public static final String TEMPLATE_NEEDS_LIST_UPDATED = "NEEDS_LIST_UPDATED_TEMPLATE_ID";
	public static final String TEMPLATE_7_DAYS_AGED_DOCUMENT = "7_DAYS_AGED_DOCUMENT_TEMPLATE_ID";
	public static final String TEMPLATE_LOAN_PREAPPROVED_PRUCHASE = "LOAN_PREAPPROVED_PRUCHASE_TEMPLATE_ID";
	public static final String TEMPLATE_LOAN_APPROVED_WITH_CONDITIONS = "LOAN_APPROVED_WITH_CONDITIONS_TEMPLATE_ID";
	public static final String TEMPLATE_LOAN_SUSPENDED = "LOAN_SUSPENDED_TEMPLATE_ID";
	public static final String TEMPLATE_LOAN_DECLINED = "LOAN_DECLINED_TEMPLATE_ID";
	public static final String TEMPLATE_LOAN_CLEAR_TO_CLOSE = "LOAN_CLEAR_TO_CLOSE_TEMPLATE_ID";
	public static final String TEMPLATE_FINAL_DOCS_SENT = "FINAL_DOCS_SENT_TEMPLATE_ID";
	public static final String TEMPLATE_DOCS_ASSIGNED_TO_FUNDER = "DOCS_ASSIGNED_TO_FUNDER_TEMPLATE_ID";

	public static final String PURCHASE_CONTRACT = "Purchase Contract Including Addendums and Counter-offers";

	// Document type credit report.
	public static final String LQB_DOC_TYPE_CR = "CREDIT REPORT";

	public static final String FILE_DOWNLOAD_SERVLET = "readFileAsStream.do?uuid=";

	public static final String elapsedPrependStr = "due in";

	public static final String elapsedAppendStr = "hrs.";

	public static final String elapsedOverDueStr = "Overdue.";
	public static final String THUMBNAIL_PARAM = "&isThumb=0";
	public static final String EMAIL_EMPTY_FOOTER = ".";
}
