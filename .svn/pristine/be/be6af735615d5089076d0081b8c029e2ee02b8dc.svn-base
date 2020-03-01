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
@Component("createSalesAmountBasedIncentiveAction")
class CreateSalesAmountBasedIncentiveAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    IncentiveSetupService incentiveSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        try{
            Map map = (Map) object
            SalesAmountBasedIncentive salesAmountBasedIncentive = map.get("salesAmountBasedIncentive")

            if(incentiveSetupService.checkOverlappingMultipleProgram(salesAmountBasedIncentive.class.getSimpleName(), salesAmountBasedIncentive.id, salesAmountBasedIncentive.effectiveDateFrom, salesAmountBasedIncentive.effectiveDateTo)){
                message = this.getMessage("Sales Amount Based Incentive", Message.ERROR, "Multiple Incentive program can’t overlap. \nPlease select a valid date range.")
                return message
            }else{
                if (!salesAmountBasedIncentive.validate()) {
                    message = this.getValidationErrorMessage(salesAmountBasedIncentive)
                }else{
                    message = this.getMessage("Sales Amount Based Incentive", Message.SUCCESS, SUCCESS_SAVE)
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

            SalesAmountBasedIncentive salesAmountBasedIncentive = new SalesAmountBasedIncentive()
            salesAmountBasedIncentive.properties = params

            salesAmountBasedIncentive.userCreated = applicationUser
            salesAmountBasedIncentive.dateCreated = new Date()

            List<SalesAmountBasedIncentiveSlab> salesAmountBasedIncentiveSlabList = []
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersList = []

            params.allIncentive.each{key, val ->
                if(val instanceof Map){
                    SalesAmountBasedIncentiveSlab salesAmountBasedIncentiveSlab = new SalesAmountBasedIncentiveSlab()
                    salesAmountBasedIncentiveSlab.salesAmountBasedIncentive = salesAmountBasedIncentive
                    salesAmountBasedIncentiveSlab.salesValueFrom = Double.parseDouble(val.achievementFrom)
                    salesAmountBasedIncentiveSlab.salesValueTo = Double.parseDouble(val.achievementTo)
                    salesAmountBasedIncentiveSlab.incentiveAmount = val.incentiveAmount?Double.parseDouble(val.incentiveAmount):0
                    salesAmountBasedIncentiveSlab.incentiveInPct = val.incentivePct?Float.parseFloat(val.incentivePct):0

                    salesAmountBasedIncentiveSlab.userCreated = applicationUser
                    salesAmountBasedIncentiveSlab.dateCreated = new Date()

                    salesAmountBasedIncentiveSlabList.add(salesAmountBasedIncentiveSlab)
                }
            }

            params.customerList.each{key, val ->
                if(val instanceof Map){
                    SalesAmountBasedIncentiveCustomers salesAmountBasedIncentiveCustomers = new SalesAmountBasedIncentiveCustomers()
                    salesAmountBasedIncentiveCustomers.salesAmountBasedIncentive = salesAmountBasedIncentive
                    salesAmountBasedIncentiveCustomers.customerMaster = CustomerMaster.read(val.customerId)

                    salesAmountBasedIncentiveCustomers.userCreated = applicationUser
                    salesAmountBasedIncentiveCustomers.dateCreated = new Date()

                    salesAmountBasedIncentiveCustomersList.add(salesAmountBasedIncentiveCustomers)
                }
            }

            Map map = [:]
            map.put("salesAmountBasedIncentive",salesAmountBasedIncentive)
            map.put("salesAmountBasedIncentiveSlabList",salesAmountBasedIncentiveSlabList)
            map.put("salesAmountBasedIncentiveCustomersList",salesAmountBasedIncentiveCustomersList)

            message = this.preCondition(params, map)

            if(message.type == 1){
                SalesAmountBasedIncentive salesAmountBasedIncentiveRow = incentiveSetupService.createSalesAmountBasedIncentive(map)
                if (salesAmountBasedIncentiveRow) {
                    message = this.getMessage("Sales Amount Based Incentive", Message.SUCCESS, "Sales Amount Based Incentive Created Successfully.")
                } else {
                    message = this.getMessage("Sales Amount Based Incentive", Message.ERROR, FAIL_SAVE)
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
