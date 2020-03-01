package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/25/2016.
 */
@Component("rejectMarketReturnAction")
class RejectMarketReturnAction extends Action {

    @Autowired
    ProcessMarketReturnService processMarketReturnService
    Message message

    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            MarketReturn marketReturn = MarketReturn.read(params.id)
            marketReturn.mrStatus = 'REJECTED'
            marketReturn.dateUpdated = new Date()
            marketReturn.userUpdated = (ApplicationUser) object
            Integer integer =  processMarketReturnService.rejectMr(marketReturn)
            if (integer == 1) {
                message = this.getMessage(marketReturn, Message.SUCCESS, "Market Return Rejected")
            } else {
                message = this.getMessage(marketReturn, Message.ERROR, "Market Return Rejection Failed")
            }
            return  message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("MarketReturn", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }
}
