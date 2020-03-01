package com.bits.bdfp.setup.salestarget.saleshead

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.SalesHead
import com.bits.bdfp.setup.salestarget.SalesHeadService
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateSalesHeadAction")
class UpdateSalesHeadAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateSalesHeadAction.class)
    private final String MESSAGE_HEADER = 'Update Sales Head'
    private final String MESSAGE_SUCCESS = 'Sales Head Updated Successfully'

    @Autowired
    SalesHeadService salesHeadService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService?.getCurrentUser()
            SalesHead newSalesHead = new SalesHead()
            SalesHead currentSalesHead = salesHeadService.read(Long.parseLong(params.id))
            newSalesHead.properties = params
            newSalesHead.userCreated = applicationUser
            currentSalesHead.userUpdated = applicationUser

            Float previousTarget = salesHeadService.previousMonthTarget(newSalesHead.targetYear, newSalesHead.employee.id)
            if(previousTarget > newSalesHead.targetAmount){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Please set a higher target")
            }

            if(currentSalesHead.employee != newSalesHead.employee){
                salesHeadService.replaceSalesHead(currentSalesHead, newSalesHead)
            }else{
                currentSalesHead.targetAmount = newSalesHead.targetAmount
                salesHeadService.updateSalesHead(currentSalesHead)
            }

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
