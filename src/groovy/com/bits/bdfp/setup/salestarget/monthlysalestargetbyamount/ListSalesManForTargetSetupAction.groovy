package com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount

import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmountService
import com.docu.common.Action
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 3/20/16.
 */
@Component("listSalesManForTargetSetupAction")
class ListSalesManForTargetSetupAction extends Action {

    @Autowired
    MonthlySalesTargetByAmountService monthlySalesTargetByAmountService
    @Autowired
    SpringSecurityService springSecurityService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            return monthlySalesTargetByAmountService.listSalesManForTargetSetup(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}