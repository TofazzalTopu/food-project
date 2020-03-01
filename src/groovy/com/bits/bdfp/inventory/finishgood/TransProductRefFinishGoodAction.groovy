package com.bits.bdfp.inventory.finishgood

import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("transProductRefFinishGoodAction")
class TransProductRefFinishGoodAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  FinishGoodWarehouseService finishGoodWarehouseService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
       try {
           return finishGoodWarehouseService.transactionProductReference(params)
       } catch (Exception ex) {
           log.error(ex.message)
           return null
       }
  }


   public Object postCondition(Object params, Object object) {
    return null
  }
}
