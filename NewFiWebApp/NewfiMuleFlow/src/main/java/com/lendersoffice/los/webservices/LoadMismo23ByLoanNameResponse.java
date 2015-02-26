
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
 *         &lt;element name="LoadMismo23ByLoanNameResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "loadMismo23ByLoanNameResult"
})
@XmlRootElement(name = "LoadMismo23ByLoanNameResponse")
public class LoadMismo23ByLoanNameResponse {

    @XmlElement(name = "LoadMismo23ByLoanNameResult")
    protected String loadMismo23ByLoanNameResult;

    /**
     * Gets the value of the loadMismo23ByLoanNameResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadMismo23ByLoanNameResult() {
        return loadMismo23ByLoanNameResult;
    }

    /**
     * Sets the value of the loadMismo23ByLoanNameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadMismo23ByLoanNameResult(String value) {
        this.loadMismo23ByLoanNameResult = value;
    }

}
