
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserPortletPreferenceDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserPortletPreferenceDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="colIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="domainStatusId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="portlet" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rowIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userPreference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserPortletPreferenceDTO", propOrder = {
    "id",
    "version",
    "colIndex",
    "domainStatusId",
    "portlet",
    "rowIndex",
    "userPreference"
})
public class UserPortletPreferenceDTO {

    protected String id;
    protected Long version;
    protected int colIndex;
    protected long domainStatusId;
    protected Long portlet;
    protected int rowIndex;
    protected String userPreference;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVersion(Long value) {
        this.version = value;
    }

    /**
     * Gets the value of the colIndex property.
     * 
     */
    public int getColIndex() {
        return colIndex;
    }

    /**
     * Sets the value of the colIndex property.
     * 
     */
    public void setColIndex(int value) {
        this.colIndex = value;
    }

    /**
     * Gets the value of the domainStatusId property.
     * 
     */
    public long getDomainStatusId() {
        return domainStatusId;
    }

    /**
     * Sets the value of the domainStatusId property.
     * 
     */
    public void setDomainStatusId(long value) {
        this.domainStatusId = value;
    }

    /**
     * Gets the value of the portlet property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPortlet() {
        return portlet;
    }

    /**
     * Sets the value of the portlet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPortlet(Long value) {
        this.portlet = value;
    }

    /**
     * Gets the value of the rowIndex property.
     * 
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Sets the value of the rowIndex property.
     * 
     */
    public void setRowIndex(int value) {
        this.rowIndex = value;
    }

    /**
     * Gets the value of the userPreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPreference() {
        return userPreference;
    }

    /**
     * Sets the value of the userPreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPreference(String value) {
        this.userPreference = value;
    }

}
