package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.demandorder.PrintInvoiceStatus
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by prianka.adhikary on 9/27/2015.
 */
@Component("updateDeliveryDateAction")
class UpdateDeliveryDateAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService


    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map =(Map) object

            boolean isValidate=true
            List <PrimaryDemandOrder> primaryDemandOrderList  = map.primaryDemandOrderList


            if (primaryDemandOrderList && primaryDemandOrderList.size()>0){
                primaryDemandOrderList.each {
                    if (!it.validate()){
                        isValidate=false
                        message = this.getValidationErrorMessage(it)
                    }
                }
            }
            else{
                isValidate=false
                message = this.getMessage('Primary Demand Order', Message.ERROR, "You did not select any demand order .Please select")
            }

            if (isValidate){
                message = this.getMessage('Primary Demand Order', Message.SUCCESS, this.SUCCESS_SAVE)

            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Primary Demand Order", Message.ERROR, "${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object

            Map map = [:]

            List <PrimaryDemandOrder> primaryDemandOrderList = []
            List <PrimaryDemandOrderDetails> primaryDemandOrderDetailsList=[]
            List priceList=[]
            PrimaryDemandOrderDetails primaryDemandOrderDetails=null
            params.items.each { key, val ->
                if (val instanceof Map) {
                    PrimaryDemandOrder primaryDemandOrder = primaryDemandOrderService.read(Long.parseLong(val.id))
                        primaryDemandOrder.updatedDeliveryDate = DateUtil.getSimpleDate(params?.orderDateFrom?.toString())
                        primaryDemandOrderList.add(primaryDemandOrder)
                }
            }
            map.put("primaryDemandOrderList",primaryDemandOrderList)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                int noOfRows = (int) primaryDemandOrderService.updateDeliveryDate(map)
                if (noOfRows>0) {
                    message = this.getMessage("Primary Demand Order", Message.SUCCESS, "Delivery Date Updated Successfully")
                } else {
                    message = this.getMessage("Primary Demand Order", Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Primary Demand Order", Message.ERROR, "${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
