package com.bits.bdfp.inventory.setup.deliverytruck

import com.bits.bdfp.inventory.setup.DeliveryTruck
import com.bits.bdfp.inventory.setup.DeliveryTruckService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDeliveryTruckAction")
class CreateDeliveryTruckAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  DeliveryTruckService deliveryTruckService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      DeliveryTruck deliveryTruck = (DeliveryTruck) object
      deliveryTruck.userCreated = applicationUser
      deliveryTruck.dateCreated = new Date()
      if (!deliveryTruck.validate()) {
        return null
      }
      return deliveryTruck
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return deliveryTruckService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}