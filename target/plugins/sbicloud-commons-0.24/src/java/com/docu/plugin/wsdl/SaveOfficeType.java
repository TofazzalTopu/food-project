
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveOfficeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveOfficeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="officeTypeDto" type="{http://com.docu}OfficeTypeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveOfficeType", propOrder = {
    "officeTypeDto"
})
public class SaveOfficeType {

    protected OfficeTypeDTO officeTypeDto;

    /**
     * Gets the value of the officeTypeDto property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeTypeDTO }
     *     
     */
    public OfficeTypeDTO getOfficeTypeDto() {
        return officeTypeDto;
    }

    /**
     * Sets the value of the officeTypeDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeTypeDTO }
     *     
     */
    public void setOfficeTypeDto(OfficeTypeDTO value) {
        this.officeTypeDto = value;
    }

}
