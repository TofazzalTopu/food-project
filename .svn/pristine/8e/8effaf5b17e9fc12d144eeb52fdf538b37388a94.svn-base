package com.bits.bdfp.accounts.mushak

import com.bits.bdfp.accounts.MushakService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/20/2016.
 */
@Component("listMushakDetailsAction")
class ListMushakDetailsAction extends Action{

    @Autowired
    MushakService mushakService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            objectList = mushakService.listMushakDetails(params)
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
        try {
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? instance.id : ''}",

                            "${instance.finish_product_id ? instance.finish_product_id : ''}",
                            "${instance.name ? instance.name : ''}",
                            "${instance.quantity ? instance.quantity : ''}",
                            "${instance.rate ? instance.rate : ''}",
                            "${instance.sd_impose ? instance.sd_impose : ''}",
                            "${instance.sd_percent ? instance.sd_percent : ''}",
                            "${instance.sd_amount ? instance.sd_amount : ''}",
                            "${instance.vat_impose ? instance.vat_impose : ''}",
                            "${instance.vat_percent ? instance.vat_percent : ''}",
                            "${instance.vat_amount ? instance.vat_amount : ''}",
                            "${instance.total_amount ? instance.total_amount : ''}",
                            '<a  href="javascript:deleteItemFromGrid(' + instance.id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return getResultForUI(params)
    }
}
