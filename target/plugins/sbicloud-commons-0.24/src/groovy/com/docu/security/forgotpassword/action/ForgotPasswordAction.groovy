package com.docu.security.forgotpassword.action

import com.docu.app.UserPreference
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
@Component("forgotPasswordAction")
class ForgotPasswordAction implements IAction {
    @Autowired
    ApplicationUserService applicationUserService

    Object preCondition(def params) {
        Map mapInstance = [:]
        Map mapResponse = applicationUserService.checkSecurityQuestion(params)

        if (!mapResponse.isValid) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.ERROR,applicationUserService.getUserMessage("forgetPassword.error.message", null))
            return mapInstance
        }

        ApplicationUser applicationUser = ApplicationUser.read(params.userid)
        params.randomPassword = applicationUserService.generateRandomNumber()

        applicationUser.password = applicationUserService.encodePassword(params.randomPassword)
        applicationUser.confirmPassword = params.randomPassword

        if (!applicationUser.validate()) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.ERROR, applicationUser)
            return mapInstance
        }
        mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.SUCCESS, null)
        mapInstance.put("applicationUser", applicationUser)
        return mapInstance
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object.get('applicationUser')
            applicationUserService.updatePassword(applicationUser)
        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.ERROR, ex.message)
        }
        return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.SUCCESS, applicationUserService.getUserMessage("forgetPassword.success.message", null))
    }

    Object checkUserName(Object params) {
        return applicationUserService.isUserExist(params.username)
    }
    
    public Map getSecurityQuestion(Object params){
        Map mapInstance = [:]
        ApplicationUser applicationUser = applicationUserService.getApplicationUserByUserName(params.username)
        if (!applicationUser) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.ERROR, applicationUserService.getUserMessage("forgetPassword.user.not.found", null))
            return mapInstance
        }
        if (applicationUser.accountLocked) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.ERROR, applicationUserService.getUserMessage("forgetPassword.user.is.locked", null))
            return mapInstance
        }

        UserPreference userPreference = applicationUserService.getUserPreference(applicationUser.id)

        if (userPreference?.securityQuestionOne == "" || userPreference?.securityQuestionTwo == "" || userPreference?.securityQuestionOne == null || userPreference?.securityQuestionTwo == null) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.ERROR, applicationUserService.getUserMessage("forgetPassword.security.question.not.found", null))
            return mapInstance
        }

        mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("forgetPassword.title", null), Message.SUCCESS, null)
        mapInstance.put("applicationUser", applicationUser)
        mapInstance.put("userPreference", userPreference)
        return mapInstance
    }
}
