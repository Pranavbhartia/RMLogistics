package com.nexera.common.commons;

public interface ErrorConstants {

	public static final String LOAN_MANAGER_DELETE_ERROR="Cannot Delete:loan manager has no active loans";

    public static final String FORGET_PASSWORD_USER_EMPTY="We’re sorry, the email you entered does not exist.";
    
    public static final String NULL_PASSWORD="Password cannot be null";
    
    public static final String LOAN_MANAGER_DOESNOT_EXSIST="Manager doenot exist.Please give a valid manager email";

    public static final String UPDATE_ERROR_CUSTOMER="Error While updating customer details. Please try again later";
    
    public static final String UPDATE_ERROR_REALTOR="Error While updating realtor details. Please try again later";
    
    public static final String UPDATE_ERROR_USER="Error While updating user details. Please try again later";
    
    public static final String REGISTRATION_USER_EXSIST="User exists. Please register with a different emailID";

    public static final String APPLY_LOAN_ERROR="Error while applying loan.Please try again later";
    public static final String LINK_EXPIRED_ERROR ="Your link has expired. Enter you email address to request a password rest link be sent again";
    
    public static final String ADMIN_CREATE_USER_ERROR="User with emailID already exists";
}
