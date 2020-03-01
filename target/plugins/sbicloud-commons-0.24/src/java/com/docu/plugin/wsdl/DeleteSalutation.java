
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteSalutation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteSalutation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="salutationDto" type="{http://com.docu}SalutationDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteSalutation", propOrder = {
    "salutationDto"
})
public class DeleteSalutation {

    protected SalutationDTO salutationDto;

    /**
     * Gets the value of the salutationDto property.
     * 
     * @return
     *     possible object is
     *     {@link SalutationDTO }
     *     
     */
    public SalutationDTO getSalutationDto() {
        return salutationDto;
    }

    /**
     * Sets the value of the salutationDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalutationDTO }
     *     
     */
    public void setSalutationDto(SalutationDTO value) {
        this.salutationDto = value;
    }

}
