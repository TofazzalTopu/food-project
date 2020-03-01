package com.bits.bdfp.finance.sdinterestcalculation

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.SecurityDepositInterest
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.sql.Array
import java.text.SimpleDateFormat

/**
 * Created by liton.miah on 2/8/2016.
 */
@Component("createSecurityDepositInterestAction")
class CreateSecurityDepositInterestAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    CustomerPaymentService customerPaymentService
    @Autowired
    SessionFactory sessionFactory
    Message message
    @Autowired
    SpringSecurityService springSecurityService

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            List<SecurityDepositInterest> securityDepositInterestList = map.securityDepositInterestList
            boolean isValidate = true

            if (securityDepositInterestList && securityDepositInterestList.size() > 0) {
                securityDepositInterestList.each {
                    if (!it.validate()) {
                        isValidate = false
                        message = this.getValidationErrorMessage(it)
                    }
                }
            }

            if(isValidate){
                message = this.getMessage("Security Deposit Interest", Message.SUCCESS, SUCCESS_SAVE)
            }

            return message

        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser =  (ApplicationUser) springSecurityService?.getCurrentUser()
            CustomerMaster defaultCustomer = CustomerMaster.read(Long.parseLong(params.defaultCustomerId))
            DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDefaultCustomer(defaultCustomer)
            EnterpriseConfiguration enterprise = defaultCustomer.enterpriseConfiguration
            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterprise, true)
            List<SecurityDepositInterest> securityDepositInterestList = []
            if(!factoryDp){
                return this.getMessage("Security Deposit Interest", Message.ERROR, "Factory DP is not available")
            }
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            /***************************** COA Entry Start ***************************/
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: enterprise, userCreated: applicationUser, isActive: true)
            journalHead.tableName =  sessionFactory.getClassMetadata(SecurityDepositInterest).tableName
//                    GrailsDomainBinder.getMapping(CustomerPayment).table.name
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterprise, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_SECURITY_DEPOSIT
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.GENERAL_EXPENSE)
            if(!chartOfAccountsMapping){
                return this.getMessage("", Message.ERROR, "General Expense head is not mapped with Chart of Accounts")
            }

            Float interestForBranch = Float.parseFloat(params.interestAmount)
            // HO Book DR
            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = ""
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = interestForBranch
            journalDetails.creditAmount = 0.00
            journalDetails.particular = "Interest calculated for Customer: [" + defaultCustomer.code + "] " + defaultCustomer.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            ChartOfAccountsMapping chartOfAccountsMappingSecurityDeposit =  ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
            if(!chartOfAccountsMappingSecurityDeposit){
                return this.getMessage("", Message.ERROR, "Deposit Refundable head is not mapped with Chart of Accounts")
            }

            // HO Book CR
            journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
            journalDetails.prefixCode = defaultCustomer.code
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = 0.00
            journalDetails.creditAmount = interestForBranch
            journalDetails.particular = "Interest calculated for Customer: [" + defaultCustomer.code + "] " + defaultCustomer.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            ChartOfAccountsMapping chartOfAccountsMappingDepositToHO =  ChartOfAccountsMapping.findByCoaType(COAType.DEPOSIT_TO_HO)
            if(!chartOfAccountsMappingDepositToHO){
                return this.getMessage("", Message.ERROR, "Deposit to HO head is not mapped with Chart of Accounts")
            }

            Float totalInterest = 0.00

            params.interest.each { key, val ->
                if (val instanceof Map) {
                    SecurityDepositInterest securityDepositInterest = new SecurityDepositInterest()
                    CustomerMaster customerMaster = CustomerMaster.read(Long.parseLong(val.customerId))

                    securityDepositInterest.customerMaster = customerMaster
                    securityDepositInterest.interestPercentage = Float.parseFloat(params.interestPercentage)
                    securityDepositInterest.lastQuarterBalance = Double.parseDouble(val.lastQuarterBalance)
                    securityDepositInterest.interestAmount = Double.parseDouble(val.interestAmount)
                    securityDepositInterest.quarterName = params.quarter
                    securityDepositInterest.transactionDate = new Date()

                    securityDepositInterest.dateCreated = new Date()
                    securityDepositInterest.userCreated = applicationUser

                    securityDepositInterestList.add(securityDepositInterest)

                    totalInterest += securityDepositInterest.interestAmount
                    // Branch Book CR
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                    journalDetails.prefixCode = customerMaster.code
                    journalDetails.postfixCode = distributionPointWarehouse?.distributionPoint?.code
                    journalDetails.debitAmount = 0.00
                    journalDetails.creditAmount = securityDepositInterest.interestAmount
                    journalDetails.particular = "Interest calculated for Customer: [" + customerMaster.code + "] " + customerMaster.name
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                }
            }
            // Branch Book DR
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingDepositToHO.chartOfAccounts
            journalDetails.prefixCode = ""
            journalDetails.postfixCode = distributionPointWarehouse?.distributionPoint?.code
            journalDetails.debitAmount = totalInterest
            journalDetails.creditAmount = 0.00
            journalDetails.particular = "Deposit to HO for Factory: [" + factoryDp.code + "] " + factoryDp.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            Map map = [:]
            map.put("securityDepositInterestList",securityDepositInterestList)

            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)
            message = this.preCondition(params, map)

            if (message.type == 1) {
                int noOfRows = customerPaymentService.createSecurityDepositInterest(map)
                if (noOfRows > 0) {
                    message = this.getMessage("Security Deposit Interest", Message.SUCCESS, "Security Deposit Interest Created Successfully.")
                } else {
                    message = this.getMessage("Security Deposit Interest", Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public Map getCalculatedInterestByQuarter(Object params){
        try{
//            String ids
//            params.interest.each{ key, val ->
//                if(val instanceof Map){
//                    if(ids){
//                        ids += ',' + Long.parseLong(val.customerId)
//                    }else{
//                        ids = Long.parseLong(val.customerId)
//                    }
//                }
//            }

            Map map = [:]
            map.put("customerId",params.customerId)
            map.put("quarter",params.quarter)

            Map noOfRows = customerPaymentService.getCalculatedInterestByQuarter(map)
//            if (noOfRows.isCalculated > 0) {
//                message = this.getMessage("Security Deposit Interest", Message.SUCCESS, "Security Deposit Interest Created Successfully.")
//            } else {
//                message = this.getMessage("Security Deposit Interest", Message.ERROR, FAIL_SAVE)
//            }
            return noOfRows
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
