package com.docu.security

import com.docu.commons.Message
import com.docu.security.userLockStatus.action.LockUserAction
import com.docu.security.userLockStatus.action.RevertLockedUserAction
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class UserLockStatusController {

    @Autowired
     LockUserAction lockUserAction
    @Autowired
     RevertLockedUserAction revertLockedUserAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"


    def show = {
          boolean isUserLocked =   lockUserAction.IsUserLocked()
              [isUserLocked:isUserLocked]
    }


    def lockUser = {
        Object object = lockUserAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = lockUserAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }
    def unlockUser = {
        Object object = revertLockedUserAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = revertLockedUserAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }


}
