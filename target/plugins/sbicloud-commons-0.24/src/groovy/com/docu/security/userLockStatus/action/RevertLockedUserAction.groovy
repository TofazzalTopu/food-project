package com.docu.security.userLockStatus.action

import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.security.UserLockStatus
import com.docu.security.UserLockStatusService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: Billal
 * Date: 21/10/14
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */

@Component("revertLockedUserAction")
class RevertLockedUserAction implements IAction {
    public static final Log log = LogFactory.getLog(RevertLockedUserAction.class)

    @Autowired
    UserLockStatusService userLockStatusService
    @Autowired
    SessionManagementService sessionManagementService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        UserLockStatus userLockStatus = userLockStatusService.getUserLockStatus()
        Date businessDate = new Date()
        try {

            if (!userLockStatus) {
                return UserMessageBuilder.createMessage(userLockStatusService.getUserMessage("userLockStatus.header", null), Message.ERROR, userLockStatusService.getUserMessage("userLockStatus.user.already.Unlocked", null))
            }
            else if (!userLockStatus.isLocked || userLockStatus.isServerBusy) {
                return UserMessageBuilder.createMessage(userLockStatusService.getUserMessage("userLockStatus.header", null), Message.ERROR, userLockStatusService.getUserMessage("userLockStatus.user.already.Unlocked", null))
            }

            userLockStatus.isServerBusy = false
            userLockStatus.unlockDate = businessDate
            userLockStatus.unlockedBy = sessionManagementService?.user?.id

            mapInstance = (Map) UserMessageBuilder.createMessage (userLockStatusService.getUserMessage("userLockStatus.header", null), Message.SUCCESS, null)
             mapInstance.put(UserLockStatus.class.simpleName, userLockStatus)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(userLockStatusService.getUserMessage("userLockStatus.header", null), Message.ERROR, userLockStatus, ex)
        }

        return mapInstance
    }


    public Object execute(def params, def object) {
        UserLockStatus userLockStatus = null
        try {
            userLockStatus = object.get(UserLockStatus.class.simpleName)
            userLockStatusService.unLockUser(userLockStatus)

        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(userLockStatusService.getUserMessage("userLockStatus.header", null), Message.ERROR, userLockStatus, ex)
        }

        return UserMessageBuilder.createMessage(userLockStatusService.getUserMessage("userLockStatus.header", null), Message.SUCCESS, userLockStatusService.getUserMessage("userLockStatus.user.Unlocked", null))
    }

    public Object postCondition(def object) {
        return null
    }

    public Map getUserLockStatus() {
        return userLockStatusService.getUserLockStatus()
    }
}
