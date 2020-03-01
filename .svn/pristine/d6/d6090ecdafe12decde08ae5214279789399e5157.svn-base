package com.docu.security.appsecurity.action

import com.docu.app.UserPreference
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.plugin.cxfws.CommonHelperWebService

//import com.docu.cxfws.HelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserService
import com.docu.security.LoginHistoryService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: Mashuk
 * Date: 1/29/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("changeSecuritySettingsAction")
class ChangeSecuritySettingsAction {
    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    LoginHistoryService loginHistoryService
    @Autowired
    CommonHelperWebService commonHelperWebService

    public static final Log log = LogFactory.getLog(ChangeSecuritySettingsAction.class);

    Object preCondition(def params) {
        Map mapInstance = [:]
        if (!params.oldPassword || params.oldPassword == "") {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.old.password.not.found"))
            return mapInstance
        }

        if (!params.password || params.password == "") {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.new.password.not.found"))
            return mapInstance
        }

        if (!params.password.equals(params.confirmPassword)) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.confirmed.password.not.match"))
            return mapInstance
        }
        if(params.password.equals(params.oldPassword)){
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.old.password.match.error"))
            return mapInstance
        }

        if (!applicationUserService.isValidPassword(params.password)) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.password.pattern.not.match"))
            return mapInstance
        }


        ApplicationUser applicationUser = ApplicationUser.findByUsername(params.uname)
        if (!applicationUserService.isOldPasswordExists(params.oldPassword, applicationUser)) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.old.password.error"))
            return mapInstance
        }
        if (!params.securityQuestionOne || params.securityQuestionOne == "" || !params.answerOne || params.answerOne == "") {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.question.one.error"))
            return mapInstance
        }
        if (!params.securityQuestionTwo || params.securityQuestionTwo == "" || !params.answerTwo || params.answerTwo == "") {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.question.two.error"))
            return mapInstance
        }
        if (params.securityQuestionOne.toString().trim() == params.securityQuestionTwo.toString().trim()) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.question.same.error"))
            return mapInstance
        }
        UserPreference userPreference = applicationUserService.getUserPreference(applicationUser.id)

        if (!applicationUser.validate()) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, applicationUser)
            return mapInstance
        }
        mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.SUCCESS, null)
        mapInstance.put("applicationUser", applicationUser)
        mapInstance.put("userPreference", userPreference)

        return mapInstance

    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        List errorList = []
        try {
            Map objectMap = (Map) object
            ApplicationUser applicationUser = (ApplicationUser) objectMap.get("applicationUser")
            UserPreference userPreference = (UserPreference) objectMap.get("userPreference")

            applicationUser.password = applicationUserService.encodePassword(params.password)
            applicationUser.passwordExpired = false
            userPreference.securityQuestionOne = params.securityQuestionOne
            userPreference.securityQuestionTwo = params.securityQuestionTwo
            userPreference.answerOne = params.answerOne
            userPreference.answerTwo = params.answerTwo

            if(applicationUserService.changeSettings(objectMap)){
             errorList =  commonHelperWebService.callApplicationUserAndDependencies(object,'')
            }
        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, ex)
        }

        String errorMessage = "Setting change failed for <br/>"
        for (int i = 0;i < errorList.size();i++){
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0){
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.ERROR, errorMessage)
        }
        return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changeSettings.title", null), Message.SUCCESS, applicationUserService.getUserMessage("changeSecuritySettings.save.successfully"))
    }

}
