
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteCity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteCity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cityDto" type="{http://com.docu}CityDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteCity", propOrder = {
    "cityDto"
})
public class DeleteCity {

    protected CityDTO cityDto;

    /**
     * Gets the value of the cityDto property.
     * 
     * @return
     *     possible object is
     *     {@link CityDTO }
     *     
     */
    public CityDTO getCityDto() {
        return cityDto;
    }

    /**
     * Sets the value of the cityDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link CityDTO }
     *     
     */
    public void setCityDto(CityDTO value) {
        this.cityDto = value;
    }

}
