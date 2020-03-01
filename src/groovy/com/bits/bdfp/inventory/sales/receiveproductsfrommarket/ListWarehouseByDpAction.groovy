package com.bits.bdfp.inventory.sales.receiveproductsfrommarket

import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/4/2016.
 */
@Component("listWarehouseByDpAction")
class ListWarehouseByDpAction extends Action {

    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

    Message message

    @Override
    public Object preCondition(Object object1, Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object object, Object params) {
        return message
    }

    @Override
    public Object execute(Object object, Object params) {
        if(params.dpId) {
            return receiveProductsFromMarketService.listWarehouse(params)
        }else{
            return receiveProductsFromMarketService.listSubWarehouse(params)
        }
    }
}
