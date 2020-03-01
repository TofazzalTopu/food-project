
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteCountryLevelEntityOffice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteCountryLevelEntityOffice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countryLevelEntityOfficeDto" type="{http://com.docu}CountryLevelEntityOfficeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteCountryLevelEntityOffice", propOrder = {
    "countryLevelEntityOfficeDto"
})
public class DeleteCountryLevelEntityOffice {

    protected CountryLevelEntityOfficeDTO countryLevelEntityOfficeDto;

    /**
     * Gets the value of the countryLevelEntityOfficeDto property.
     * 
     * @return
     *     possible object is
     *     {@link CountryLevelEntityOfficeDTO }
     *     
     */
    public CountryLevelEntityOfficeDTO getCountryLevelEntityOfficeDto() {
        return countryLevelEntityOfficeDto;
    }

    /**
     * Sets the value of the countryLevelEntityOfficeDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryLevelEntityOfficeDTO }
     *     
     */
    public void setCountryLevelEntityOfficeDto(CountryLevelEntityOfficeDTO value) {
        this.countryLevelEntityOfficeDto = value;
    }

}
