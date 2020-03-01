package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.sales.MarketReturnService
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/7/2016.
 */
@Component("readCustomerForMarketReturnAction")
class ReadCustomerForMarketReturnAction extends Action {

    @Autowired
    MarketReturnService marketReturnService
    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

    public Object preCondition(def Object object1, def Object object2) {
        return null
    }

    public Object postCondition(def Object object1, def Object object2) {
        return null
    }

    public Object execute(Object object, Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) object
            params.put('userId', applicationUser.id)
            Map result = [:]
            CustomerMaster customerMaster = null
            List dpList
            List factoryList
            List wareHouseList = null
            List subWareHouseList = null

            dpList = receiveProductsFromMarketService.listDpForUser(params)
            if (dpList.size() == 1) {
                params.put('dpId', dpList[0].id)
                wareHouseList = receiveProductsFromMarketService.listWareHouse(params)
                customerMaster = marketReturnService.fetchDefaultCustomer(params)
            }
            if (wareHouseList?.size() == 1) {
                params.put('invId', wareHouseList[0].id)
                subWareHouseList = receiveProductsFromMarketService.listSubWareHouse(params)
            }
            factoryList = marketReturnService.listDp(params)

            result.put('dpList', dpList)
            result.put('factoryList', factoryList)
            result.put('wareHouseList', wareHouseList)
            result.put('subWareHouseList', subWareHouseList)
            result.put('customer', customerMaster)
            return result
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
