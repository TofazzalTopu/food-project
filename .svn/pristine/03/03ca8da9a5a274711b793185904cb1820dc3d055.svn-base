package com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProduct
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProductService

@Component("listDailySalesTargetFinishProductAction")
class ListDailySalesTargetFinishProductAction extends Action {

    @Autowired
    DailySalesTargetFinishProductService dailySalesTargetFinishProductService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            List objectList = dailySalesTargetFinishProductService.listDailyTarget(params)
            List result = (List) this.wrapListInGridEntityList(objectList, start)
            Map output = [page: 1, total: 1, records: result.size(), rows: result]
            return output;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
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
                            "${instance.targetDate? instance.targetDate : ''}",
                            "${instance.quantity? instance.quantity : ''}"
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
        return null
   }
}