package com.bits.bdfp.customer.customershippingaddress

import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.customer.CustomerShippingAddressService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerShippingAddressAction")
class UpdateCustomerShippingAddressAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerShippingAddressService customerShippingAddressService

   public Object preCondition(Object params, Object object) {
    CustomerShippingAddress customerShippingAddress = CustomerShippingAddress.read(Long.parseLong(params?.id?.toString()))
    customerShippingAddress.properties=params
    if (!customerShippingAddress.validate()) {
      return null
    }
    return customerShippingAddress
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return customerShippingAddressService.update(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }
}
