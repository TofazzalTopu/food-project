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
@Component("mergeDemandOrderAction")
class MergeDemandOrderAction extends Action {

    @Autowired
    SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService
    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService

    Message message = null

    @Override
    protected Object preCondition(Object params, Object object) {
        return null
    }

    @Override
    protected Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    protected Object execute(Object params, Object object) {
        try {
            String[] ids = params.ids.split(',')
            Long latest = Long.parseLong(ids[0])
            int index = 0
            for (int i = 0; i < ids.size(); i++) {
                Long x = Long.parseLong(ids[i])
                if (x > latest) {
                    latest = x
                    index = i
                }
            }
            SecondaryDemandOrder secondaryDemandOrder = SecondaryDemandOrder.read(latest)

            for (int i = 0; i < ids.size(); i++) {
                if (i != index) {
                    int x = 1
                    while (x == 1) {
                        SecondaryDemandOrderDetails secondaryDemandOrderDetails = secondaryDemandOrderDetailsService.search('secondaryDemandOrder', ids[i])
                        if (secondaryDemandOrderDetails) {
                            secondaryDemandOrderDetails.secondaryDemandOrder = secondaryDemandOrder
                            secondaryDemandOrderDetailsService.update(secondaryDemandOrderDetails)
                        } else {
                            x = 0
                        }
                    }
                    SecondaryDemandOrder secondaryDemandOrder1 = SecondaryDemandOrder.read(Long.parseLong(ids[i]))
                    int j = secondaryDemandOrderService.delete(secondaryDemandOrder1)
                }
            }
            message = this.getMessage("Demand Orders", Message.SUCCESS, "Merge complete")
            return message
        }
        catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("SecondaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
            return null
        }
    }
}
