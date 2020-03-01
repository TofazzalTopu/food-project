
package com.docu.plugin.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteTaskStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteTaskStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="taskStatusDto" type="{http://com.docu}TaskStatusDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteTaskStatus", propOrder = {
    "taskStatusDto"
})
public class DeleteTaskStatus {

    protected TaskStatusDTO taskStatusDto;

    /**
     * Gets the value of the taskStatusDto property.
     * 
     * @return
     *     possible object is
     *     {@link TaskStatusDTO }
     *     
     */
    public TaskStatusDTO getTaskStatusDto() {
        return taskStatusDto;
    }

    /**
     * Sets the value of the taskStatusDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskStatusDTO }
     *     
     */
    public void setTaskStatusDto(TaskStatusDTO value) {
        this.taskStatusDto = value;
    }

}
