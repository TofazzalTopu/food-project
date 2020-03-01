package com.bits.bdfp.currency.currencydemonstration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.currency.CurrencyDemonstration
import com.bits.bdfp.currency.CurrencyDemonstrationService

@Component("readCurrencyDemonstrationAction")
class ReadCurrencyDemonstrationAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadCurrencyDemonstrationAction.class)

  @Autowired
  CurrencyDemonstrationService currencyDemonstrationService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    CurrencyDemonstration currencyDemonstration = currencyDemonstrationService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(currencyDemonstration)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}