package com.bits.bdfp.bonus.customerbonusfinishgood

import com.bits.bdfp.bonus.CustomerBonusFinishGood
import com.bits.bdfp.bonus.CustomerBonusFinishGoodService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerBonusFinishGoodAction")
class UpdateCustomerBonusFinishGoodAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerBonusFinishGoodService customerBonusFinishGoodService

   public Object preCondition(Object params, Object object) {
    CustomerBonusFinishGood customerBonusFinishGood = CustomerBonusFinishGood.read(Long.parseLong(params?.id?.toString()))
    customerBonusFinishGood.properties=params
    if (!customerBonusFinishGood.validate()) {
      return null
    }
    return customerBonusFinishGood
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return customerBonusFinishGoodService.update(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }
}
