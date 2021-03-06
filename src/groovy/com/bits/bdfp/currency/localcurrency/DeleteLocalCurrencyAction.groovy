package com.bits.bdfp.currency.localcurrency




import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.currency.LocalCurrency
import com.bits.bdfp.currency.LocalCurrencyService







@Component("deleteLocalCurrencyAction")
class DeleteLocalCurrencyAction implements IAction {
  public static final Log log = LogFactory.getLog(DeleteLocalCurrencyAction.class)
  private final String MESSAGE_HEADER = 'localCurrency.header'
  private final String MESSAGE_SUCCESS = 'localCurrency.delete.success'
  private final String MESSAGE_FAILURE = 'localCurrency.failure.success'

  @Autowired
  LocalCurrencyService localCurrencyService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    LocalCurrency localCurrency = null
    try {
        localCurrency = localCurrencyService.read(params)
        if (!localCurrency) {
            return UserMessageBuilder.createMessage(localCurrencyService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, localCurrencyService.getUserMessage(MESSAGE_FAILURE, null))
        }
        
        mapInstance = (Map) UserMessageBuilder.createMessage(localCurrencyService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(LocalCurrency.class.simpleName, localCurrency)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(localCurrencyService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, localCurrency, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        LocalCurrency localCurrency = null
        try {
            localCurrency = object.get(LocalCurrency.class.simpleName)
            
            
            
            localCurrencyService.delete(localCurrency)
            
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(localCurrencyService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, localCurrency, ex)
        }

        return UserMessageBuilder.createMessage(localCurrencyService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, localCurrencyService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}