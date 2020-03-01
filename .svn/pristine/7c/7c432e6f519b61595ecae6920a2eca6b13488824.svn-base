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
 * Created by mdalinaser.khan on 1/24/16.
 */
@Component("listRetailOrderForProcessAction")
class ListRetailOrderForProcessAction extends Action {
    public static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, Object object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            List result = retailOrderService.listRetailOrderForProcessing(params)
            List objList = this.wrapListInGridEntityList(result, start)
            return [page: 1, total: 1, records: objList.size(), rows: objList]
        }
        catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
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
            obj.cell = ["${counter}",
                    "${instance.id ? instance.id : ''}",
                    "${instance.orderNo ? instance.orderNo : ''}",
                    "${instance.orderAmount ? instance.orderAmount : ''}",
                    "${instance.orderDate ? instance.orderDate : ''}",
                    "${instance.legacyId ? instance.legacyId : ''}",
                    "${instance.customerCode ? instance.customerCode : ''}",
                    "${instance.customerName ? instance.customerName : ''}",
                    "${instance.deliveryDate ? instance.deliveryDate : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
