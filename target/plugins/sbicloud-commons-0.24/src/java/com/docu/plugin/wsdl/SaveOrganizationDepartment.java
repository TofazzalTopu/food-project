
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveOrganizationDepartment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveOrganizationDepartment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="organizationDepartmentDto" type="{http://com.docu}OrganizationDepartmentDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveOrganizationDepartment", propOrder = {
    "organizationDepartmentDto"
})
public class SaveOrganizationDepartment {

    protected OrganizationDepartmentDTO organizationDepartmentDto;

    /**
     * Gets the value of the organizationDepartmentDto property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationDepartmentDTO }
     *     
     */
    public OrganizationDepartmentDTO getOrganizationDepartmentDto() {
        return organizationDepartmentDto;
    }

    /**
     * Sets the value of the organizationDepartmentDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationDepartmentDTO }
     *     
     */
    public void setOrganizationDepartmentDto(OrganizationDepartmentDTO value) {
        this.organizationDepartmentDto = value;
    }

}
