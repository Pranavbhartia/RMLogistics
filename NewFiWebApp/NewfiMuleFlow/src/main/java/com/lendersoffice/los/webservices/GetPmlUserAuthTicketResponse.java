
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
 *         &lt;element name="GetPmlUserAuthTicketResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPmlUserAuthTicketResult"
})
@XmlRootElement(name = "GetPmlUserAuthTicketResponse")
public class GetPmlUserAuthTicketResponse {

    @XmlElement(name = "GetPmlUserAuthTicketResult")
    protected String getPmlUserAuthTicketResult;

    /**
     * Gets the value of the getPmlUserAuthTicketResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPmlUserAuthTicketResult() {
        return getPmlUserAuthTicketResult;
    }

    /**
     * Sets the value of the getPmlUserAuthTicketResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPmlUserAuthTicketResult(String value) {
        this.getPmlUserAuthTicketResult = value;
    }

}
