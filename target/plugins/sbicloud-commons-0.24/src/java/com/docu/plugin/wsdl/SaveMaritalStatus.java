
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveMaritalStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveMaritalStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maritalStatusDto" type="{http://com.docu}MaritalStatusDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveMaritalStatus", propOrder = {
    "maritalStatusDto"
})
public class SaveMaritalStatus {

    protected MaritalStatusDTO maritalStatusDto;

    /**
     * Gets the value of the maritalStatusDto property.
     * 
     * @return
     *     possible object is
     *     {@link MaritalStatusDTO }
     *     
     */
    public MaritalStatusDTO getMaritalStatusDto() {
        return maritalStatusDto;
    }

    /**
     * Sets the value of the maritalStatusDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaritalStatusDTO }
     *     
     */
    public void setMaritalStatusDto(MaritalStatusDTO value) {
        this.maritalStatusDto = value;
    }

}
