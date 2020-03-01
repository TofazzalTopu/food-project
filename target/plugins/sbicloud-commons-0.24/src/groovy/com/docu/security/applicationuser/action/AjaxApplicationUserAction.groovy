package com.docu.security.applicationuser.action

import com.docu.security.util.SecurityQuestionUtil
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import com.docu.security.ApplicationUserService
import com.docu.commons.SessionManagementService
import com.docu.security.SecurityQuestion
import com.docu.security.ApplicationUser
import com.docu.app.UserPreference
import grails.converters.JSON

/**
 * Created by IntelliJ IDEA.
 * User: Mashuk
 * Date: 2/26/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("ajaxApplicationUserAction")
class AjaxApplicationUserAction {
    public static final Log log = LogFactory.getLog(AjaxApplicationUserAction.class)

    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    SessionManagementService sessionManagementService


    public getSecuritySettingsObject(){
        List<SecurityQuestion> securityQuestionList = SecurityQuestionUtil.instance.getAll()
        ApplicationUser user = sessionManagementService.getUser()
        if(user == null){
            user = ApplicationUser.findByUsername(sessionManagementService.getSession()['SPRING_SECURITY_LAST_USERNAME'])
        }
        UserPreference userPreference = applicationUserService.getUserPreference(user.id)
        [userName: user?.username, securityQuestionList: securityQuestionList, userPreference: userPreference]
    }


    public boolean backupApplicationUser(){
       return applicationUserService.backupApplicationUser()
    }

    public boolean lockApplicationUser(){
        return applicationUserService.lockApplicationUser()
    }

    public boolean restoreApplicationUser(){
        return applicationUserService.restoreApplicationUser()
    }

//    public boolean unLockApplicationUser(){
//        return applicationUserService.unLockApplicationUser()
//    }
}
