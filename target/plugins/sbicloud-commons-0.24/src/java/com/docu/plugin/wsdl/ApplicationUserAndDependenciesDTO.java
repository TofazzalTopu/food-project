
package com.docu.plugin.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationUserAndDependenciesDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationUserAndDependenciesDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationUserDTO" type="{http://com.docu}ApplicationUserDTO" minOccurs="0"/>
 *         &lt;element name="applicationUserAuthorityDTOList" type="{http://com.docu}ApplicationUserAuthorityDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="userPreferenceDTO" type="{http://com.docu}UserPreferenceDTO" minOccurs="0"/>
 *         &lt;element name="userPortletPreferenceDTOList" type="{http://com.docu}UserPortletPreferenceDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deleteUserAuthorityDTOList" type="{http://com.docu}ApplicationUserAuthorityDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deletePortletPreferenceDTOList" type="{http://com.docu}UserPortletPreferenceDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="applicationUserDTOList" type="{http://com.docu}ApplicationUserDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationUserAndDependenciesDTO", propOrder = {
    "applicationUserDTO",
    "applicationUserAuthorityDTOList",
    "userPreferenceDTO",
    "userPortletPreferenceDTOList",
    "deleteUserAuthorityDTOList",
    "deletePortletPreferenceDTOList",
    "applicationUserDTOList"
})
public class ApplicationUserAndDependenciesDTO {

    protected ApplicationUserDTO applicationUserDTO;
    @XmlElement(nillable = true)
    protected List<ApplicationUserAuthorityDTO> applicationUserAuthorityDTOList;
    protected UserPreferenceDTO userPreferenceDTO;
    @XmlElement(nillable = true)
    protected List<UserPortletPreferenceDTO> userPortletPreferenceDTOList;
    @XmlElement(nillable = true)
    protected List<ApplicationUserAuthorityDTO> deleteUserAuthorityDTOList;
    @XmlElement(nillable = true)
    protected List<UserPortletPreferenceDTO> deletePortletPreferenceDTOList;
    @XmlElement(nillable = true)
    protected List<ApplicationUserDTO> applicationUserDTOList;

    /**
     * Gets the value of the applicationUserDTO property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationUserDTO }
     *     
     */
    public ApplicationUserDTO getApplicationUserDTO() {
        return applicationUserDTO;
    }

    /**
     * Sets the value of the applicationUserDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationUserDTO }
     *     
     */
    public void setApplicationUserDTO(ApplicationUserDTO value) {
        this.applicationUserDTO = value;
    }

    /**
     * Gets the value of the applicationUserAuthorityDTOList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicationUserAuthorityDTOList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicationUserAuthorityDTOList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApplicationUserAuthorityDTO }
     * 
     * 
     */
    public List<ApplicationUserAuthorityDTO> getApplicationUserAuthorityDTOList() {
        if (applicationUserAuthorityDTOList == null) {
            applicationUserAuthorityDTOList = new ArrayList<ApplicationUserAuthorityDTO>();
        }
        return this.applicationUserAuthorityDTOList;
    }

    /**
     * Gets the value of the userPreferenceDTO property.
     * 
     * @return
     *     possible object is
     *     {@link UserPreferenceDTO }
     *     
     */
    public UserPreferenceDTO getUserPreferenceDTO() {
        return userPreferenceDTO;
    }

    /**
     * Sets the value of the userPreferenceDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserPreferenceDTO }
     *     
     */
    public void setUserPreferenceDTO(UserPreferenceDTO value) {
        this.userPreferenceDTO = value;
    }

    /**
     * Gets the value of the userPortletPreferenceDTOList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userPortletPreferenceDTOList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserPortletPreferenceDTOList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserPortletPreferenceDTO }
     * 
     * 
     */
    public List<UserPortletPreferenceDTO> getUserPortletPreferenceDTOList() {
        if (userPortletPreferenceDTOList == null) {
            userPortletPreferenceDTOList = new ArrayList<UserPortletPreferenceDTO>();
        }
        return this.userPortletPreferenceDTOList;
    }

    /**
     * Gets the value of the deleteUserAuthorityDTOList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deleteUserAuthorityDTOList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeleteUserAuthorityDTOList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApplicationUserAuthorityDTO }
     * 
     * 
     */
    public List<ApplicationUserAuthorityDTO> getDeleteUserAuthorityDTOList() {
        if (deleteUserAuthorityDTOList == null) {
            deleteUserAuthorityDTOList = new ArrayList<ApplicationUserAuthorityDTO>();
        }
        return this.deleteUserAuthorityDTOList;
    }

    /**
     * Gets the value of the deletePortletPreferenceDTOList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deletePortletPreferenceDTOList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeletePortletPreferenceDTOList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserPortletPreferenceDTO }
     * 
     * 
     */
    public List<UserPortletPreferenceDTO> getDeletePortletPreferenceDTOList() {
        if (deletePortletPreferenceDTOList == null) {
            deletePortletPreferenceDTOList = new ArrayList<UserPortletPreferenceDTO>();
        }
        return this.deletePortletPreferenceDTOList;
    }

    /**
     * Gets the value of the applicationUserDTOList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicationUserDTOList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicationUserDTOList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApplicationUserDTO }
     * 
     * 
     */
    public List<ApplicationUserDTO> getApplicationUserDTOList() {
        if (applicationUserDTOList == null) {
            applicationUserDTOList = new ArrayList<ApplicationUserDTO>();
        }
        return this.applicationUserDTOList;
    }

}
