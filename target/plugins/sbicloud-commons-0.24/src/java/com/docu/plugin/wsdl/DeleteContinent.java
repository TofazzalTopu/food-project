
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteContinent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteContinent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="continentDto" type="{http://com.docu}ContinentDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteContinent", propOrder = {
    "continentDto"
})
public class DeleteContinent {

    protected ContinentDTO continentDto;

    /**
     * Gets the value of the continentDto property.
     * 
     * @return
     *     possible object is
     *     {@link ContinentDTO }
     *     
     */
    public ContinentDTO getContinentDto() {
        return continentDto;
    }

    /**
     * Sets the value of the continentDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContinentDTO }
     *     
     */
    public void setContinentDto(ContinentDTO value) {
        this.continentDto = value;
    }

}
