
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteApplicationUserAuthority complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteApplicationUserAuthority">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationUserAuthorityDto" type="{http://com.docu}ApplicationUserAuthorityDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteApplicationUserAuthority", propOrder = {
    "applicationUserAuthorityDto"
})
public class DeleteApplicationUserAuthority {

    protected ApplicationUserAuthorityDTO applicationUserAuthorityDto;

    /**
     * Gets the value of the applicationUserAuthorityDto property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationUserAuthorityDTO }
     *     
     */
    public ApplicationUserAuthorityDTO getApplicationUserAuthorityDto() {
        return applicationUserAuthorityDto;
    }

    /**
     * Sets the value of the applicationUserAuthorityDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationUserAuthorityDTO }
     *     
     */
    public void setApplicationUserAuthorityDto(ApplicationUserAuthorityDTO value) {
        this.applicationUserAuthorityDto = value;
    }

}
