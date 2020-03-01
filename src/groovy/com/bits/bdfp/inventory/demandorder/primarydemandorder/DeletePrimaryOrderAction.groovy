package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderApprovalStatus
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/22/2015.
 */
@Component("deletePrimaryOrderAction")
class DeletePrimaryOrderAction extends Action {


    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            return primaryDemandOrderService.read(Long.parseLong(params.id.toString()))
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = primaryDemandOrderService.read(Long.parseLong(params.id.toString()))
            int length = Integer.parseInt(params.count)
            PrimaryDemandOrderDetails [] primaryDemandOrderDetails = new PrimaryDemandOrderDetails[length]
            params.items.each { key, val ->
                for (int i = 0; i < length; i++) {
                    if (key == "details[" + i + "]") {
                        primaryDemandOrderDetails[i] = PrimaryDemandOrderDetails.read(Long.parseLong(val.id))
                    }
                }
            }

            Map result = [:]
            result.put('primaryDemandOrder', primaryDemandOrder)
            result.put('primaryDemandOrderDetails', primaryDemandOrderDetails)
            Integer integer = primaryDemandOrderService.deletePrimaryOrder(result)
            if (integer == 1) {
                message = this.getMessage(primaryDemandOrder, Message.SUCCESS, this.SUCCESS_DELETE)
            } else {
                message = this.getMessage(primaryDemandOrder, Message.ERROR, this.FAIL_DELETE)
            }
            return message
        }
        catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("PrimaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

}

