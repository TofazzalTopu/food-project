package com.bits.bdfp.customer.customershippingaddress

import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.customer.CustomerShippingAddressService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerShippingAddressAction")
class CreateCustomerShippingAddressAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerShippingAddressService customerShippingAddressService

  public Object preCondition(Object params, Object object) {
    try {
      CustomerShippingAddress customerShippingAddress = (CustomerShippingAddress) object
      if (!customerShippingAddress.validate()) {
        return null
      }
      return customerShippingAddress
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return customerShippingAddressService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}