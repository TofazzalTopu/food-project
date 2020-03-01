package com.docu.accesscontrol.featureinfo.action

import com.docu.accesscontrol.FeatureInfo
import com.docu.accesscontrol.FeatureInfoService
import com.docu.accesscontrol.ModuleInfo
import com.docu.accesscontrol.commons.PluginMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul
 * Date: 2/13/12
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("arrangeFeatureInfoAction")
class ArrangeFeatureInfoAction {
    @Autowired
     FeatureInfoService featureInfoService

    Object preCondition(def params) {

    Map mapInstance = [:]
    FeatureInfo featureInfoInstance

    ModuleInfo moduleInfo = ModuleInfo.read(params.moduleList)


    List<FeatureInfo> featureInfoList = new ArrayList<FeatureInfo>()
    //featureActionList = ObjectUtil.instantiateObjects(params.items, FeatureAction.class)
    featureInfoList = instantiateObjects(params.items,FeatureInfo.class )

    //1 means success
    mapInstance = createMessage("FeatureInfo",1,"FeatureInfo Successfully Committed")
    mapInstance.put("featureInfo", featureInfoList)

//    log.info("DayCloseAccountsRestrictionRuleAction:preCondition::Exit")
    return mapInstance;
  }

  Object execute(Object params, def object) {

    try {
      featureInfoService.arrangeFeatureInfo(object)
    } catch (Exception ex) {
      return createMessage("FeatureInfo",0,object,ex )
    }
    return createMessage("FeatureInfo",1,"FeatureInfo Successfully Committed")
  }


    Object getFeatureInfoList(def params, Object object) {
//    log.info("showFeatureInfoAction::execute::Enter")
        Map mapInstance = [:]
        List<FeatureInfo> featureInfoList = []
        ModuleInfo moduleInfo = ModuleInfo.read(params.id)
        featureInfoList = FeatureInfo.findAllByModuleInfo(moduleInfo)

        if (!featureInfoList.size()) {
            mapInstance = createMessage("FeatureInfo", 0, "No Data Found For This Module")
        }else{
             mapInstance = createMessage("FeatureInfo", 1, "Data Found")
            mapInstance.put("featureInfo",featureInfoList)
        }

//        return featureInfoList
        return mapInstance
    }

  private static Map createMessage(String title, int messageType, String messageDesc){
    Map map = [:]
    PluginMessage message = new PluginMessage()
    message.type=messageType
    message.messageTitle = title
    message.messageBody.add(messageDesc)
    map.put("message", message)
    return map
  }

  public static List instantiateObjects(Map data, Class className) {
    List list = []
    data.each {key, val ->
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
}
