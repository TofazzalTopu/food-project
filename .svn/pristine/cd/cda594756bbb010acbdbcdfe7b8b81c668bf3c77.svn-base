package com.bits.bdfp.inventory.setup.deliverytruck

import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.inventory.setup.DeliveryTruck
import com.bits.bdfp.inventory.setup.DeliveryTruckService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readDeliveryTruckAction")
class ReadDeliveryTruckAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  DeliveryTruckService deliveryTruckService
    @Autowired
  EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
  DistributionPointService      distributionPointService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       DeliveryTruck deliveryTruck = deliveryTruckService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(deliveryTruck.enterpriseConfiguration.id)
        DistributionPoint distributionPoint=distributionPointService.read(deliveryTruck.distributionPoint.id)
        return [deliveryTruck:deliveryTruck,distributionPoint:distributionPoint,enterpriseConfiguration:enterpriseConfiguration]
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