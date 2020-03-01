package com.docu.accesscontrol

import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired
import com.docu.accesscontrol.menuGroup.action.AjaxMenuGroupAction
import com.docu.accesscontrol.commons.PluginMessage
import com.docu.accesscontrol.menuGroup.action.SaveMenuGroupAction
import org.springframework.web.context.request.RequestContextHolder
import com.docu.accesscontrol.menuGroup.action.SaveMenuGroupSortingAction
import com.docu.accesscontrol.menuGroup.action.SaveMenuItemSortingAction

class MenuGroupController {
    MenuGroupService menuGroupService

    @Autowired
    AjaxMenuGroupAction ajaxMenuGroupAction
    @Autowired
    SaveMenuGroupAction saveMenuGroupAction
    @Autowired
    SaveMenuGroupSortingAction saveMenuGroupSortingAction
    @Autowired
    SaveMenuItemSortingAction saveMenuItemSortingAction

    def index = {
       redirect(action: "create", params: params)
    }

    def create = {

    }

    def edit = {
//        println params
        [moduleId:params.moduleInfoId ,title:params.title,menugroupid:params.id ]
    }
    def save = {
        PluginMessage msg = null
        Map object = (Map) saveMenuGroupAction.preCondition(params)
        if (object.containsKey("menugroup")) {
            def result = saveMenuGroupAction.execute(null, object)
            msg = result.get("message")
        }
        else {
            msg = object.get("message")
        }
        if (msg.type == PluginMessage.SUCCESS) {
            //accessControlSessionManagementService.message .message = msg.toString()
            RequestContextHolder.currentRequestAttributes().getSession().message = msg.toString()

            render '{"isError":0, "message":' + msg.toString() + '}'
        } else {
            render '{"isError":1, "message":' + msg.toString() + ' }'
        }


        //------------
//        MenuGroup menuGroup = null
//        MenuItem menuItem = null
//        List<MenuItem> menuItemList = []
//
//        if (params.id == null){
//            menuGroup = new MenuGroup()
//        }
//        else{
//            menuGroup = MenuGroup.read(params.id)
//        }
//        menuGroup.properties = params
//
//        Object paramsIds = params['featureInfoIds']
//        List incomingIdList = new ArrayList()
//        if (params['featureInfoIds'] instanceof String) {
//            incomingIdList.add(paramsIds)
//        }
//        else if (params['featureInfoIds'] instanceof String[]) {
//            incomingIdList = (List) paramsIds
//        }
//
//        incomingIdList.each {
//            menuItem = new MenuItem()
//            menuItem.featureInfoId = Long.parseLong(it.toString())
//            menuItemList.add(menuItem)
//        }
//
//        menuGroupService.save(menuGroup, menuItemList)
//        render '{"isError":0}'
    }

    def sort = {
        String message = RequestContextHolder.currentRequestAttributes().getSession().message
       [moduleInformatonId:params.id, message:message]
    }

    def saveMenuGroupSorting = {

        PluginMessage msg = null
        Map object = (Map) saveMenuGroupSortingAction.preCondition(params)
        if (object.containsKey("menuGroupList")) {
            def result = saveMenuGroupSortingAction.execute(null, object)
            msg = result.get("message")
        }
        else {
            msg = object.get("message")
        }
        if (msg.type == PluginMessage.SUCCESS) {
            //accessControlSessionManagementService.message .message = msg.toString()
            RequestContextHolder.currentRequestAttributes().getSession().message = msg.toString()

            render '{"isError":0, "message":' + msg.toString() + '}'
        } else {
            render '{"isError":1, "message":' + msg.toString() + ' }'
        }


//        List list = []
//        params['items'].each {key, val ->
//            if (val instanceof java.util.Map) {
//                list.add(val)
//            }
//        }
//
//        List<MenuGroup> menuGroupList= []
//        MenuGroup menuGroup = null
//        list.each {Map data ->
//            menuGroup = MenuGroup.read(Long.parseLong(data.id));
//            menuGroup.position = Long.parseLong(data.position)
//            menuGroupList.add(menuGroup)
//        }
//
//        menuGroupService.saveMenuGroupSorting(menuGroupList)
//        render '{"isError":0}'
    }

    def saveMenuItemSorting = {
        PluginMessage msg = null
        Map object = (Map) saveMenuItemSortingAction.preCondition(params)
        if (object.containsKey("menuItemList")) {
            def result = saveMenuItemSortingAction.execute(null, object)
            msg = result.get("message")
        }
        else {
            msg = object.get("message")
        }
        if (msg.type == PluginMessage.SUCCESS) {
            //accessControlSessionManagementService.message .message = msg.toString()
            RequestContextHolder.currentRequestAttributes().getSession().message = msg.toString()

            render '{"isError":0, "message":' + msg.toString() + '}'
        } else {
            render '{"isError":1, "message":' + msg.toString() + ' }'
        }
//        List list = []
//        params['items'].each {key, val ->
//            if (val instanceof java.util.Map) {
//                list.add(val)
//            }
//        }
//
//        List<MenuItem> menuItemList= []
//        MenuItem menuItem = null
//        list.each {Map data ->
//            menuItem = MenuItem.read(Long.parseLong(data.id));
//            menuItem.position = Long.parseLong(data.position)
//            menuItemList.add(menuItem)
//        }
//
//        menuGroupService.saveMenuItemSorting(menuItemList)
//        render '{"isError":0}'
    }

    def ajaxFeatureInfoList = {
        List selectedFeatureInfoList=[]
        ModuleInfo moduleInfo = (ModuleInfo)ajaxMenuGroupAction.getModuleInfo(params)
        List<FeatureInfo> featureInfoList = (List<FeatureInfo>) ajaxMenuGroupAction.getFeatureInfo(moduleInfo)
        if (params.id) {
            if (featureInfoList.size()) {
//            params.id = 6
                List<FeatureInfo> featureInfos = (List<FeatureInfo>) ajaxMenuGroupAction.getSelectedFeatureInfo(params)
                featureInfos.each {
                    featureInfoList.each {items ->
                        if (it.feature_info_id == items.id) {
                            selectedFeatureInfoList.add(items)
                        }
                    }

                }
                render(view: 'ajaxFeatureInfoList', model: [featureInfoList: featureInfoList, selectedFeatureInfoList: selectedFeatureInfoList])
            } else {
                render(view: 'ajaxFeatureInfoList', model: [])
            }
        }else{
           render(view: 'ajaxFeatureInfoList', model: [featureInfoList: featureInfoList])
        }


    }

    def ajaxMenuGroupListByModuleInfo = {
        List<MenuGroup> menuGroupList = MenuGroup.findAllByModuleInfoId(params.moduleInfoId).sort {it.position}
        render(view: 'ajaxMenuGroupListByModuleInfo', model: [menuGroupList:menuGroupList])
    }

    def ajaxMenuItemListByMenuGroup = {
        MenuGroup menuGroup = MenuGroup.read(params.menuGroupId)
        List<GroovyRowResult> menuItemList = ajaxMenuGroupAction.getMenuItemListByMenuGroupId(Long.parseLong(params.menuGroupId))
        render(view: 'ajaxMenuItemListByMenuGroup', model: [menuGroup:menuGroup, menuItemList:menuItemList])
    }
}
