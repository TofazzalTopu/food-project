package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("processSecondaryDemandOrderAction")
class ProcessSecondaryDemandOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProcessOrderService processOrderService
    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService
    Message message

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object preCondition(Object params, Object object) {
        Boolean demandMet = true
        Boolean priceSet = true
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            boolean metDemand = true
            Map processOrderInvoiceMap = processOrderService.getInvoiceAndSubLedgerList(params, applicationUser)
            SecondaryDemandOrder secondaryDemandOrder = secondaryDemandOrderService.read(Long.parseLong(params?.val?.orderIds))
            if (secondaryDemandOrder) {
                List demandOrderDetailsList = processOrderService.checkSecondaryOrderDetailsList(secondaryDemandOrder.id.toString(), params?.val?.orderNo)
                if (demandOrderDetailsList && demandOrderDetailsList.size() > 0) {
                    for (int i = 0; i < demandOrderDetailsList.size(); i++) {
                        if (demandOrderDetailsList[i].qty) {
                            if (demandOrderDetailsList[i].order_qty > demandOrderDetailsList[i].qty) {
                                metDemand = false
                                break;
                            }
                        } else {
                            metDemand = false
                            break;
                        }

                    }
                } else {
                    metDemand = false
                }
                if (!metDemand) {
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
                return [isValid: false, demandMet: demandMet, secondaryDemandOrder: secondaryDemandOrder,processOrderInvoiceMap:processOrderInvoiceMap,priceSet:priceSet]
            } else {
                return [isValid: true, demandMet: demandMet, secondaryDemandOrder: secondaryDemandOrder,processOrderInvoiceMap:processOrderInvoiceMap,priceSet:priceSet]
            }


        } catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: false, demandMet: null]
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
                    if (processOrderMap) {
                        if (!processOrderMap?.isValid && !processOrderMap?.demandMet) {
                            msgBuffer.append("Demand can not be met for order no: " + processOrderMap?.secondaryDemandOrder?.orderNo)
                            msgBuffer.append(System.lineSeparator())
                        }else if(!processOrderMap?.priceSet){
                            msgBuffer.append("Price not set for territory: " + processOrderMap?.primaryDemandOrder?.orderNo)
                            msgBuffer.append(System.lineSeparator())
                        }else {
                            if(!processOrderMap?.processOrderInvoiceMap.containsKey("message")){
                                processOrderService.bookSecondaryOrderChangeInventoryAndOrderStatus(processOrderMap?.secondaryDemandOrder,applicationUser, params?.val?.orderNo,processOrderMap?.processOrderInvoiceMap)
                            }else{
                                msgBuffer.append("Error: " + processOrderMap?.processOrderInvoiceMap.message)
                                msgBuffer.append(System.lineSeparator())
                            }

                        }
                    }
                }
            }
            if (msgBuffer.toString().length() > 0) {
                message = this.getMessage("Secondary Demand Order", Message.ERROR, msgBuffer.toString())
            } else {
                message = this.getMessage("Secondary Demand Order", Message.SUCCESS, "Demand order processed")
            }


        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Secondary Demand Order", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }
}
