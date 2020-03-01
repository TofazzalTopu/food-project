
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressTypeDto" type="{http://com.docu}AddressTypeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveAddressType", propOrder = {
    "addressTypeDto"
})
public class SaveAddressType {

    protected AddressTypeDTO addressTypeDto;

    /**
     * Gets the value of the addressTypeDto property.
     * 
     * @return
     *     possible object is
     *     {@link AddressTypeDTO }
     *     
     */
    public AddressTypeDTO getAddressTypeDto() {
        return addressTypeDto;
    }

    /**
     * Sets the value of the addressTypeDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressTypeDTO }
     *     
     */
    public void setAddressTypeDto(AddressTypeDTO value) {
        this.addressTypeDto = value;
    }

}
