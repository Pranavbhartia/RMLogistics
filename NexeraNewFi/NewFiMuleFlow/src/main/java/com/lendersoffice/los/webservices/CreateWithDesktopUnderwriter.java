
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
 *         &lt;element name="doUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sDuCaseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isImportCredit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "doUserName",
    "doPassword",
    "sDuCaseId",
    "isImportCredit"
})
@XmlRootElement(name = "CreateWithDesktopUnderwriter")
public class CreateWithDesktopUnderwriter {

    protected String sTicket;
    protected String doUserName;
    protected String doPassword;
    protected String sDuCaseId;
    protected boolean isImportCredit;

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
     * Gets the value of the doUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoUserName() {
        return doUserName;
    }

    /**
     * Sets the value of the doUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoUserName(String value) {
        this.doUserName = value;
    }

    /**
     * Gets the value of the doPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoPassword() {
        return doPassword;
    }

    /**
     * Sets the value of the doPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoPassword(String value) {
        this.doPassword = value;
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
     * Gets the value of the isImportCredit property.
     * 
     */
    public boolean isIsImportCredit() {
        return isImportCredit;
    }

    /**
     * Sets the value of the isImportCredit property.
     * 
     */
    public void setIsImportCredit(boolean value) {
        this.isImportCredit = value;
    }

}
