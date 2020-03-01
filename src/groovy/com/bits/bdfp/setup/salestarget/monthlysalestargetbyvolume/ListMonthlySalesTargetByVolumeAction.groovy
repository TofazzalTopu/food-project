package com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.Message
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolumeService

@Component("listMonthlySalesTargetByVolumeAction")
class ListMonthlySalesTargetByVolumeAction extends Action {

    @Autowired
    MonthlySalesTargetByVolumeService monthlySalesTargetByVolumeService
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
            }
            CustomerMaster customerMaster = CustomerMaster.get(applicationUser.customerMasterId)
            return monthlySalesTargetByVolumeService.monthWiseYearlyTargetList(Integer.parseInt(params.targetYear), customerMaster)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}