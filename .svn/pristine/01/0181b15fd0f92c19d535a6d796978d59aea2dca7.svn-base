package com.bits.bdfp.inventory.product.productprice

import com.bits.bdfp.inventory.product.ProductPriceService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/14/15.
 */
@Component("activateProductPriceAction")
class ActivateProductPriceAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductPriceService productPriceService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            String[] priceNameList = params.priceNameList.split(",")
            int  activated = 0
            for(int i = 0; i < priceNameList.length; i++){
                if(priceNameList[i] != '') {
                    params.put("priceName", priceNameList[i])
                    activated += productPriceService.activateProductPrice(params)
                }
            }
            if(activated >= 0){
                return this.getMessage("Product Price Name Activation", Message.SUCCESS, activated.toString() + " product price name Activated Successfully")
            }else{
                return this.getMessage("Product Price Name Activation", Message.ERROR, "No product price name Activated Successfully")
            }
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Activate Product Price", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}