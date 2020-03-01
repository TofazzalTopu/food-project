package com.docu.accesscontrol.menuGroup.action

import org.springframework.stereotype.Component
import com.docu.accesscontrol.MenuGroup
import com.docu.accesscontrol.commons.PluginMessage
import org.springframework.beans.factory.annotation.Autowired
import com.docu.accesscontrol.MenuGroupService

/**
 * Created by IntelliJ IDEA.
 * User: bipul
 * Date: 3/5/12
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("saveMenuGroupSortingAction")
class SaveMenuGroupSortingAction {
    @Autowired
    MenuGroupService menuGroupService

    Object preCondition(def params) {
       Map mapInstance=[:]
         List list = []
        params['items'].each {key, val ->
            if (val instanceof java.util.Map) {
                list.add(val)
            }
        }

        List<MenuGroup> menuGroupList= []
        MenuGroup menuGroup = null
        list.each {Map data ->
            menuGroup = MenuGroup.read(Long.parseLong(data.id));
            menuGroup.position = Long.parseLong(data.position)
            menuGroupList.add(menuGroup)
        }

//        if (!menuGroup.validate()) {
//            return createMessage("MenuGroupSorting", 0, "Error Occured, MenuGroupSrorting Data")
//        }
        //1 means success
        mapInstance = createMessage("MenuGroupSrorting", 1, "MenuGroupSrorting Data")
        mapInstance.put("menuGroupList", menuGroupList)
//    log.info("DayCloseAccountsRestrictionRuleAction:preCondition::Exit")
        return mapInstance;
    }

    Object execute(Object params, def object) {

        try {
            menuGroupService.saveMenuGroupSorting(object)
        } catch (Exception ex) {
            return createMessage("MenuGroupSrorting", 0, object, ex)
        }
        return createMessage("MenuGroupSrorting", 1, "MenuGroup Sorted Successfully")
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
}
