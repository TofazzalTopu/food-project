package com.docu.accesscontrol.featurecontrollermapping.action

import com.docu.accesscontrol.FeatureControllerMappingService

import com.docu.accesscontrol.FeatureAction
import com.docu.accesscontrol.FeatureControllerMapping
import com.docu.accesscontrol.commons.PluginMessage
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/16/11
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("featureControllerMappingAction")
class FeatureControllerMappingAction {
    @Autowired
    com.docu.accesscontrol.FeatureControllerMappingService featureControllerMappingService

    Object preCondition(def params) {
        Map mapInstance = [:]
        List<FeatureControllerMapping> featureControllerMappingList = []
        params.items.each { key, val ->
            if (val instanceof Map) {
                if (val.isDeleted == 'false') {
                    GrailsParameterMap thisControllerAndActions = val
                    String controllerName = thisControllerAndActions.controllerName
                    def controllerActions = thisControllerAndActions.controllerAction
                    FeatureAction featureAction = FeatureAction.read(params.actionList)
                    if (controllerActions instanceof String) {
                        FeatureControllerMapping featureControllerMapping = buildFeatureControllerMapping(featureAction, controllerName, controllerActions)
                        featureControllerMappingList.add(featureControllerMapping)
                    } else if (controllerActions instanceof String[]) {
                        for (String controllerAction : controllerActions) {
                            FeatureControllerMapping featureControllerMapping = buildFeatureControllerMapping(featureAction, controllerName, controllerAction)
                            featureControllerMappingList.add(featureControllerMapping)
                        }
                    }
                }
            }

        }
        for (FeatureControllerMapping featureControllerMapping : featureControllerMappingList) {
            if (!featureControllerMapping.validate()) {
                mapInstance = createMessage("FeatureControllerMapping", 0, "Error Occured During Data Submit")
                return mapInstance
            }
        }
        mapInstance = createMessage("FeatureControllerMapping", 1, "Feature Controller Mapping Successfully Submitted")
        mapInstance.put("featureControllerMapping", featureControllerMappingList)
        mapInstance.put("featureActionId", params.actionList)

        return mapInstance
    }

    Object postCondition(Object object) {
        return null
    }

    Object execute(Object params, Object object) {
        Map data = (Map) object
        try {
            featureControllerMappingService.save(data)
        }
        catch (Exception ex) {
            return createMessage("FeatureControllerMapping", 0, object, ex)
        }
        return createMessage("FeatureInfo", 1, "Feature Controller Mapping Successfully Committed")
    }

    private FeatureControllerMapping buildFeatureControllerMapping(FeatureAction featureAction, String controllerName, String controllerAction) {
        FeatureControllerMapping featureControllerMapping = new FeatureControllerMapping()
        featureControllerMapping.featureAction = featureAction
        featureControllerMapping.controllerName = controllerName
        featureControllerMapping.controllerAction = controllerAction
        return featureControllerMapping
    }

    // any exception

    private static Map createMessage(String title, int messageType, def object, Exception ex) {
        Map map = [:]
        PluginMessage message = new PluginMessage()
        message.type = messageType
        message.messageTitle = title
        message.messageBody.add(ex.message)
        map.put(com.docu.accesscontrol.commons.PluginMessage.MESSAGE, message)
        map.put(com.docu.accesscontrol.commons.PluginMessage.OBJECT, object)
        return map
    }

    //success

    private static Map createMessage(String title, int messageType, String messageDesc) {
        Map map = [:]
        PluginMessage message = new PluginMessage()
        message.type = messageType
        message.messageTitle = title
        message.messageBody.add(messageDesc)
        map.put("message", message)
        return map
    }
}
