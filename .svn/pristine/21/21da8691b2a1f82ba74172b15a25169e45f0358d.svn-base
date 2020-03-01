package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnService
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/27/2016.
 */
@Component("listWarehouseByUserAction")
class ListWarehouseByUserAction extends Action {

    @Autowired
    ProcessMarketReturnService processMarketReturnService

    @Override
    public Object preCondition(Object object, Object params) {
        return null
    }

    @Override
    public Object postCondition(Object object, Object params) {
        return null
    }

    @Override
    public Object execute(Object object, Object params) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            params.put('userId', applicationUser.id)
            List dpList = processMarketReturnService.listDpByUser(params)
            Map result = [:]
            result.put('dpList', dpList? dpList : '')
            return result
        }catch (Exception e){

        }
    }
}
