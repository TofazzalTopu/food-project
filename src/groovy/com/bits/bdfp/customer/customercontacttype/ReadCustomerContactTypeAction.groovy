package com.bits.bdfp.customer.customercontacttype

import com.bits.bdfp.customer.CustomerContactType
import com.bits.bdfp.customer.CustomerContactTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readCustomerContactTypeAction")
class ReadCustomerContactTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerContactTypeService customerContactTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       CustomerContactType customerContactType= customerContactTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(customerContactType.enterpriseConfiguration.id)
        return [customerContactType:customerContactType,enterpriseConfiguration:enterpriseConfiguration]
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