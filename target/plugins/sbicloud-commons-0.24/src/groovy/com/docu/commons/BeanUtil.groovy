package com.docu.commons

import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import javassist.util.proxy.MethodHandler
import org.hibernate.proxy.LazyInitializer

class BeanUtil {
  public static final Log log = LogFactory.getLog(BeanUtil.class);

  public static Object cloneDomainObject(Object objInstance) {
    GrailsDomainClassProperty[] propertyList = null
    propertyList = new DefaultGrailsDomainClass(objInstance.class).getPersistantProperties()
    Object clonedObject = objInstance.class.newInstance()

    for (property in propertyList) {
      if (!property.isHasOne() && !property.isManyToMany() && !property.isManyToMany()
              && !property.isOneToMany() && property.type != MethodHandler.class
              && property.type != LazyInitializer.class) {
        clonedObject[property.name] = objInstance[property.name]
      }
    }
    return clonedObject
  }

  public static Object cloneDomainObjectWIthChild(Object objInstance) {
    GrailsDomainClassProperty[] propertyList = null
    propertyList = new DefaultGrailsDomainClass(objInstance.class).getPersistantProperties()
    Object clonedObject = objInstance.class.newInstance()

    Set childList = new HashSet()

    for (property in propertyList) {
      if (!property.isHasOne()
              && property.type != MethodHandler.class
              && property.type != LazyInitializer.class) {
        if (property.isOneToMany()) {
          childList = new HashSet()
          if (objInstance[property.name] != null) {
            objInstance[property.name].each{obj->
              childList.add(obj)
            }
          }else {
            clonedObject[property.name] = new HashSet()
          }
          clonedObject[property.name] = childList
        } else {
          clonedObject[property.name] = objInstance[property.name]
        }
      }
    }
    return clonedObject
  }
}