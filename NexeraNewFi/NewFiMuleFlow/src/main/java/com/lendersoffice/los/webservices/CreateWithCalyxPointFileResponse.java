
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
 *         &lt;element name="CreateWithCalyxPointFileResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "createWithCalyxPointFileResult"
})
@XmlRootElement(name = "CreateWithCalyxPointFileResponse")
public class CreateWithCalyxPointFileResponse {

    @XmlElement(name = "CreateWithCalyxPointFileResult")
    protected String createWithCalyxPointFileResult;

    /**
     * Gets the value of the createWithCalyxPointFileResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateWithCalyxPointFileResult() {
        return createWithCalyxPointFileResult;
    }

    /**
     * Sets the value of the createWithCalyxPointFileResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateWithCalyxPointFileResult(String value) {
        this.createWithCalyxPointFileResult = value;
    }

}
