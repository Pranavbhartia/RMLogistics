
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
 *         &lt;element name="GetActiveDirectoryUserAuthTicketResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getActiveDirectoryUserAuthTicketResult"
})
@XmlRootElement(name = "GetActiveDirectoryUserAuthTicketResponse")
public class GetActiveDirectoryUserAuthTicketResponse {

    @XmlElement(name = "GetActiveDirectoryUserAuthTicketResult")
    protected String getActiveDirectoryUserAuthTicketResult;

    /**
     * Gets the value of the getActiveDirectoryUserAuthTicketResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetActiveDirectoryUserAuthTicketResult() {
        return getActiveDirectoryUserAuthTicketResult;
    }

    /**
     * Sets the value of the getActiveDirectoryUserAuthTicketResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetActiveDirectoryUserAuthTicketResult(String value) {
        this.getActiveDirectoryUserAuthTicketResult = value;
    }

}
