
package com.lendersoffice.los.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="sDuCaseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isDo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "sDuCaseId",
    "duUserName",
    "duPassword",
    "isDo"
})
@XmlRootElement(name = "GetDesktopUnderwriterCasefileStatus")
public class GetDesktopUnderwriterCasefileStatus {

    protected String sTicket;
    protected String sDuCaseId;
    protected String duUserName;
    protected String duPassword;
    protected boolean isDo;

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
     * Gets the value of the sDuCaseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSDuCaseId() {
        return sDuCaseId;
    }

    /**
     * Sets the value of the sDuCaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSDuCaseId(String value) {
        this.sDuCaseId = value;
    }

    /**
     * Gets the value of the duUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuUserName() {
        return duUserName;
    }

    /**
     * Sets the value of the duUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuUserName(String value) {
        this.duUserName = value;
    }

    /**
     * Gets the value of the duPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuPassword() {
        return duPassword;
    }

    /**
     * Sets the value of the duPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuPassword(String value) {
        this.duPassword = value;
    }

    /**
     * Gets the value of the isDo property.
     * 
     */
    public boolean isIsDo() {
        return isDo;
    }

    /**
     * Sets the value of the isDo property.
     * 
     */
    public void setIsDo(boolean value) {
        this.isDo = value;
    }

}
