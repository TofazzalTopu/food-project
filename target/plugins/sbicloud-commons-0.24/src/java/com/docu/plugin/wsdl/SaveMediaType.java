
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saveMediaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saveMediaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mediaTypeDto" type="{http://com.docu}MediaTypeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveMediaType", propOrder = {
    "mediaTypeDto"
})
public class SaveMediaType {

    protected MediaTypeDTO mediaTypeDto;

    /**
     * Gets the value of the mediaTypeDto property.
     * 
     * @return
     *     possible object is
     *     {@link MediaTypeDTO }
     *     
     */
    public MediaTypeDTO getMediaTypeDto() {
        return mediaTypeDto;
    }

    /**
     * Sets the value of the mediaTypeDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link MediaTypeDTO }
     *     
     */
    public void setMediaTypeDto(MediaTypeDTO value) {
        this.mediaTypeDto = value;
    }

}
