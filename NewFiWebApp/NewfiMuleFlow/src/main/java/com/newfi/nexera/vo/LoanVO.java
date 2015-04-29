/**
 * 
 */
package com.newfi.nexera.vo;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author Utsav
 *
 */
public class LoanVO
{
    private String sTicket;

    private Map<String, String> sXmlDataMap;

    private String sTemplateName;

    private String sLoanNumber;

    private Map<String, String> sDataContentMap;

    private String sDataContent;

    private String documentType;

    private String condition;

    private String notes;

    private Integer format;

    private Map<String, String> sXmlQueryMap;

    private String IlpTemplateId;

    private BigDecimal requestedRate;

    private BigDecimal requestedFee;

    private String docId;

    private String appCode;


    /**
     * @return the sTicket
     */
    public String getsTicket()
    {
        return sTicket;
    }


    /**
     * @param sTicket the sTicket to set
     */
    public void setsTicket( String sTicket )
    {
        this.sTicket = sTicket;
    }


    /**
     * @return the sTemplateName
     */
    public String getsTemplateName()
    {
        return sTemplateName;
    }


    /**
     * @param sTemplateName the sTemplateName to set
     */
    public void setsTemplateName( String sTemplateName )
    {
        this.sTemplateName = sTemplateName;
    }


    /**
     * @return the sLoanNumber
     */
    public String getsLoanNumber()
    {
        return sLoanNumber;
    }


    /**
     * @param sLoanNumber the sLoanNumber to set
     */
    public void setsLoanNumber( String sLoanNumber )
    {
        this.sLoanNumber = sLoanNumber;
    }


    /**
     * @return the ilpTemplateId
     */
    public String getIlpTemplateId()
    {
        return IlpTemplateId;
    }


    /**
     * @param ilpTemplateId the ilpTemplateId to set
     */
    public void setIlpTemplateId( String ilpTemplateId )
    {
        IlpTemplateId = ilpTemplateId;
    }


    /**
     * @return the documentType
     */
    public String getDocumentType()
    {
        return documentType;
    }


    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType( String documentType )
    {
        this.documentType = documentType;
    }


    /**
     * @return the notes
     */
    public String getNotes()
    {
        return notes;
    }


    /**
     * @param notes the notes to set
     */
    public void setNotes( String notes )
    {
        this.notes = notes;
    }


    /**
     * @return the format
     */
    public Integer getFormat()
    {
        return format;
    }


    /**
     * @param format the format to set
     */
    public void setFormat( Integer format )
    {
        this.format = format;
    }


    /**
     * @return the requestedFee
     */
    public BigDecimal getRequestedFee()
    {
        return requestedFee;
    }


    /**
     * @param requestedFee the requestedFee to set
     */
    public void setRequestedFee( BigDecimal requestedFee )
    {
        this.requestedFee = requestedFee;
    }


    /**
     * @return the sXmlDataMap
     */
    public Map<String, String> getsXmlDataMap()
    {
        return sXmlDataMap;
    }


    /**
     * @param sXmlDataMap the sXmlDataMap to set
     */
    public void setsXmlDataMap( Map<String, String> sXmlDataMap )
    {
        this.sXmlDataMap = sXmlDataMap;
    }


    /**
     * @return the sXmlQueryMap
     */
    public Map<String, String> getsXmlQueryMap()
    {
        return sXmlQueryMap;
    }


    /**
     * @param sXmlQueryMap the sXmlQueryMap to set
     */
    public void setsXmlQueryMap( Map<String, String> sXmlQueryMap )
    {
        this.sXmlQueryMap = sXmlQueryMap;
    }


    /**
     * @return the sDataContentMap
     */
    public Map<String, String> getsDataContentMap()
    {
        return sDataContentMap;
    }


    /**
     * @param sDataContentMap the sDataContentMap to set
     */
    public void setsDataContentMap( Map<String, String> sDataContentMap )
    {
        this.sDataContentMap = sDataContentMap;
    }


    /**
     * @return the sDataContent
     */
    public String getsDataContent()
    {
        return sDataContent;
    }


    /**
     * @param sDataContent the sDataContent to set
     */
    public void setsDataContent( String sDataContent )
    {
        this.sDataContent = sDataContent;
    }


    /**
     * @return the requestedRate
     */
    public BigDecimal getRequestedRate()
    {
        return requestedRate;
    }


    /**
     * @param requestedRate the requestedRate to set
     */
    public void setRequestedRate( BigDecimal requestedRate )
    {
        this.requestedRate = requestedRate;
    }


    /**
     * @return the docId
     */
    public String getDocId()
    {
        return docId;
    }


    /**
     * @param docId the docId to set
     */
    public void setDocId( String docId )
    {
        this.docId = docId;
    }


    /**
     * @return the condition
     */
    public String getCondition()
    {
        return condition;
    }


    /**
     * @param condition the condition to set
     */
    public void setCondition( String condition )
    {
        this.condition = condition;
    }


    /**
     * @return the appCode
     */
    public String getAppCode()
    {
        return appCode;
    }


    /**
     * @param appCode the appCode to set
     */
    public void setAppCode( String appCode )
    {
        this.appCode = appCode;
    }

}
