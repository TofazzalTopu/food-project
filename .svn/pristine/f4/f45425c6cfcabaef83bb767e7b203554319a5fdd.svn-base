package com.docu.commons.addresstype


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.CommonConstants
import com.docu.commons.AddressType
import com.docu.commons.AddressTypeService

@Component("deleteAddressTypeAction")
class DeleteAddressTypeAction implements IAction {
  public static final Log log = LogFactory.getLog(DeleteAddressTypeAction.class)
  private final String MESSAGE_HEADER = 'Address Type'
  private final String MESSAGE_SUCCESS = 'Address Type Deleted Successfully'
  private final String MESSAGE_FAILURE = 'Address Type Failed to Delete'

  @Autowired
  AddressTypeService addressTypeService


  public Object preCondition(def params) {
    Map mapInstance = [:]
    AddressType addressType = null
    try {
        addressType = addressTypeService.read(params)
        if (!addressType) {
            return UserMessageBuilder.createMessage(addressTypeService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, addressTypeService.getUserMessage(MESSAGE_FAILURE, null))
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
            
            
            addressTypeService.delete(addressType)
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