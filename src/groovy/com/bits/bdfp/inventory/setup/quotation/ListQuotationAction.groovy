package com.bits.bdfp.inventory.setup.quotation


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.bits.bdfp.inventory.setup.Quotation
import com.bits.bdfp.inventory.setup.QuotationService

@Component("listQuotationAction")
class ListQuotationAction extends Action {

    @Autowired
    QuotationService quotationService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            List objectList = null
            objectList = quotationService.fetchDetails(params)
            total = objectList.size()
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
                obj.cell = ["${instance.id ? instance.id : ''}",
                            
                            "${instance.product_id? instance.product_id : ''}",
                            "${instance.name? instance.name : ''}",
                            "${instance.code? instance.code : ''}",
                            "${instance.quantity? instance.quantity : ''}",
                            "${instance.rate? instance.rate : ''}",
                            "${instance.total? instance.total : ''}",
                            '<a  href="javascript:deleteItemFromGrid(' + instance.id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
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