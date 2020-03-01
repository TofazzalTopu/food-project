
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteUserPreference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteUserPreference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userPreferenceDTO" type="{http://com.docu}UserPreferenceDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteUserPreference", propOrder = {
    "userPreferenceDTO"
})
public class DeleteUserPreference {

    protected UserPreferenceDTO userPreferenceDTO;

    /**
     * Gets the value of the userPreferenceDTO property.
     * 
     * @return
     *     possible object is
     *     {@link UserPreferenceDTO }
     *     
     */
    public UserPreferenceDTO getUserPreferenceDTO() {
        return userPreferenceDTO;
    }

    /**
     * Sets the value of the userPreferenceDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserPreferenceDTO }
     *     
     */
    public void setUserPreferenceDTO(UserPreferenceDTO value) {
        this.userPreferenceDTO = value;
    }

}
