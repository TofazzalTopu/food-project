package com.docu.app.dashboardportlet

import com.docu.app.DashboardPortlet
import com.docu.app.DashboardPortletService
import com.docu.commons.IAction
import com.docu.commons.ObjectUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("readDashboardPortletAction")
class ReadDashboardPortletAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadDashboardPortletAction.class)
  private final String MESSAGE_HEADER = 'dashboardPortlet.header'
  private final String MESSAGE_SUCCESS = 'dashboardPortlet.update.success'

  @Autowired
  DashboardPortletService dashboardPortletService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    DashboardPortlet dashboardPortlet = dashboardPortletService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(dashboardPortlet)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}