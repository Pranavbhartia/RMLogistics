
package com.lendersoffice.los.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AuthTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sLNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DocumentClassification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="base64PdfContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "authTicket",
    "slNm",
    "documentClassification",
    "base64PdfContent"
})
@XmlRootElement(name = "RecievePdf")
public class RecievePdf {

    @XmlElement(name = "AuthTicket")
    protected String authTicket;
    @XmlElement(name = "sLNm")
    protected String slNm;
    @XmlElement(name = "DocumentClassification")
    protected String documentClassification;
    protected String base64PdfContent;

    /**
     * Gets the value of the authTicket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthTicket() {
        return authTicket;
    }

    /**
     * Sets the value of the authTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthTicket(String value) {
        this.authTicket = value;
    }

    /**
     * Gets the value of the slNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSLNm() {
        return slNm;
    }

    /**
     * Sets the value of the slNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSLNm(String value) {
        this.slNm = value;
    }

    /**
     * Gets the value of the documentClassification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentClassification() {
        return documentClassification;
    }

    /**
     * Sets the value of the documentClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentClassification(String value) {
        this.documentClassification = value;
    }

    /**
     * Gets the value of the base64PdfContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBase64PdfContent() {
        return base64PdfContent;
    }

    /**
     * Sets the value of the base64PdfContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBase64PdfContent(String value) {
        this.base64PdfContent = value;
    }

}
