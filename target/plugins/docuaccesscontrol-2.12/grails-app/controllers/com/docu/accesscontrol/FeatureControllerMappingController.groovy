package com.docu.accesscontrol

import com.docu.accesscontrol.commons.PluginGrailsClassUtil
import com.docu.accesscontrol.commons.PluginMessage
import com.docu.accesscontrol.featurecontrollermapping.action.FeatureControllerMappingAction
import grails.converters.JSON
import groovy.sql.GroovyRowResult
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.springframework.beans.factory.annotation.Autowired

class FeatureControllerMappingController {
    @Autowired
    PluginGrailsClassUtil pluginGrailsClassUtil
    @Autowired
    FeatureControllerMappingAction featureControllerMappingAction

    FeatureControllerMappingService featureControllerMappingService

    def index = {
        redirect(action: "create", params: params)
        pluginGrailsClassUtil.resolve()
    }

    def create = {

    }

    def getFeatureListByModule = {
        ModuleInfo moduleInfo = ModuleInfo.read(params?.moduleId)
        List<FeatureInfo> featureInfoList = FeatureInfo.findAllByModuleInfo(moduleInfo, [sort: "featureName", order: "asc"])
        List data = featureInfoList.collect { [id: it.id, featureName: it.featureName] }
        String output = data as JSON
        render output
    }

    def getActionListByFeature = {
        FeatureInfo featureInfo = FeatureInfo.read(params?.featureId)
        List<FeatureAction> featureActionList = FeatureAction.findAllByFeatureInfo(featureInfo)
        List data = featureActionList.collect { [id: it.id, actionName: it.actionName] }
        String output = data as JSON
        render output
    }

    def ajaxControllerHead = {
        pluginGrailsClassUtil.resolve()
//        FeatureAction featureAction = FeatureAction.read(params.actionId)
        List<GroovyRowResult> featureControllerMappingListDb = featureControllerMappingService.getFeatureControllerName(Long.parseLong(params.actionId))
        List<GrailsControllerClass> controllerClassList = pluginGrailsClassUtil.controllerClassList()

        if (featureControllerMappingListDb.size()) {
            render(view: "controllerHead", model: [controllerClassList: controllerClassList, featureControllerClassListDb: featureControllerMappingListDb.collect { [fullName: it.controllerName, shortName: it.controllerName.split(/\./).last()] }])
        } else {
            render(view: "controllerHead", model: [controllerClassList: controllerClassList, featureControllerMappingListDb: featureControllerMappingListDb])
        }

    }

    def loadControllerAction = {
        Map map = [:]
        List selectedActionsOfController = []
        if (params.url != null && params.url != "null") {
            // List actionList
            map = pluginGrailsClassUtil.getActionList(params.url)

            selectedActionsOfController = FeatureControllerMapping.findAll("from FeatureControllerMapping fcm where fcm.controllerName='${params.url}' and fcm.controllerAction.featureAction.id='${params.actionid}'").collect { it.controllerAction }
        }
        if (selectedActionsOfController.size() > 0) {
            render(view: 'actionSelect', model: [map: map, selected: selectedActionsOfController, 'j': params.index])
        } else {
            render(view: 'actionSelect', model: [map: map, selected: ["NoSelection"], 'j': params.index])
        }
    }


    def saveFeatureControllerMapping = {
        PluginMessage msg = null
        Map object = (Map) featureControllerMappingAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == PluginMessage.SUCCESS) {
            def result = featureControllerMappingAction.execute(null, object)
            msg = result.get("message")
        }
        if (msg.type == PluginMessage.SUCCESS) {
            render '{"isError":0, "message":' + msg.toString() + ' ,"actionId":' + params.actionList + '}'
        } else {
            render '{"isError":1, "message":' + msg.toString() + ' }'
        }
    }


}

