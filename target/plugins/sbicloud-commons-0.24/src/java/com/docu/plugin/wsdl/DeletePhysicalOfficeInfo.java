
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deletePhysicalOfficeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deletePhysicalOfficeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="physicalOfficeInfoDto" type="{http://com.docu}PhysicalOfficeInfoDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deletePhysicalOfficeInfo", propOrder = {
    "physicalOfficeInfoDto"
})
public class DeletePhysicalOfficeInfo {

    protected PhysicalOfficeInfoDTO physicalOfficeInfoDto;

    /**
     * Gets the value of the physicalOfficeInfoDto property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalOfficeInfoDTO }
     *     
     */
    public PhysicalOfficeInfoDTO getPhysicalOfficeInfoDto() {
        return physicalOfficeInfoDto;
    }

    /**
     * Sets the value of the physicalOfficeInfoDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalOfficeInfoDTO }
     *     
     */
    public void setPhysicalOfficeInfoDto(PhysicalOfficeInfoDTO value) {
        this.physicalOfficeInfoDto = value;
    }

}
