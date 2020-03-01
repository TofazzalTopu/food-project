package com.bits.bdfp.accounts

import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 5/4/16.
 */
@Component("listChartOfAccountsHeadByGroupAction")
class ListChartOfAccountsHeadByGroupAction extends Action {
    @Autowired
    private List<ChartOfAccounts> listCoaHeadByGroup = null

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try {
            ChartOfAccounts coaGroup = ChartOfAccounts.get(Long.parseLong(params.coaGroupId))
            listCoaHeadByGroup = new ArrayList<ChartOfAccounts>()
            List<ChartOfAccounts> chartOfAccountsList = listCoaHead(ChartOfAccounts.findAllByParentId(coaGroup.id))
            List<Map> results = new ArrayList<Map>()
            chartOfAccountsList.each { chartOfAccounts->
                Map result = [:]
                result.id = chartOfAccounts.id
                result.name = ChartOfAccounts.get(chartOfAccounts.parentId).chartOfAccountName + "->" + chartOfAccounts.chartOfAccountName +  " [" + chartOfAccounts.chartOfAccountCodeUser + "]"
                results.add(result)
            }
            return results
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }


    public Object postCondition(def params, def object) {
        return null
    }

    private List<ChartOfAccounts> listCoaHead(List<ChartOfAccounts> chartOfAccountsList){
        chartOfAccountsList.each { chartOfAccounts->
            if(ChartOfAccounts.countByParentId(chartOfAccounts.id)){
                listCoaHead(ChartOfAccounts.findAllByParentId(chartOfAccounts.id))
            }
            if(chartOfAccounts.chartOfAccountLayer.layerNumber == chartOfAccounts.enterpriseConfiguration.noOfLayers){
                listCoaHeadByGroup.add(chartOfAccounts)
            }
        }
        return listCoaHeadByGroup
    }
}
