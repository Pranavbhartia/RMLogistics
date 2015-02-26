
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
 *         &lt;element name="ImportFromFannieMaeResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "importFromFannieMaeResult"
})
@XmlRootElement(name = "ImportFromFannieMaeResponse")
public class ImportFromFannieMaeResponse {

    @XmlElement(name = "ImportFromFannieMaeResult")
    protected String importFromFannieMaeResult;

    /**
     * Gets the value of the importFromFannieMaeResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportFromFannieMaeResult() {
        return importFromFannieMaeResult;
    }

    /**
     * Sets the value of the importFromFannieMaeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportFromFannieMaeResult(String value) {
        this.importFromFannieMaeResult = value;
    }

}
