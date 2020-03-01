package com.bits.bdfp.inventory.product.productprice

import com.bits.bdfp.inventory.product.ProductPriceService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 4/21/16.
 */
@Component("getProductPriceByCustomerAndProductAction")
class GetProductPriceByCustomerAndProductAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductPriceService productPriceService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            if(params.territorySubAreaId) {
                return productPriceService.getProductPriceByCustomerAndProduct(Long.parseLong(params.customerId), Long.parseLong(params.productId), Long.parseLong(params.territorySubAreaId))
            }else{
                return productPriceService.getProductPriceByCustomerAndProduct(Long.parseLong(params.customerId), Long.parseLong(params.productId))
            }
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