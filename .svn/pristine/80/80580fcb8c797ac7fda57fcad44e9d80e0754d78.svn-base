package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by abhijit.majumder on 24/11/2015
 */
@Component("getAdvanceAmountFromSubLedgerAction")
class GetAdvanceAmountFromSubLedgerAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProcessOrderService processOrderService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }
    public Double execute(Object params, Object object) {
        try{

            Double result;
            result = processOrderService.getAdvanceAmountFromSubLedger(params)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }

    public Object postCondition(Object objList, Object object) {
        //not need to implement
        return null
    }


}
