package com.bits.bdfp.setup.salestarget.saleshead

import com.bits.bdfp.setup.salestarget.YearlySalesTargetByAmount
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.DateUtil
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


@Component("createSalesHeadAction")
class CreateSalesHeadAction extends Action{
    public static final Log log = LogFactory.getLog(CreateSalesHeadAction.class)
    private final String MESSAGE_HEADER = 'New Sales Head'
    private final String MESSAGE_SUCCESS = 'Sales Head Created Successfully'

    @Autowired
    SalesHeadService salesHeadService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            SalesHead salesHead = new SalesHead()
            salesHead.properties = params
            
            salesHead.userCreated = (ApplicationUser) springSecurityService?.getCurrentUser()
            salesHead.isActive = true
            if(salesHead.targetYear){
                salesHead.startDate = DateUtil.getSimpleDateWithFormat('01-01-' + salesHead.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
                salesHead.endDate = DateUtil.getSimpleDateWithFormat('31-12-' + salesHead.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
            }
            if (!salesHead.validate()) {
                return this.getValidationErrorMessage(salesHead)
            }

            if(salesHead.targetYear < DateUtil.getCurrentSystemYear()){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "You can't set previous year target")
            }
            if(SalesHead.findByTargetYear(salesHead.targetYear)){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "This year is already defined. Please Choose a Valid Value")
            }
            if(YearlySalesTargetByAmount.findByEmployeeAndTargetYear(salesHead.employee, salesHead.targetYear)){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "The Employee already has a target for the selected year. Please select a valid employee")
            }
            salesHeadService.create(salesHead)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}