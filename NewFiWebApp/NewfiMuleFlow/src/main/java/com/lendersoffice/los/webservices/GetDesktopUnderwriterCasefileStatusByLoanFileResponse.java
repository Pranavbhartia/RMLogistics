
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
 *         &lt;element name="GetDesktopUnderwriterCasefileStatusByLoanFileResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getDesktopUnderwriterCasefileStatusByLoanFileResult"
})
@XmlRootElement(name = "GetDesktopUnderwriterCasefileStatusByLoanFileResponse")
public class GetDesktopUnderwriterCasefileStatusByLoanFileResponse {

    @XmlElement(name = "GetDesktopUnderwriterCasefileStatusByLoanFileResult")
    protected String getDesktopUnderwriterCasefileStatusByLoanFileResult;

    /**
     * Gets the value of the getDesktopUnderwriterCasefileStatusByLoanFileResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetDesktopUnderwriterCasefileStatusByLoanFileResult() {
        return getDesktopUnderwriterCasefileStatusByLoanFileResult;
    }

    /**
     * Sets the value of the getDesktopUnderwriterCasefileStatusByLoanFileResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetDesktopUnderwriterCasefileStatusByLoanFileResult(String value) {
        this.getDesktopUnderwriterCasefileStatusByLoanFileResult = value;
    }

}
