
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveOrganization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveOrganization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="organizationDto" type="{http://com.docu}OrganizationDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveOrganization", propOrder = {
    "organizationDto"
})
public class SaveOrganization {

    protected OrganizationDTO organizationDto;

    /**
     * Gets the value of the organizationDto property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationDTO }
     *     
     */
    public OrganizationDTO getOrganizationDto() {
        return organizationDto;
    }

    /**
     * Sets the value of the organizationDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationDTO }
     *     
     */
    public void setOrganizationDto(OrganizationDTO value) {
        this.organizationDto = value;
    }

}
