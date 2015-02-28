
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
 *         &lt;element name="RetrieveLoanActivityListResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "retrieveLoanActivityListResult"
})
@XmlRootElement(name = "RetrieveLoanActivityListResponse")
public class RetrieveLoanActivityListResponse {

    @XmlElement(name = "RetrieveLoanActivityListResult")
    protected String retrieveLoanActivityListResult;

    /**
     * Gets the value of the retrieveLoanActivityListResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieveLoanActivityListResult() {
        return retrieveLoanActivityListResult;
    }

    /**
     * Sets the value of the retrieveLoanActivityListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieveLoanActivityListResult(String value) {
        this.retrieveLoanActivityListResult = value;
    }

}
