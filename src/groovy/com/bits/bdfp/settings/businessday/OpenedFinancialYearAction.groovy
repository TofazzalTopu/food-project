package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.FinancialYearService
import com.bits.bdfp.settings.bussinessday.FinancialYear
import com.docu.common.Action
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("openedFinancialYearAction")
class OpenedFinancialYearAction extends Action {
  public static final Log log = LogFactory.getLog(OpenedFinancialYearAction.class)

  @Autowired
  FinancialYearService financialYearService

  public Object preCondition(Object params,Object object) {
    return null
  }

  public Object execute(Object params, Object object) {
    List<FinancialYear> financialYearList = financialYearService.openedFinancialYearList()
    return financialYearList
  }

  public Object postCondition(Object params,Object object) {
    return null
  }
}