package com.docu.security.applicationuser.action

import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.docu.security.ApplicationUserService

@Component("listApplicationUserControlPanelAction")
class ListApplicationUserControlPanelAction implements IAction {

    public static final Log log = LogFactory.getLog(ListApplicationUserControlPanelAction.class)

    @Autowired
    ApplicationUserService applicationUserService

    public Object preCondition(def params) {
        return null
    }

    public Object postCondition(def object) {
        return null
    }

    public Object execute(def params, def object) {
        Map map = [:]
        List<Map> mapList = applicationUserService.applicationUserControlPanelData(params)
        map.mapList = mapList
        return map
    }
}