package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.inventory.sales.MarketReturnService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/21/2016.
 */
@Component("listInvoiceFromStockAction")
class ListInvoiceFromStockAction extends Action {

    @Autowired
    MarketReturnService marketReturnService

    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object object, Object params) {
        return marketReturnService.fetchInvoiceFromStock(params)
    }
}
