
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
 *         &lt;element name="ParseFnma32ToLoXmlResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "parseFnma32ToLoXmlResult"
})
@XmlRootElement(name = "ParseFnma32ToLoXmlResponse")
public class ParseFnma32ToLoXmlResponse {

    @XmlElement(name = "ParseFnma32ToLoXmlResult")
    protected String parseFnma32ToLoXmlResult;

    /**
     * Gets the value of the parseFnma32ToLoXmlResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParseFnma32ToLoXmlResult() {
        return parseFnma32ToLoXmlResult;
    }

    /**
     * Sets the value of the parseFnma32ToLoXmlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParseFnma32ToLoXmlResult(String value) {
        this.parseFnma32ToLoXmlResult = value;
    }

}
