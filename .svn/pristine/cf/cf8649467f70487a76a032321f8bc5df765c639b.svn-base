package com.bits.bdfp.inventory.demandorder.writeoff

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountService
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.commons.ObjectUtil
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.demandorder.WriteOff
import com.bits.bdfp.inventory.demandorder.WriteOffService

import com.docu.security.ApplicationUser
import java.text.SimpleDateFormat


@Component("createWriteOffAction")
class CreateWriteOffAction extends Action {
    public static final Log log = LogFactory.getLog(CreateWriteOffAction.class)
    private final String MESSAGE_HEADER = 'New Write Off'
    private final String MESSAGE_SUCCESS = 'Write Off Created Successfully'

    @Autowired
    SessionFactory sessionFactory
    @Autowired
    WriteOffService writeOffService
    @Autowired
    ChartOfAccountService chartOfAccountService

    Message message

    public Object preCondition(def params, def object) {
        try {
            Map map = (Map) object
            List<WriteOff> writeOffs = map.writeOffs
            writeOffs.each {
                if (!it.validate()) {
                    message = this.getValidationErrorMessage(it)
                    return message
                } else {
                    message = this.getMessage(it, Message.SUCCESS, SUCCESS_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Write Off", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object execute(def params, def object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.enterpriseConfiguration.id))
            Date dateNow = new Date()
            List<Invoice> invoices = []
            List<Invoice> branchInvoices = new ArrayList<Invoice>()
            Float totalWriteOffAmount=0.0
            List<WriteOff> writeOffs = ObjectUtil.instantiateObjects(params.items, WriteOff.class)
            CustomerMaster customerMaster = CustomerMaster.read(params.customerMaster.id)
            for (int i = 0; i < writeOffs.size(); i++) {
                writeOffs[i].dateCreated = dateNow
                writeOffs[i].userCreated = (ApplicationUser) object
                invoices.add(writeOffs[i].invoice)
                invoices[i].paidAmount += writeOffs[i].writeOffAmount
                invoices[i].lastUpdated = dateNow
                invoices[i].userUpdated = (ApplicationUser) object
                totalWriteOffAmount += writeOffs[i].writeOffAmount
            }

            Float amount = totalWriteOffAmount
            if(params.isBranch == '1') {
                List invoiceList = writeOffService.findInvoicesForBranch(params)
                for(int i = 0; amount > 0 && i < invoiceList.size(); i++){
                    Invoice invoice = Invoice.read(invoiceList[i].id)
                    if(invoiceList[i].due_amount >= amount){
                        invoice.paidAmount += amount
                        amount = 0
                    }else{
                        invoice.paidAmount += invoiceList[i].due_amount
                        amount -= invoiceList[i].due_amount
                    }
                    branchInvoices.add(invoice)
                }
            }

            //*******************************************COA Start*****************************************//

            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null

            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            Journal journalHead = new Journal(enterprise: enterpriseConfiguration,userCreated: applicationUser, isActive: true)
            journalHead.tableName = sessionFactory.getClassMetadata(WriteOff).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_WRITE_OFF

            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterpriseConfiguration, true)

            if(!journalHead.validate()){
                throw new RuntimeException(this.getValidationErrorMessage(journalHead).messageBody[0].toString())
            }

            ChartOfAccountsMapping chartOfAccountsBadDeptReverse =  ChartOfAccountsMapping.findByCoaType(COAType.BAD_DEPT_RESERVE)
            if(!chartOfAccountsBadDeptReverse){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bad Dept Reverse head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsReceivable =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
            if(!chartOfAccountsReceivable){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Account Receivable head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsPayable =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
            if(!chartOfAccountsPayable){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Account Payable head is not mapped with Chart of Accounts")
            }

            if(params.isBranch == '1'){
                DistributionPoint dp = DistributionPoint.read(Long.parseLong(params.distributionPoint.id))
                //Branch Dr
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsBadDeptReverse.chartOfAccounts
                journalDetails.prefixCode = ''
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = totalWriteOffAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "Bad Debt Reserve"

                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                //Branch Cr
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsReceivable.chartOfAccounts
                journalDetails.prefixCode = params.defaultCustomerCode
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0
                journalDetails.creditAmount = totalWriteOffAmount - amount
                journalDetails.particular = "Accounts Receivable of DP Default Customer: " + params.defaultCustomer
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                if(amount > 0) {
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsReceivable.chartOfAccounts
                    journalDetails.prefixCode = params.defaultCustomerCode
                    journalDetails.postfixCode = factoryDp.code
                    journalDetails.debitAmount = 0
                    journalDetails.creditAmount = amount
                    journalDetails.particular = "Accounts Receivable Remaining of DP Default Customer: " + params.defaultCustomer
                    if (!journalDetails.validate()) {
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)
                }

                //Branch Dr
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsPayable.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = dp.code
                journalDetails.debitAmount = totalWriteOffAmount
                journalDetails.creditAmount = 0
                journalDetails.particular = "Accounts Payable"
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                //Branch Cr
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsReceivable.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = dp.code
                journalDetails.debitAmount = 0
                journalDetails.creditAmount = totalWriteOffAmount
                journalDetails.particular = "Receivable of Sales Man: " + params.searchKey
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

            } else{
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsBadDeptReverse.chartOfAccounts
                journalDetails.prefixCode = ''
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = totalWriteOffAmount
                journalDetails.creditAmount = 0
                journalDetails.particular = "Bad Debt Reserve"
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsReceivable.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0
                journalDetails.creditAmount = totalWriteOffAmount
                journalDetails.particular = "Accounts Receivable of Customer: " + params.searchKey

                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

            }

            //*******************************************COA End*****************************************//

            Map map = [:]
            map.put('writeOffs', writeOffs)
            map.put('invoices', invoices)
            if(params.isBranch == '1') {
                map.put('branchInvoices', branchInvoices)
            }
            //map.put('subLedgers', subLedgers)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)

            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = writeOffService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage("Write Off", Message.SUCCESS, 'Selected Invoices Successfully Written Off.')
                } else {
                    message = this.getMessage('Write Off', Message.ERROR, 'Selected Invoices Could Not Be Written Off.')
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}