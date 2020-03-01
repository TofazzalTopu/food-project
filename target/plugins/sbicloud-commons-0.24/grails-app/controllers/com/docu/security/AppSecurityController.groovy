package com.docu.security

import com.docu.commons.Message
import com.docu.security.applicationuser.action.AjaxApplicationUserAction
import com.docu.security.appsecurity.action.ChangeSecuritySettingsAction
import org.springframework.beans.factory.annotation.Autowired

class AppSecurityController {
    @Autowired
    ChangeSecuritySettingsAction changeSecuritySettingsAction
    @Autowired
    AjaxApplicationUserAction ajaxApplicationUserAction
    def springSecurityService
    private static final String MESSAGE_PASSWORD_EXPIRED = "Sorry, your password has expired."

    static defaultAction = "changeSecuritySetting"

    def changeSecuritySetting = {
        if (!springSecurityService.isLoggedIn()) {
            flash.message = MESSAGE_PASSWORD_EXPIRED
        }
        Map securitySettingObject = ajaxApplicationUserAction.getSecuritySettingsObject()
        render(view: "changeSecuritySetting", model: securitySettingObject)
    }

    def saveChangedSecuritySettings = {
        Message msg = null
        Map object = (Map) changeSecuritySettingsAction.preCondition(params)
        msg = object.get(Message.MESSAGE)
        if (msg.type == Message.SUCCESS) {
            Map result = (Map) changeSecuritySettingsAction.execute(params, object)
            msg = result.get(Message.MESSAGE)
        }
        render msg.toString()
    }
}
