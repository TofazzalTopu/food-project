package com.bits.bdfp.inventory.product.finishproduct

import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listFinishProductAction")
class ListFinishProductAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  FinishProductService finishProductService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
    try{
    init(params)
    Map result = [:]
    List objectList = null

    result = finishProductService.getListForGrid(this,params)
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
                  
                  "${instance.en_name? instance.en_name : ''}",
                  "${instance.bu_name? instance.bu_name : ''}",
                  "${instance.pc_name? instance.pc_name : ''}",
                  "${instance.master_name? instance.master_name : ''}",
                  "${instance.main_name? instance.main_name : ''}",
                  "${instance.product_type_name? instance.product_type_name : ''}",
                  "${instance.code? instance.code : ''}",
                  "${instance.old_code? instance.old_code : ''}",
                  "${instance.name? instance.name : ''}",
                  "${instance.description? instance.description : ''}",
                  "${instance.pack_size? instance.pack_size : ''}",
                  "${instance.measure_name? instance.measure_name : ''}",
                  "${instance.length? instance.length : ''}",
                  "${instance.width? instance.width : ''}",
                  "${instance.color? instance.color : ''}",
                  "${instance.density? instance.density : ''}",
                  "${instance.texture? instance.texture : ''}",
                  "${instance.other? instance.other : ''}",
                  "${instance.sequence_number? instance.sequence_number : ''}",
                  "${instance.is_active ? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}"
                 ]
      instants << obj
      counter++
    };
    return instants
  }

   public Object postCondition(Object params, Object object) {
    return getResultForUI(object)
  }

    public Object finishProductList(Object params, Object user) {
        return finishProductService.finishProductList(params)
    }

    public Object finishProductPackSizeByProduct(Object params, Object user) {
        return finishProductService.finishProductPackSizeByProduct(params)
    }
    public Object getMainProduct(Object params, Object user) {
        return finishProductService.getMainProductList(params)
    }
     public Object getCategoryByChannelId(Object params, Object user) {
        return finishProductService.getCategoryByChannelId(params)
    }


}
