package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.FinancialYearService
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("editBusinessMonthAction")
class EditBusinessMonthAction implements IAction {
  public static final Log log = LogFactory.getLog(EditBusinessMonthAction.class)

  @Autowired
  FinancialYearService financialYearService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    try {
       financialYearService.getEditBusinessMonthDetails(params)
    }
    catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }

  public Object postCondition(def object) {
    return null
  }
}