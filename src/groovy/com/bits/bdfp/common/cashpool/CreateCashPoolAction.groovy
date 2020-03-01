package com.bits.bdfp.common.cashpool

import com.bits.bdfp.common.CashPool
import com.bits.bdfp.common.CashPoolService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCashPoolAction")
class CreateCashPoolAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  CashPoolService cashPoolService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      CashPool cashPool = (CashPool) object
      cashPool.userCreated = applicationUser
      cashPool.dateCreated = new Date()
      if (!cashPool.validate()) {
        return null
      }
      return cashPool
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return cashPoolService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}