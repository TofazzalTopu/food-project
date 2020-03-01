
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveCountryHeadOffice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveCountryHeadOffice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countryHeadOfficeDto" type="{http://com.docu}CountryHeadOfficeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveCountryHeadOffice", propOrder = {
    "countryHeadOfficeDto"
})
public class SaveCountryHeadOffice {

    protected CountryHeadOfficeDTO countryHeadOfficeDto;

    /**
     * Gets the value of the countryHeadOfficeDto property.
     * 
     * @return
     *     possible object is
     *     {@link CountryHeadOfficeDTO }
     *     
     */
    public CountryHeadOfficeDTO getCountryHeadOfficeDto() {
        return countryHeadOfficeDto;
    }

    /**
     * Sets the value of the countryHeadOfficeDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryHeadOfficeDTO }
     *     
     */
    public void setCountryHeadOfficeDto(CountryHeadOfficeDTO value) {
        this.countryHeadOfficeDto = value;
    }

}
