
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteBloodGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteBloodGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bloodGroupDto" type="{http://com.docu}BloodGroupDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteBloodGroup", propOrder = {
    "bloodGroupDto"
})
public class DeleteBloodGroup {

    protected BloodGroupDTO bloodGroupDto;

    /**
     * Gets the value of the bloodGroupDto property.
     * 
     * @return
     *     possible object is
     *     {@link BloodGroupDTO }
     *     
     */
    public BloodGroupDTO getBloodGroupDto() {
        return bloodGroupDto;
    }

    /**
     * Sets the value of the bloodGroupDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BloodGroupDTO }
     *     
     */
    public void setBloodGroupDto(BloodGroupDTO value) {
        this.bloodGroupDto = value;
    }

}
