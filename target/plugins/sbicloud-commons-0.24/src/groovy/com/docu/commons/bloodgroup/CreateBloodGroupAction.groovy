package com.docu.commons.bloodgroup

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.BloodGroup
import com.docu.commons.BloodGroupService

@Component("createBloodGroupAction")
class CreateBloodGroupAction implements IAction {
  public static final Log log = LogFactory.getLog(CreateBloodGroupAction.class)
  private final String MESSAGE_HEADER = 'bloodGroup.header'
  private final String MESSAGE_SUCCESS = 'bloodGroup.save.success'

  @Autowired
  BloodGroupService bloodGroupService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    BloodGroup bloodGroup = null
    try {
        bloodGroup = new BloodGroup()
        bloodGroup.properties = params
        
        
        
        
        if (!bloodGroup.validate()) {
            return UserMessageBuilder.createMessage(bloodGroupService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, bloodGroupService.getErrorMessage(bloodGroup), bloodGroup)
        }
        mapInstance = (Map) UserMessageBuilder.createMessage(bloodGroupService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(BloodGroup.class.simpleName, bloodGroup)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(bloodGroupService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, bloodGroup, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        BloodGroup bloodGroup = null
        try {
            bloodGroup = object.get(BloodGroup.class.simpleName)
            bloodGroupService.save(bloodGroup)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(bloodGroupService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, bloodGroup, ex)
        }

        return UserMessageBuilder.createMessage(bloodGroupService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, bloodGroupService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}