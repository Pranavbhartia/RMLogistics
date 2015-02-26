
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
 *         &lt;element name="SubmitToDesktopUnderwriterSeamlessResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "submitToDesktopUnderwriterSeamlessResult"
})
@XmlRootElement(name = "SubmitToDesktopUnderwriterSeamlessResponse")
public class SubmitToDesktopUnderwriterSeamlessResponse {

    @XmlElement(name = "SubmitToDesktopUnderwriterSeamlessResult")
    protected String submitToDesktopUnderwriterSeamlessResult;

    /**
     * Gets the value of the submitToDesktopUnderwriterSeamlessResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmitToDesktopUnderwriterSeamlessResult() {
        return submitToDesktopUnderwriterSeamlessResult;
    }

    /**
     * Sets the value of the submitToDesktopUnderwriterSeamlessResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmitToDesktopUnderwriterSeamlessResult(String value) {
        this.submitToDesktopUnderwriterSeamlessResult = value;
    }

}
