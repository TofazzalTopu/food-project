package com.docu.security.authority.action

import com.docu.security.Requestmap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.security.UserAuthority
import com.docu.security.UserAuthorityService

@Component("deleteAuthorityAction")
class DeleteAuthorityAction implements IAction {
    public static final Log log = LogFactory.getLog(DeleteAuthorityAction.class)
    private final String MESSAGE_HEADER = 'authority.header'
    private final String MESSAGE_SUCCESS = 'authority.delete.success'
    private final String MESSAGE_FAILURE = 'authority.failure.success'

    @Autowired
    UserAuthorityService userAuthorityService


    public Object preCondition(def params) {
        Map mapInstance = [:]
        UserAuthority authority = null
        try {
            authority = userAuthorityService.read(params)
            if (!authority) {
                return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, userAuthorityService.getUserMessage(MESSAGE_FAILURE, null))
            }

            def accessCount = Requestmap.countByConfigAttributeLike("%" + authority.authority + "%")
            if(accessCount > 0){
                return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "Authority has Access Permission. First remove permission")
            }

            mapInstance = (Map) UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(UserAuthority.class.simpleName, authority)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, authority, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        UserAuthority authority = null
        try {
            authority = object.get(UserAuthority.class.simpleName)
            userAuthorityService.delete(authority)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, authority, ex)
        }

        return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, userAuthorityService.getUserMessage(MESSAGE_SUCCESS, null))
    }

    public Object postCondition(def object) {
        return null
    }
}