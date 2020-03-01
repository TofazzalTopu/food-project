package com.docu.accesscontrol.menuGroup.action

import org.springframework.stereotype.Component
import com.docu.accesscontrol.MenuGroup
import org.springframework.beans.factory.annotation.Autowired
import com.docu.accesscontrol.MenuGroupService
import com.docu.accesscontrol.commons.PluginMessage
import com.docu.accesscontrol.MenuItem

/**
 * Created by IntelliJ IDEA.
 * User: bipul
 * Date: 3/5/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("saveMenuItemSortingAction")
class SaveMenuItemSortingAction {
    @Autowired
    MenuGroupService menuGroupService

    Object preCondition(def params) {
        Map mapInstance = [:]
        List list = []
        params['items'].each {key, val ->
            if (val instanceof java.util.Map) {
                list.add(val)
            }
        }

        List<MenuItem> menuItemList= []
        MenuItem menuItem = null
        list.each {Map data ->
            menuItem = MenuItem.read(Long.parseLong(data.id));
            menuItem.position = Long.parseLong(data.position)
            menuItemList.add(menuItem)
        }

        //1 means success
        mapInstance = createMessage("MenuItemSrorting", 1, "MenuItemSrorting Data")
        mapInstance.put("menuItemList", menuItemList)
//    log.info("DayCloseAccountsRestrictionRuleAction:preCondition::Exit")
        return mapInstance;
    }

    Object execute(Object params, def object) {

        try {
            menuGroupService.saveMenuItemSorting(object)
        } catch (Exception ex) {
            return createMessage("MenuItemSrorting", 0, object, ex)
        }
        return createMessage("MenuItemSrorting", 1, "MenuItem Sorted Successfully")
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
