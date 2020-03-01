package com.bits.bdfp.setup.salestarget.saleshead


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.bits.bdfp.setup.salestarget.SalesHead
import com.bits.bdfp.setup.salestarget.SalesHeadService

@Component("listSalesHeadAction")
class ListSalesHeadAction extends Action {

    @Autowired
    SalesHeadService salesHeadService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = salesHeadService.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            return this.wrapListInGridEntityList(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

   Map getResultForUI(List objList) {
        int pageCount = 1
        if (total > resultPerPage) {
            pageCount = Math.ceil(total / resultPerPage)
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
   }

   private Object wrapListInGridEntityList(objList, start) {
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${counter}",
                          "${instance.id ? instance.id : ''}",
                            
                            "${instance.year? instance.year : ''}",
                            "${instance.employee? instance.employee : ''}",
                            "${instance.targetAmount? instance.targetAmount : ''}",
                            "${instance.startDate? instance.startDate : ''}",
                            "${instance.endDate? instance.endDate : ''}",
                            "${instance.isActive? instance.isActive : ''}"
                            ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
   }

   public Object postCondition(def params, def object) {
        return getResultForUI(params)
   }
}