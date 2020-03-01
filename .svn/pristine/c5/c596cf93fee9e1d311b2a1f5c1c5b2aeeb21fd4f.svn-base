package com.bits.bdfp.customer.customerpaymenttype

import com.bits.bdfp.customer.CustomerPaymentType
import com.bits.bdfp.customer.CustomerPaymentTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readCustomerPaymentTypeAction")
class ReadCustomerPaymentTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerPaymentTypeService customerPaymentTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        CustomerPaymentType customerPaymentType = customerPaymentTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(customerPaymentType.enterpriseConfiguration.id)
        return [customerPaymentType:customerPaymentType,enterpriseConfiguration:enterpriseConfiguration]
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