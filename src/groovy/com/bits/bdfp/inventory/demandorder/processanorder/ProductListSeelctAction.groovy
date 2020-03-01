package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by shazadur.rahman on 9/15/2015.
 */
@Component("productListSeelctAction")
class ProductListSeelctAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProcessOrderService processOrderService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            List objectList = []
            if(params.secondary){
                objectList = processOrderService.productListForUpdateOrderSecondary(params)
            }else {
                objectList = processOrderService.productListForUpdateOrder(params)
            }
            return objectList
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }


    public Object postCondition(Object objList, Object object) {
       return  null
    }


}
