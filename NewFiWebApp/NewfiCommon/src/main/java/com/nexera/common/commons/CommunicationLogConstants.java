package com.nexera.common.commons;

public interface CommunicationLogConstants {

	/*
	 * Replacable args list
	 */
	public static final String CUSTOMER = "{customer}";
	public static final String USER = "{user}";
	
	/*
	 * User initiated need list
	 */

	public static final String INITIATE_NEED = "Hi {customer},\n {user} has initiated the need list for you. Please navigate to your Need list section to upload the necessary documents";

	/*
	 * User modified a need list
	 */
	public static final String MODIFY_NEED = "Hi {customer},\n {user} has updated the need list for you. Please navigate to your Need list section to upload the necessary documents";
	
	
	/*
	 * User has uploaded a document
	 * 
	 */
	public static final String DOCUMENT_UPLOAD = "{user} has uploaded document(s) to this loan";
}
