package com.nexera.common.commons;

public interface ErrorConstants {

	public static final String LOAN_MANAGER_DELETE_ERROR="Cannot Delete:loan manager has no active loans";

    public static final String FORGET_PASSWORD_USER_EMPTY="User does not exist";
    
    public static final String NULL_PASSWORD="Password cannot be null";
    
    public static final String LOAN_MANAGER_DOESNOT_EXSIST="Manager doenot exist.Please give a valid manager email";

    public static final String UPDATE_ERROR_CUSTOMER="Error While updating customer details. Please try again later";
    
    public static final String UPDATE_ERROR_REALTOR="Error While updating realtor details. Please try again later";
    
    public static final String UPDATE_ERROR_USER="Error While updating user details. Please try again later";
    
    public static final String REGISTRATION_USER_EXSIST="User exist.Please register with different emailID";

    public static final String APPLY_LOAN_ERROR="Error while applying loan.Please try again later";
}
