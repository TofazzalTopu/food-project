package com.bits.bdfp.bonus.customerbonusfinishgood

import com.bits.bdfp.bonus.CustomerBonusFinishGood
import com.bits.bdfp.bonus.CustomerBonusFinishGoodService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerBonusFinishGoodAction")
class CreateCustomerBonusFinishGoodAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerBonusFinishGoodService customerBonusFinishGoodService

  public Object preCondition(Object params, Object object) {
    try {
      CustomerBonusFinishGood customerBonusFinishGood = (CustomerBonusFinishGood) object
      if (!customerBonusFinishGood.validate()) {
        return null
      }
      return customerBonusFinishGood
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return customerBonusFinishGoodService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}