package com.bits.bdfp.inventory.sales.receiveproductsfrommarket

import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/12/2016.
 */
@Component("listEnterpriseFinishProductAction")
class ListEnterpriseFinishProductAction extends Action {

    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

    public Object preCondition(def Object object1, def Object object2) {
        return null
    }

    public Object postCondition(def Object object1, def Object object2) {
        return null
    }

    public Object execute(Object object, Object params) {
        params.put('appId', object.id)
        return receiveProductsFromMarketService.listProduct(params);
    }
}
