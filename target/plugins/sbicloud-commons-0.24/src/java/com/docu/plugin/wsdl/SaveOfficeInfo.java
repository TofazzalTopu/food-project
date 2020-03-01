
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveOfficeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveOfficeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="officeInfoDto" type="{http://com.docu}OfficeInfoDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveOfficeInfo", propOrder = {
    "officeInfoDto"
})
public class SaveOfficeInfo {

    protected OfficeInfoDTO officeInfoDto;

    /**
     * Gets the value of the officeInfoDto property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeInfoDTO }
     *     
     */
    public OfficeInfoDTO getOfficeInfoDto() {
        return officeInfoDto;
    }

    /**
     * Sets the value of the officeInfoDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeInfoDTO }
     *     
     */
    public void setOfficeInfoDto(OfficeInfoDTO value) {
        this.officeInfoDto = value;
    }

}
