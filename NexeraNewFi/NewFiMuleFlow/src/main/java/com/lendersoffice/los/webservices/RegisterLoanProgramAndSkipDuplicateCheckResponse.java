
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
 *         &lt;element name="RegisterLoanProgramAndSkipDuplicateCheckResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "registerLoanProgramAndSkipDuplicateCheckResult"
})
@XmlRootElement(name = "RegisterLoanProgramAndSkipDuplicateCheckResponse")
public class RegisterLoanProgramAndSkipDuplicateCheckResponse {

    @XmlElement(name = "RegisterLoanProgramAndSkipDuplicateCheckResult")
    protected String registerLoanProgramAndSkipDuplicateCheckResult;

    /**
     * Gets the value of the registerLoanProgramAndSkipDuplicateCheckResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterLoanProgramAndSkipDuplicateCheckResult() {
        return registerLoanProgramAndSkipDuplicateCheckResult;
    }

    /**
     * Sets the value of the registerLoanProgramAndSkipDuplicateCheckResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterLoanProgramAndSkipDuplicateCheckResult(String value) {
        this.registerLoanProgramAndSkipDuplicateCheckResult = value;
    }

}
