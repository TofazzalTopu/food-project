package com.bits.bdfp.customer.customerterritorysubarea

import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.customer.CustomerTerritorySubAreaService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerTerritorySubAreaAction")
class CreateCustomerTerritorySubAreaAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerTerritorySubAreaService customerTerritorySubAreaService

  public Object preCondition(Object params, Object object) {
    try {
      CustomerTerritorySubArea customerTerritorySubArea = (CustomerTerritorySubArea) object
      if (!customerTerritorySubArea.validate()) {
        return null
      }
      return customerTerritorySubArea
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return customerTerritorySubAreaService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}