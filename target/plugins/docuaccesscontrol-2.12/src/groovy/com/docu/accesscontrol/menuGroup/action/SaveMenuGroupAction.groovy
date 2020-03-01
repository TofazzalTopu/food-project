package com.docu.accesscontrol.menuGroup.action

import org.springframework.stereotype.Component
import com.docu.accesscontrol.MenuItem
import com.docu.accesscontrol.MenuGroup
import com.docu.accesscontrol.commons.PluginMessage
import org.springframework.beans.factory.annotation.Autowired
import com.docu.accesscontrol.MenuGroupService

/**
 * Created by IntelliJ IDEA.
 * User: bipul
 * Date: 2/29/12
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("MenuGroupAction")
class SaveMenuGroupAction {
    @Autowired
    MenuGroupService menuGroupService

    Object preCondition(def params) {

        Map mapInstance = [:]
        MenuGroup menuGroup = null
        MenuItem menuItem = null
        List<MenuItem> menuItemList = []

        if (params.id == null || params.id=='') {
            menuGroup = new MenuGroup()
        }
        else {
            menuGroup = MenuGroup.read(params.id)
        }
        menuGroup.properties = params

        Object paramsIds = params['featureInfoIds']
        List incomingIdList = new ArrayList()
        if (params['featureInfoIds'] instanceof String) {
            incomingIdList.add(paramsIds)
        }
        else if (params['featureInfoIds'] instanceof String[]) {
            incomingIdList = (List) paramsIds
        }

        incomingIdList.each {
            menuItem = new MenuItem()
            menuItem.featureInfoId = Long.parseLong(it.toString())
            menuItemList.add(menuItem)
        }

        if (!menuGroup.validate()) {
            return createMessage("MenuGroupInfo", 0, "Error Occurred, MenuGroupInfo Data")
        }
        //1 means success
        mapInstance = createMessage("MenuGroupInfo", 1, "MenuGroupInfo Data")
        mapInstance.put("menugroup", menuGroup)
        mapInstance.put("menuitems", menuItemList)
//    log.info("DayCloseAccountsRestrictionRuleAction:preCondition::Exit")
        return mapInstance;
    }

    Object execute(Object params, def object) {

        try {
            menuGroupService.save(object)
        } catch (Exception ex) {
            return createMessage("MenuGroupInfo", 0, object, ex)
        }
        return createMessage("MenuGroupInfo", 1, "MenuGroupInfo Saved Successfully")
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
