
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
 *         &lt;element name="IsLoanExistedResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "isLoanExistedResult"
})
@XmlRootElement(name = "IsLoanExistedResponse")
public class IsLoanExistedResponse {

    @XmlElement(name = "IsLoanExistedResult")
    protected boolean isLoanExistedResult;

    /**
     * Gets the value of the isLoanExistedResult property.
     * 
     */
    public boolean isIsLoanExistedResult() {
        return isLoanExistedResult;
    }

    /**
     * Sets the value of the isLoanExistedResult property.
     * 
     */
    public void setIsLoanExistedResult(boolean value) {
        this.isLoanExistedResult = value;
    }

}
