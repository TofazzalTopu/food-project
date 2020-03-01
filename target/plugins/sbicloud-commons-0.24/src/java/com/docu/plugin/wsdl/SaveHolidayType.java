
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveHolidayType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveHolidayType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="holidayTypeDto" type="{http://com.docu}HolidayTypeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveHolidayType", propOrder = {
    "holidayTypeDto"
})
public class SaveHolidayType {

    protected HolidayTypeDTO holidayTypeDto;

    /**
     * Gets the value of the holidayTypeDto property.
     * 
     * @return
     *     possible object is
     *     {@link HolidayTypeDTO }
     *     
     */
    public HolidayTypeDTO getHolidayTypeDto() {
        return holidayTypeDto;
    }

    /**
     * Sets the value of the holidayTypeDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link HolidayTypeDTO }
     *     
     */
    public void setHolidayTypeDto(HolidayTypeDTO value) {
        this.holidayTypeDto = value;
    }

}
