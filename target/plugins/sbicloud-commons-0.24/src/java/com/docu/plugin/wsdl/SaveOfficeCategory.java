
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveOfficeCategory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveOfficeCategory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="officeCategoryDto" type="{http://com.docu}OfficeCategoryDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveOfficeCategory", propOrder = {
    "officeCategoryDto"
})
public class SaveOfficeCategory {

    protected OfficeCategoryDTO officeCategoryDto;

    /**
     * Gets the value of the officeCategoryDto property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeCategoryDTO }
     *     
     */
    public OfficeCategoryDTO getOfficeCategoryDto() {
        return officeCategoryDto;
    }

    /**
     * Sets the value of the officeCategoryDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeCategoryDTO }
     *     
     */
    public void setOfficeCategoryDto(OfficeCategoryDTO value) {
        this.officeCategoryDto = value;
    }

}
