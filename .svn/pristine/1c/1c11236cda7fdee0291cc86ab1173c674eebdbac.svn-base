package com.bits.bdfp.accounts.chartofaccountsmapping

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.settings.EnterpriseConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.ChartOfAccountsMappingService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("createChartOfAccountsMappingAction")
class CreateChartOfAccountsMappingAction extends Action{
    public static final Log log = LogFactory.getLog(CreateChartOfAccountsMappingAction.class)
    private final String MESSAGE_HEADER = 'Chart Of Accounts Mapping'
    private final String MESSAGE_SUCCESS = 'Chart Of Accounts Mapping Submitted Successfully'

    @Autowired
    ChartOfAccountsMappingService chartOfAccountsMappingService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
//            List nCoaMappingList = []
            if(!params.enterpriseId){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Enterprise is not selected")
            }
            if(!params.coaType){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Chart of Accounts Type is not selected")
            }
            EnterpriseConfiguration enterprise = EnterpriseConfiguration.get(Long.parseLong(params.enterpriseId))
            COAType coaType = COAType.valueOf(params.coaType)
            int previousCount = ChartOfAccountsMapping.countByEnterpriseAndCoaType(enterprise, coaType)
            if(previousCount > 0){
                if(params.coaList){
                    Map resultData = (Map)chartOfAccountsMappingService.checkForDeletedChartOfAccounts(params.coaList, enterprise.id)
                    if(resultData.transactionFound){
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, resultData.message.toString())
                    }
                } else{
                    Map resultData = (Map)chartOfAccountsMappingService.checkForDeletedChartOfAccountsByCOAType(coaType.code(), enterprise.id)
                    if(resultData.transactionFound){
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, resultData.message.toString())
                    }
                }
            }
            List<ChartOfAccountsMapping> chartOfAccountsMappingList = new ArrayList<ChartOfAccountsMapping>()

            if(params.coaList){
                String coaIds =  params.coaList
                String[] coaList = coaIds.split(',')
                for(int i = 0; i < coaList.size(); i++){
                    ChartOfAccounts chartOfAccounts = ChartOfAccounts.get(Long.parseLong(coaList[i]))
                    ChartOfAccountsMapping chartOfAccountsMapping = new ChartOfAccountsMapping()
                    chartOfAccountsMapping.coaType = coaType
                    chartOfAccountsMapping.enterprise = enterprise
                    chartOfAccountsMapping.chartOfAccounts = chartOfAccounts
                    chartOfAccountsMapping.userCreated = (ApplicationUser) springSecurityService?.getCurrentUser()
                    if(previousCount <= 0){
                         if(!chartOfAccountsMapping.validate()){
                             return this.getValidationErrorMessage(chartOfAccountsMapping)
                         }
                    }
                    chartOfAccountsMappingList.add(chartOfAccountsMapping)
                }

            }

            chartOfAccountsMappingService.submit(enterprise, coaType, chartOfAccountsMappingList)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}