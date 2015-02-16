
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
 *         &lt;element name="RetrieveClosingCostTemplateFeeListResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "retrieveClosingCostTemplateFeeListResult"
})
@XmlRootElement(name = "RetrieveClosingCostTemplateFeeListResponse")
public class RetrieveClosingCostTemplateFeeListResponse {

    @XmlElement(name = "RetrieveClosingCostTemplateFeeListResult")
    protected String retrieveClosingCostTemplateFeeListResult;

    /**
     * Gets the value of the retrieveClosingCostTemplateFeeListResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieveClosingCostTemplateFeeListResult() {
        return retrieveClosingCostTemplateFeeListResult;
    }

    /**
     * Sets the value of the retrieveClosingCostTemplateFeeListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieveClosingCostTemplateFeeListResult(String value) {
        this.retrieveClosingCostTemplateFeeListResult = value;
    }

}
