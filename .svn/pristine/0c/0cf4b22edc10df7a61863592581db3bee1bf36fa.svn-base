package com.docu.commons

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsControllerClass

/**
 * Created by IntelliJ IDEA.
 * User: rafiqul.islam
 * Date: 4/12/11
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("grailsClassUtil")
class GrailsClassUtil {

  private List<GrailsDomainClass> domainClassList = []
  private List<GrailsControllerClass> controllerClassList = []

  @Autowired
  GrailsApplication grailsApplication

  public void resolve() {
    domainClassList = grailsApplication.getArtefacts(CommonConstants.GRAILS_DOMAIN)
    domainClassList.removeAll { it.packageName.startsWith(CommonConstants.APPLICATION_PACKAGE) == false }
    domainClassList.sort { it.shortName }
    controllerClassList = grailsApplication.getArtefacts(CommonConstants.GRAILS_CONTROLLER)
    controllerClassList.removeAll { it.packageName.startsWith(CommonConstants.APPLICATION_PACKAGE) == false }
    controllerClassList.sort { it.shortName }
  }

  public List<GrailsDomainClass> domainClassList() {
    return domainClassList
  }

  public GrailsDomainClass getDomainClass(long id) {
    return domainClassList.get(id)
  }

  public List<GrailsControllerClass> controllerClassList() {
    return controllerClassList
  }

  public GrailsControllerClass getGrailsControllerClass(long id) {
    return controllerClassList.get(id)
  }

  public Map getActionList(String className) {
    Map mapInstance = [:]
    GrailsControllerClass clazz = controllerClassList.find { it.fullName == className }
    if (clazz){
      return clazz.viewNames
    }
    return mapInstance
  }
}
