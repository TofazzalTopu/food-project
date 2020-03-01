package com.docu.commons.addresstype

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.commons.AddressType
import com.docu.commons.AddressTypeService

@Component("readAddressTypeAction")
class ReadAddressTypeAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadAddressTypeAction.class)
  private final String MESSAGE_HEADER = 'addressType.header'
  private final String MESSAGE_SUCCESS = 'addressType.update.success'

  @Autowired
  AddressTypeService addressTypeService

  public Object preCondition(def params) {

    return null
  }

  public Object execute(def params, def object) {
    AddressType addressType = addressTypeService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(addressType)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}