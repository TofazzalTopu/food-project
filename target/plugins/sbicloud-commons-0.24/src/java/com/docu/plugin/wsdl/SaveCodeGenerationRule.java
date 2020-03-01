
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveCodeGenerationRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveCodeGenerationRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codeGenerationRuleDto" type="{http://com.docu}CodeGenerationRuleDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveCodeGenerationRule", propOrder = {
    "codeGenerationRuleDto"
})
public class SaveCodeGenerationRule {

    protected CodeGenerationRuleDTO codeGenerationRuleDto;

    /**
     * Gets the value of the codeGenerationRuleDto property.
     * 
     * @return
     *     possible object is
     *     {@link CodeGenerationRuleDTO }
     *     
     */
    public CodeGenerationRuleDTO getCodeGenerationRuleDto() {
        return codeGenerationRuleDto;
    }

    /**
     * Sets the value of the codeGenerationRuleDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeGenerationRuleDTO }
     *     
     */
    public void setCodeGenerationRuleDto(CodeGenerationRuleDTO value) {
        this.codeGenerationRuleDto = value;
    }

}
