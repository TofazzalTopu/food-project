package com.bits.bdfp.inventory.workflow.workflow

import com.bits.bdfp.common.CashPool
import com.bits.bdfp.currency.CurrencyDemonstration
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentCurrencyDenomination
import com.bits.bdfp.inventory.demandorder.CustomerDemandOrderPayment
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.bits.bdfp.inventory.workflow.Workflow
import com.bits.bdfp.inventory.workflow.WorkflowService
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 10/6/2015.
 */
@Component("savePrioritySequenceAction")
class SavePrioritySequenceAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WorkflowService workflowService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map =(Map) object

            List <Workflow>workflowList = map.workflowList

            boolean isValidate=true

                if (workflowList && workflowList.size()>0){
                    workflowList.each {
                        if (!it.validate()){
                            isValidate=false
                            message = this.getValidationErrorMessage(it)
                        }
                    }
                }

                else{
                    isValidate=false
                    message = this.getMessage("Workflow", Message.ERROR, "You did not put any data for priority sequence.")
                }



            if (isValidate){
                        message = this.getMessage("Workflow", Message.SUCCESS, SUCCESS_SAVE)
                         }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Workflow", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            List <Workflow> workflowList = []
            Workflow workflow = null

            params.items.each { key, val ->
                if (val instanceof Map) {
                        workflow = workflowService.read(Long.parseLong(val.id))
                        workflow.prioritySequence=Integer.parseInt(val.priority)
                        workflow.userUpdated = applicationUser
                        workflow.dateUpdated = new Date()
                        workflowList.add(workflow)
                }

            }


            Map map =[:]
            map.put('workflowList', workflowList)

            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = workflowService.savePrioritySequnce(map)
                if (noOfRows>0) {
                    message = this.getMessage("Workflow", Message.SUCCESS, SUCCESS_SAVE)
                } else {
                    message = this.getMessage("Workflow", Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Workflow", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}




