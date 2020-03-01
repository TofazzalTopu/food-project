package com.bits.bdfp.inventory.setup.quotation

import com.bits.bdfp.inventory.setup.QuotationService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 4/5/2016.
 */
@Component("showCsListAction")
class ShowCsListAction extends Action {

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
            objectList = quotationService.fetchCsDetails(params)
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
                            "${instance.finish_product_id? instance.finish_product_id : ''}",
                            "${instance.name? instance.name : ''}",
                            "${instance.code? instance.code : ''}",
                            "${instance.quantity? instance.quantity : ''}",
                            "${instance.rate? instance.rate : ''}",
                            "${instance.total? instance.total : ''}",
                            "${instance.customer_name? instance.customer_name : ''}",
                            "${instance.quotation_no? instance.quotation_no : ''}",
                            "${instance.quotation_date? instance.quotation_date : ''}",
                            "${instance.valid_from? instance.valid_from : ''}",
                            "${instance.valid_to? instance.valid_to : ''}"
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
