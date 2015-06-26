package com.nexera.core.utility;

/**
 * @author charu
 *
 */
public interface CoreCommonConstants {
	public static String MARITAL_STATUS_DIVORCED = "Divorced";
	public static String MARITAL_STATUS_SETTLEMENT = "Settlement";
	public static int LOAN_TYPE_PURCHASE = 1;
	public static int LOAN_TYPE_REFINANCE = 2;
	public static int LOAN_TYPE_REFINANCE_CASH_OUT = 3;

	public static int PROPERTY_TYPE_RENTING = 2;

	// Disclosures related constants
	public static String FOLDER_NAME_INITIAL_DISCLOSURES = "GENERATED DOCUMENTS";

	public static String SYSTEM_GENERATED_NEED_MASTER_DISCLOSURES_AVAILABILE = "Disclosure Available";

	public static String SYSTEM_GENERATED_NEED_MASTER_DISCLOSURES_SIGNED = "Signed Disclosure";

	// Rate Lock status

	public static String RATE_NOT_LOCKED = "0";

	public static String RATE_LOCK_REQUESTED = "2";

	public static String RATE_LOCKED = "1";

	// LQB xml constants

	public static String SOAP_XML_RESPONSE_MESSAGE = "responseMessage";

	public static String SOAP_XML_RESPONSE_TIME = "responseTime";

	public static String SOAP_XML_ERROR_DESCRIPTION = "errorDescription";

	public static String SOAP_XML_UNDERWRITING_CONDITION_DESCRIPTION = "CondDesc";

	public static String SOAP_XML_LOAD_LOAN_STATUS = "sStatusT";

	public static String SOAP_XML_USER_SSN_NUMBER = "aBSsn";

	public static String SOAP_XML_CREDIT_REPORT_FIELD = "aCreditReportId";

	public static String SOAP_XML_RATE_LOCK_STATUS = "sRateLockStatusT";

	public static String SOAP_XML_RATE_LOCK_DATA = "sPmlCertXmlContent";

	public static String SOAP_XML_RATE_LOCK_EXPIRED_DATE = "sRLckdExpiredD";

	public static String SOAP_XML_LOCKED_RATE = "sBrokerLockOriginatorPriceNoteIR";
	
	public static String SOAP_XML_DOCS_OUT = "sDocsD";

	// Borrower Credit Score Constants
	public static String SOAP_XML_BORROWER_EQUIFAX_SCORE = "aBEquifaxScore";
	public static String SOAP_XML_BORROWER_EXPERIAN_SCORE = "aBExperianScore";
	public static String SOAP_XML_BORROWER_TRANSUNION_SCORE = "aBTransUnionScore";

	// Co Borrower Credit Score Constants
	public static String SOAP_XML_CO_BORROWER_EQUIFAX_SCORE = "aCEquifaxScore";
	public static String SOAP_XML_CO_BORROWER_EXPERIAN_SCORE = "aCExperianScore";
	public static String SOAP_XML_CO_BORROWER_TRANSUNION_SCORE = "aCTransUnionScore";

	// Exception Types
	public static String EXCEPTION_TYPE_LOAN_BATCH = "loan_batch";
	public static String EXCEPTION_TYPE_EMAIL_BATCH = "email_batch";

}
