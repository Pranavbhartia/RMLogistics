
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
 *         &lt;element name="sSecretKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brokerId" type="{http://microsoft.com/wsdl/types/}guid"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "sSecretKey",
    "brokerId",
    "userName"
})
@XmlRootElement(name = "GetBrokerPartnerAuthTicket")
public class GetBrokerPartnerAuthTicket {

    protected String sSecretKey;
    @XmlElement(required = true)
    protected String brokerId;
    protected String userName;

    /**
     * Gets the value of the sSecretKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSecretKey() {
        return sSecretKey;
    }

    /**
     * Sets the value of the sSecretKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSecretKey(String value) {
        this.sSecretKey = value;
    }

    /**
     * Gets the value of the brokerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrokerId() {
        return brokerId;
    }

    /**
     * Sets the value of the brokerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrokerId(String value) {
        this.brokerId = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

}
