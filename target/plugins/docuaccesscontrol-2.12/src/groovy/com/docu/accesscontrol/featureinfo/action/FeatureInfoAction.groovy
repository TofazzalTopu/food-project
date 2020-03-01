package com.docu.accesscontrol.featureinfo.action

import com.docu.accesscontrol.FeatureAction
import com.docu.accesscontrol.FeatureInfo
import com.docu.accesscontrol.FeatureInfoService
import com.docu.accesscontrol.ModuleInfo
import com.docu.accesscontrol.commons.PluginMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/15/11
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("featureInfoAction")
class FeatureInfoAction {
//  public static final Log log = LogFactory.getLog(FeatureInfoAction.class)

    @Autowired
    FeatureInfoService featureInfoService

    Object preCondition(def params) {

        Map mapInstance = [:]
        FeatureInfo featureInfoInstance
        if (params.featureInfo.id != "") {
            featureInfoInstance = FeatureInfo.read(params.featureInfo.id)
        } else {
            featureInfoInstance = new FeatureInfo()
        }

        ModuleInfo moduleInfo = ModuleInfo.read(params.moduleList)
        featureInfoInstance.featureName = params.featureInfo.featureName
        featureInfoInstance.description = params.featureInfo.description

        featureInfoInstance.moduleInfo = moduleInfo

        List<FeatureAction> featureActionList = new ArrayList<FeatureAction>()
        //featureActionList = ObjectUtil.instantiateObjects(params.items, FeatureAction.class)
        featureActionList = instantiateObjects(params.items, FeatureAction.class)

        List nFeatureActions = []

        for (FeatureAction featureAction : featureActionList) {
            featureAction.featureInfo = featureInfoInstance
            String strCss = featureAction.menuUrl.toString().replace('/', '_')
            featureAction.imageCss = strCss
            if (!featureAction.validate()) {
                mapInstance = createMessage("FeatureInfo", 0, "Error Occured During Data Submit")
                return mapInstance
            }
            nFeatureActions.add(featureAction)
        }
        if (!featureInfoInstance.validate()) {
            mapInstance = createMessage("FeatureInfo", 0, "Error Occured During Data Submit")
            return mapInstance
        }
        //1 means success
        mapInstance = createMessage("FeatureInfo", 1, "FeatureInfo Successfully Committed")
        mapInstance.put("featureInfo", featureInfoInstance)
        mapInstance.put("featureAction", nFeatureActions)
//    log.info("DayCloseAccountsRestrictionRuleAction:preCondition::Exit")
        return mapInstance;
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, def object) {

        try {
            featureInfoService.save(object)
        } catch (Exception ex) {
            return createMessage("FeatureInfo", 0, object, ex)
        }
        return createMessage("FeatureInfo", 1, "FeatureInfo Successfully Committed")
    }

    public static List instantiateObjects(Map data, Class className) {
        List list = []
        data.each { key, val ->
            if (val instanceof java.util.Map) {
                def object = null
                if (val.containsKey("id") && val.id != '') {
                    object = className.newInstance().invokeMethod("read", val.id.toString())
                } else {
                    object = className.newInstance()
                }
                object.properties = val

                list.add(object)
            }
        }

        return list
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

}
