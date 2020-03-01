package com.bits.bdfp.settings.businessday


import com.bits.bdfp.settings.FinancialYearService
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("openedBusinessMonthAction")
class OpenedBusinessMonthAction implements IAction {
  public static final Log log = LogFactory.getLog(OpenedBusinessMonthAction.class)

  @Autowired
  FinancialYearService financialYearService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    List list = financialYearService.getOpenedBusinessMonthDetails(params)
    return list
  }

  public Object postCondition(def object) {
    return null
  }
}