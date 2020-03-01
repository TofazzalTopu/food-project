package com.bits.bdfp.inventory.sales.receiveproductsfrommarket

import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/14/2016.
 */
@Component("readInvoiceByCustomerAction")
class ReadInvoiceByCustomerAction extends Action {

    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

    Message message

    @Override
    public Object preCondition(Object object1, Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object object, Object params) {
        List list = (List) receiveProductsFromMarketService.checkIntegrity(params)
        if(!list){
            message = this.getMessage("Integrity Check", Message.ERROR, 'Product not found in the given invoice.')
            return message
        }
        if (list[0].quantity < Float.parseFloat(params.quantity)) {
            message = this.getMessage("Integrity Check", Message.INFORMATION, 'Given quantity greater than invoiced quantity.')
        } else {
            message = this.getMessage("Integrity Check", Message.SUCCESS, 'Information Correct')
        }
        return message
    }

    @Override
    public Object execute(Object object, Object params) {
        if (object != null) {
            return receiveProductsFromMarketService.listBatch(params)
        } else {
            return receiveProductsFromMarketService.listInvoice(params)
        }
    }
}
