
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteAuthorityDashboardMapping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteAuthorityDashboardMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authorityDashboardMappingDto" type="{http://com.docu}AuthorityDashboardMappingDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteAuthorityDashboardMapping", propOrder = {
    "authorityDashboardMappingDto"
})
public class DeleteAuthorityDashboardMapping {

    protected AuthorityDashboardMappingDTO authorityDashboardMappingDto;

    /**
     * Gets the value of the authorityDashboardMappingDto property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorityDashboardMappingDTO }
     *     
     */
    public AuthorityDashboardMappingDTO getAuthorityDashboardMappingDto() {
        return authorityDashboardMappingDto;
    }

    /**
     * Sets the value of the authorityDashboardMappingDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorityDashboardMappingDTO }
     *     
     */
    public void setAuthorityDashboardMappingDto(AuthorityDashboardMappingDTO value) {
        this.authorityDashboardMappingDto = value;
    }

}
