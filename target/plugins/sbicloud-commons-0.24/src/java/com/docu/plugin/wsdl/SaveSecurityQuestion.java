
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveSecurityQuestion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveSecurityQuestion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="securityQuestionDto" type="{http://com.docu}SecurityQuestionDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveSecurityQuestion", propOrder = {
    "securityQuestionDto"
})
public class SaveSecurityQuestion {

    protected SecurityQuestionDTO securityQuestionDto;

    /**
     * Gets the value of the securityQuestionDto property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityQuestionDTO }
     *     
     */
    public SecurityQuestionDTO getSecurityQuestionDto() {
        return securityQuestionDto;
    }

    /**
     * Sets the value of the securityQuestionDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityQuestionDTO }
     *     
     */
    public void setSecurityQuestionDto(SecurityQuestionDTO value) {
        this.securityQuestionDto = value;
    }

}
