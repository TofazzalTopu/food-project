package com.docu.accesscontrol

import com.docu.accesscontrol.commons.PluginMessage
import com.docu.accesscontrol.requestmap.action.RequestMapAction
import com.docu.accesscontrol.requestmap.action.ShowRequestMapAction
import org.springframework.beans.factory.annotation.Autowired
//import grails.plugins.springsecurity.SpringSecurityService

class RequestmapController {
  //SpringSecurityService springSecurityService
  @Autowired
  ShowRequestMapAction showRequestMapAction
  @Autowired
  RequestMapAction requestMapAction
  @Autowired
  PluginClassUtil pluginClassUtil

  def index = {
    redirect(action: "create", params: params)
  }

    def create = {
        Class authority = pluginClassUtil.getAuthorityFromConfig()
        List allAuthorityList
        def authorityInstance
        List authorities=[]
        try{
            if (authority) {
                allAuthorityList = authority.list()
                List<ModuleInfo> moduleInfoList = ModuleInfo.list()

                // authorities = springSecurityService.getAuthentication().getAuthorities()
                if (authorities.find { it.role != 'ROLE_SA' }) {
                    authorityInstance = authority.findByAuthority('ROLE_SA')
                    allAuthorityList.remove(authorityInstance)
                    ModuleInfo moduleInstance = ModuleInfo.read(11)
                    if (moduleInstance) {
                        moduleInfoList.remove(moduleInstance)
                    }
                }
                render(view: 'create', model:[authorityList: allAuthorityList, moduleInfoList: moduleInfoList])
            }
        }catch(Exception ex){

        }

    }

    def showRequestMapHead = {
        List featureAction = []
        List checkedAction = []
        Map checkedMap = [:]
        Map actionMap = [:]
        boolean editMode=false
        params.userName = session.getAttribute('user').username
        params.applicationVersion = pluginClassUtil.getAppVersion()
        if (params.moduleid != "0") {
            featureAction = (List) showRequestMapAction.execute(params, null)
            checkedAction = (List) showRequestMapAction.getCheckAction(params)

            featureAction.each {
                if (actionMap.containsKey(it.featureName)) {
                    actionMap[it.featureName].add(it)

                } else {
                    actionMap.put(it.featureName, [it])
                }
            }

            checkedAction.each {
                if (checkedMap.containsKey(it.featureName)) {
                    checkedMap[it.featureName].add(it)

                } else {
                    checkedMap.put(it.featureName, [it])
                }
            }

            if (checkedAction.size()>0 ){
                editMode=true
            }else{
                editMode=false
            }
            render(view: "requestmap", model: ['featureAction': actionMap, 'checkedAction': checkedMap,editMode:editMode])
        } else {
            render(view: "requestmap", model: ['featureAction': featureAction,editMode:editMode])
        }
    }

    def saveRequestMap = {
        PluginMessage msg = null
        Map object = (Map) requestMapAction.preCondition(params)

        if (object.containsKey("actionUrl")) {
            Map result = (Map) requestMapAction.execute(params, object)
            msg = result.get("message")
        }
        else {
            msg = object.get("message")
        }
        if (msg.type == PluginMessage.SUCCESS) {
           //springSecurityService.clearCachedRequestmaps()
            render '{"isError":0, "message":' + msg.toString() + ',"moduleid":' + params.moduleList + '}'
        } else {
            render '{"isError":1, "message":' + msg.toString() + '}'
        }
    }

}
