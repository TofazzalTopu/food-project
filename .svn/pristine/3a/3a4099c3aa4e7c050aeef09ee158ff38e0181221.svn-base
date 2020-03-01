package com.bits.bdfp.inventory.product.finishproduct

import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.inventory.product.FinishProductService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/10/2015.
 */
@Component("listProductByEnterpriseAction")
class ListProductByEnterpriseAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishProductService finishProductService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return finishProductService.listProductByEnterprise(params)
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
