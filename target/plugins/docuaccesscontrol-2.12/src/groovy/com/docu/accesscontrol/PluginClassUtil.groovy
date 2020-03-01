package com.docu.accesscontrol

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/24/11
 * Time: 8:09 PM
 * To change this template use File | Settings | File Templates.
 */

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.stereotype.Component

@Component("pluginClassUtil")
class PluginClassUtil {

  // set at startup
  static GrailsApplication application

  Class getRequestmapFromConfig() {
    ConfigObject applicationHolder = ApplicationHolder.application.config
    String requestMapClassPath = applicationHolder.grails.plugins.springsecurity.requestMap.className
    Class Requestmap = application.getClassForName(requestMapClassPath)
    if (!Requestmap) {
      throw new IllegalStateException(
              'Cannot load Requestmaps, "requestMap.className" property is not set')
    }
    return Requestmap
  }

    Class getAuthorityFromConfig() {
        ConfigObject applicationHolder = ApplicationHolder.application.config
        String authorityClassPath = applicationHolder.grails.plugins.springsecurity.authority.className

        Class Authority = application.getClassForName(authorityClassPath)
        if (!Authority) {
            throw new IllegalStateException(
                    'Cannot load Authority, "authority.className" property is not set')
        }
        return Authority
    }

    String getAppVersion() {
        return ApplicationHolder.application.metadata['app.version']
    }
}
