package com.bits.bdfp.currency.localcurrency

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.currency.LocalCurrency
import com.bits.bdfp.currency.LocalCurrencyService

@Component("readLocalCurrencyAction")
class ReadLocalCurrencyAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadLocalCurrencyAction.class)

  @Autowired
  LocalCurrencyService localCurrencyService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    LocalCurrency localCurrency = localCurrencyService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(localCurrency)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}