package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountService
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.DepositCashCurrencyDenomination
import com.bits.bdfp.finance.DepositCashToDepositPool
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by NZ on 4/19/2016.
 */
@Component("createDepositCashToDepositPoolAction")
class CreateDepositCashToDepositPoolAction extends Action {
    @Autowired
    CustomerPaymentService customerPaymentService
    @Autowired
    ChartOfAccountService chartOfAccountService
    @Autowired
    SessionFactory sessionFactory

    Message message

    @Override
    public Object preCondition(def params, def object) {
        try {
            Map map = (Map) object
            DepositCashToDepositPool depositCashToDepositPool = map.depositCashToDepositPool
            if (!depositCashToDepositPool.validate()) {
                message = this.getValidationErrorMessage(depositCashToDepositPool)
            } else {
                message = this.getMessage(depositCashToDepositPool, Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Deposit Cash To Deposit Pool", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            Date dateNow = new Date()
            ApplicationUser applicationUser = (ApplicationUser) object
            SimpleDateFormat formatDate = new SimpleDateFormat("DD")
            String currentDay = formatDate.format(dateNow)
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            DepositCashToDepositPool depositCashToDepositPool = new DepositCashToDepositPool(params)
            depositCashToDepositPool.transactionNo = CodeGenerationUtil.instance.generateCode(depositCashToDepositPool.enterpriseConfiguration, "DEPOSIT_CASH_TO_DEPOSIT_POOL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            depositCashToDepositPool.dateCreated = dateNow
            depositCashToDepositPool.userCreated = applicationUser
            depositCashToDepositPool.status = ApplicationConstants.NOT_ACKNOWLEDGED
            List<DepositCashCurrencyDenomination> depositCashCurrencyDenominationList = ObjectUtil.instantiateObjects(params.items, DepositCashCurrencyDenomination.class)
            for (int i = 0; i < depositCashCurrencyDenominationList.size(); i++) {
                depositCashCurrencyDenominationList[i].depositCashToDepositPool = depositCashToDepositPool
            }
            Journal journalHead = null
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            if(depositCashToDepositPool.hoDeposit) {
                /***************************** COA Entry Start ***************************/
                DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(depositCashToDepositPool.distributionPoint.enterpriseConfiguration, true)
                DistributionPoint branchDp = depositCashToDepositPool.distributionPoint
                EnterpriseConfiguration enterprise = branchDp.enterpriseConfiguration
//                DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterprise, true)

                JournalDetails journalDetails = null
                ChartOfAccountsMapping chartOfAccountsMapping = null
                journalHead = new Journal(enterprise: enterprise, userCreated: applicationUser, isActive: true)
                journalHead.tableName =  sessionFactory.getClassMetadata(DepositCashToDepositPool).tableName
                journalHead.transactionDate = new Date()
                journalHead.journalStatus = JournalStatus.CHECKED
                journalHead.transactionNo = depositCashToDepositPool.transactionNo
                journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterprise, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
                journalHead.moduleName = ApplicationConstants.MODULE_DEPOSIT_CASH
                if(!journalHead.validate()){
                    return this.getValidationErrorMessage(journalHead)
                }
                ChartOfAccountsMapping chartOfAccountsMappingCash = ChartOfAccountsMapping.findByCoaType(COAType.CASH)
                if(!chartOfAccountsMappingCash){
                    return this.getMessage("Error", Message.ERROR, "Cash head is not mapped with Chart of Accounts")
                }

                Float salesAmount = Float.parseFloat(params.salesAmount)
                if(salesAmount > 0.00){
                    // Sales Amount
                    chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
                    if(!chartOfAccountsMapping){
                        return this.getMessage("Error", Message.ERROR, "Accounts Payable head is not mapped with Chart of Accounts")
                    }
                    // DR
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                    journalDetails.prefixCode = factoryDp.code
                    journalDetails.postfixCode = branchDp.code
                    journalDetails.debitAmount = salesAmount
                    journalDetails.creditAmount = 0.00
                    journalDetails.particular = "Deposit to HO for Sales from DP: [" + branchDp.code + "] " + branchDp.name
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                    // CR
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingCash.chartOfAccounts
                    journalDetails.prefixCode = factoryDp.code
                    journalDetails.postfixCode = branchDp.code
                    journalDetails.debitAmount = 0.00
                    journalDetails.creditAmount = salesAmount
                    journalDetails.particular = "Deposit to HO For Sales from DP: [" + branchDp.code + "] " + branchDp.name
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)
                }
                Float sdAmount = Float.parseFloat(params.sdAmount)    // Security Deposit
                if(sdAmount > 0.00){
                    // Security Deposit
                    chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.DEPOSIT_TO_HO)
                    if(!chartOfAccountsMapping){
                        return this.getMessage("Error", Message.ERROR, "Deposit to HO head is not mapped with Chart of Accounts")
                    }
                    // DR
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                    journalDetails.prefixCode = factoryDp.code
                    journalDetails.postfixCode = branchDp.code
                    journalDetails.debitAmount = sdAmount
                    journalDetails.creditAmount = 0.00
                    journalDetails.particular = "Deposit to HO for SD from DP: [" + branchDp.code + "] " + branchDp.name
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                    // CR
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingCash.chartOfAccounts
                    journalDetails.prefixCode = ""
                    journalDetails.postfixCode = branchDp.code
                    journalDetails.debitAmount = 0.00
                    journalDetails.creditAmount = sdAmount
                    journalDetails.particular = "Deposit to HO for SD from DP: [" + branchDp.code + "] " + branchDp.name
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)
                }
            }
            /*************************** COA Entry End *********************************/

            Map map = [:]
            map.put('depositCashToDepositPool', depositCashToDepositPool)
            map.put('journalHead', journalHead)
            map.put('journalDetailsList', journalDetailsList)
            if(depositCashCurrencyDenominationList) {
                map.put('depositCashCurrencyDenominationList', depositCashCurrencyDenominationList)
            }

            if (!depositCashToDepositPool.validate()) {
               return this.getValidationErrorMessage(depositCashToDepositPool)
            }

            int noOfRows = customerPaymentService.createDepositCashToDepositPool(map)
            if (noOfRows > 0) {
                message = this.getMessage("Deposit Cash To Deposit Pool", Message.SUCCESS, 'Deposit Successful. Transaction No: ' + depositCashToDepositPool.transactionNo)
            } else {
                message = this.getMessage('Deposit Cash To Deposit Pool', Message.ERROR, 'Cash Could Not Be Deposited.')
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException('Cash Could Not Be Deposited.')
        }
    }
}
