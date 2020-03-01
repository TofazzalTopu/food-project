package com.docu.accesscontrol.requestmap.action

import com.docu.accesscontrol.commons.PluginMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.accesscontrol.RequestMapService
import com.docu.accesscontrol.commons.PluginMessage

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/16/11
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("requestMapAction")
class RequestMapAction {
    //public static final Log log = LogFactory.getLog(Requestmap.class)
    @Autowired
    RequestMapService requestMapService

    Object preCondition(def params) {
        List allActionUrl = []
        List actionIds = []
        Map mapInstance = [:]
        List<Map> data = getRequestMapData(params.items)
        data.each { itemMap ->
            if (itemMap.actions) {
                itemMap.actions.each { action ->
                    List actionUrl = (List) requestMapService.getControllerActionUrl(params, action, itemMap.featureName)
                    actionUrl.each {
                        allActionUrl.add(it.controllerAction + ',' + it.featureControllerMappingId)
                    }
                }

            }

        }
        //to get featureAction id for menuMap
        List<Map> idData = getRequestMapData(params.items)
        idData.each { itemMap ->
            if (itemMap.actions) {
                itemMap.actions.each { action ->
                    List actionList = (List) requestMapService.getFeatureActionId(params, action, itemMap.featureName)
                    actionList.each {
                        actionIds.add(it.id)
                    }

                }

            }

        }

        if (params.editMode == 'true') {
            mapInstance = createMessage("RequestMap", 1, "Request Map Saved Successfully")
            mapInstance.put("actionUrl", allActionUrl)
            mapInstance.put("actionIds", actionIds)

        } else {
            if (allActionUrl.size() > 0) {
                mapInstance = createMessage("RequestMap", 1, "Request Map Saved Successfully")
                mapInstance.put("actionUrl", allActionUrl)
                mapInstance.put("actionIds", actionIds)
            } else {
                mapInstance = createMessage("RequestMap", 0, "There is no Feature mapping for the feature action")
                return mapInstance
            }
        }
        return mapInstance
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        try {
            requestMapService.save(params, object)
        } catch (Exception ex) {
//      log.error("RequestMapAction::execute::" + ex.message)
            return createMessage("Request Map", 0, object, ex)
        }
        return createMessage("RequestMap", 1, "Request Map Saved Successfully")
    }

    // any exception
    private static Map createMessage(String title, int messageType, def object, Exception ex) {
        Map map = [:]
        PluginMessage message = new PluginMessage()
        message.type = messageType
        message.messageTitle = title
        message.messageBody.add(ex.message)
        map.put(PluginMessage.MESSAGE, message)
        map.put(PluginMessage.OBJECT, object)
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

    private List<Map> getRequestMapData(Map items) {
        List list = []
        Map map = [:]
        items.each { key, val ->
            if (val instanceof java.util.Map) {
                map = [:]
                map['featureName'] = val.featureName
                map['actions'] = []
                if (val.actions != null) {
                    val.actions.each { thisKey, thisVal ->
                        if (thisVal instanceof java.util.Map) {
                            map['actions'].add(thisVal.featureAction)
                        }
                    }
                }
                list.add(map)
            }
        }

        return list
    }
}
