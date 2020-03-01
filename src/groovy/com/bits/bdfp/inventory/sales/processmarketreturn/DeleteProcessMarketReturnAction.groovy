package com.bits.bdfp.inventory.sales.processmarketreturn

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.sales.ProcessMarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.commons.CommonConstants



@Component("deleteProcessMarketReturnAction")
class DeleteProcessMarketReturnAction implements IAction {
  public static final Log log = LogFactory.getLog(DeleteProcessMarketReturnAction.class)
  private final String MESSAGE_HEADER = 'processMarketReturn.header'
  private final String MESSAGE_SUCCESS = 'processMarketReturn.delete.success'
  private final String MESSAGE_FAILURE = 'processMarketReturn.failure.success'

  @Autowired
  ProcessMarketReturnService processMarketReturnService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    ProcessMarketReturn processMarketReturn = null
    try {
        processMarketReturn = processMarketReturnService.read(params)
        if (!processMarketReturn) {
            return UserMessageBuilder.createMessage(processMarketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, processMarketReturnService.getUserMessage(MESSAGE_FAILURE, null))
        }
        
        mapInstance = (Map) UserMessageBuilder.createMessage(processMarketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(ProcessMarketReturn.class.simpleName, processMarketReturn)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(processMarketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, processMarketReturn, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        ProcessMarketReturn processMarketReturn = null
        try {
            processMarketReturn = object.get(ProcessMarketReturn.class.simpleName)
            processMarketReturnService.delete(processMarketReturn)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(processMarketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, processMarketReturn, ex)
        }

        return UserMessageBuilder.createMessage(processMarketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, processMarketReturnService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}