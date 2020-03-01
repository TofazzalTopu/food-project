package com.bits.bdfp.inventory.sales.receiveproductsfrommarket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarket
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.docu.commons.CommonConstants



@Component("deleteReceiveProductsFromMarketAction")
class DeleteReceiveProductsFromMarketAction implements IAction {
  public static final Log log = LogFactory.getLog(DeleteReceiveProductsFromMarketAction.class)
  private final String MESSAGE_HEADER = 'receiveProductsFromMarket.header'
  private final String MESSAGE_SUCCESS = 'receiveProductsFromMarket.delete.success'
  private final String MESSAGE_FAILURE = 'receiveProductsFromMarket.failure.success'

  @Autowired
  ReceiveProductsFromMarketService receiveProductsFromMarketService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    ReceiveProductsFromMarket receiveProductsFromMarket = null
    try {
        receiveProductsFromMarket = receiveProductsFromMarketService.read(params)
        if (!receiveProductsFromMarket) {
            return UserMessageBuilder.createMessage(receiveProductsFromMarketService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, receiveProductsFromMarketService.getUserMessage(MESSAGE_FAILURE, null))
        }
        
        mapInstance = (Map) UserMessageBuilder.createMessage(receiveProductsFromMarketService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(ReceiveProductsFromMarket.class.simpleName, receiveProductsFromMarket)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(receiveProductsFromMarketService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, receiveProductsFromMarket, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        ReceiveProductsFromMarket receiveProductsFromMarket = null
        try {
            receiveProductsFromMarket = object.get(ReceiveProductsFromMarket.class.simpleName)
            receiveProductsFromMarketService.delete(receiveProductsFromMarket)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(receiveProductsFromMarketService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, receiveProductsFromMarket, ex)
        }

        return UserMessageBuilder.createMessage(receiveProductsFromMarketService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, receiveProductsFromMarketService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}