
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
 *         &lt;element name="ListClosingCostTemplateNameResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "listClosingCostTemplateNameResult"
})
@XmlRootElement(name = "ListClosingCostTemplateNameResponse")
public class ListClosingCostTemplateNameResponse {

    @XmlElement(name = "ListClosingCostTemplateNameResult")
    protected String listClosingCostTemplateNameResult;

    /**
     * Gets the value of the listClosingCostTemplateNameResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListClosingCostTemplateNameResult() {
        return listClosingCostTemplateNameResult;
    }

    /**
     * Sets the value of the listClosingCostTemplateNameResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListClosingCostTemplateNameResult(String value) {
        this.listClosingCostTemplateNameResult = value;
    }

}
