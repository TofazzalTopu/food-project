package com.bits.bdfp.inventory.retailorder.processretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/31/16.
 */
@Component("listManualProcessOrderDetailsAction")
class ListManualProcessOrderDetailsAction extends Action {
    public static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, Object object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            List result = retailOrderService.listManualProcessOrderDetailsForProcessing(params)
            List objList = this.wrapListInGridEntityList(result, start)
            return [page: 1, total: 1, records: objList.size(), rows: objList]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }

    private List wrapListInGridEntityList(List retailOrderList, int start) {
        List instants = []
        int counter = start + 1;
        retailOrderList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.productId ? instance.productId : ''}",
                    "${instance.productCode ? instance.productCode : ''}",
                    "${instance.productName ? instance.productName : ''}",
                    "${instance.availableQty ? instance.availableQty : ''}",
                    "${instance.orderQty ? instance.orderQty : ''}",
                    "${instance.rate ? instance.rate : ''}",
                //    "${instance.batchNo ? instance.batchNo : ''}",
                    "${instance.processQty ? instance.processQty : ''}",
                    "${instance.amount ? instance.amount : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
