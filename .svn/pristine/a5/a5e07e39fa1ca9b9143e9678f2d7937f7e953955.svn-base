package com.bits.bdfp.inventory.product.finishproduct

import com.bits.bdfp.inventory.product.FinishProductService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 12/7/15.
 */
@Component("listProductPriceByCustomerAction")
class ListProductPriceByCustomerAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishProductService finishProductService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public List listProductForRetailOrder(Object params, Object object) {
        try {
            return finishProductService.listProductPriceByCustomerForRetail(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    public Object execute(Object params, Object object) {
        try {
            return finishProductService.listProductPriceByCustomer(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
