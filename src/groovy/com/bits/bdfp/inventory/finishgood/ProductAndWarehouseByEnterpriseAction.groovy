package com.bits.bdfp.inventory.finishgood

import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseService
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("productAndWarehouseByEnterpriseAction")
class ProductAndWarehouseByEnterpriseAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  FinishGoodWarehouseService finishGoodWarehouseService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
       try {
           ApplicationUser applicationUser=(ApplicationUser) object
           return finishGoodWarehouseService.inventoryListForFinishGood(applicationUser)
       } catch (Exception ex) {
           log.error(ex.message)
           throw new RuntimeException(ex.message)
       }
  }


   public Object postCondition(Object params, Object object) {
    return null
  }
}
