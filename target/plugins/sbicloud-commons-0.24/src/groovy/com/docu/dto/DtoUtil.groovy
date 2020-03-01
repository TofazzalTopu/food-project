package com.docu.dto


import com.docu.commons.DateUtil

import javassist.util.proxy.MethodHandler
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.hibernate.proxy.LazyInitializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: bipul
 * Date: 11/27/13
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Component("dtoUtil")
class DtoUtil {

    @Autowired
    GrailsApplication grailsApplication

    public Object toDTO(Object domainObj, Object dtoObj) {
        private GrailsDomainClassProperty[] propertyList = null
        propertyList = new DefaultGrailsDomainClass(domainObj.class).getPersistantProperties()


        for (property in propertyList) {
            if (!property.isHasOne() && !property.isManyToMany() && !property.isManyToMany()
                    && !property.isOneToMany() && property.type != MethodHandler.class
                    && property.type != LazyInitializer.class) {

//                if (property.type == Author.class) {
                if (grailsApplication.isDomainClass(property.type)) {
                    //println("Property Type:" + domainObj.getAt(property.name))
                    String propName = property.name
                    if (domainObj.getAt(property.name) != null) {
                        dtoObj[propName] = domainObj.getAt(property.name).id
                    }
                } else if (property.type == Date && domainObj.getAt(property.name) != null) {
                    Calendar toCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
                    Calendar fromCalendar = GregorianCalendar.getInstance()
                    fromCalendar.setTime(domainObj.getAt(property.name))
                    toCalendar.set(Calendar.DAY_OF_MONTH, fromCalendar.get(Calendar.DAY_OF_MONTH))
                    toCalendar.set(Calendar.MONTH, fromCalendar.get(Calendar.MONTH))
                    toCalendar.set(Calendar.YEAR, fromCalendar.get(Calendar.YEAR))
                    XMLGregorianCalendar xmldate = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendar);
                    dtoObj[property.name] = xmldate
                } else if (property.type == TimeZone && domainObj.getAt(property.name) != null) {
                    dtoObj[property.name] = domainObj[property.name].ID
                } else {
                    dtoObj[property.name] = domainObj[property.name]
                }
            }
        }

        dtoObj.id = domainObj.id
        dtoObj.version = (domainObj.version == 0) ? null : domainObj.version
        return dtoObj
    }

    public Object fromDTO(Object dtoObj, Object domainObj) {

        private GrailsDomainClassProperty[] propertyList = null
        propertyList = new DefaultGrailsDomainClass(domainObj.class).getPersistantProperties()

        for (property in propertyList) {
            if (!property.isHasOne() && !property.isManyToMany() && !property.isManyToMany()
                    && !property.isOneToMany() && property.type != MethodHandler.class
                    && property.type != LazyInitializer.class) {

                // domainObj[property.name] = dtoObj[property.name]
                if (grailsApplication.isDomainClass(property.type)) {
                    Object refObj = property.type.newInstance().invokeMethod("read", dtoObj.getAt(property.name))
                    domainObj[property.name] = refObj

                } else if (property.type == Currency && dtoObj.getAt(property.name) != null) {
                    domainObj[property.name] = Currency.getInstance((String) dtoObj[property.name])
                } else if (property.type == Date && dtoObj.getAt(property.name) != null) {
                    Calendar calendar = GregorianCalendar.getInstance()
                    calendar.setTime(dtoObj[property.name])
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    domainObj[property.name] = calendar.getTime()
                } else if (property.type == Time) {
                    if (dtoObj.getAt(property.name) != null) {
                        domainObj[property.name] = Time.valueOf(dtoObj[property.name])
                    } else {
                        domainObj[property.name] = null
                    }

                } else if (property.type == TimeZone && dtoObj.getAt(property.name) != null) {
                    domainObj[property.name] = TimeZone.getTimeZone(dtoObj[property.name])
                } else {
                    domainObj[property.name] = dtoObj[property.name]
                }
            }
        }
        domainObj.id = dtoObj.id
        domainObj.version = (dtoObj.version == 0) ? null : dtoObj.version

        return domainObj
    }

    public Object domainGenerator2(Class clazz, String id) {
//        Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == className }.clazz
        def object = clazz.newInstance().invokeMethod("read", id)
        return object
    }


}
