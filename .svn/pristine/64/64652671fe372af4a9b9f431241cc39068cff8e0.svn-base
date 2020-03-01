package com.bits.bdfp.common.unioninfo

import com.bits.bdfp.common.UnionInfo
import com.bits.bdfp.common.UnionInfoService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createUnionInfoAction")
class CreateUnionInfoAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  UnionInfoService unionInfoService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser=(ApplicationUser)user
      UnionInfo unionInfo = (UnionInfo) object
      unionInfo.userCreated=applicationUser
      if (!unionInfo.validate()) {
        return null
      }
      return unionInfo
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return unionInfoService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}