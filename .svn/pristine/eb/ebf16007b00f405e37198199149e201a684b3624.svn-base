package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserAuthority
import com.docu.security.UserAuthority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("updateUserAccountAuthorityAction")
class UpdateUserAccountAuthorityAction {
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    UserAccountService userAccountService
    @Autowired
    CommonHelperWebService commonHelperWebService

    Object preCondition(def params) {
        Map mapInstance = [:]
        ApplicationUserAuthority applicationUserAuthority = null
        List nAuthorityList = []
        List applicationUserAuthorityList = []

        List deleteApplicationUserAuthorityList = []
        try{
            ApplicationUser applicationUser = userAccountService.read(params)
            applicationUser.properties = params
            applicationUser.clearErrors()
            applicationUser.username = params.username
            applicationUser.fullName = params.fullName


            applicationUser.id = Long.parseLong(params.id)
            applicationUser.version = Long.parseLong(params.version)

            if (params.enabled == null || params.enabled == "") {
                applicationUser.enabled = false
            } else {
                applicationUser.enabled = params.enabled
            }
            if (params.accountExpired == null || params.accountExpired == "") {
                applicationUser.accountExpired = false
            } else {
                applicationUser.accountExpired = params.accountExpired
            }
            if (params.accountLocked == null || params.accountLocked == "") {
                applicationUser.accountLocked = false
            } else {
                applicationUser.accountLocked = params.accountLocked
            }

            if (params.passwordExpired == "" || params.passwordExpired == null) {
                applicationUser.passwordExpired = false
            } else {
                applicationUser.passwordExpired = params.passwordExpired
            }
            applicationUser.emailAddress = params.email

            if (!applicationUser.validate()) {
                mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUser)
                return mapInstance
            }

            if (params.nAuthority instanceof String) {
                nAuthorityList.add(params.nAuthority)
            } else if (params.nAuthority instanceof String[]) {
                nAuthorityList = (List) params.nAuthority
            }
            if(nAuthorityList.size()<=0){
                return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.WARNING,
                        userAccountService.getUserMessage("applicationUser.authorityMapping.error", null) )
            }

            nAuthorityList.each {
                UserAuthority authority = UserAuthority.read(it)
                applicationUserAuthority = new ApplicationUserAuthority()
                applicationUserAuthority.applicationUser = applicationUser
                applicationUserAuthority.userAuthority = authority//new Authority(id: it)

                if (!applicationUserAuthority.validate()) {
                    return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUserAuthority)
                }
                applicationUserAuthorityList.add(applicationUserAuthority)
            }
            deleteApplicationUserAuthorityList = userAccountService.getUserAuthorityByApplicationUser(applicationUser)

            mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)
            mapInstance.put("applicationUser", applicationUser)
            mapInstance.put("applicationUserAuthority", applicationUserAuthorityList)
            mapInstance.put("deleteApplicationUserAuthority", deleteApplicationUserAuthorityList)
            return mapInstance
        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, params, ex)
        }

    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        List errorList = []
        try {
            if (userAccountService.updateAuthority((Map)object)){
                return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, "Application User Updated Successfully")
//                errorList = commonHelperWebService.callApplicationUserAndDependencies(object , "")
            }

        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, ex)
        }

        String errorMessage = "Application User update failed for <br/>"
        for (int i = 0;i < errorList.size();i++){
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0){
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, errorMessage)
        }
    }
}
