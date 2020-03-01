package com.docu.commons.religion


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.CommonConstants
import com.docu.commons.Religion
import com.docu.commons.ReligionService

@Component("deleteReligionAction")
class DeleteReligionAction implements IAction {
    public static final Log log = LogFactory.getLog(DeleteReligionAction.class)
    private final String MESSAGE_HEADER = 'religion.header'
    private final String MESSAGE_SUCCESS = 'religion.delete.success'
    private final String MESSAGE_FAILURE = 'religion.failure.success'

    @Autowired
    ReligionService religionService


    public Object preCondition(def params) {
        Map mapInstance = [:]
        Religion religion = null
        try {
            religion = religionService.read(params)
            if (!religion) {
                return UserMessageBuilder.createMessage(religionService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, religionService.getUserMessage(MESSAGE_FAILURE, null))
            }

            mapInstance = (Map) UserMessageBuilder.createMessage(religionService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(Religion.class.simpleName, religion)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(religionService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, religion, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        Religion religion = null
        try {
            religion = object.get(Religion.class.simpleName)


            religionService.delete(religion)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(religionService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, religion, ex)
        }

        return UserMessageBuilder.createMessage(religionService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, religionService.getUserMessage(MESSAGE_SUCCESS, null))
    }

    public Object postCondition(def object) {
        return null
    }
}