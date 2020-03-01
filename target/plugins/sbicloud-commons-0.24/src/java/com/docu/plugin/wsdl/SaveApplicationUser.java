
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveApplicationUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveApplicationUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationUserDto" type="{http://com.docu}ApplicationUserDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveApplicationUser", propOrder = {
    "applicationUserDto"
})
public class SaveApplicationUser {

    protected ApplicationUserDTO applicationUserDto;

    /**
     * Gets the value of the applicationUserDto property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationUserDTO }
     *     
     */
    public ApplicationUserDTO getApplicationUserDto() {
        return applicationUserDto;
    }

    /**
     * Sets the value of the applicationUserDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationUserDTO }
     *     
     */
    public void setApplicationUserDto(ApplicationUserDTO value) {
        this.applicationUserDto = value;
    }

}
