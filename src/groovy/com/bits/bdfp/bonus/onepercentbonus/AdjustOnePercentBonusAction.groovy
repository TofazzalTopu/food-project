package com.bits.bdfp.bonus.onepercentbonus

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountService
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.bonus.AdjustOnePercentBonus
import com.bits.bdfp.bonus.AdjustOnePercentBonusDetails
import com.bits.bdfp.bonus.OnePercentBonusService
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by NZ on 3/9/2016.
 */
@Component("adjustOnePercentBonusAction")
class AdjustOnePercentBonusAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    Message message

    @Autowired
    SessionFactory sessionFactory

    @Autowired
    OnePercentBonusService onePercentBonusService
    @Autowired
    ChartOfAccountService chartOfAccountService

    private final String MESSAGE_HEADER = 'Adjust 1% Bonus'

    @Override
    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            List<AdjustOnePercentBonus> adjustOnePercentBonusList = map.adjustOnePercentBonusList
            for (int i = 0; i < adjustOnePercentBonusList.size(); i++) {
                if (!adjustOnePercentBonusList[i].validate()) {
                    message = this.getValidationErrorMessage(adjustOnePercentBonusList[i])
                    return message
                } else {
                    message = this.getMessage(adjustOnePercentBonusList[i], Message.SUCCESS, SUCCESS_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("OnePercentBonus", Message.ERROR, "Exception-${ex.message}")
        }
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.enterpriseConfiguration))
            Map map = [:]
            CustomerMaster customerMaster = null

            /*Journal Head*/
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat("DD")
            String currentDay = formatDay.format(dateNow)

            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName = sessionFactory.getClassMetadata(AdjustOnePercentBonus).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_ADJUST_ONE_PERCENT_BONUS


            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            List<Invoice> unadjustedInvoiceList = []
            List<AdjustOnePercentBonusDetails> adjustOnePercentBonusDetailsList = []
            List<AdjustOnePercentBonus> adjustOnePercentBonusList = ObjectUtil.instantiateObjects(params.items, AdjustOnePercentBonus.class)
            for (int x = 0; x < adjustOnePercentBonusList.size(); x++) {
                Double adjustedAmount = 0.00
                List list = onePercentBonusService.getLastCalculated(adjustOnePercentBonusList[x].customerMaster.id)
                /*if (params.branch == '1') {
                    list = onePercentBonusService.getLastCalculatedBranch(adjustOnePercentBonusList[x].customerMaster.id)
                } else {

                }*/
                adjustOnePercentBonusList[x].transactionNo = journalHead.transactionNo
                adjustOnePercentBonusList[x].calculatedFrom = list[0].last_calculated
                adjustOnePercentBonusList[x].calculatedTo = DateUtil.getSimpleDate(params?.calculatedTo)
                adjustOnePercentBonusList[x].dateCreated = new Date()
                adjustOnePercentBonusList[x].userCreated = applicationUser

                adjustedAmount = adjustOnePercentBonusList[x].bonus
                adjustedAmount = adjustedAmount.round(2)

                /*Adjust Invoice*/
                List<Invoice> invoiceList = Invoice.findAll("from Invoice where (invoiceAmount - paidAmount) > 0.00 and isActive = true and defaultCustomer.id = " + adjustOnePercentBonusList[x].customerMaster.id)

                if(adjustedAmount > 0.00 && invoiceList.size()>0){
                    for(int i=0; i<invoiceList.size(); i++){
                        AdjustOnePercentBonusDetails adjustOnePercentBonusDetails = new AdjustOnePercentBonusDetails()
                        adjustOnePercentBonusDetails.adjustOnePercentBonus = adjustOnePercentBonusList[x]

                        double adjustableAmount = (invoiceList[i].invoiceAmount - invoiceList[i].paidAmount)
                        adjustableAmount = adjustableAmount.round(2)

                        if(adjustableAmount == 0.00){
                            continue
                        }

                        if(adjustedAmount >= adjustableAmount){
                            adjustOnePercentBonusDetails.paidAmount = adjustableAmount
                            adjustOnePercentBonusDetails.invoice = invoiceList[i]
                            adjustedAmount -= adjustableAmount
                            invoiceList[i].paidAmount += adjustableAmount
                        }else{
                            adjustOnePercentBonusDetails.paidAmount = adjustedAmount
                            adjustOnePercentBonusDetails.invoice = invoiceList[i]
                            invoiceList[i].paidAmount += adjustedAmount
                            adjustedAmount = 0.00
                        }

                        invoiceList[i].userUpdated = applicationUser
                        invoiceList[i].lastUpdated = new Date()

                        adjustOnePercentBonusDetails.userCreated = applicationUser
                        adjustOnePercentBonusDetails.dateCreated = new Date()

                        adjustOnePercentBonusDetailsList.add(adjustOnePercentBonusDetails)
                        unadjustedInvoiceList.add(invoiceList[i])

                        if(adjustedAmount == 0.00){
                            break
                        }
                    }
                }
            }

            //*************************************COA Start***************************************//

            if (!journalHead.validate()) {
                throw new RuntimeException(this.getValidationErrorMessage(journalHead).messageBody[0].toString())
            }

            ChartOfAccountsMapping chartOfAccountsOnePercentExpense = ChartOfAccountsMapping.findByCoaType(COAType.ONE_PERCENT_EXPENSE)
            if (!chartOfAccountsOnePercentExpense) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "One Percent Expense head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsMappingReceivable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
            if (!chartOfAccountsMappingReceivable) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsMappingPayable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
            if (!chartOfAccountsMappingPayable) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Payable head is not mapped with Chart of Accounts")
            }


            ChartOfAccountsMapping chartOfAccountsOnePercentIncome = ChartOfAccountsMapping.findByCoaType(COAType.ONE_PERCENT_INCOME)
            if (!chartOfAccountsOnePercentExpense) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "One Percent Income head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsActualLeakShort = ChartOfAccountsMapping.findByCoaType(COAType.ACTUAL_LEAK_OR_SHORT)
            if (!chartOfAccountsOnePercentExpense) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Actual Leak/Short head is not mapped with Chart of Accounts")
            }

            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterpriseConfiguration, true)

            if (params.isFactory == 'true') {
                // For Primary Customer
                for (int j = 0; j < adjustOnePercentBonusList.size(); j++) {
                    customerMaster = adjustOnePercentBonusList[j].customerMaster

                    //Factory Dr
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsOnePercentExpense.chartOfAccounts
                    journalDetails.prefixCode = ""
                    journalDetails.postfixCode = factoryDp.code
                    journalDetails.debitAmount = adjustOnePercentBonusList[j].bonus
                    journalDetails.creditAmount = 0.00
                    journalDetails.particular = "1% Expenses of HO Book for Customer: " + customerMaster.name + " [" + customerMaster.code + "]"
                    if (!journalDetails.validate()) {
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                    //Factory Cr
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true, dateCreated: new Date())
                    journalDetails.chartOfAccounts = chartOfAccountsMappingReceivable.chartOfAccounts
                    journalDetails.prefixCode = customerMaster.code
                    journalDetails.postfixCode = factoryDp.code
                    journalDetails.debitAmount = 0
                    journalDetails.creditAmount = adjustOnePercentBonusList[j].bonus
                    journalDetails.particular = "Accounts Receivable of HO Book for Customer: " + customerMaster.name + " [" + customerMaster.code + "]"
                    if (!journalDetails.validate()) {
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                    if (customerMaster.category.id == ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID) {
                        DistributionPoint branchDp = DistributionPointWarehouse.findByDefaultCustomer(customerMaster)?.distributionPoint
                        if(!branchDp){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Branch Book not found for Customer: " + customerMaster.name)
                        }
                        //Branch Dr
                        journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true, dateCreated: new Date())
                        journalDetails.chartOfAccounts = chartOfAccountsMappingPayable.chartOfAccounts
                        journalDetails.prefixCode = factoryDp.code
                        journalDetails.postfixCode = branchDp.code
                        journalDetails.debitAmount = adjustOnePercentBonusList[j].bonus
                        journalDetails.creditAmount = 0.00
                        journalDetails.particular = "Accounts Payable of Branch Book for Customer: " + customerMaster.name + " [" + customerMaster.code + "]"
                        if (!journalDetails.validate()) {
                            return this.getValidationErrorMessage(journalDetails)
                        }
                        journalDetailsList.add(journalDetails)
                        //Branch Cr
                        journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true, dateCreated: new Date())
                        journalDetails.chartOfAccounts = chartOfAccountsOnePercentIncome.chartOfAccounts
                        journalDetails.prefixCode = ''
                        journalDetails.postfixCode = branchDp.code
                        journalDetails.debitAmount = 0
                        journalDetails.creditAmount = adjustOnePercentBonusList[j].bonus
                        journalDetails.particular = "1% Income of Branch Book for Customer:" + customerMaster.name + " ["  + customerMaster.code + "]"
                        if (!journalDetails.validate()) {
                            return this.getValidationErrorMessage(journalDetails)
                        }
                        journalDetailsList.add(journalDetails)
                    }

                }
            } else {
                // For Sales Man

                for (int j = 0; j < adjustOnePercentBonusList.size(); j++) {
                    customerMaster = adjustOnePercentBonusList[j].customerMaster
                    CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.findByCustomerMaster(customerMaster)
                    DistributionPointTerritorySubArea distributionPointTerritorySubArea = DistributionPointTerritorySubArea.findByTerritorySubArea(customerTerritorySubArea.territorySubArea)
                    DistributionPoint salesManDP = distributionPointTerritorySubArea.distributionPoint

                    //1429
//                    DistributionPoint branchDp = DistributionPointWarehouse.findByDefaultCustomer(customerMaster)?.distributionPoint

                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsActualLeakShort.chartOfAccounts
                    journalDetails.prefixCode = ''
                    journalDetails.postfixCode = salesManDP.code
                    journalDetails.debitAmount = adjustOnePercentBonusList[j].bonus
                    journalDetails.creditAmount = 0
                    journalDetails.particular = "Actual Leak/Short of Branch Book for Sales Man: " + customerMaster.name + " ["  + customerMaster.code + "]"
                    if (!journalDetails.validate()) {
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingReceivable.chartOfAccounts
                    journalDetails.prefixCode = customerMaster.code
                    journalDetails.postfixCode = salesManDP.code
                    journalDetails.debitAmount = 0
                    journalDetails.creditAmount = adjustOnePercentBonusList[j].bonus
                    journalDetails.particular = "Accounts Receivable of Branch Book for Sales Man: " + customerMaster.name + " ["  + customerMaster.code + "]"

                    if (!journalDetails.validate()) {
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)
                }
            }

            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)
            map.put('adjustOnePercentBonusList', adjustOnePercentBonusList)
            map.put('adjustOnePercentBonusDetailsList', adjustOnePercentBonusDetailsList)
            map.put('unadjustedInvoiceList', unadjustedInvoiceList)
            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = onePercentBonusService.adjust(map)
                if (noOfRows > 0) {
                    message = this.getMessage("OnePercentBonus", Message.SUCCESS, "Bonus adjusted for selected Customers successfully.")
                } else {
                    message = this.getMessage("OnePercentBonus", Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
