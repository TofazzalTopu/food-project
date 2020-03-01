package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.accounts.*
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.AdjustSalesCommission
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.setup.CustomerSalesCommission
import com.bits.bdfp.inventory.setup.ProductSalesCommission
import com.bits.bdfp.inventory.setup.SalesCommission
import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by NZ on 5/4/2016.
 */
@Component("deleteCheckSalesCommissionAction")
class DeleteCheckSalesCommissionAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    SessionFactory sessionFactory

    @Autowired
    SalesCommissionService salesCommissionService
    Message message

    public Object preCondition(Object params, Object object) {
       return null
    }

    public Object execute(Object user, Object params) {
        try {

//            Date dateNow = new Date()
//            //********************************COA Start********************//
//            ApplicationUser applicationUser = (ApplicationUser) user
//            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
//            String currentMonth = formatMonth.format(dateNow)
//            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
//            String currentYear = formatYear.format(dateNow)
//            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
//            String currentDay = formatDay.format(dateNow)
//
//
//            //EnterpriseConfiguration enterpriseConfiguration=DistributionPoint.read(params.distributionPoint).enterpriseConfiguration
//            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(params.enterpriseConfiguration.id)
//            DistributionPoint distributionPoint = DistributionPoint.read(params.distributionPoint.id)
//
//            Journal journalHead = new Journal(enterprise: enterpriseConfiguration,userCreated: applicationUser, isActive: true)
//            journalHead.tableName = sessionFactory.getClassMetadata(AdjustSalesCommission).tableName
//            journalHead.transactionDate = new Date()
//            journalHead.journalStatus = JournalStatus.CHECKED
//            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
//            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
//            journalHead.moduleName = ApplicationConstants.MODULE_ADJUST_COMMISSION
//
//
//            if(!journalHead.validate()){
//                throw new RuntimeException(this.getValidationErrorMessage(journalHead).messageBody[0].toString())
//            }
//
//            ChartOfAccountsMapping chartOfAccountsPayable =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
//            if(!chartOfAccountsPayable){
//                return this.getMessage("Adjust Sales Commission", Message.ERROR, "Accounts Payable head is not mapped with Chart of Accounts")
//            }
//            ChartOfAccountsMapping chartOfAccountsReceivable =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
//            if(!chartOfAccountsReceivable){
//                return this.getMessage("Adjust Sales Commission", Message.ERROR, "Account Receivable head is not mapped with Chart of Accounts")
//            }
//            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
//            JournalDetails journalDetails = null
//            //********************************COA End********************//
//
//            CustomerMaster customerMaster = CustomerMaster.read(params.customerMaster.id)
//            Float totalAdjusted=0.00
//            List<Invoice> invoices = ObjectUtil.instantiateObjects(params.items, Invoice.class)
//            for (int i = 0; i < invoices.size(); i++) {
//                totalAdjusted+=Float.parseFloat(params.totalAdjusted)
//                invoices[i].userUpdated = (ApplicationUser) user
//                invoices[i].lastUpdated = new Date()
//            }
//
//            //********************************COA Start********************//
//            journalDetails = new JournalDetails(journal: journalHead, isActive: true, userCreated: applicationUser)
//            journalDetails.chartOfAccounts = chartOfAccountsPayable.chartOfAccounts
//            journalDetails.prefixCode = customerMaster.code
//            journalDetails.postfixCode = distributionPoint.code
//            journalDetails.debitAmount = totalAdjusted
//            journalDetails.creditAmount = 0.00
//            journalDetails.particular = "Accounts payable of Branch Book for customer: [" + customerMaster.code + "] "+customerMaster.name
//            journalDetailsList.add(journalDetails)
//
//
//            journalDetails = new JournalDetails(journal: journalHead, isActive: true, userCreated: applicationUser)
//            journalDetails.chartOfAccounts = chartOfAccountsReceivable.chartOfAccounts
//            journalDetails.prefixCode = customerMaster.code
//            journalDetails.postfixCode = distributionPoint.code
//            journalDetails.debitAmount = 0.00
//            journalDetails.creditAmount = totalAdjusted
//            journalDetails.particular = "Accounts receivable of Branch Book for customer: ["+customerMaster.code+"] "+customerMaster.name
//            journalDetailsList.add(journalDetails)
//
//            //********************************COA Start********************//
//
//            AdjustSalesCommission adjustSalesCommission = salesCommissionService.searchAdjust('customerMaster.id',params.customerMaster.id)
//            if (adjustSalesCommission) {
//                adjustSalesCommission.properties = params
//                adjustSalesCommission.userUpdated = (ApplicationUser) user
//            } else {
//                adjustSalesCommission = new AdjustSalesCommission(params)
//                adjustSalesCommission.userCreated = (ApplicationUser) user
//            }
//
//            Map map = [:]
//            map.put("invoices", invoices)
//            map.put("adjustSalesCommission", adjustSalesCommission)
//            map.put("journal", journalHead)
//            map.put("journalDetailsList", journalDetailsList)
//
//            message = this.preCondition(params, map)
//            if (message.type == 1) {
//                int noOfRows = salesCommissionService.adjustSalesCommission(map)
//                if (noOfRows > 0) {
//                    salesCommissionService.updateInvoice(params)
//                    message = this.getMessage("Adjust Sales Commission", Message.SUCCESS, "Invoices Successfully Adjusted Against Sales Commission.")
//                } else {
//                    message = this.getMessage("Adjust Sales Commission", Message.ERROR, "Invoices Could Not Be Adjusted.")
//                }
//            }

        } catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public Object checkSalesCommissionCustomerAssosiation(Object params, Object object) {
        SalesCommission salesCommission = SalesCommission.read(Long.parseLong(params.salesCommissionId))
        List<CustomerSalesCommission> customerSalesCommissionList = CustomerSalesCommission.findAllBySalesCommission(salesCommission)
        if(customerSalesCommissionList)
        {
            message = this.getMessage("Update Sales Commission", Message.ERROR, "'${customerSalesCommissionList.size()}'  Assosiation with Customer Could Not Be Deleted")
        }
        else{
             salesCommission = salesCommissionService.deleteSalesCommision(salesCommission)
            if (salesCommission) {
                message = this.getMessage(salesCommission, Message.SUCCESS, this.SUCCESS_DELETE)
            }
        }
        return message
    }
}
