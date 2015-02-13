/**
 * 
 */
package com.newfi.nexera.vo;

/**
 * @author Utsav
 *
 */
public class AuthenticateVO
{
    private String userName;

    private String passWord;

    private String customerCode;


    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }


    /**
     * @param userName the userName to set
     */
    public void setUserName( String userName )
    {
        this.userName = userName;
    }


    /**
     * @return the passWord
     */
    public String getPassWord()
    {
        return passWord;
    }


    /**
     * @param passWord the passWord to set
     */
    public void setPassWord( String passWord )
    {
        this.passWord = passWord;
    }


    /**
     * @return the customerCode
     */
    public String getCustomerCode()
    {
        return customerCode;
    }


    /**
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode( String customerCode )
    {
        this.customerCode = customerCode;
    }
}
