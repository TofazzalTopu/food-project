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
 * Created by mdalinaser.khan on 3/27/16.
 */
@Component("createTargetBasedIncentiveAction")
class CreateTargetBasedIncentiveAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    IncentiveSetupService incentiveSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        try{
            Map map = (Map) object
            TargetBasedIncentive targetBasedIncentive = map.get("targetBasedIncentive")

            if(incentiveSetupService.checkOverlappingMultipleProgram(targetBasedIncentive.class.getSimpleName(), targetBasedIncentive.id, targetBasedIncentive.effectiveDateFrom, targetBasedIncentive.effectiveDateTo)){
                message = this.getMessage("Target Based Incentive", Message.ERROR, "Multiple Incentive program can’t overlap. \nPlease select a valid date range.")
                return message
            }else{
                if (!targetBasedIncentive.validate()) {
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

            TargetBasedIncentive targetBasedIncentive = new TargetBasedIncentive()
            targetBasedIncentive.properties = params

            targetBasedIncentive.userCreated = applicationUser
            targetBasedIncentive.dateCreated = new Date()

            List<TargetBasedIncentiveSlab> targetBasedIncentiveSlabList = []
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersList = []

            params.allIncentive.each{key, val ->
                if(val instanceof Map){
                    TargetBasedIncentiveSlab targetBasedIncentiveSlab = new TargetBasedIncentiveSlab()
                    targetBasedIncentiveSlab.targetBasedIncentive = targetBasedIncentive
                    targetBasedIncentiveSlab.achievementFrom = Float.parseFloat(val.achievementFrom)
                    targetBasedIncentiveSlab.achievementTo = Float.parseFloat(val.achievementTo)
                    targetBasedIncentiveSlab.incentiveAmount = Double.parseDouble(val.incentiveAmount)

                    targetBasedIncentiveSlab.userCreated = applicationUser
                    targetBasedIncentiveSlab.dateCreated = new Date()

                    targetBasedIncentiveSlabList.add(targetBasedIncentiveSlab)
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

            Map map = [:]
            map.put("targetBasedIncentive",targetBasedIncentive)
            map.put("targetBasedIncentiveSlabList",targetBasedIncentiveSlabList)
            map.put("targetBasedIncentiveCustomersList",targetBasedIncentiveCustomersList)

            message = this.preCondition(params, map)

            if(message.type == 1){
                TargetBasedIncentive targetBasedIncentiveRow = incentiveSetupService.createTargetBasedIncentive(map)
                if (targetBasedIncentiveRow) {
                    message = this.getMessage("Target Based Incentive", Message.SUCCESS, "Target Based Incentive Created Successfully.")
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
