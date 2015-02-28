
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
 *         &lt;element name="RegisterLoanProgramWithRateOptionIdResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "registerLoanProgramWithRateOptionIdResult"
})
@XmlRootElement(name = "RegisterLoanProgramWithRateOptionIdResponse")
public class RegisterLoanProgramWithRateOptionIdResponse {

    @XmlElement(name = "RegisterLoanProgramWithRateOptionIdResult")
    protected String registerLoanProgramWithRateOptionIdResult;

    /**
     * Gets the value of the registerLoanProgramWithRateOptionIdResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterLoanProgramWithRateOptionIdResult() {
        return registerLoanProgramWithRateOptionIdResult;
    }

    /**
     * Sets the value of the registerLoanProgramWithRateOptionIdResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterLoanProgramWithRateOptionIdResult(String value) {
        this.registerLoanProgramWithRateOptionIdResult = value;
    }

}
