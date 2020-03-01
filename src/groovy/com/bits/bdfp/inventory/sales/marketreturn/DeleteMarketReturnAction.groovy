package com.bits.bdfp.inventory.sales.marketreturn

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnService
import com.docu.commons.CommonConstants



@Component("deleteMarketReturnAction")
class DeleteMarketReturnAction implements IAction {
  public static final Log log = LogFactory.getLog(DeleteMarketReturnAction.class)
  private final String MESSAGE_HEADER = 'marketReturn.header'
  private final String MESSAGE_SUCCESS = 'marketReturn.delete.success'
  private final String MESSAGE_FAILURE = 'marketReturn.failure.success'

  @Autowired
  MarketReturnService marketReturnService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    MarketReturn marketReturn = null
    try {
        marketReturn = marketReturnService.read(params)
        if (!marketReturn) {
            return UserMessageBuilder.createMessage(marketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, marketReturnService.getUserMessage(MESSAGE_FAILURE, null))
        }
        
        mapInstance = (Map) UserMessageBuilder.createMessage(marketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(MarketReturn.class.simpleName, marketReturn)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(marketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, marketReturn, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        MarketReturn marketReturn = null
        try {
            marketReturn = object.get(MarketReturn.class.simpleName)
            marketReturnService.delete(marketReturn)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(marketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, marketReturn, ex)
        }

        return UserMessageBuilder.createMessage(marketReturnService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, marketReturnService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}