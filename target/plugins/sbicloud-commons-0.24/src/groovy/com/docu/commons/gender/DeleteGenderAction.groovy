package com.docu.commons.gender


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.CommonConstants
import com.docu.commons.Gender
import com.docu.commons.GenderService

@Component("deleteGenderAction")
class DeleteGenderAction implements IAction {
    public static final Log log = LogFactory.getLog(DeleteGenderAction.class)
    private final String MESSAGE_HEADER = 'gender.header'
    private final String MESSAGE_SUCCESS = 'gender.delete.success'
    private final String MESSAGE_FAILURE = 'gender.failure.success'

    @Autowired
    GenderService genderService


    public Object preCondition(def params) {
        Map mapInstance = [:]
        Gender gender = null
        try {
            gender = genderService.read(params)
            if (!gender) {
                return UserMessageBuilder.createMessage(genderService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, genderService.getUserMessage(MESSAGE_FAILURE, null))
            }

            mapInstance = (Map) UserMessageBuilder.createMessage(genderService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(Gender.class.simpleName, gender)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(genderService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, gender, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        Gender gender = null
        try {
            gender = object.get(Gender.class.simpleName)


            genderService.delete(gender)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(genderService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, gender, ex)
        }

        return UserMessageBuilder.createMessage(genderService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, genderService.getUserMessage(MESSAGE_SUCCESS, null))
    }

    public Object postCondition(def object) {
        return null
    }
}