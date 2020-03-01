package com.docu.app.dashboardportlet

import com.docu.app.DashboardPortlet
import com.docu.app.DashboardPortletService
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("createDashboardPortletAction")
class CreateDashboardPortletAction implements IAction {
  public static final Log log = LogFactory.getLog(CreateDashboardPortletAction.class)
  private final String MESSAGE_HEADER = 'dashboardPortlet.header'
  private final String MESSAGE_SUCCESS = 'dashboardPortlet.save.success'

  @Autowired
  DashboardPortletService dashboardPortletService

  public Object preCondition(def params) {
    Map mapInstance = [:]
    DashboardPortlet dashboardPortlet = null
    try {
        dashboardPortlet = new DashboardPortlet()
        dashboardPortlet.properties = params
        
        
        if (!dashboardPortlet.validate()) {
            return UserMessageBuilder.createMessage(dashboardPortletService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, dashboardPortletService.getErrorMessage(dashboardPortlet), dashboardPortlet)
        }
        mapInstance = (Map) UserMessageBuilder.createMessage(dashboardPortletService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(DashboardPortlet.class.simpleName, dashboardPortlet)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(dashboardPortletService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, dashboardPortlet, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        DashboardPortlet dashboardPortlet = null
        try {
            dashboardPortlet = object.get(DashboardPortlet.class.simpleName)
            dashboardPortletService.save(dashboardPortlet)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(dashboardPortletService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, dashboardPortlet, ex)
        }

        return UserMessageBuilder.createMessage(dashboardPortletService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, dashboardPortletService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}