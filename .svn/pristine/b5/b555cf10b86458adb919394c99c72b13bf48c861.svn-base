package com.docu.commons.addresstype


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.AddressType
import com.docu.commons.AddressTypeService
import com.docu.commons.SessionManagementService

@Component("createAddressTypeAction")
class CreateAddressTypeAction implements IAction {
  public static final Log log = LogFactory.getLog(CreateAddressTypeAction.class)
  private final String MESSAGE_HEADER = 'Address Type'
  private final String MESSAGE_SUCCESS = 'Address Type Saved Successfully'

  @Autowired
  AddressTypeService addressTypeService

  @Autowired
  SessionManagementService sessionManagementService

  public Object preCondition(def params) {
    Map mapInstance = [:]
    AddressType addressType = null
    try {
        addressType = new AddressType()
        addressType.properties = params
        
        if (!addressType.validate()) {
            return UserMessageBuilder.createMessage(addressTypeService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, addressTypeService.getErrorMessage(addressType), addressType)
        }
        mapInstance = (Map) UserMessageBuilder.createMessage(addressTypeService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(AddressType.class.simpleName, addressType)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(addressTypeService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, addressType, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        AddressType addressType = null
        try {
            addressType = object.get(AddressType.class.simpleName)
            addressTypeService.save(addressType)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(addressTypeService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, addressType, ex)
        }

        return UserMessageBuilder.createMessage(addressTypeService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, addressTypeService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}