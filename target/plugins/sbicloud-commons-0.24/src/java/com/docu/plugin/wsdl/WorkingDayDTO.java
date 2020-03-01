
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkingDayDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkingDayDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="friEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="friStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="friday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="monEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="officeTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="satEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="satStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="saturday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sunEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sunStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sunday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="thuEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thuStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thursday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tueEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tueStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tuesday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="wedEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wedStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wednesday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="weekStartDay" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkingDayDTO", propOrder = {
    "id",
    "version",
    "country",
    "friEndTime",
    "friStartTime",
    "friday",
    "monEndTime",
    "monStartTime",
    "monday",
    "officeTypeId",
    "satEndTime",
    "satStartTime",
    "saturday",
    "sunEndTime",
    "sunStartTime",
    "sunday",
    "thuEndTime",
    "thuStartTime",
    "thursday",
    "tueEndTime",
    "tueStartTime",
    "tuesday",
    "wedEndTime",
    "wedStartTime",
    "wednesday",
    "weekStartDay"
})
public class WorkingDayDTO {

    protected Long id;
    protected Long version;
    protected Long country;
    protected String friEndTime;
    protected String friStartTime;
    protected boolean friday;
    protected String monEndTime;
    protected String monStartTime;
    protected boolean monday;
    protected long officeTypeId;
    protected String satEndTime;
    protected String satStartTime;
    protected boolean saturday;
    protected String sunEndTime;
    protected String sunStartTime;
    protected boolean sunday;
    protected String thuEndTime;
    protected String thuStartTime;
    protected boolean thursday;
    protected String tueEndTime;
    protected String tueStartTime;
    protected boolean tuesday;
    protected String wedEndTime;
    protected String wedStartTime;
    protected boolean wednesday;
    protected Long weekStartDay;

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
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountry(Long value) {
        this.country = value;
    }

    /**
     * Gets the value of the friEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFriEndTime() {
        return friEndTime;
    }

    /**
     * Sets the value of the friEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFriEndTime(String value) {
        this.friEndTime = value;
    }

    /**
     * Gets the value of the friStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFriStartTime() {
        return friStartTime;
    }

    /**
     * Sets the value of the friStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFriStartTime(String value) {
        this.friStartTime = value;
    }

    /**
     * Gets the value of the friday property.
     * 
     */
    public boolean isFriday() {
        return friday;
    }

    /**
     * Sets the value of the friday property.
     * 
     */
    public void setFriday(boolean value) {
        this.friday = value;
    }

    /**
     * Gets the value of the monEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonEndTime() {
        return monEndTime;
    }

    /**
     * Sets the value of the monEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonEndTime(String value) {
        this.monEndTime = value;
    }

    /**
     * Gets the value of the monStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonStartTime() {
        return monStartTime;
    }

    /**
     * Sets the value of the monStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonStartTime(String value) {
        this.monStartTime = value;
    }

    /**
     * Gets the value of the monday property.
     * 
     */
    public boolean isMonday() {
        return monday;
    }

    /**
     * Sets the value of the monday property.
     * 
     */
    public void setMonday(boolean value) {
        this.monday = value;
    }

    /**
     * Gets the value of the officeTypeId property.
     * 
     */
    public long getOfficeTypeId() {
        return officeTypeId;
    }

    /**
     * Sets the value of the officeTypeId property.
     * 
     */
    public void setOfficeTypeId(long value) {
        this.officeTypeId = value;
    }

    /**
     * Gets the value of the satEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSatEndTime() {
        return satEndTime;
    }

    /**
     * Sets the value of the satEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSatEndTime(String value) {
        this.satEndTime = value;
    }

    /**
     * Gets the value of the satStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSatStartTime() {
        return satStartTime;
    }

    /**
     * Sets the value of the satStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSatStartTime(String value) {
        this.satStartTime = value;
    }

    /**
     * Gets the value of the saturday property.
     * 
     */
    public boolean isSaturday() {
        return saturday;
    }

    /**
     * Sets the value of the saturday property.
     * 
     */
    public void setSaturday(boolean value) {
        this.saturday = value;
    }

    /**
     * Gets the value of the sunEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSunEndTime() {
        return sunEndTime;
    }

    /**
     * Sets the value of the sunEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSunEndTime(String value) {
        this.sunEndTime = value;
    }

    /**
     * Gets the value of the sunStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSunStartTime() {
        return sunStartTime;
    }

    /**
     * Sets the value of the sunStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSunStartTime(String value) {
        this.sunStartTime = value;
    }

    /**
     * Gets the value of the sunday property.
     * 
     */
    public boolean isSunday() {
        return sunday;
    }

    /**
     * Sets the value of the sunday property.
     * 
     */
    public void setSunday(boolean value) {
        this.sunday = value;
    }

    /**
     * Gets the value of the thuEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThuEndTime() {
        return thuEndTime;
    }

    /**
     * Sets the value of the thuEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThuEndTime(String value) {
        this.thuEndTime = value;
    }

    /**
     * Gets the value of the thuStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThuStartTime() {
        return thuStartTime;
    }

    /**
     * Sets the value of the thuStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThuStartTime(String value) {
        this.thuStartTime = value;
    }

    /**
     * Gets the value of the thursday property.
     * 
     */
    public boolean isThursday() {
        return thursday;
    }

    /**
     * Sets the value of the thursday property.
     * 
     */
    public void setThursday(boolean value) {
        this.thursday = value;
    }

    /**
     * Gets the value of the tueEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTueEndTime() {
        return tueEndTime;
    }

    /**
     * Sets the value of the tueEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTueEndTime(String value) {
        this.tueEndTime = value;
    }

    /**
     * Gets the value of the tueStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTueStartTime() {
        return tueStartTime;
    }

    /**
     * Sets the value of the tueStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTueStartTime(String value) {
        this.tueStartTime = value;
    }

    /**
     * Gets the value of the tuesday property.
     * 
     */
    public boolean isTuesday() {
        return tuesday;
    }

    /**
     * Sets the value of the tuesday property.
     * 
     */
    public void setTuesday(boolean value) {
        this.tuesday = value;
    }

    /**
     * Gets the value of the wedEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWedEndTime() {
        return wedEndTime;
    }

    /**
     * Sets the value of the wedEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWedEndTime(String value) {
        this.wedEndTime = value;
    }

    /**
     * Gets the value of the wedStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWedStartTime() {
        return wedStartTime;
    }

    /**
     * Sets the value of the wedStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWedStartTime(String value) {
        this.wedStartTime = value;
    }

    /**
     * Gets the value of the wednesday property.
     * 
     */
    public boolean isWednesday() {
        return wednesday;
    }

    /**
     * Sets the value of the wednesday property.
     * 
     */
    public void setWednesday(boolean value) {
        this.wednesday = value;
    }

    /**
     * Gets the value of the weekStartDay property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getWeekStartDay() {
        return weekStartDay;
    }

    /**
     * Sets the value of the weekStartDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setWeekStartDay(Long value) {
        this.weekStartDay = value;
    }

}
