package com.docu.security.applicationuser.action

import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserService
import grails.converters.JSON
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.SessionManagementService

@Component("updateApplicationUserStatusAction")
class UpdateApplicationUserStatusAction implements IAction {

    public static final Log log = LogFactory.getLog(UpdateApplicationUserStatusAction.class)

    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    SessionManagementService sessionManagementService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        List<ApplicationUser> applicationUserList= []
        ApplicationUser applicationUser= null

        List<JSON> items = (List<JSON>) JSON.parse("[" + params.items + "]")
        items = (List) items.first()
        for(Map item : items){
            applicationUser = ApplicationUser.read(item.id)
            
            applicationUser.enabled = item.enabled
            applicationUser.accountLocked = item.accountLocked
            applicationUser.accountExpired= item.accountExpired
            applicationUser.passwordExpired= item.passwordExpired
            applicationUserList.add(applicationUser)
        }
        mapInstance.message = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("userControlPanel.label", null), Message.SUCCESS, applicationUserService.getUserMessage("success", null)).get(Message.MESSAGE)
        mapInstance.applicationUserList = applicationUserList
        return mapInstance
    }

    public Object postCondition(def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            List<ApplicationUser> applicationUserList = object.applicationUserList
            applicationUserService.updateUserStatus(applicationUserList)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("userControlPanel.label", null), Message.ERROR, object, ex)
        }

        return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("userControlPanel.label", null), Message.SUCCESS,
                applicationUserService.getUserMessage("userControlPanel.update.success", null))
    }
}