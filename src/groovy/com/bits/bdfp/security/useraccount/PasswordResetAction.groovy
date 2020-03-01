package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.common.Message
import com.docu.commons.CommonConstants
import com.docu.commons.IAction
import com.docu.commons.UserMessageBuilder
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.setupoption.AjaxSetupOptionAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 12/10/2015.
 */
@Component("passwordResetAction")
class PasswordResetAction implements IAction {
    @Autowired
    UserAccountService userAccountService
    @Autowired
    AjaxSetupOptionAction ajaxSetupOptionAction
    @Autowired
    CommonHelperWebService commonHelperWebService

    def springSecurityService

    Object preCondition(def params) {
        Map mapInstance = [:]
        ApplicationUser applicationUser = ApplicationUser.read(params.resetUser)
        if (!params.password.equals(params.confirmPassword)) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "Confirm Password does not match with password")
        }

        if(!ajaxSetupOptionAction.isPasswordPatternMatched(params.password)){
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, CommonConstants.PASSWORD_VALIDATION_MESSAGE)
        }

        applicationUser.password = userAccountService.encodePassword(params.password)
        applicationUser.confirmPassword = params.confirmPassword

        applicationUser.accountLocked = false
        applicationUser.passwordExpired = false
        mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)

        mapInstance.put("applicationUser", applicationUser)
        return mapInstance
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        List errorList = []
        try {
            if (userAccountService.simpleUpdate(object.applicationUser)) {
                errorList = commonHelperWebService.callApplicationUserAndDependencies((Map)object, "")
            }
        }
        catch (Exception ex) {
            return UserMessageBuilder.createMessage("User Not Updated", Message.ERROR, object, ex)
        }
        String errorMessage = "Password Reset failed for <br/>"
        for (int i = 0; i < errorList.size(); i++) {
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0) {
            return UserMessageBuilder.createMessage("User Not Updated", Message.ERROR, errorMessage)
        }

        return UserMessageBuilder.createMessage("User Updated", Message.SUCCESS, "Password Reset Successful.")
    }
}
