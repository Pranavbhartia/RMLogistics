
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
 *         &lt;element name="GetViewDesktopUrlResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getViewDesktopUrlResult"
})
@XmlRootElement(name = "GetViewDesktopUrlResponse")
public class GetViewDesktopUrlResponse {

    @XmlElement(name = "GetViewDesktopUrlResult")
    protected String getViewDesktopUrlResult;

    /**
     * Gets the value of the getViewDesktopUrlResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetViewDesktopUrlResult() {
        return getViewDesktopUrlResult;
    }

    /**
     * Sets the value of the getViewDesktopUrlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetViewDesktopUrlResult(String value) {
        this.getViewDesktopUrlResult = value;
    }

}
