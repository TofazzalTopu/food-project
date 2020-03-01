package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("updateUserAccountStatusAction")
class UpdateUserAccountStatusAction implements IAction {

    public static final Log log = LogFactory.getLog(UpdateUserAccountStatusAction.class)

    @Autowired
    UserAccountService userAccountService
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
        mapInstance.message = UserMessageBuilder.createMessage(userAccountService.getUserMessage("userControlPanel.label", null), Message.SUCCESS, userAccountService.getUserMessage("success", null)).get(Message.MESSAGE)
        mapInstance.applicationUserList = applicationUserList
        return mapInstance
    }

    public Object postCondition(def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            List<ApplicationUser> applicationUserList = object.applicationUserList
            userAccountService.updateUserStatus(applicationUserList)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("userControlPanel.label", null), Message.ERROR, object, ex)
        }

        return UserMessageBuilder.createMessage(userAccountService.getUserMessage("userControlPanel.label", null), Message.SUCCESS,
                userAccountService.getUserMessage("userControlPanel.update.success", null))
    }
}