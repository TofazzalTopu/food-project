package com.bits.bdfp.common.cashpool

import com.bits.bdfp.common.CashPool
import com.bits.bdfp.common.CashPoolService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readCashPoolAction")
class ReadCashPoolAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CashPoolService cashPoolService
  @Autowired
  EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
   DistributionPointService distributionPointService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        CashPool cashPool= cashPoolService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(cashPool.enterpriseConfiguration.id)
        DistributionPoint distributionPoint=distributionPointService.read(cashPool.distributionPoint.id)
        return [cashPool:cashPool,distributionPoint:distributionPoint,enterpriseConfiguration:enterpriseConfiguration]
     } catch (Exception ex) {
     log.error(ex.message)
        return null
    }
  }

  public Object postCondition(Object params, Object object) {
    //Not implement
    return null
  }
}