package com.bits.bdfp.customer.customertype

import com.bits.bdfp.customer.CustomerTypeService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listCustomerTypeAction")
class ListCustomerTypeAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerTypeService customerTypeService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
    try{
    init(params)
    Map result = [:]
    List objectList = null

    result = customerTypeService.getListForGrid(this)
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
                  "${instance.name? instance.name : ''}",
                  "${instance.receivableCode? instance.receivableCode : ''}",
                  "${instance.advanceCode? instance.advanceCode : ''}",
                  "${instance.note? instance.note : ''}",
                  "${instance.userCreated? instance.userCreated : ''}",
                  "${instance.userUpdated? instance.userUpdated : ''}",
                  "${instance.dateCreated? instance.dateCreated : ''}",
                  "${instance.lastUpdated? instance.lastUpdated : ''}"
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
