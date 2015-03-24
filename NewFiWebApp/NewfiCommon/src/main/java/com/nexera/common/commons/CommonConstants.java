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
	public static final int RANDOM_PASSWORD_LENGTH = 8;
	
	/**
	 * Entity status
	 */
	//Inactive status of a user
	int STATUS_INACTIVE = 0;
	
	//Active status of a user
	int STATUS_ACTIVE = 1;

	int PAGINATION_SIZE = 5;

	public static final String DEFAULT_FROM_ADDRESS = "newfiportal";

	public static final String MESSAGE_PROPERTIES_FILE = "displaymessage.properties";	
	public static final String LABEL_PROPERTIES_FILE = "message.properties";
	public static final String CONFIG_PROPERTIES_FILE = "config.properties";
	public static final String DEFAULT_LOCALE="en_US";
	
	/**
	 * Braintree constants
	 */
	public static final int SANDBOX_MODE_TRUE = 1;
	public static final String SUCCESS_KEY = "success";
	public static final String MESSAGE_KEY = "message";
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	
	/*
	 * SendGrid constants
	 */
	public static final String SENDER_NAME = "NewFi Team";
	public static final String SENDER_EMAIL_ID = "support@newfi.com";
	public static final String EMAIL_FOOTER = "Thanks and Regards," + "<br>" + "Newfi Team";
}