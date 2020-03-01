
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveMeasurementUnit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveMeasurementUnit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="measurementUnitDto" type="{http://com.docu}MeasurementUnitDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveMeasurementUnit", propOrder = {
    "measurementUnitDto"
})
public class SaveMeasurementUnit {

    protected MeasurementUnitDTO measurementUnitDto;

    /**
     * Gets the value of the measurementUnitDto property.
     * 
     * @return
     *     possible object is
     *     {@link MeasurementUnitDTO }
     *     
     */
    public MeasurementUnitDTO getMeasurementUnitDto() {
        return measurementUnitDto;
    }

    /**
     * Sets the value of the measurementUnitDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasurementUnitDTO }
     *     
     */
    public void setMeasurementUnitDto(MeasurementUnitDTO value) {
        this.measurementUnitDto = value;
    }

}
