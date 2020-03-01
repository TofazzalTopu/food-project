
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteMailNotificationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteMailNotificationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mailNotificationInfoDto" type="{http://com.docu}MailNotificationInfoDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteMailNotificationInfo", propOrder = {
    "mailNotificationInfoDto"
})
public class DeleteMailNotificationInfo {

    protected MailNotificationInfoDTO mailNotificationInfoDto;

    /**
     * Gets the value of the mailNotificationInfoDto property.
     * 
     * @return
     *     possible object is
     *     {@link MailNotificationInfoDTO }
     *     
     */
    public MailNotificationInfoDTO getMailNotificationInfoDto() {
        return mailNotificationInfoDto;
    }

    /**
     * Sets the value of the mailNotificationInfoDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link MailNotificationInfoDTO }
     *     
     */
    public void setMailNotificationInfoDto(MailNotificationInfoDTO value) {
        this.mailNotificationInfoDto = value;
    }

}
