package com.bits.bdfp.accounts.chartofaccountsmapping

import com.bits.bdfp.accounts.ChartOfAccounts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.Message
import com.bits.bdfp.accounts.ChartOfAccountsMappingService

@Component("listChartOfAccountsMappingAction")
class ListChartOfAccountsMappingAction extends Action {

    @Autowired
    ChartOfAccountsMappingService chartOfAccountsMappingService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try {
            String parentData = ""
            Map result = chartOfAccountsMappingService.listChartOfAccountsMapping(params)
            result.chartOfAccountsList.each {
                parentData = ""
                ChartOfAccounts chartOfAccounts = ChartOfAccounts.get(it.id)
                while (chartOfAccounts.parentId > 0){
                    chartOfAccounts = ChartOfAccounts.get(chartOfAccounts.parentId)
                    parentData = chartOfAccounts.chartOfAccountName + "->" + parentData
                }
//                it.parents = parentData
                it.name = parentData + it.name
            }
            result.chartOfAccountsList.sort{it.name}
            result.selectedChartOfAccounts.each {
                parentData = ""
                ChartOfAccounts chartOfAccounts = ChartOfAccounts.get(it.id)
                while (chartOfAccounts.parentId > 0){
                    chartOfAccounts = ChartOfAccounts.get(chartOfAccounts.parentId)
                    parentData = chartOfAccounts.chartOfAccountName + "->" + parentData
                }
//                it.parents = parentData
                it.name = parentData + it.name
            }
            result.selectedChartOfAccounts.sort{it.name}
            return result
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }


    public Object postCondition(def params, def object) {
        return null
    }
}