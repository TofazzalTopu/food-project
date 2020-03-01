package com.docu.security.resetpassword.action

import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 6/13/11
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("resetPasswordAction")
class ResetPasswordAction implements IAction {
    @Autowired
    ApplicationUserService applicationUserService

    Object preCondition(def params) {
        Map mapInstance = [:]
        if (!params.password.equals(params.confirmPassword)) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "Confirm Password does not mathch with password")
            return mapInstance
        }

        ApplicationUser applicationUser = ApplicationUser.read(params.userid)
        applicationUser.password = applicationUserService.encodePassword(params.password)

        if (!applicationUser.validate()) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUser)
            return mapInstance
        }
        mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)
        mapInstance.put("applicationUser", applicationUser)
        return mapInstance
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        try {
            applicationUserService.resetPassword(object)
        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, null, ex)
        }
         return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.success.title", null), Message.SUCCESS, "Password Updated Successfully")
    }
}
