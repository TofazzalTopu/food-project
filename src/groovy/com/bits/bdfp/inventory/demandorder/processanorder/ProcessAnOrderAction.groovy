package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.bits.bdfp.inventory.warehouse.FinishedProductBookedDetails
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("processAnOrderAction")
class ProcessAnOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    ProcessOrderService processOrderService
    Message message

    public Object preCondition(Object params, Object object) {
        List<FinishedProductBookedDetails> finishedProductBookedDetailsArrayList = []
        List<FinishedProductBooked> finishedProductBookedArrayList = []
        Boolean demandMet = true
        Boolean priceSet = true
        ApplicationUser applicationUser = (ApplicationUser) object
        try {
            Map processOrderInvoiceMap = processOrderService.getInvoiceAndSubLedgerList(params, applicationUser)
            boolean metDemand = true
            PrimaryDemandOrder primaryDemandOrder = primaryDemandOrderService.read(Long.parseLong(params?.val?.orderId))
            if (primaryDemandOrder) {
                List demandOrderDetailsList = processOrderService.primaryOrderDetailsList(primaryDemandOrder)
                if (demandOrderDetailsList && demandOrderDetailsList.size() > 0) {
                    for (int i = 0; i < demandOrderDetailsList.size(); i++) {
                        if(demandOrderDetailsList[i].qty){
                            if (demandOrderDetailsList[i].order_qty > demandOrderDetailsList[i].qty) {
                                metDemand = false
                                break;
                            }
                        }
                        else{
                            metDemand = false
                            break;
                        }

                    }
                } else {
                    metDemand = false
                }
                if (!metDemand) {
//                    msgBuffer.append("Demand can not be met for order no: " + primaryDemandOrder.orderNo)
                    demandMet = false
                }
                if(processOrderInvoiceMap?.invoiceList && processOrderInvoiceMap?.invoiceList.size()){
                    for (int j = 0; j < processOrderInvoiceMap?.invoiceList.size(); j++) {
                        if(processOrderInvoiceMap?.invoiceList[j].invoiceAmount == 0.0){
                            priceSet = false
                            break;
                        }
                    }
                }
            }



            if (!demandMet) {
//                message = this.getMessage("Process Demand Order", Message.ERROR, msgBuffer.toString())
                return [isValid: false,demandMet:demandMet,primaryDemandOrder:primaryDemandOrder,processOrderInvoiceMap:processOrderInvoiceMap,priceSet:priceSet]
            } else {
//                message = this.getMessage("Process Demand Order", Message.SUCCESS, "Demand can be met")
                return [ isValid: true,demandMet:demandMet,primaryDemandOrder:primaryDemandOrder,processOrderInvoiceMap:processOrderInvoiceMap,priceSet:priceSet]
            }


        } catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: false,demandMet: null]
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            StringBuffer msgBuffer = new StringBuffer();
            params.items.each { key, val ->
                if (val instanceof Map) {
                    params.put("val", val)
                    Map processOrderMap = this.preCondition(params, applicationUser)
                    if(processOrderMap){
                        if(!processOrderMap?.isValid && !processOrderMap?.demandMet){
                            msgBuffer.append("Demand can not be met for order no: " + processOrderMap?.primaryDemandOrder?.orderNo)
                            msgBuffer.append(System.lineSeparator())
                        }
                        else if(!processOrderMap?.priceSet){
                            msgBuffer.append("Price not set for territory: " + processOrderMap?.primaryDemandOrder?.orderNo)
                            msgBuffer.append(System.lineSeparator())
                        }
                        else{
                            if(!processOrderMap?.processOrderInvoiceMap.containsKey("message")){
                                processOrderService.bookOrderChangeInventoryAndOrderStatus(processOrderMap?.primaryDemandOrder,applicationUser,processOrderMap?.processOrderInvoiceMap)
                            }
                            else{
                                msgBuffer.append("Error Processing Order: " + processOrderMap?.processOrderInvoiceMap?.message)
                                msgBuffer.append(System.lineSeparator())
                            }
                        }
                    }
                }
            }
            if(msgBuffer.toString().length()>0){
                message = this.getMessage("Process Demand Order", Message.ERROR, msgBuffer.toString())
            }
            else{
                message = this.getMessage("Process Demand Order", Message.SUCCESS, "Demand order processed")
            }


        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Process Demand Order", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}