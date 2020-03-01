package com.bits.bdfp.common.depositpool

import com.bits.bdfp.common.DepositPool
import com.bits.bdfp.common.DepositPoolService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDepositPoolAction")
class CreateDepositPoolAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  DepositPoolService depositPoolService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      DepositPool depositPool = (DepositPool) object
      depositPool.userCreated = applicationUser
      depositPool.dateCreated = new Date()
      if (!depositPool.validate()) {
        return null
      }
      return depositPool
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return depositPoolService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}