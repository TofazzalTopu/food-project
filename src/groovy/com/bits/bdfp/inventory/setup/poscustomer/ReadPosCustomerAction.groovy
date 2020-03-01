package com.bits.bdfp.inventory.setup.poscustomer

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.inventory.setup.PosCustomer
import com.bits.bdfp.inventory.setup.PosCustomerService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readPosCustomerAction")
class ReadPosCustomerAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  PosCustomerService posCustomerService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
    DistributionPointService      distributionPointService
    @Autowired
    CustomerMasterService customerMasterService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       PosCustomer posCustomer = posCustomerService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(posCustomer.enterpriseConfiguration.id)
        DistributionPoint distributionPoint=distributionPointService.read(posCustomer.distributionPoint.id)
        CustomerMaster customerMaster = customerMasterService.read(posCustomer.customerMaster.id)
        return [posCustomer:posCustomer,distributionPoint:distributionPoint,
                enterpriseConfiguration:enterpriseConfiguration,customerMaster:customerMaster]
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