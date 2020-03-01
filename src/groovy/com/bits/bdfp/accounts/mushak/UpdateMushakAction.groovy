package com.bits.bdfp.accounts.mushak

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.accounts.MushakDetails
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
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.accounts.Mushak
import com.bits.bdfp.accounts.MushakService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService

import java.text.SimpleDateFormat


@Component("updateMushakAction")
class UpdateMushakAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateMushakAction.class)
    private final String MESSAGE_HEADER = 'Update Mushak'
    private final String MESSAGE_SUCCESS = 'Mushak Updated Successfully'

    @Autowired
    SessionFactory sessionFactory

    @Autowired
    MushakService mushakService

    Message message

    public Object preCondition(def params, def object) {
        try {
            Map map = (Map) object
            Mushak mushak = map.mushak
            if (!mushak.validate()) {
                message = this.getValidationErrorMessage(mushak)
            }else{
                message = this.getMessage(mushak, Message.SUCCESS, SUCCESS_SAVE)
            }
            return  message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Mushak", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(def params, def object) {
        try {
            //ApplicationUser applicationUser= (ApplicationUser) object
            Map map = [:]
            Mushak mushak = mushakService.read(Long.parseLong(params.id))
            mushak.properties = params
            mushak.userUpdated = (ApplicationUser) object
            mushak.lastUpdated = new Date()
            List<MushakDetails> mushakDetailsList = ObjectUtil.instantiateObjects(params.items, MushakDetails.class)


            /***************************** Delete Journal Start ***************************/

            List<Journal> listExistJournal = Journal.findAllByTransactionNoAndTableName(mushak.mushakNo,sessionFactory.getClassMetadata(Mushak).tableName)
            listExistJournal.each { journal ->
                List<JournalDetails> listJournalDetailsExisting=JournalDetails.findAllByJournal( journal).toList()
                listJournalDetailsExisting.each { journalDetails ->
                    journalDetails.delete()
                }
                journal.delete()
            }
            /***************************** Delete Journal Start ***************************/

            /***************************** COA Entry Start ***************************/

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)


            //DistributionPoint distributionPoint=DistributionPoint.read(params.distributionPoint.id)

            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null

            Float totalMushakVatAmount=0;
            for(int i = 0; i < mushakDetailsList.size(); i++)
            {
                //mushakDetailsList[i].mushak = mushak
                totalMushakVatAmount+=mushakDetailsList[i].vatAmount
            }

//            EnterpriseConfiguration enterpriseConfiguration=EnterpriseConfiguration.read(params.enterpriseConfiguration.id)
            EnterpriseConfiguration enterpriseConfiguration=mushak.enterpriseConfiguration


            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: (ApplicationUser) object, isActive: true)
            journalHead.tableName = sessionFactory.getClassMetadata(Mushak).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = mushak.mushakNo
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_MUSHAK
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }
            for(int i = 0; i < mushakDetailsList.size(); i++) {
                String particular = ""
                journalDetails = new JournalDetails(journal: journalHead, userCreated: (ApplicationUser) object, isActive: true)
//                chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.SALE_OF_PASTEURIZED_MILK_100_ML)
//                if (!chartOfAccountsMapping) {
//                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "SALE OF PASTEURIZED MILK 100 ML head is not mapped with Chart of Accounts")
//                }
//                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.chartOfAccounts=mushakDetailsList[i].finishProduct.chartOfAccountHead
                journalDetails.prefixCode = mushakDetailsList[i].finishProduct.code
                journalDetails.debitAmount = totalMushakVatAmount
                journalDetails.creditAmount = 0.00
                //journalDetails.particular = particular + " paid for Customer: [" + customerMaster.code + "] " + customerMaster.name
                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: (ApplicationUser) object, isActive: true)
                chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.VAT_CURRENT_ACCOUNT)
                if (!chartOfAccountsMapping) {
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "VAT CURRENT ACCOUNT head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = mushakDetailsList[i].finishProduct.code
                //journalDetails.chartOfAccounts=mushakDetailsList[i].finishProduct.chartOfAccountHead
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = totalMushakVatAmount
                //journalDetails.particular = particular + " received by Customer: [" + customerMaster.code + "] " + customerMaster.name
                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }
            Map returnMap = [:]

            /***************************** COA Entry End ***************************/






            map.put('mushak', mushak)
            map.put('mushakDetailsList', mushakDetailsList)
            map.put("journalDetailsList", journalDetailsList)
            map.put("journalHead", journalHead)
            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = mushakService.update(map)
                if (noOfRows > 0) {
                    message = this.getMessage(mushak, Message.SUCCESS, 'Mushak no: ' + mushak.mushakNo + ' Successfully Updated')
                } else {
                    message = this.getMessage(mushak, Message.ERROR, 'Mushak Could Not Be Updated.')
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage('Update Alert', Message.ERROR, 'Mushak Could Not Be Updated.')
        }
//            if(Long.parseLong(params.version) > mushak.version) {
//                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
//            }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
