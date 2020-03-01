package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.UserMessageBuilder
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by abhijit.majumder on 11/25/2015.
 */
@Component("processInvoiceAccountsAction")
class ProcessInvoiceAccountsAction extends Action {
    @Autowired
    ProcessOrderService processOrderService
    Message message

    public Object preCondition(Object params, Object object) {
        Map mapInstance = [:]
        StringBuffer msgBuffer = new StringBuffer();
        try {
            SubLedger subLedger = null
            List<SubLedger> subLedgerList = []
            List<List<SubLedger>> listList = []
            ApplicationUser applicationUser = (ApplicationUser) object
            ApplicationUserEnterprise applicationUserEnterprise = ApplicationUserEnterprise.findByApplicationUser(applicationUser)
            EnterpriseConfiguration enterpriseConfiguration = applicationUserEnterprise.enterpriseConfiguration
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            List<Invoice> invoiceList = []
            Invoice invoice = null
            params.invItems.each { key, val ->
                if (val instanceof Map) {
                    invoice = new Invoice()
                    invoice.primaryDemandOrder = PrimaryDemandOrder.read(val.orderId)
                    invoice.defaultCustomer = CustomerMaster.read(val.defaultCustomerId)
                    invoice.invoiceAmount = Double.parseDouble(val.orderAmount)
                    invoice.vat = 0
                    invoice.ait = 0
                    invoice.paidAmount = getAdvanceAmount(val.advAccCode)
                    invoice.userCreated = applicationUser
                    invoice.dateCreated = new Date()
                    invoice.code = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "INVOICE", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
                    invoice.isActive = true
                    invoiceList.add(invoice)
                    subLedgerList = GetSubLedgerlist(invoice.invoiceAmount, invoice.paidAmount, applicationUser, val.advAccCode, val.rcvAccCode, invoice.defaultCustomer.name)
                    listList.add(subLedgerList)
                }
            }
            if (!invoice.validate()) {
                return message = this.getMessage("Process Demand Order", Message.ERROR, msgBuffer.toString())
            }
            message = this.getMessage("Process Demand Order", Message.SUCCESS, msgBuffer.toString())
            mapInstance.put("message", message)
            mapInstance.put("invoiceList", invoiceList)
            mapInstance.put("subLedgerList", listList)
        }
        catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: false, demandMet: null]
        }

        return mapInstance


    }

    public List<SubLedger> GetSubLedgerlist(double invoiceAmount, double advanceAmount, ApplicationUser applicationUser, String advAccCode, String rcvAccCode, String customerName) {
        SubLedger subLedger = null
        StringBuffer msgBuffer = new StringBuffer();
        ApplicationUserEnterprise applicationUserEnterprise = ApplicationUserEnterprise.findByApplicationUser(applicationUser)
        EnterpriseConfiguration enterpriseConfiguration = applicationUserEnterprise.enterpriseConfiguration
        Date dateNow = new Date()
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
        String currentMonth = formatMonth.format(dateNow)
        SimpleDateFormat formatYear = new SimpleDateFormat("YY")
        String currentYear = formatYear.format(dateNow)
        List<SubLedger> subLedgerList = []
        if (invoiceAmount > advanceAmount && advanceAmount == 0.0) {
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = "212330069"
            subLedger.credit = invoiceAmount
            subLedger.debit = 0.0
            subLedger.description = "Sales"
            subLedger.isActive = true
            subLedger.transactionNo = "111000"
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = rcvAccCode
            subLedger.credit = 0.0
            subLedger.debit = invoiceAmount
            subLedger.description = "Receivable from " + customerName
            subLedger.isActive = true
            subLedger.transactionNo = "111000"
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)

        } else if (invoiceAmount > advanceAmount && advanceAmount != 0.0) {
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = "212330069"
            subLedger.credit = invoiceAmount
            subLedger.debit = 0.0
            subLedger.description = "Sales"
            subLedger.isActive = true
            subLedger.transactionNo = "111000"
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = rcvAccCode
            subLedger.credit = 0.0
            subLedger.debit = invoiceAmount - advanceAmount
            subLedger.description = "Receivable from " + customerName
            subLedger.isActive = true
            subLedger.transactionNo = "111000"
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
        } else if (invoiceAmount <= advanceAmount) {
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = "212330069"
            subLedger.credit = invoiceAmount
            subLedger.debit = 0.0
            subLedger.description = "Sales"
            subLedger.isActive = true
            subLedger.transactionNo = "111000"
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = advAccCode
            subLedger.credit = 0.0
            subLedger.debit = invoiceAmount
            subLedger.description = "Receivable from " + customerName
            subLedger.isActive = true
            subLedger.transactionNo = "111000"
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
        }
        return subLedgerList
    }

    public double getAdvanceAmount(String advAccCode) {
        return processOrderService.getAdvanceAmountFromSubLedger(advAccCode)
    }

    public Object execute(Object params, Object object) {
        ApplicationUser applicationUser = (ApplicationUser) object
        Map processOrderMap = this.preCondition(params, applicationUser)
        Map map = [:]
        try {
            map = (Map) processOrderMap
            message = processOrderService.SaveInvoice(processOrderMap)

        } catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: false, demandMet: null]
        }

        return message

    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
