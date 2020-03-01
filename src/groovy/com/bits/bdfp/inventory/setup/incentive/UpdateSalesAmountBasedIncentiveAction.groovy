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
@Component("updateSalesAmountBasedIncentiveAction")
class UpdateSalesAmountBasedIncentiveAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    IncentiveSetupService incentiveSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        try{
            Map map = (Map) object
            boolean isExistingCustomer = false
            String msg = ''
            SalesAmountBasedIncentive salesAmountBasedIncentive = map.get("salesAmountBasedIncentive")
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersList = (List<SalesAmountBasedIncentiveCustomers>) map.get("salesAmountBasedIncentiveCustomersList")

            salesAmountBasedIncentiveCustomersList.each {
                SalesAmountBasedIncentiveCustomers salesAmountBasedIncentiveCustomers = SalesAmountBasedIncentiveCustomers.findByCustomerMasterAndSalesAmountBasedIncentive(it.customerMaster,salesAmountBasedIncentive)
                if(salesAmountBasedIncentiveCustomers){
                    if(msg){
                        msg += ', '+it.customerMaster.name+'['+it.customerMaster.code+']'
                    }else{
                        msg = it.customerMaster.name+'['+it.customerMaster.code+']'
                    }
                    isExistingCustomer = true
                }
            }

            if(incentiveSetupService.checkOverlappingMultipleProgram(salesAmountBasedIncentive.class.getSimpleName(), salesAmountBasedIncentive.id, salesAmountBasedIncentive.effectiveDateFrom, salesAmountBasedIncentive.effectiveDateTo)){
                message = this.getMessage("Sales Amount Based Incentive", Message.ERROR, "Multiple Incentive program can’t overlap. \nPlease select a valid date range.")
                return message
            }else{
                if(isExistingCustomer){
                    message = this.getMessage("Sales Amount Based Incentive", Message.ERROR, "Customer "+msg+" already exists.")
                }else if (!salesAmountBasedIncentive.validate()) {
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

            SalesAmountBasedIncentive salesAmountBasedIncentive = SalesAmountBasedIncentive.get(Long.parseLong(params.id))
            salesAmountBasedIncentive.properties = params

            salesAmountBasedIncentive.userUpdated = applicationUser
            salesAmountBasedIncentive.lastUpdated = new Date()

            List<SalesAmountBasedIncentiveSlab> salesAmountBasedIncentiveSlabList = []
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersList = []

            List<SalesAmountBasedIncentiveSlab> salesAmountBasedIncentiveSlabDeleteList = []
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersDeleteList = []

            params.allIncentive.each{key, val ->
                if(val instanceof Map){
                    if(!val.id){
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

            if(params.containsKey('incentiveSlabDeleteList')){
                params.incentiveSlabDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        SalesAmountBasedIncentiveSlab salesAmountBasedIncentiveSlab = SalesAmountBasedIncentiveSlab.get(Long.parseLong(val.id))
                        salesAmountBasedIncentiveSlabDeleteList.add(salesAmountBasedIncentiveSlab)
                    }
                }
            }

            if(params.containsKey('incentiveCustomersDeleteList')){
                params.incentiveCustomersDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        SalesAmountBasedIncentiveCustomers salesAmountBasedIncentiveCustomers = SalesAmountBasedIncentiveCustomers.get(Long.parseLong(val.id))
                        salesAmountBasedIncentiveCustomersDeleteList.add(salesAmountBasedIncentiveCustomers)
                    }
                }
            }

            Map map = [:]
            map.put("salesAmountBasedIncentive",salesAmountBasedIncentive)
            map.put("salesAmountBasedIncentiveSlabList",salesAmountBasedIncentiveSlabList)
            map.put("salesAmountBasedIncentiveCustomersList",salesAmountBasedIncentiveCustomersList)

            map.put("salesAmountBasedIncentiveSlabDeleteList",salesAmountBasedIncentiveSlabDeleteList)
            map.put("salesAmountBasedIncentiveCustomersDeleteList",salesAmountBasedIncentiveCustomersDeleteList)

            message = this.preCondition(params, map)

            if(message.type == 1){
                SalesAmountBasedIncentive salesAmountBasedIncentiveRow = incentiveSetupService.createSalesAmountBasedIncentive(map)
                if (salesAmountBasedIncentiveRow) {
                    message = this.getMessage("Sales Amount Based Incentive", Message.SUCCESS, "Sales Amount Based Incentive Updated Successfully.")
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
