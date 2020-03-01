
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationConfigDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationConfigDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="appBannerPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baseCountryId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="companyGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="favIconPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginLogoPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportLogoPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="showBackgroundImage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="showFooterText" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationConfigDTO", propOrder = {
    "id",
    "version",
    "appBannerPath",
    "appName",
    "appTitle",
    "baseCountryId",
    "companyGroup",
    "companyName",
    "countryId",
    "countryName",
    "favIconPath",
    "loginLogoPath",
    "reportLogoPath",
    "showBackgroundImage",
    "showFooterText"
})
public class ApplicationConfigDTO {

    protected Long id;
    protected Long version;
    protected String appBannerPath;
    protected String appName;
    protected String appTitle;
    protected Long baseCountryId;
    protected String companyGroup;
    protected String companyName;
    protected Long countryId;
    protected String countryName;
    protected String favIconPath;
    protected String loginLogoPath;
    protected String reportLogoPath;
    protected boolean showBackgroundImage;
    protected boolean showFooterText;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
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
     * Gets the value of the appBannerPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppBannerPath() {
        return appBannerPath;
    }

    /**
     * Sets the value of the appBannerPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppBannerPath(String value) {
        this.appBannerPath = value;
    }

    /**
     * Gets the value of the appName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Sets the value of the appName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppName(String value) {
        this.appName = value;
    }

    /**
     * Gets the value of the appTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * Sets the value of the appTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppTitle(String value) {
        this.appTitle = value;
    }

    /**
     * Gets the value of the baseCountryId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBaseCountryId() {
        return baseCountryId;
    }

    /**
     * Sets the value of the baseCountryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBaseCountryId(Long value) {
        this.baseCountryId = value;
    }

    /**
     * Gets the value of the companyGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyGroup() {
        return companyGroup;
    }

    /**
     * Sets the value of the companyGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyGroup(String value) {
        this.companyGroup = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the countryId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * Sets the value of the countryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountryId(Long value) {
        this.countryId = value;
    }

    /**
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the favIconPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFavIconPath() {
        return favIconPath;
    }

    /**
     * Sets the value of the favIconPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFavIconPath(String value) {
        this.favIconPath = value;
    }

    /**
     * Gets the value of the loginLogoPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginLogoPath() {
        return loginLogoPath;
    }

    /**
     * Sets the value of the loginLogoPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginLogoPath(String value) {
        this.loginLogoPath = value;
    }

    /**
     * Gets the value of the reportLogoPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportLogoPath() {
        return reportLogoPath;
    }

    /**
     * Sets the value of the reportLogoPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportLogoPath(String value) {
        this.reportLogoPath = value;
    }

    /**
     * Gets the value of the showBackgroundImage property.
     * 
     */
    public boolean isShowBackgroundImage() {
        return showBackgroundImage;
    }

    /**
     * Sets the value of the showBackgroundImage property.
     * 
     */
    public void setShowBackgroundImage(boolean value) {
        this.showBackgroundImage = value;
    }

    /**
     * Gets the value of the showFooterText property.
     * 
     */
    public boolean isShowFooterText() {
        return showFooterText;
    }

    /**
     * Sets the value of the showFooterText property.
     * 
     */
    public void setShowFooterText(boolean value) {
        this.showFooterText = value;
    }

}
