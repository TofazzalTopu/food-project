package com.bits.bdfp.settings.businessunitconfiguration

import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readBusinessUnitConfigurationAction")
class ReadBusinessUnitConfigurationAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  BusinessUnitConfigurationService businessUnitConfigurationService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        ApplicationUser applicationUser=(ApplicationUser) object
       return businessUnitConfigurationService.readList(Long.parseLong(params.id),applicationUser)
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