
package com.lendersoffice.los.webservices;

import java.math.BigDecimal;
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
 *         &lt;element name="lLpTemplateId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestedRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="requestedFee" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
    "lLpTemplateId",
    "requestedRate",
    "requestedFee"
})
@XmlRootElement(name = "RegisterLoanProgram")
public class RegisterLoanProgram {

    protected String sTicket;
    @XmlElement(name = "sLNm")
    protected String slNm;
    protected String lLpTemplateId;
    @XmlElement(required = true)
    protected BigDecimal requestedRate;
    @XmlElement(required = true)
    protected BigDecimal requestedFee;

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
     * Gets the value of the lLpTemplateId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLLpTemplateId() {
        return lLpTemplateId;
    }

    /**
     * Sets the value of the lLpTemplateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLLpTemplateId(String value) {
        this.lLpTemplateId = value;
    }

    /**
     * Gets the value of the requestedRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRequestedRate() {
        return requestedRate;
    }

    /**
     * Sets the value of the requestedRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRequestedRate(BigDecimal value) {
        this.requestedRate = value;
    }

    /**
     * Gets the value of the requestedFee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRequestedFee() {
        return requestedFee;
    }

    /**
     * Sets the value of the requestedFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRequestedFee(BigDecimal value) {
        this.requestedFee = value;
    }

}
