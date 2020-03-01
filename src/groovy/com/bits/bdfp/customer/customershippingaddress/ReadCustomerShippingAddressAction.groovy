package com.bits.bdfp.customer.customershippingaddress

import com.bits.bdfp.customer.CustomerShippingAddressService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readCustomerShippingAddressAction")
class ReadCustomerShippingAddressAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerShippingAddressService customerShippingAddressService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       return customerShippingAddressService.read(Long.parseLong(params.id))
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