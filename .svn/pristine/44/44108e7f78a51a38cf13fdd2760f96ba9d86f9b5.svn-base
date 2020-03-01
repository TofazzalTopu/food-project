package com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmountService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 3/8/16.
 */
@Component("listMonthWiseYearlyTargetAction")
class ListMonthWiseYearlyTargetAction extends Action {

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
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            if(!applicationUser.customerMasterId){
                throw new RuntimeException("Employee is not assigned to current user")
//                return this.getMessage("Message", Message.ERROR, "Employee is not assigned to current user")
            }
            CustomerMaster customerMaster = CustomerMaster.get(applicationUser.customerMasterId)
            return monthlySalesTargetByAmountService.monthWiseYearlyTargetList(Integer.parseInt(params.targetYear), customerMaster)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}