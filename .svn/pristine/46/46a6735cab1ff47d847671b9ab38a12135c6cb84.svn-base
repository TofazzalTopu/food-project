package com.bits.bdfp.inventory.product.productprice

import com.bits.bdfp.inventory.product.ProductPriceService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/15/15.
 */
@Component("readProductPriceCommonDataAction")
class ReadProductPriceCommonDataAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductPriceService productPriceService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return productPriceService.productPriceCommonData(params)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}