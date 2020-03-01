package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.commons.IAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("showUserAccountAction")
class ShowUserAccountAction implements IAction {

    @Autowired
    UserAccountService userAccountService

    Object preCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        return userAccountService.showApplicationUserData(Long.parseLong(params.id))
    }

    Object showApplicationUserRoles(Object params) {
        return userAccountService.showApplicationUserRoles(Long.parseLong(params.id))
    }

    Object listApplicationUserDistributionPoint(Object params) {
        Map result = userAccountService.listApplicationUserDistributionPoint(Long.parseLong(params.id))
        if (result) { // in case: normal list
            objectList = result.objList
            total = result.count
        }
        List objList = this.wrapListInGridEntityList(objectList, start)
        return objList
    }

    Object showApplicationUserEnterprises(Object params) {
        return userAccountService.showApplicationUserEnterprises(Long.parseLong(params.id))
    }

    Object showSelectedRules(Object params) {
        return userAccountService.showSelectedRules(Long.parseLong(params.id))
    }

    boolean checkAvailability(Object params) {
        return userAccountService.checkAvailability(params.username)
    }

    Object checkUserName(Object params) {
        return userAccountService.checkUserName(params.username)
    }
}
