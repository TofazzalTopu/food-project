
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteHoliday complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteHoliday">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="holidayDto" type="{http://com.docu}HolidayDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteHoliday", propOrder = {
    "holidayDto"
})
public class DeleteHoliday {

    protected HolidayDTO holidayDto;

    /**
     * Gets the value of the holidayDto property.
     * 
     * @return
     *     possible object is
     *     {@link HolidayDTO }
     *     
     */
    public HolidayDTO getHolidayDto() {
        return holidayDto;
    }

    /**
     * Sets the value of the holidayDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link HolidayDTO }
     *     
     */
    public void setHolidayDto(HolidayDTO value) {
        this.holidayDto = value;
    }

}
