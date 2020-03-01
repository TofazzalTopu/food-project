package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.commons.IAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("listUserAccountAction")
class ListUserAccountAction implements IAction {

    @Autowired
    UserAccountService userAccountService

    Object preCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        return userAccountService.getDataForGrid()
    }
}
