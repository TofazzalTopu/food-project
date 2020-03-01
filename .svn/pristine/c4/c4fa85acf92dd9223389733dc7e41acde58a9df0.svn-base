package com.bits.bdfp.inventory.sales.receiveproductsfrommarket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarket
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService

@Component("readReceiveProductsFromMarketAction")
class ReadReceiveProductsFromMarketAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadReceiveProductsFromMarketAction.class)

  @Autowired
  ReceiveProductsFromMarketService receiveProductsFromMarketService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    ReceiveProductsFromMarket receiveProductsFromMarket = receiveProductsFromMarketService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(receiveProductsFromMarket)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}