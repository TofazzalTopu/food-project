package com.bits.bdfp.accounts.mushak

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountService
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
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.accounts.Mushak
import com.bits.bdfp.accounts.MushakService

import com.docu.security.ApplicationUser
import java.text.SimpleDateFormat


@Component("createMushakAction")
class CreateMushakAction extends Action{
    public static final Log log = LogFactory.getLog(CreateMushakAction.class)
    private final String MESSAGE_HEADER = 'New Mushak'
    private final String MESSAGE_SUCCESS = 'Mushak Created Successfully'

    @Autowired
    SessionFactory sessionFactory

    @Autowired
    MushakService mushakService
    @Autowired
    ChartOfAccountService chartOfAccountService

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

    public Object execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            Mushak mushak = new Mushak(params)
            mushak.mushakNo = CodeGenerationUtil.generate(8).toString()
            mushak.userCreated = (ApplicationUser) object
            mushak.dateCreated = new Date()
            mushak.enterpriseConfiguration=DistributionPoint.read(Long.parseLong(params.distributionPoint.id)).enterpriseConfiguration

            List<MushakDetails> mushakDetailsList = ObjectUtil.instantiateObjects(params.items, MushakDetails.class)
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

//            /***************************** COA Entry Start ***************************/

            //DistributionPoint distributionPoint=DistributionPoint.read(params.distributionPoint.id)

            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null

//            Float totalMushakVatAmount=0;
            for(int i = 0; i < mushakDetailsList.size(); i++)
            {
                mushakDetailsList[i].mushak = mushak
                //totalMushakVatAmount+=mushakDetailsList[i].vatAmount
            }


            EnterpriseConfiguration enterpriseConfiguration=DistributionPoint.read(Long.parseLong(params.distributionPoint.id)).enterpriseConfiguration
            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterpriseConfiguration, true)

            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
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
                chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.VAT_EXPENSES)
                if (!chartOfAccountsMapping) {
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "VAT Expenses Account head is not mapped with Chart of Accounts")
                }
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts=chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ''
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = mushakDetailsList[i].vatAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "VAT of the product: [" + mushakDetailsList[i].finishProduct.code + "] " + mushakDetailsList[i].finishProduct.name
                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.VAT_CURRENT_ACCOUNT)
                if (!chartOfAccountsMapping) {
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "VAT CURRENT ACCOUNT head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = mushakDetailsList[i].vatAmount
                journalDetails.particular ="Vat of the product: [" + mushakDetailsList[i].finishProduct.code + "] " + mushakDetailsList[i].finishProduct.name

                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }
            Map returnMap = [:]

        /***************************** COA Entry End ***************************/



            Map map = [:]
            map.put('mushak', mushak)
            map.put('mushakDetailsList', mushakDetailsList)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)


            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = mushakService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage(mushak, Message.SUCCESS, 'Mushak Successfully Saved For Mushak no: ' + mushak.mushakNo)
                } else {
                    message = this.getMessage(mushak, Message.ERROR, 'Mushak Could Not Be Saved.')
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