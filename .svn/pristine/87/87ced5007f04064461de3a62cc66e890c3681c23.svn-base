package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.demandorder.PrintInvoiceStatus
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/21/2015.
 */
@Component("saveSecondaryPrintStatusAction")
class SaveSecondaryPrintStatusAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map =(Map) object

            boolean isValidate=true
            List <PrintInvoiceStatus> printInvoiceStatusList  = map.printInvoiceStatusList


                if (printInvoiceStatusList && printInvoiceStatusList.size()>0){
                    printInvoiceStatusList.each {
                        if (!it.validate()){
                            isValidate=false
                            message = this.getValidationErrorMessage(it)
                        }
                    }
                }
                else{
                    isValidate=false
                    message = this.getMessage('PrintInvoiceStatus', Message.ERROR, "You did not select any invoice .Please select")
                }

            if (isValidate){
                    message = this.getMessage('PrintInvoiceStatus', Message.SUCCESS, this.SUCCESS_SAVE)

            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("PrintInvoiceStatus", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            Integer counter=0
            Map map = [:]
            PrintInvoiceStatus printInvoiceStatus=null
            List <PrintInvoiceStatus> printInvoiceStatusList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    SecondaryDemandOrder secondaryDemandOrder = SecondaryDemandOrder.read(Long.parseLong(val.id))
                    printInvoiceStatus = PrintInvoiceStatus.findBySecondaryDemandOrder(secondaryDemandOrder)
                    if (printInvoiceStatus){
                        printInvoiceStatus.secondaryDemandOrder= secondaryDemandOrder
                        printInvoiceStatus.invoiceNumber = val.invoice_number
                        printInvoiceStatus.printStatus =  printInvoiceStatus.printStatus + 1
                        printInvoiceStatus.userUpdated = applicationUser
                        printInvoiceStatus.lastUpdated = new Date()
                        printInvoiceStatusList.add(printInvoiceStatus)
                    }
                    else{
                        printInvoiceStatus = new PrintInvoiceStatus()
                        printInvoiceStatus.secondaryDemandOrder = secondaryDemandOrder
                        printInvoiceStatus.invoiceNumber = val.invoice_number
                        printInvoiceStatus.printStatus = counter+1
                        printInvoiceStatus.userCreated = applicationUser
                        printInvoiceStatus.dateCreated = new Date()
                        printInvoiceStatusList.add(printInvoiceStatus)
                    }
                }
            }
            map.put("printInvoiceStatusList",printInvoiceStatusList)


            message = this.preCondition(null, map)
            if (message.type == 1) {
                int noOfRows = (int) primaryDemandOrderService.createSecondaryPrintInvoice(map)
                if (noOfRows>0) {
                    message = this.getMessage("Message", Message.SUCCESS, "Print Status Updated Successfully")
                } else {
                    message = this.getMessage("Message", Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("PrintInvoiceStatus", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}
