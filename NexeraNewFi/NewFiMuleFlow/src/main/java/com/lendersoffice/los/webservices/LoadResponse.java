
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
 *         &lt;element name="LoadResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "loadResult"
})
@XmlRootElement(name = "LoadResponse")
public class LoadResponse {

    @XmlElement(name = "LoadResult")
    protected String loadResult;

    /**
     * Gets the value of the loadResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadResult() {
        return loadResult;
    }

    /**
     * Sets the value of the loadResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadResult(String value) {
        this.loadResult = value;
    }

}
