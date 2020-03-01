package com.docu.commons.maritalstatus


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.CommonConstants
import com.docu.commons.MaritalStatus
import com.docu.commons.MaritalStatusService

@Component("deleteMaritalStatusAction")
class DeleteMaritalStatusAction implements IAction {
    public static final Log log = LogFactory.getLog(DeleteMaritalStatusAction.class)
    private final String MESSAGE_HEADER = 'maritalStatus.header'
    private final String MESSAGE_SUCCESS = 'maritalStatus.delete.success'
    private final String MESSAGE_FAILURE = 'maritalStatus.failure.success'
    private final String MESSAGE_ERROR = 'maritalStatus.error.delete'


    @Autowired
    MaritalStatusService maritalStatusService


    public Object preCondition(def params) {
        Map mapInstance = [:]
        MaritalStatus maritalStatus = null
        try {
            maritalStatus = maritalStatusService.read(params)
            if (!maritalStatus) {
                return UserMessageBuilder.createMessage(maritalStatusService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, maritalStatusService.getUserMessage(MESSAGE_FAILURE, null))
            }

            mapInstance = (Map) UserMessageBuilder.createMessage(maritalStatusService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(MaritalStatus.class.simpleName, maritalStatus)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(maritalStatusService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, maritalStatus, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        MaritalStatus maritalStatus = null
        try {
            maritalStatus = object.get(MaritalStatus.class.simpleName)


            maritalStatusService.delete(maritalStatus)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(maritalStatusService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, maritalStatusService.getUserMessage(MESSAGE_ERROR, null))
        }

        return UserMessageBuilder.createMessage(maritalStatusService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, maritalStatusService.getUserMessage(MESSAGE_SUCCESS, null))
    }

    public Object postCondition(def object) {
        return null
    }
}