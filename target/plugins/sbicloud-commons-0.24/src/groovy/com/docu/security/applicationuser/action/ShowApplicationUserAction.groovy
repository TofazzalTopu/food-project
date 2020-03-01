package com.docu.security.applicationuser.action

import com.docu.commons.IAction
import org.springframework.beans.factory.annotation.Autowired
import com.docu.security.ApplicationUserService
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 6/11/11
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("showApplicationUserAction")
class ShowApplicationUserAction implements IAction {

    @Autowired
    ApplicationUserService applicationUserService

    Object preCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        return applicationUserService.showApplicationUserData(Long.parseLong(params.id))
    }

    Object showApplicationUserRoles(Object params) {
        return applicationUserService.showApplicationUserRoles(Long.parseLong(params.id))
    }

    Object showSelectedRules(Object params) {
        return applicationUserService.showSelectedRules(Long.parseLong(params.id))
    }

    boolean checkAvailability(Object params) {
        return applicationUserService.checkAvailability(params.username)
    }

    Object checkUserName(Object params) {
        return applicationUserService.checkUserName(params.username)
    }

}
