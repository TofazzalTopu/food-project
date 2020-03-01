
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteApplicationConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteApplicationConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationConfigDto" type="{http://com.docu}ApplicationConfigDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteApplicationConfig", propOrder = {
    "applicationConfigDto"
})
public class DeleteApplicationConfig {

    protected ApplicationConfigDTO applicationConfigDto;

    /**
     * Gets the value of the applicationConfigDto property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationConfigDTO }
     *     
     */
    public ApplicationConfigDTO getApplicationConfigDto() {
        return applicationConfigDto;
    }

    /**
     * Sets the value of the applicationConfigDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationConfigDTO }
     *     
     */
    public void setApplicationConfigDto(ApplicationConfigDTO value) {
        this.applicationConfigDto = value;
    }

}
