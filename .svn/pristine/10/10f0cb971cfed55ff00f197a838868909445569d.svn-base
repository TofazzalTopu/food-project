package com.bits.bdfp.inventory.warehouse.subwarehousetype

import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.inventory.warehouse.SubWarehouseTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createSubWarehouseTypeAction")
class CreateSubWarehouseTypeAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  SubWarehouseTypeService subWarehouseTypeService

  public Object preCondition(Object user, Object object) {
    try {
      SubWarehouseType subWarehouseType = (SubWarehouseType) object
      ApplicationUser applicationUser=(ApplicationUser)user
      subWarehouseType.dateCreated=new Date()
      subWarehouseType.userCreated=applicationUser
      if (!subWarehouseType.validate()) {
        return null
      }
      return subWarehouseType
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return subWarehouseTypeService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}