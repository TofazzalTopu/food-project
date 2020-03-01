package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/16/2015.
 */
@Component("cancelDemandOrderAction")
class CancelDemandOrderAction extends Action {

    @Autowired
    SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService
    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService

    Message message = null


    public Object preCondition(Object params, Object object) {
        return null
    }


    public Object postCondition(Object params, Object object) {
        return null
    }


    public Object execute(Object params, Object object) {
        try {
//            String[] ids = params.ids.split(',')
//            for (int i = 0; i < ids.size(); i++) {
//                int x = 1
//                while (x == 1) {
//                    SecondaryDemandOrderDetails secondaryDemandOrderDetails = secondaryDemandOrderDetailsService.search('secondaryDemandOrder', ids[i])
//                    if (secondaryDemandOrderDetails) {
//                        secondaryDemandOrderDetailsService.delete(secondaryDemandOrderDetails)
//                    } else {
//                        x = 0
//                    }
//                }
//                SecondaryDemandOrder secondaryDemandOrder1 = SecondaryDemandOrder.read(Long.parseLong(ids[i]))
//                int j = secondaryDemandOrderService.delete(secondaryDemandOrder1)
//            }
            secondaryDemandOrderService.cancelOrder(params)
            message = this.getMessage("Demand Orders", Message.SUCCESS, "Cancellation complete")
            return message
        }
        catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("SecondaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
            return null
        }
    }
}
