package com.bits.bdfp.accounts

import com.bits.bdfp.accounts.chartofaccountsmapping.CreateChartOfAccountsMappingAction
import com.bits.bdfp.accounts.chartofaccountsmapping.ListChartOfAccountsMappingAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ChartOfAccountsMappingController {

    @Autowired
    private CreateChartOfAccountsMappingAction createChartOfAccountsMappingAction
    @Autowired
    private ListChartOfAccountsMappingAction listChartOfAccountsMappingAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST"]
    static defaultAction = "show"

    def show = {
        Map result =[:]
        ApplicationUser applicationUser = session?.applicationUser
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(view: "show", model: [list: enterpriseList,result:result as JSON])
    }

    def create = {
        Message message = createChartOfAccountsMappingAction.execute(params, null)
        render message as JSON
    }

    def showCoaMappingData = {
        Map coaMappingData = (Map) listChartOfAccountsMappingAction.execute(params, null)
        render(template: "/chartOfAccountsMapping/coaTypeCoa", model: [chartOfAccountsList: coaMappingData.chartOfAccountsList, selectedChartOfAccounts: coaMappingData.selectedChartOfAccounts])
    }
}
