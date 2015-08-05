package com.nexera.common.commons;

public interface ErrorConstants {

	public static final String LOAN_MANAGER_DELETE_ERROR = "Cannot Delete: loan advisor has active loans";

	public static final String FORGET_PASSWORD_USER_EMPTY = "We�re sorry, the email you entered does not exist.";

	public static final String NULL_PASSWORD = "Password field cannot be empty";

	public static final String LOAN_MANAGER_DOESNOT_EXSIST = "Sorry, we cannot find any user corresponding to this email.";

	public static final String UPDATE_ERROR_CUSTOMER = "Customer details cannot be updated right now. Please try again later";

	public static final String UPDATE_ERROR_REALTOR = "Realtor details cannot be updated right now. Please try again later";

	public static final String UPDATE_ERROR_USER = "User details cannot be updated right now. Please try again later";

	public static final String REGISTRATION_USER_EXSIST = "User corresponding to this email aldready exists. Please register with a different email ID or use forgot password to login.";

	public static final String APPLY_LOAN_ERROR = "Error while applying loan.Please try again later";
	public static final String LINK_EXPIRED_ERROR = "Your link has expired. Enter your email ID to request a password reset link be sent again";

	public static final String ADMIN_CREATE_USER_ERROR = "User with email ID already exists";

	public static final String LQB_ENCRYPTION_MESSAGE = "Error occured while saving the LQB Credentials";
	public static final String LQB_CRED_ALREADY_SAVED = "Details already exist in the system.";
	public static final String LQB_TOKEN_GENERATION_ERR_MESSAGE = "LQB Credentials was not found valid";

	public static final String LQB_SAVE_FAILED = "LQB Credentials are incorrect, please try later";

	public static final String UPLOADFILEFAILEDCODE = "501";

	public static final String UPLOADFILEFAILEDMESSAGE = "Error while assigning document";
	
	public static final String USER_STATUS_INACTIVE = "Your account has been inactivated.Please contact newfi for reactivation.";
	
}
