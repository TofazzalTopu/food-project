package com.docu.security.changepassword.action

import com.docu.app.UserPreference
import com.docu.commons.CommonConstants
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.plugin.cxfws.CommonHelperWebService

//import com.docu.cxfws.HelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserService
import com.docu.security.setupoption.AjaxSetupOptionAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 6/14/11
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("changePasswordAction")
class ChangePasswordAction implements IAction {
    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    CommonHelperWebService commonHelperWebService
    @Autowired
    AjaxSetupOptionAction ajaxSetupOptionAction

    Object preCondition(def params) {
        Map mapInstance = [:]
        if (!params.oldPassword || params.oldPassword == "") {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.old.password.not.found"))
            return mapInstance
        }

        if (!params.password || params.password == "") {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.new.password.not.found"))
            return mapInstance
        }

        if (!params.password.equals(params.confirmPassword)) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.confirmed.password.not.match"))
            return mapInstance
        }

//        if (!applicationUserService.isValidPassword(params.password)) {
//            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.password.pattern.not.match"))
//            return mapInstance
//        }

        if(!ajaxSetupOptionAction.isPasswordPatternMatched(params.password)){
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, CommonConstants.PASSWORD_VALIDATION_MESSAGE)
        }

        ApplicationUser applicationUser = ApplicationUser.read(sessionManagementService.getUser().id)
        if (!applicationUserService.isOldPasswordExists(params.oldPassword, applicationUser)) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, applicationUserService.getUserMessage("changeSecuritySettings.old.password.error"))
            return mapInstance
        }

        if (!applicationUser.validate()) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, applicationUser)
            return mapInstance
        }
        mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.SUCCESS, null)
        mapInstance.put("applicationUser", applicationUser)
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

            applicationUser.password = applicationUserService.encodePassword(params.password)
            if(applicationUserService.changeSettings(objectMap)){
                errorList =  commonHelperWebService.callApplicationUserAndDependencies(object,'')
            }

        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, ex)
        }

        String errorMessage = "Password change failed for <br/>"
        for (int i = 0;i < errorList.size();i++){
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0){
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.ERROR, errorMessage)
        }

        return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("changePassword.title", null), Message.SUCCESS, applicationUserService.getUserMessage("changePassword.save.successfully"))
    }

    boolean checkOldPassword(String oldPas) {
        String oldPassword = applicationUserService.checkOldPassword(sessionManagementService.user.id)
        if (oldPassword == applicationUserService.encodePassword(oldPas)) {
            true
        } else {
            false
        }
    }
}
