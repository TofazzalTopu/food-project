
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteModeOfPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteModeOfPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="modeOfPaymentDto" type="{http://com.docu}ModeOfPaymentDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteModeOfPayment", propOrder = {
    "modeOfPaymentDto"
})
public class DeleteModeOfPayment {

    protected ModeOfPaymentDTO modeOfPaymentDto;

    /**
     * Gets the value of the modeOfPaymentDto property.
     * 
     * @return
     *     possible object is
     *     {@link ModeOfPaymentDTO }
     *     
     */
    public ModeOfPaymentDTO getModeOfPaymentDto() {
        return modeOfPaymentDto;
    }

    /**
     * Sets the value of the modeOfPaymentDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModeOfPaymentDTO }
     *     
     */
    public void setModeOfPaymentDto(ModeOfPaymentDTO value) {
        this.modeOfPaymentDto = value;
    }

}
