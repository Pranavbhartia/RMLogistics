/**
 * 
 */
package com.newfi.nexera.vo;

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

    private String sDataContent;

    private String documentType;

    private String notes;

    private Integer format;

    private Map<String, String> sXmlQueryMap;

    private String IlpTemplateId;

    private Float requestedRate;

    private Float requestedFee;


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
     * @return the requestedRate
     */
    public Float getRequestedRate()
    {
        return requestedRate;
    }


    /**
     * @param requestedRate the requestedRate to set
     */
    public void setRequestedRate( Float requestedRate )
    {
        this.requestedRate = requestedRate;
    }


    /**
     * @return the requestedFee
     */
    public Float getRequestedFee()
    {
        return requestedFee;
    }


    /**
     * @param requestedFee the requestedFee to set
     */
    public void setRequestedFee( Float requestedFee )
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

}
