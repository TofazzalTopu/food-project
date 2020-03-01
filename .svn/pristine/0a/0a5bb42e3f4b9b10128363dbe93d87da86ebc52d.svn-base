package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.setup.IncentiveSetupService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 3/10/2016.
 */
@Component("updateTargetBasedIncentiveAction")
class UpdateTargetBasedIncentiveAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    IncentiveSetupService incentiveSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        try{
            Map map = (Map) object
            boolean isExistingCustomer = false
            String msg = ''
            TargetBasedIncentive targetBasedIncentive = map.get("targetBasedIncentive")
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersList = (List<TargetBasedIncentiveCustomers>) map.get("targetBasedIncentiveCustomersList")

            targetBasedIncentiveCustomersList.each {
                TargetBasedIncentiveCustomers targetBasedIncentiveCustomers = TargetBasedIncentiveCustomers.findByCustomerMasterAndTargetBasedIncentive(it.customerMaster,targetBasedIncentive)
                if(targetBasedIncentiveCustomers){
                    if(msg){
                        msg += ', '+it.customerMaster.name+'['+it.customerMaster.code+']'
                    }else{
                        msg = it.customerMaster.name+'['+it.customerMaster.code+']'
                    }
                    isExistingCustomer = true
                }
            }

            if(incentiveSetupService.checkOverlappingMultipleProgram(targetBasedIncentive.class.getSimpleName(), targetBasedIncentive.id, targetBasedIncentive.effectiveDateFrom, targetBasedIncentive.effectiveDateTo)){
                message = this.getMessage("Target Based Incentive", Message.ERROR, "Multiple Incentive program can’t overlap. \nPlease select a valid date range.")
                return message
            }else{
                if(isExistingCustomer){
                    message = this.getMessage("Target Based Incentive", Message.ERROR, "Customer "+msg+" already exists.")
                }else if(!targetBasedIncentive.validate()) {
                    message = this.getValidationErrorMessage(targetBasedIncentive)
                }else{
                    message = this.getMessage("Target Based Incentive", Message.SUCCESS, SUCCESS_SAVE)
                }
                return message
            }
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object user) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user

            TargetBasedIncentive targetBasedIncentive = TargetBasedIncentive.get(Long.parseLong(params.id))
            targetBasedIncentive.properties = params

            targetBasedIncentive.userUpdated = applicationUser
            targetBasedIncentive.lastUpdated = new Date()

            List<TargetBasedIncentiveSlab> targetBasedIncentiveSlabList = []
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersList = []

            List<TargetBasedIncentiveSlab> targetBasedIncentiveSlabDeleteList = []
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersDeleteList = []

            params.allIncentive.each{key, val ->
                if(val instanceof Map){
                    if(!val.id){
                        TargetBasedIncentiveSlab targetBasedIncentiveSlab = new TargetBasedIncentiveSlab()
                        targetBasedIncentiveSlab.targetBasedIncentive = targetBasedIncentive

                        targetBasedIncentiveSlab.achievementFrom = Float.parseFloat(val.achievementFrom)
                        targetBasedIncentiveSlab.achievementTo = Float.parseFloat(val.achievementTo)
                        targetBasedIncentiveSlab.incentiveAmount = Float.parseFloat(val.incentiveAmount)

                        targetBasedIncentiveSlab.userCreated = applicationUser
                        targetBasedIncentiveSlab.dateCreated = new Date()

                        targetBasedIncentiveSlabList.add(targetBasedIncentiveSlab)
                    }
                }
            }

            params.customerList.each{key, val ->
                if(val instanceof Map){
                    TargetBasedIncentiveCustomers targetBasedIncentiveCustomers = new TargetBasedIncentiveCustomers()
                    targetBasedIncentiveCustomers.targetBasedIncentive = targetBasedIncentive
                    targetBasedIncentiveCustomers.customerMaster = CustomerMaster.read(val.customerId)

                    targetBasedIncentiveCustomers.userCreated = applicationUser
                    targetBasedIncentiveCustomers.dateCreated = new Date()

                    targetBasedIncentiveCustomersList.add(targetBasedIncentiveCustomers)
                }
            }

            if(params.containsKey('incentiveSlabDeleteList')){
                params.incentiveSlabDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        TargetBasedIncentiveSlab targetBasedIncentiveSlab = TargetBasedIncentiveSlab.get(Long.parseLong(val.id))
                        targetBasedIncentiveSlabDeleteList.add(targetBasedIncentiveSlab)
                    }
                }
            }

            if(params.containsKey('incentiveCustomersDeleteList')){
                params.incentiveCustomersDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        TargetBasedIncentiveCustomers targetBasedIncentiveCustomers = TargetBasedIncentiveCustomers.get(Long.parseLong(val.id))
                        targetBasedIncentiveCustomersDeleteList.add(targetBasedIncentiveCustomers)
                    }
                }
            }

            Map map = [:]
            map.put("targetBasedIncentive",targetBasedIncentive)
            map.put("targetBasedIncentiveSlabList",targetBasedIncentiveSlabList)
            map.put("targetBasedIncentiveCustomersList",targetBasedIncentiveCustomersList)

            map.put("targetBasedIncentiveSlabDeleteList",targetBasedIncentiveSlabDeleteList)
            map.put("targetBasedIncentiveCustomersDeleteList",targetBasedIncentiveCustomersDeleteList)

            message = this.preCondition(params, map)

            if(message.type == 1){
                TargetBasedIncentive targetBasedIncentiveRow = incentiveSetupService.createTargetBasedIncentive(map)
                if (targetBasedIncentiveRow) {
                    message = this.getMessage("Target Based Incentive", Message.SUCCESS, "Target Based Incentive Updated Successfully.")
                } else {
                    message = this.getMessage("Target Based Incentive", Message.ERROR, FAIL_SAVE)
                }
            }
            return message

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return  null
    }
}
