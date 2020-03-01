package com.bits.bdfp.rest

import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 12/27/2016.
 */

@Component("listProductForCheckAllQuantityAction")
class ListProductForCheckAllQuantityAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    RestDataService restDataService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object applicationUser) {
        try {
            return restDataService.getProductListForCheckAllQuantity(params,applicationUser)
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
