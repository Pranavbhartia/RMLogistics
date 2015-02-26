
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
 *         &lt;element name="CreateWithDesktopUnderwriterResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "createWithDesktopUnderwriterResult"
})
@XmlRootElement(name = "CreateWithDesktopUnderwriterResponse")
public class CreateWithDesktopUnderwriterResponse {

    @XmlElement(name = "CreateWithDesktopUnderwriterResult")
    protected String createWithDesktopUnderwriterResult;

    /**
     * Gets the value of the createWithDesktopUnderwriterResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateWithDesktopUnderwriterResult() {
        return createWithDesktopUnderwriterResult;
    }

    /**
     * Sets the value of the createWithDesktopUnderwriterResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateWithDesktopUnderwriterResult(String value) {
        this.createWithDesktopUnderwriterResult = value;
    }

}
