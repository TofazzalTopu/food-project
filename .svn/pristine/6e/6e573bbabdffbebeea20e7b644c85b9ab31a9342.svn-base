package com.bits.bdfp.bonus.bonuscriteriasetup

import com.bits.bdfp.bonus.BonusCriteriaSetup
import com.bits.bdfp.bonus.BonusCriteriaSetupService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createBonusCriteriaSetupAction")
class CreateBonusCriteriaSetupAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  BonusCriteriaSetupService bonusCriteriaSetupService

  public Object preCondition(Object user, Object object) {

    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      BonusCriteriaSetup bonusCriteriaSetup = (BonusCriteriaSetup) object
      bonusCriteriaSetup.userCreated = applicationUser
      bonusCriteriaSetup.dateCreated = new Date()
      if (!bonusCriteriaSetup.validate()) {
        return null
      }
      return bonusCriteriaSetup
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return bonusCriteriaSetupService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}