
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relationshipDto" type="{http://com.docu}RelationshipDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveRelationship", propOrder = {
    "relationshipDto"
})
public class SaveRelationship {

    protected RelationshipDTO relationshipDto;

    /**
     * Gets the value of the relationshipDto property.
     * 
     * @return
     *     possible object is
     *     {@link RelationshipDTO }
     *     
     */
    public RelationshipDTO getRelationshipDto() {
        return relationshipDto;
    }

    /**
     * Sets the value of the relationshipDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationshipDTO }
     *     
     */
    public void setRelationshipDto(RelationshipDTO value) {
        this.relationshipDto = value;
    }

}
