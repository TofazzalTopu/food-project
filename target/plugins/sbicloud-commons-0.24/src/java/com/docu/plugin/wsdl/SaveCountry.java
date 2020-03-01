
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveCountry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveCountry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countryDto" type="{http://com.docu}CountryDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveCountry", propOrder = {
    "countryDto"
})
public class SaveCountry {

    protected CountryDTO countryDto;

    /**
     * Gets the value of the countryDto property.
     * 
     * @return
     *     possible object is
     *     {@link CountryDTO }
     *     
     */
    public CountryDTO getCountryDto() {
        return countryDto;
    }

    /**
     * Sets the value of the countryDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryDTO }
     *     
     */
    public void setCountryDto(CountryDTO value) {
        this.countryDto = value;
    }

}
