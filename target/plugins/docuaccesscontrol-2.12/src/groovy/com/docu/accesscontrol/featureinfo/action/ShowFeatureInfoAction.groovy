package com.docu.accesscontrol.featureinfo.action

//import com.docu.commons.UserMessageBuilder


import com.docu.accesscontrol.FeatureInfo
import com.docu.accesscontrol.ModuleInfo
import com.docu.accesscontrol.commons.PluginMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/15/11
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("showFeatureInfoAction")
class ShowFeatureInfoAction {
    //public static final Log log = LogFactory.getLog(ShowFeatureInfoAction.class)

    @Autowired
    com.docu.accesscontrol.FeatureInfoService featureInfoService

    Object preCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(def params, Object object) {
        FeatureInfo featureInfo = null
        featureInfo = FeatureInfo.read(params.id)
        if (!featureInfo) {
            return createMessage("FeatureInfo", 0, "Error Occurred During Data Preview")
        }
        return featureInfo
    }

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
