
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteOfficeCategoryMapping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteOfficeCategoryMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="officeCategoryMappingDto" type="{http://com.docu}OfficeCategoryMappingDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteOfficeCategoryMapping", propOrder = {
    "officeCategoryMappingDto"
})
public class DeleteOfficeCategoryMapping {

    protected OfficeCategoryMappingDTO officeCategoryMappingDto;

    /**
     * Gets the value of the officeCategoryMappingDto property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeCategoryMappingDTO }
     *     
     */
    public OfficeCategoryMappingDTO getOfficeCategoryMappingDto() {
        return officeCategoryMappingDto;
    }

    /**
     * Sets the value of the officeCategoryMappingDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeCategoryMappingDTO }
     *     
     */
    public void setOfficeCategoryMappingDto(OfficeCategoryMappingDTO value) {
        this.officeCategoryMappingDto = value;
    }

}
