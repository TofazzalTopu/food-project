package com.bits.bdfp.customer.customerpriority

import com.bits.bdfp.customer.CustomerPriority
import com.bits.bdfp.customer.CustomerPriorityService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readCustomerPriorityAction")
class ReadCustomerPriorityAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerPriorityService customerPriorityService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        CustomerPriority customerPriority= customerPriorityService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(customerPriority.enterpriseConfiguration.id)
        return [customerPriority:customerPriority,enterpriseConfiguration:enterpriseConfiguration]
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