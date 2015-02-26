
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
 *         &lt;element name="sTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sLNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sXmlQuery" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="format" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "sTicket",
    "slNm",
    "sXmlQuery",
    "format"
})
@XmlRootElement(name = "Load")
public class Load {

    protected String sTicket;
    @XmlElement(name = "sLNm")
    protected String slNm;
    protected String sXmlQuery;
    protected int format;

    /**
     * Gets the value of the sTicket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTicket() {
        return sTicket;
    }

    /**
     * Sets the value of the sTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTicket(String value) {
        this.sTicket = value;
    }

    /**
     * Gets the value of the slNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSLNm() {
        return slNm;
    }

    /**
     * Sets the value of the slNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSLNm(String value) {
        this.slNm = value;
    }

    /**
     * Gets the value of the sXmlQuery property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSXmlQuery() {
        return sXmlQuery;
    }

    /**
     * Sets the value of the sXmlQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSXmlQuery(String value) {
        this.sXmlQuery = value;
    }

    /**
     * Gets the value of the format property.
     * 
     */
    public int getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     */
    public void setFormat(int value) {
        this.format = value;
    }

}
