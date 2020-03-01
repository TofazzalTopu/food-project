package com.bits.bdfp.currency.currencydemonstration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.currency.CurrencyDemonstration
import com.bits.bdfp.currency.CurrencyDemonstrationService




@Component("updateCurrencyDemonstrationAction")
class UpdateCurrencyDemonstrationAction implements IAction {
  public static final Log log = LogFactory.getLog(UpdateCurrencyDemonstrationAction.class)
  private final String MESSAGE_HEADER = 'currencyDemonstration.header'
  private final String MESSAGE_SUCCESS = 'currencyDemonstration.update.success'

  @Autowired
  CurrencyDemonstrationService currencyDemonstrationService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    CurrencyDemonstration currencyDemonstration = null
    try {
        currencyDemonstration = currencyDemonstrationService.read(params)
        currencyDemonstration.properties = params
        currencyDemonstration.dateUpdated = new Date()
        if (!currencyDemonstration.validate()) {
            return UserMessageBuilder.createMessage(currencyDemonstrationService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, currencyDemonstrationService.getErrorMessage(currencyDemonstration), currencyDemonstration)
        }
        
        mapInstance = (Map) UserMessageBuilder.createMessage(currencyDemonstrationService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(CurrencyDemonstration.class.simpleName, currencyDemonstration)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(currencyDemonstrationService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, currencyDemonstration, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        CurrencyDemonstration currencyDemonstration = null
        try {
            currencyDemonstration = object.get(CurrencyDemonstration.class.simpleName)
            currencyDemonstrationService.update(currencyDemonstration)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(currencyDemonstrationService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, currencyDemonstration, ex)
        }

        return UserMessageBuilder.createMessage(currencyDemonstrationService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, currencyDemonstrationService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}
