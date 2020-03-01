package com.bits.bdfp.accounts

import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 5/4/16.
 */
@Component("listChartOfAccountsGroupAction")
class ListChartOfAccountsGroupAction extends Action {
    @Autowired
    ChartOfAccountService chartOfAccountService
    private List<ChartOfAccounts> listCoaHeadByGroup = null

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try {
            return chartOfAccountService.listCOAGroups(Long.parseLong(params.enterpriseId))
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }


    public Object postCondition(def params, def object) {
        return null
    }

}

