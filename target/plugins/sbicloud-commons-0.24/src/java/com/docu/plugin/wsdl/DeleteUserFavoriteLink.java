
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteUserFavoriteLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteUserFavoriteLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userFavoriteLinkDto" type="{http://com.docu}UserFavoriteLinkDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteUserFavoriteLink", propOrder = {
    "userFavoriteLinkDto"
})
public class DeleteUserFavoriteLink {

    protected UserFavoriteLinkDTO userFavoriteLinkDto;

    /**
     * Gets the value of the userFavoriteLinkDto property.
     * 
     * @return
     *     possible object is
     *     {@link UserFavoriteLinkDTO }
     *     
     */
    public UserFavoriteLinkDTO getUserFavoriteLinkDto() {
        return userFavoriteLinkDto;
    }

    /**
     * Sets the value of the userFavoriteLinkDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserFavoriteLinkDTO }
     *     
     */
    public void setUserFavoriteLinkDto(UserFavoriteLinkDTO value) {
        this.userFavoriteLinkDto = value;
    }

}
