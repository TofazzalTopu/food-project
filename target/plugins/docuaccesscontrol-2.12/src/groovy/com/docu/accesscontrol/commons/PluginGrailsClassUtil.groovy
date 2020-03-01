package com.docu.accesscontrol.commons

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: rafiqul.islam
 * Date: 4/12/11
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("pluginGrailsClassUtil")
class PluginGrailsClassUtil {

    private List<GrailsDomainClass> domainClassList = []
    private List<GrailsControllerClass> controllerClassList = []
    private List<String> appUrlList = []

    @Autowired
    GrailsApplication grailsApplication

    public void resolve() {
        domainClassList = (List<GrailsDomainClass>) grailsApplication.getArtefacts("Domain")
//        domainClassList.removeAll { !it.packageName.startsWith("com.docu.") }
        domainClassList.sort { it.shortName }
        controllerClassList = (List<GrailsControllerClass>) grailsApplication.getArtefacts("Controller")
//        controllerClassList.removeAll {
//            !it.packageName.startsWith("com.docu.") && !it.packageName.startsWith("org.codehaus.groovy.grails.plugins.jasper")
//        }
        controllerClassList.sort { it.shortName }

        List urlList = controllerClassList.viewNames
        urlList.each { controllers ->
            controllers.each {
                appUrlList.add(it.value.toString().replaceFirst('/', ''))
            }
        }
    }

    public void destroy() {
        domainClassList = null
        controllerClassList = null
        appUrlList = null
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
        if (clazz) {
            //return clazz.viewNames
            return clazz.uri2closureMap
        }
        return mapInstance
    }

    public List getActionViewNamesList() {
        Map mapInstance = [:]
        List clazzList = controllerClassList
        return clazzList.viewNames
    }

    public List<String> getApplicationUrlList() {
        return appUrlList
    }
}
