
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
 *         &lt;element name="SaveMismo23Result" type="{http://microsoft.com/wsdl/types/}guid"/>
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
    "saveMismo23Result"
})
@XmlRootElement(name = "SaveMismo23Response")
public class SaveMismo23Response {

    @XmlElement(name = "SaveMismo23Result", required = true)
    protected String saveMismo23Result;

    /**
     * Gets the value of the saveMismo23Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaveMismo23Result() {
        return saveMismo23Result;
    }

    /**
     * Sets the value of the saveMismo23Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaveMismo23Result(String value) {
        this.saveMismo23Result = value;
    }

}
