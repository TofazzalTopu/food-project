
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteOfficeAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteOfficeAddress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="officeAddressDto" type="{http://com.docu}OfficeAddressDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteOfficeAddress", propOrder = {
    "officeAddressDto"
})
public class DeleteOfficeAddress {

    protected OfficeAddressDTO officeAddressDto;

    /**
     * Gets the value of the officeAddressDto property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeAddressDTO }
     *     
     */
    public OfficeAddressDTO getOfficeAddressDto() {
        return officeAddressDto;
    }

    /**
     * Sets the value of the officeAddressDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeAddressDTO }
     *     
     */
    public void setOfficeAddressDto(OfficeAddressDTO value) {
        this.officeAddressDto = value;
    }

}
