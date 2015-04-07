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
	public static String DOCUMENT_TYPE_INITIAL_DISCLOSURES = "INITIAL  DISCLOSURES";

	public static String SYSTEM_GENERATED_NEED_MASTER_DISCLOSURES_AVAILABILE = "Disclosure Available";

	public static String SYSTEM_GENERATED_NEED_MASTER_DISCLOSURES_SIGNED = "Signed Disclosure";

	// LQB xml constants

	public static String SOAP_XML_RESPONSE_MESSAGE = "responseMessage";

	public static String SOAP_XML_UNDERWRITING_CONDITION_DESCRIPTION = "CondDesc";

	public static String SOAP_XML_LOAD_LOAN_STATUS = "sStatusT";

	// Borrower Credit Score Constants
	public static String SOAP_XML_BORROWER_EQUIFAX_SCORE = "aBEquifaxScore";
	public static String SOAP_XML_BORROWER_EXPERIAN_SCORE = "aBExperianScore";
	public static String SOAP_XML_BORROWER_TRANSUNION_SCORE = "aBTransUnionScore";

	// Co Borrower Credit Score Constants
	public static String SOAP_XML_CO_BORROWER_EQUIFAX_SCORE = "aCEquifaxScore";
	public static String SOAP_XML_CO_BORROWER_EXPERIAN_SCORE = "aCExperianScore";
	public static String SOAP_XML_CO_BORROWER_TRANSUNION_SCORE = "aCTransUnionScore";
}
