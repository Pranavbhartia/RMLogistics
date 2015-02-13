
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
 *         &lt;element name="LockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "lockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult"
})
@XmlRootElement(name = "LockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResponse")
public class LockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResponse {

    @XmlElement(name = "LockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult")
    protected String lockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult;

    /**
     * Gets the value of the lockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult() {
        return lockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult;
    }

    /**
     * Sets the value of the lockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult(String value) {
        this.lockLoanProgramWithRateOptionIdAndSkipDuplicateCheckResult = value;
    }

}
