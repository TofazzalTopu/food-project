package com.bits.bdfp.bonus.customerbonusfinishgood

import com.bits.bdfp.bonus.CustomerBonusFinishGoodService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listCustomerBonusFinishGoodAction")
class ListCustomerBonusFinishGoodAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerBonusFinishGoodService customerBonusFinishGoodService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
    try{
    init(params)
    Map result = [:]
    List objectList = null

    result = customerBonusFinishGoodService.getListForGrid(this)
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
                  
                  "${instance.bonusCriteriaSetup? instance.bonusCriteriaSetup : ''}",
                  "${instance.bonusQuantity? instance.bonusQuantity : ''}",
                  "${instance.dateCreated? instance.dateCreated : ''}",
                  "${instance.finishedProductBookedDetails? instance.finishedProductBookedDetails : ''}",
                  "${instance.lastUpdated? instance.lastUpdated : ''}",
                  "${instance.userCreated? instance.userCreated : ''}",
                  "${instance.userUpdated? instance.userUpdated : ''}"
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
