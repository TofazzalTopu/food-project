package com.bits.bdfp.inventory.demandorder.adjustusingho

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountService
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.AdjustUsingHoDetails
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
import com.bits.bdfp.inventory.demandorder.AdjustUsingHo
import com.bits.bdfp.inventory.demandorder.AdjustUsingHoService


import com.docu.security.ApplicationUser

import java.text.SimpleDateFormat


@Component("createAdjustUsingHoAction")
class CreateAdjustUsingHoAction extends Action {
    public static final Log log = LogFactory.getLog(CreateAdjustUsingHoAction.class)
    private final String MESSAGE_HEADER = 'New Adjust Using Ho'
    private final String MESSAGE_SUCCESS = 'Adjust Using Ho Created Successfully'

    Message message
    @Autowired
    SessionFactory sessionFactory

    @Autowired
    AdjustUsingHoService adjustUsingHoService

    @Autowired
    ChartOfAccountService chartOfAccountService


    public Object preCondition(def params, def object) {
        try {
            Map map = (Map) object
            AdjustUsingHo adjustUsingHo = map.adjustUsingHo
            if (!adjustUsingHo.validate()) {
                message = this.getValidationErrorMessage(adjustUsingHo)
            } else {
                message = this.getMessage(adjustUsingHo, Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Write Off", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(def params, def object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.enterpriseConfiguration.id))
            AdjustUsingHo adjustUsingHo = new AdjustUsingHo(params)
            Date dateNow = new Date()
            adjustUsingHo.userCreated = applicationUser
            adjustUsingHo.dateCreated = dateNow
            if(!adjustUsingHo.validate()) {
                return this.getValidationErrorMessage(adjustUsingHo)
            }

            List<Invoice> invoices = []
            List<AdjustUsingHoDetails> adjustUsingHoDetailsList = ObjectUtil.instantiateObjects(params.items, AdjustUsingHoDetails.class)
            for (int i = 0; i < adjustUsingHoDetailsList.size(); i++) {
                adjustUsingHoDetailsList[i].adjustUsingHo = adjustUsingHo
                invoices.add(adjustUsingHoDetailsList[i].invoice)
                invoices[i].paidAmount += adjustUsingHoDetailsList[i].adjustedAmount
                invoices[i].lastUpdated = dateNow
                invoices[i].userUpdated = applicationUser
            }

            CustomerMaster customerMaster = adjustUsingHo.customerMaster

            /***************************** COA Entry Start ***************************/
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterpriseConfiguration, true)
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName =  sessionFactory.getClassMetadata(AdjustUsingHo).tableName
//                    GrailsDomainBinder.getMapping(CustomerPayment).table.name
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo =  CodeGenerationUtil.generate(8).toString()   //
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_ADJUST_USING_HO_ACCOUNT
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            ChartOfAccountsMapping chartOfAccountsMappingCurrentHo =  ChartOfAccountsMapping.findByCoaType(COAType.CURRENT_ACCOUNT_WITH_HO)
            if(!chartOfAccountsMappingCurrentHo){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Current Account with HO head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsMappingAccountsReceivable =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
            if(!chartOfAccountsMappingAccountsReceivable){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable head is not mapped with Chart of Accounts")
            }
            // DR
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingCurrentHo.chartOfAccounts
            journalDetails.prefixCode = ''
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = adjustUsingHo.totalAmount
            journalDetails.creditAmount = 0.00
            journalDetails.particular = ApplicationConstants.ADJUSTMENT_USING_HO + " for Customer: [" + customerMaster.code + "] " + customerMaster.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)


            // CR
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsReceivable.chartOfAccounts
            journalDetails.prefixCode = customerMaster.code
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = 0.00
            journalDetails.creditAmount = adjustUsingHo.totalAmount
            journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " of Customer: [" + customerMaster.code + "] " + customerMaster.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            /*
            List<SubLedger> subLedgers = []
            SubLedger subLedger

            String receivable = chartOfAccountService.getHead("Receivable")
            String current = chartOfAccountService.getHead("HO Account")

            for (int j = 0; j < 2; j++) {
                subLedger = new SubLedger()
                if (j == 0) {
                    subLedger.accCode = current
                    subLedger.description = "Current Account With HO"
                    subLedger.debit = Double.parseDouble(params.totalAmount)
                    subLedger.credit = 0
                } else if (j == 1) {
                    subLedger.accCode = params.customerMaster.code + receivable
                    subLedger.description = "Accounts Receivable of Customer: " + params.searchKey
                    subLedger.debit = 0
                    subLedger.credit = Double.parseDouble(params.totalAmount)
                }
                subLedger.transactionNo = params.customerMaster.code
                subLedger.transactionType = 2
                subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
                subLedger.dateCreated = dateNow
                subLedger.userCreated = (ApplicationUser) object
                subLedger.isActive = true
                subLedgers.add(subLedger)
            }
             */
            Map map = [:]
            map.put('adjustUsingHo', adjustUsingHo)
            map.put('adjustUsingHoDetailsList', adjustUsingHoDetailsList)
            map.put('invoices', invoices)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)

            int noOfRows = adjustUsingHoService.create(map)
            if (noOfRows > 0) {
                message = this.getMessage("Adjust Using HO", Message.SUCCESS, 'Selected Invoices Successfully Adjusted.')
            } else {
                message = this.getMessage('Adjust Using HO', Message.ERROR, 'Selected Invoices Could Not Be Adjusted.')
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, 'Selected Invoices Could Not Be Adjusted.')
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}