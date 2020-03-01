package com.bits.bdfp.customer.customerterritorysubarea

import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.customer.CustomerTerritorySubAreaService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerTerritorySubAreaAction")
class UpdateCustomerTerritorySubAreaAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerTerritorySubAreaService customerTerritorySubAreaService

   public Object preCondition(Object params, Object object) {
    CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.read(Long.parseLong(params?.id?.toString()))
    customerTerritorySubArea.properties=params
    if (!customerTerritorySubArea.validate()) {
      return null
    }
    return customerTerritorySubArea
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return customerTerritorySubAreaService.update(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }
}
