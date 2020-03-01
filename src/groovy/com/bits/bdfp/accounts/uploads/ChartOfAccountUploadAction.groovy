package com.bits.bdfp.accounts.uploads

import com.bits.bdfp.accounts.ChartOfAccountUploadService
import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by liton.miah on 10/25/2016.
 */

@Component("chartOfAccountUploadAction")
class ChartOfAccountUploadAction extends Action{
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    ChartOfAccountUploadService chartOfAccountUploadService
    Message message

    public Map preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            String accCodeLine = ''
            String preFixLine = ''
            String postFixLine = ''
            String msg = ''
            String lines = ''

            params.dataList.each{key, val->
                if(val instanceof Map){
                    ChartOfAccounts chartOfAccounts = ChartOfAccounts.findByChartOfAccountCodeUserAndIsActive(val.accountCode,true)
                    if(!chartOfAccounts){
                        if(accCodeLine){
                            accCodeLine += ","+val.rowNum
                        }else{
                            accCodeLine = val.rowNum
                        }
                    }

                    if(val.prefixCode){
                        CustomerMaster customerMasterPreFix = CustomerMaster.findByCode(val.prefixCode)
                        if(!customerMasterPreFix){
                            if(preFixLine){
                                preFixLine += ","+val.rowNum
                            }else{
                                preFixLine = val.rowNum
                            }
                        }
                    }

                    DistributionPoint distributionPoint = DistributionPoint.findByCode(val.postFixCode)
                    if(!distributionPoint){
                        if(postFixLine){
                            postFixLine += ","+val.rowNum
                        }else{
                            postFixLine = val.rowNum
                        }
                    }
                }
            }

            if(accCodeLine){
                if(msg){
                    msg += "<br> System does not found 'Account Code' in line ("+accCodeLine+")"
                }else{
                    msg = "System does not found 'Account Code' in line ("+accCodeLine+")"
                }

                if(lines){
                    lines += ","+accCodeLine
                }else{
                    lines = accCodeLine
                }
            }

            if(preFixLine){
                if(msg){
                    msg += "<br> System does not found 'Prefix-Code' in line ("+preFixLine+")"
                }else{
                    msg = "System does not found 'Prefix-Code' in line ("+preFixLine+")"
                }

                if(lines){
                    lines += ","+preFixLine
                }else{
                    lines = preFixLine
                }
            }

            if(postFixLine){
                if(msg){
                    msg += "<br> System does not found 'Post-Fix-Code' in line ("+postFixLine+")"
                }else{
                    msg = "System does not found 'Post-Fix-Code' in line ("+postFixLine+")"
                }

                if(lines){
                    lines += ","+postFixLine
                }else{
                    lines = postFixLine
                }
            }

            if(msg){
                message = this.getMessage("Chart Of Account Upload", Message.ERROR, msg)
            }else{
                message = this.getMessage("Chart Of Account Upload", Message.SUCCESS, "System checked all data successfully.")
            }

            return  [message:message, lines:lines, accCodeLine:accCodeLine,preFixLine:preFixLine,postFixLine:postFixLine]
        } catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object execute(Object object, Object params) {
        try {
            ApplicationUser applicationUser=(ApplicationUser) object

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.enterprise))

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date transactionDate =  df.parse(params.transactionDate);

            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName =  "opening_balance"// sessionFactory.getClassMetadata(CalculateAndAdjustIncentive).tableName
            journalHead.transactionDate = transactionDate
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.OPENING_BALANCE

            List<JournalDetails> journalDetailsList = []

            params.dataList.each{key, val->
                if(val instanceof Map){
                    ChartOfAccounts chartOfAccounts = ChartOfAccounts.findByChartOfAccountCodeUserAndIsActive(val.accountCode,true)

                    JournalDetails journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    journalDetails.prefixCode = val.prefixCode
                    journalDetails.postfixCode = val.postFixCode
                    journalDetails.chartOfAccounts = chartOfAccounts
                    journalDetails.debitAmount = Double.parseDouble(val.drBalance)
                    journalDetails.creditAmount = Double.parseDouble(val.crBalance)
                    journalDetails.particular = "Opening Balance"

                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }

                    journalDetailsList.add(journalDetails)
                }
            }

            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            Map map = [:]
            map.put("journal",journalHead)
            map.put("journalDetailsList",journalDetailsList)

            Integer rows = chartOfAccountUploadService.migrateAllData(map)
            if(rows>0){
                message = this.getMessage("Chart Of Account Upload", Message.SUCCESS, "All data has been migrated successfully.")
            }else{
                message = this.getMessage("Chart Of Account Upload", Message.ERROR, FAIL_SAVE)
            }

            return message

        } catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
