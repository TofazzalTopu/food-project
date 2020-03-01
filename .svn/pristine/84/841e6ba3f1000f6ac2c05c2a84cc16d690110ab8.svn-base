package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.demandorder.PrintInvoiceStatus
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.bits.common.CodeGenerationUtil
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
 * Created by prianka.adhikary on 9/21/2015.
 */
@Component("savePrintStatusAction")
class SavePrintStatusAction extends Action{

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
                            isValidate = false
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
                    Invoice primaryInvoice = Invoice.read(Long.parseLong(val.id))
                    printInvoiceStatus = PrintInvoiceStatus.findByPrimaryDemandOrder(primaryInvoice.primaryDemandOrder)
                    if (printInvoiceStatus){
                        printInvoiceStatus.printStatus =  printInvoiceStatus.printStatus + 1
                        printInvoiceStatus.userUpdated = applicationUser
                        printInvoiceStatus.lastUpdated = new Date()
                    }
                    else{
                        printInvoiceStatus = new PrintInvoiceStatus()
                        printInvoiceStatus.primaryDemandOrder = primaryInvoice.primaryDemandOrder
                        printInvoiceStatus.invoiceNumber = primaryInvoice.code
                        printInvoiceStatus.printStatus = 1
                        printInvoiceStatus.userCreated = applicationUser
                        printInvoiceStatus.dateCreated = new Date()
                    }
                    printInvoiceStatusList.add(printInvoiceStatus)
                }

            }
            map.put("printInvoiceStatusList",printInvoiceStatusList)
            message = this.preCondition(null, map)
            if (message.type == 1) {
                int noOfRows = (int) primaryDemandOrderService.createPrintInvoice(map, applicationUser)
                if (noOfRows>0) {
                    message = this.getMessage("Message", Message.SUCCESS, "Print Status Updated Successfully")
                } else {
                    message = this.getMessage("Message", Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Message", Message.ERROR, "${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}
