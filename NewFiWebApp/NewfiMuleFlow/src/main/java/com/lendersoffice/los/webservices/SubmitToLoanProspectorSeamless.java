
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
 *         &lt;element name="sTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sLNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lpUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lpPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="craProvider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="borrowerCreditInfoXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "sTicket",
    "slNm",
    "lpUserId",
    "lpPassword",
    "creditMethod",
    "craProvider",
    "borrowerCreditInfoXml"
})
@XmlRootElement(name = "SubmitToLoanProspectorSeamless")
public class SubmitToLoanProspectorSeamless {

    protected String sTicket;
    @XmlElement(name = "sLNm")
    protected String slNm;
    protected String lpUserId;
    protected String lpPassword;
    protected String creditMethod;
    protected String craProvider;
    protected String borrowerCreditInfoXml;

    /**
     * Gets the value of the sTicket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTicket() {
        return sTicket;
    }

    /**
     * Sets the value of the sTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTicket(String value) {
        this.sTicket = value;
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
     * Gets the value of the lpUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpUserId() {
        return lpUserId;
    }

    /**
     * Sets the value of the lpUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpUserId(String value) {
        this.lpUserId = value;
    }

    /**
     * Gets the value of the lpPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpPassword() {
        return lpPassword;
    }

    /**
     * Sets the value of the lpPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpPassword(String value) {
        this.lpPassword = value;
    }

    /**
     * Gets the value of the creditMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditMethod() {
        return creditMethod;
    }

    /**
     * Sets the value of the creditMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditMethod(String value) {
        this.creditMethod = value;
    }

    /**
     * Gets the value of the craProvider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCraProvider() {
        return craProvider;
    }

    /**
     * Sets the value of the craProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCraProvider(String value) {
        this.craProvider = value;
    }

    /**
     * Gets the value of the borrowerCreditInfoXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorrowerCreditInfoXml() {
        return borrowerCreditInfoXml;
    }

    /**
     * Sets the value of the borrowerCreditInfoXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorrowerCreditInfoXml(String value) {
        this.borrowerCreditInfoXml = value;
    }

}
