package com.bits.bdfp.accounts.mushak

import com.bits.bdfp.accounts.MushakService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/15/2016.
 */
@Component("listInvoiceDetailsForMushakAction")
class ListInvoiceDetailsForMushakAction extends Action {

    public static final Log log = LogFactory.getLog(ReadMushakAction.class)

    @Autowired
    MushakService mushakService

    Message message

    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    @Override
    public Object execute(Object params, Object Object) {
        try {
            init(params)
            Map result = [:]
            List objectList = mushakService.fetchInvoiceDetails(params)
            return this.wrapListInGridEntityList(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    private Object wrapListInGridEntityList(objList, start) {
        try {
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.finish_product_id
                obj.cell = ["${instance.finish_product_id ? instance.finish_product_id : ''}",
                            "${instance.name ? instance.name : ''}",
                            "${instance.quantity ? instance.quantity : ''}",
                            "${instance.rate ? instance.rate : ''}",
                            "${instance.sd_impose ? instance.sd_impose : '0'}",
                            "${instance.supplementary_duty ? instance.supplementary_duty : '0'}",
                            "${instance.sd_amount ? instance.sd_amount : '0'}",
                            "${instance.supplementary_duty ? instance.sd_impose + instance.sd_amount : instance.sd_impose}",
                            "${instance.vat ? instance.vat : '0'}",
                            "${instance.vat_amount ? instance.vat_amount : '0'}",
                            "${instance.supplementary_duty ? instance.sd_impose + instance.sd_amount + instance.vat_amount : instance.sd_impose + instance.vat_amount}",
                            '<a  href="javascript:deleteItemFromGrid(' + instance.finish_product_id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
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

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (total > resultPerPage) {
            pageCount = Math.ceil(total / resultPerPage)
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
