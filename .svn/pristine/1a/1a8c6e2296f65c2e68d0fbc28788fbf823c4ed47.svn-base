package com.bits.bdfp.settings.businessunitconfiguration

import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listBusinessUnitConfigurationAction")
class ListBusinessUnitConfigurationAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  BusinessUnitConfigurationService businessUnitConfigurationService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
    try{
        ApplicationUser applicationUser=(ApplicationUser) object
    init(params)
    Map result = [:]
    List objectList = null

    result = businessUnitConfigurationService.getListForGrid(this,params,applicationUser)
    if (result) { // in case: normal list
      objectList = result.objList
      total = result.count
        List objList = this.wrapListInGridEntityList(objectList, start)
        result = this.postCondition(objList, null)
        return result
    }

    }
    catch (Exception ex) {
    log.error(ex.message)
      return  null
    }
  }

  private List wrapListInGridEntityList(objList, start) {
    List instants = []
    int counter = start + 1;
    objList.each { instance ->
      GridEntity obj = new GridEntity()
      obj.id = instance.id
      obj.cell = ["${counter}",
                  "${instance.id ? instance.id : ''}",
                  
                  "${instance.ename? instance.ename : ''}",
                  "${instance.code? instance.code : ''}",
                  "${instance.name? instance.name : ''}",
                  "${instance.note? instance.note : ''}",

                  "${instance.is_active ? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}",
                 ]
      instants << obj
      counter++
    };
    return instants
  }

   public Object postCondition(Object objList, Object object) {
       int pageCount = 1
       if (resultPerPage < 0) {
           pageCount = 1
           resultPerPage=total
       }
       else {
           if (total > resultPerPage) {
               pageCount = Math.ceil(total / resultPerPage)
           }
       }
       Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
       return output;
  }
}
