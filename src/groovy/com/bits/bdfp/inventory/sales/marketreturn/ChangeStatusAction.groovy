package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.inventory.sales.MarketReturnService
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 7/26/2016.
 */
@Component("changeStatusAction")
class ChangeStatusAction extends Action {

    @Autowired
    MarketReturnService marketReturnService
    @Autowired
    ProcessMarketReturnService processMarketReturnService

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
            if(object) {
                params.put('appUser', object.id)
                marketReturnService.changeStatus(params)
            }else{
                processMarketReturnService.changeStatus(params)
            }
            return this.getMessage("Market Return", Message.SUCCESS, '')
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
