package com.bits.bdfp.accounts

import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 12/19/2016.
 */
@Component("listForChartOfAccountsChildAction")
class ListForChartOfAccountsChildAction extends Action{
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    ChartOfAccountService chartOfAccountService

    @Override
    Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
     Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    Object execute(def Object object1,def Object object2) {
        try {
            return chartOfAccountService.chartOfAccountsChild()
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }
}
