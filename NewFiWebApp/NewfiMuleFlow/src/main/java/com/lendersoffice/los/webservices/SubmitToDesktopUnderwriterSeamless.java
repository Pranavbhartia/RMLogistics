
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
 *         &lt;element name="duUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institutionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="craProvider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="craLoginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="craPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isCopyLiabilities" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "duUserName",
    "duPassword",
    "institutionId",
    "craProvider",
    "craLoginName",
    "craPassword",
    "isCopyLiabilities"
})
@XmlRootElement(name = "SubmitToDesktopUnderwriterSeamless")
public class SubmitToDesktopUnderwriterSeamless {

    protected String sTicket;
    @XmlElement(name = "sLNm")
    protected String slNm;
    protected String duUserName;
    protected String duPassword;
    protected String institutionId;
    protected String craProvider;
    protected String craLoginName;
    protected String craPassword;
    protected boolean isCopyLiabilities;

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
     * Gets the value of the institutionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitutionId() {
        return institutionId;
    }

    /**
     * Sets the value of the institutionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitutionId(String value) {
        this.institutionId = value;
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
     * Gets the value of the craLoginName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCraLoginName() {
        return craLoginName;
    }

    /**
     * Sets the value of the craLoginName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCraLoginName(String value) {
        this.craLoginName = value;
    }

    /**
     * Gets the value of the craPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCraPassword() {
        return craPassword;
    }

    /**
     * Sets the value of the craPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCraPassword(String value) {
        this.craPassword = value;
    }

    /**
     * Gets the value of the isCopyLiabilities property.
     * 
     */
    public boolean isIsCopyLiabilities() {
        return isCopyLiabilities;
    }

    /**
     * Sets the value of the isCopyLiabilities property.
     * 
     */
    public void setIsCopyLiabilities(boolean value) {
        this.isCopyLiabilities = value;
    }

}
