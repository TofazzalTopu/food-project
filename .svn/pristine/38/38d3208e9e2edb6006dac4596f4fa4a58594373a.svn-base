package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("listUserAccountControlPanelAction")
class ListUserAccountControlPanelAction implements IAction {

    public static final Log log = LogFactory.getLog(ListUserAccountControlPanelAction.class)

    @Autowired
    UserAccountService userAccountService

    public Object preCondition(def params) {
        return null
    }

    public Object postCondition(def object) {
        return null
    }

    public Object execute(def params, def object) {
        Map map = [:]
        List<Map> mapList = userAccountService.applicationUserControlPanelData(params)
        map.mapList = mapList
        return map
    }
}