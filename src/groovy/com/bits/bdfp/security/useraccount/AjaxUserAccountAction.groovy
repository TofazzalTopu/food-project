package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.app.UserPreference
import com.docu.commons.SessionManagementService
import com.docu.security.ApplicationUser
import com.docu.security.SecurityQuestion
import com.docu.security.util.SecurityQuestionUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("AjaxUserAccountAction")
class AjaxUserAccountAction {
    public static final Log log = LogFactory.getLog(AjaxUserAccountAction.class)

    @Autowired
    UserAccountService userAccountService
    @Autowired
    SessionManagementService sessionManagementService

    public getSecuritySettingsObject(){
        List<SecurityQuestion> securityQuestionList = SecurityQuestionUtil.instance.getAll()
        ApplicationUser user = sessionManagementService.getUser()
        if(user == null){
            user = ApplicationUser.findByUsername(sessionManagementService.getSession()['SPRING_SECURITY_LAST_USERNAME'])
        }
        UserPreference userPreference = userAccountService.getUserPreference(user.id)
        [userName: user?.username, securityQuestionList: securityQuestionList, userPreference: userPreference]
    }


    public boolean backupApplicationUser(){
        return userAccountService.backupApplicationUser()
    }

    public boolean lockApplicationUser(){
        return userAccountService.lockApplicationUser()
    }

    public boolean restoreApplicationUser(){
        return userAccountService.restoreApplicationUser()
    }

}
