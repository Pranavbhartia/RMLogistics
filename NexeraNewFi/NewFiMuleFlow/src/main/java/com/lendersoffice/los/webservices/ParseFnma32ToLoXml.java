
package com.lendersoffice.los.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="fnma32Content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sXmlQuery" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "fnma32Content",
    "sXmlQuery"
})
@XmlRootElement(name = "ParseFnma32ToLoXml")
public class ParseFnma32ToLoXml {

    protected String sTicket;
    protected String fnma32Content;
    protected String sXmlQuery;

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
     * Gets the value of the fnma32Content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFnma32Content() {
        return fnma32Content;
    }

    /**
     * Sets the value of the fnma32Content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFnma32Content(String value) {
        this.fnma32Content = value;
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

}
