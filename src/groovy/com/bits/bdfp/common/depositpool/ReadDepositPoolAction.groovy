package com.bits.bdfp.common.depositpool

import com.bits.bdfp.common.DepositPool
import com.bits.bdfp.common.DepositPoolService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readDepositPoolAction")
class ReadDepositPoolAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  DepositPoolService depositPoolService
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
        DepositPool depositPool= depositPoolService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(depositPool.enterpriseConfiguration.id)
        DistributionPoint distributionPoint=distributionPointService.read(depositPool.distributionPoint.id)
        return [depositPool:depositPool,distributionPoint:distributionPoint,enterpriseConfiguration:enterpriseConfiguration]
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