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
	
	/**
	 * Entity status
	 */
	//Inactive status of a user
	public static final Integer STATUS_INACTIVE = 0;
	
	//Active status of a user
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

	
	public static final String DEFAULT_FROM_ADDRESS = "newfiportal";

	public static final String MESSAGE_PROPERTIES_FILE = "displaymessage.properties";	
	public static final String LABEL_PROPERTIES_FILE = "message.properties";
	public static final String CONFIG_PROPERTIES_FILE = "config.properties";
	public static final String DEFAULT_LOCALE="en_US";
	public static final int DEFAULT_LOAN_ID = 0;
	
	/**
	 * Braintree constants
	 */
	public static final Integer SANDBOX_MODE_TRUE = 1;
	public static final String SUCCESS_KEY = "success";
	public static final String MESSAGE_KEY = "message";
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	
	/*
	 * SendGrid constants
	 */
	public static final String SENDER_NAME = "NewFi Team";
	
	
	public static final String SENDER_EMAIL_ID = "support@loan.newfi.com";
	public static final String EMAIL_FOOTER = "Thanks and Regards," + "<br>" + "Newfi Team";
	
	public static final String SENDER_DOMAIN="@loan.newfi.com";
	
	/*
	 * Constants for email subjects
	 */
	public static final String NOTE_SUBJECT = "NewFi - You have a new note";

	public static final String ANONYMOUS_USER = "User name unavailable";
	
	/*
	 * 
	 */
	public static final String SENDER_NAME_REGEX = SENDER_NAME+" <";
	public static final String SENDER_DOMAIN_REGEX = SENDER_DOMAIN+">";
	
	public static final Integer SYSTEM_USER_USERID = 1;
}