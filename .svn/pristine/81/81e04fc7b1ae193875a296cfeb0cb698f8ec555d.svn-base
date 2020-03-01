package com.docu.app

import com.docu.accesscontrol.ModuleInfo
import com.docu.app.dashboardportlet.*
import com.docu.commons.Message
import com.docu.commons.ModuleInfoUtil
import com.docu.commons.SessionManagementService
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class DashboardPortletController {

    @Autowired
    private CreateDashboardPortletAction createDashboardPortletAction
    @Autowired
    private UpdateDashboardPortletAction updateDashboardPortletAction
    @Autowired
    private ListDashboardPortletAction listDashboardPortletAction
    @Autowired
    private DeleteDashboardPortletAction deleteDashboardPortletAction
    @Autowired
    private ReadDashboardPortletAction readDashboardPortletAction
    @Autowired
    private SearchDashboardPortletAction searchDashboardPortletAction
    SessionManagementService sessionManagementService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
       render listDashboardPortletAction.execute(params, null) as JSON
    }

    def show = {
        List<Long> authorizedModuleInfo = sessionManagementService.getAllowedModuleList()
        List moduleInfoList = []
        authorizedModuleInfo.each {
            ModuleInfo moduleInfo = ModuleInfoUtil.instance.get(it)
            moduleInfoList.add(moduleInfo)
        }
        [moduleInfoList : moduleInfoList]
    }

    def save = {
      Object object = createDashboardPortletAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = createDashboardPortletAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def edit = {
      Map data = (Map) readDashboardPortletAction.execute(params, null)
      render data as JSON
    }

    def update = {
      Object object = updateDashboardPortletAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = updateDashboardPortletAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def delete = {
      Object object = deleteDashboardPortletAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = deleteDashboardPortletAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def search = {
        DashboardPortlet dashboardPortlet = (DashboardPortlet) searchDashboardPortletAction.execute(params, null)
        if(dashboardPortlet){
            render dashboardPortlet as JSON
        }
        else{
            render ''
        }
    }
}
