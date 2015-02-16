
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
 *         &lt;element name="ListModifiedLoansResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "listModifiedLoansResult"
})
@XmlRootElement(name = "ListModifiedLoansResponse")
public class ListModifiedLoansResponse {

    @XmlElement(name = "ListModifiedLoansResult")
    protected String listModifiedLoansResult;

    /**
     * Gets the value of the listModifiedLoansResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListModifiedLoansResult() {
        return listModifiedLoansResult;
    }

    /**
     * Sets the value of the listModifiedLoansResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListModifiedLoansResult(String value) {
        this.listModifiedLoansResult = value;
    }

}
