package com.docu.commons.religion


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.Religion
import com.docu.commons.ReligionService
import com.docu.commons.SessionManagementService

@Component("createReligionAction")
class CreateReligionAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateReligionAction.class)
    private final String MESSAGE_HEADER = 'religion.header'
    private final String MESSAGE_SUCCESS = 'religion.save.success'

    @Autowired
    ReligionService religionService

    @Autowired
    SessionManagementService sessionManagementService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        Religion religion = null
        try {
            religion = new Religion()
            religion.properties = params

            /*
              if(params.isActive && params.isActive.toString().toLowerCase() =="on"){
                hrPayrollPaycode.isActive = true;
              }
            */


            if (!religion.validate()) {
                return UserMessageBuilder.createMessage(religionService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, religionService.getErrorMessage(religion), religion)
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
            religionService.save(religion)
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