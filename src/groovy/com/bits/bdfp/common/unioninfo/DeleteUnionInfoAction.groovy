package com.bits.bdfp.common.unioninfo

import com.bits.bdfp.common.UnionInfoService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteUnionInfoAction")
class DeleteUnionInfoAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  UnionInfoService unionInfoService

   public Object preCondition(Object params, Object object) {
  try{
    return unionInfoService.read(Long.parseLong(params.id.toString()))
     }catch(Exception ex)
      {
        log.error(ex.getMessage());
      }
  }
   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return unionInfoService.delete(object)
    }
    catch (Exception ex) {
    log.error(ex.message)
      return new Integer(0)
    }
  }

}