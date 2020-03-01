package com.bits.bdfp.inventory.sales.processmarketreturn

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.inventory.sales.ProcessMarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService

@Component("readProcessMarketReturnAction")
class ReadProcessMarketReturnAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadProcessMarketReturnAction.class)

  @Autowired
  ProcessMarketReturnService processMarketReturnService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    ProcessMarketReturn processMarketReturn = processMarketReturnService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(processMarketReturn)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}