/**
 * 
 */
package com.newfi.nexera.vo;

/**
 * @author Utsav
 *
 */
public class ResponseVO
{
    private String status;

    private String responseCode;

    private String responseMessage;

    private String responseTime;

    private String errorCode;

    private String errorDescription;


    /**
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }


    /**
     * @param status the status to set
     */
    public void setStatus( String status )
    {
        this.status = status;
    }


    /**
     * @return the responseCode
     */
    public String getResponseCode()
    {
        return responseCode;
    }


    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode( String responseCode )
    {
        this.responseCode = responseCode;
    }


    /**
     * @return the responseMessage
     */
    public String getResponseMessage()
    {
        return responseMessage;
    }


    /**
     * @param responseMessage the responseMessage to set
     */
    public void setResponseMessage( String responseMessage )
    {
        this.responseMessage = responseMessage;
    }


    /**
     * @return the responseTime
     */
    public String getResponseTime()
    {
        return responseTime;
    }


    /**
     * @param responseTime the responseTime to set
     */
    public void setResponseTime( String responseTime )
    {
        this.responseTime = responseTime;
    }


    /**
     * @return the errorCode
     */
    public String getErrorCode()
    {
        return errorCode;
    }


    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode( String errorCode )
    {
        this.errorCode = errorCode;
    }


    /**
     * @return the errorDescription
     */
    public String getErrorDescription()
    {
        return errorDescription;
    }


    /**
     * @param errorDescription the errorDescription to set
     */
    public void setErrorDescription( String errorDescription )
    {
        this.errorDescription = errorDescription;
    }


}
