
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
 *         &lt;element name="RetrieveUsersAssignedToLoanResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "retrieveUsersAssignedToLoanResult"
})
@XmlRootElement(name = "RetrieveUsersAssignedToLoanResponse")
public class RetrieveUsersAssignedToLoanResponse {

    @XmlElement(name = "RetrieveUsersAssignedToLoanResult")
    protected String retrieveUsersAssignedToLoanResult;

    /**
     * Gets the value of the retrieveUsersAssignedToLoanResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieveUsersAssignedToLoanResult() {
        return retrieveUsersAssignedToLoanResult;
    }

    /**
     * Sets the value of the retrieveUsersAssignedToLoanResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieveUsersAssignedToLoanResult(String value) {
        this.retrieveUsersAssignedToLoanResult = value;
    }

}
