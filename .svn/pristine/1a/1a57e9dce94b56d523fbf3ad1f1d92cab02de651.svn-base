package com.docu.security.applicationuser.action

import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.springframework.beans.factory.annotation.Autowired
import com.docu.security.ApplicationUserService

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 6/11/11
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("listApplicationUserAction")
class ListApplicationUserAction implements IAction {

    @Autowired
    ApplicationUserService applicationUserService

    Object preCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        return applicationUserService.getDataForGrid()
    }
}
