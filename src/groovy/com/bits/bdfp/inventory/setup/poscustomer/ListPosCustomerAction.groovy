package com.bits.bdfp.inventory.setup.poscustomer

import com.bits.bdfp.inventory.setup.PosCustomerService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listPosCustomerAction")
class ListPosCustomerAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  PosCustomerService posCustomerService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
    try{
    init(params)
    Map result = [:]
    List objectList = null

    result = posCustomerService.getListForGrid(this)
    if (result) { // in case: normal list
      objectList = result.objList
      total = result.count
    }
    List objList = this.wrapListInGridEntityList(objectList, start)
    return objList
    }
    catch (Exception ex) {
    log.error(ex.message)
      return  null
    }
  }

  Map getResultForUI(List objList) {
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

  private List wrapListInGridEntityList(objList, start) {
    List instants = []
    int counter = start + 1;
    objList.each { instance ->
      GridEntity obj = new GridEntity()
      obj.id = instance.id
      obj.cell = ["${counter}",
                  "${instance.id ? instance.id : ''}",
                  
                  "${instance.enterpriseConfiguration? instance.enterpriseConfiguration : ''}",
                  "${instance.distributionPoint? instance.distributionPoint : ''}",
                  "${instance.customerMaster? instance.customerMaster : ''}",
                  "${instance.userCreated? instance.userCreated : ''}",
                  "${instance.userUpdated? instance.userUpdated : ''}",
                  "${instance.dateCreated? instance.dateCreated : ''}",
                  "${instance.dateUpdated? instance.dateUpdated : ''}"
                 ]
      instants << obj
      counter++
    };
    return instants
  }

   public Object postCondition(Object params, Object object) {
    return getResultForUI(object)
  }
}
