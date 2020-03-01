package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
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
@Component("updateQuantityBasedIncentiveAction")
class UpdateQuantityBasedIncentiveAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    IncentiveSetupService incentiveSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        try{
            Map map = (Map) object
            boolean isExistingCustomer = false
            String msg = ''
            QuantityBasedIncentive quantityBasedIncentive = map.get("quantityBasedIncentive")
            List<QuantityBasedIncentiveCustomers> quantityBasedIncentiveCustomersList = (List<QuantityBasedIncentiveCustomers>) map.get("quantityBasedIncentiveCustomersList")

            quantityBasedIncentiveCustomersList.each {
                QuantityBasedIncentiveCustomers quantityBasedIncentiveCustomers = QuantityBasedIncentiveCustomers.findByCustomerMasterAndQuantityBasedIncentive(it.customerMaster,quantityBasedIncentive)
                if(quantityBasedIncentiveCustomers){
                    if(msg){
                        msg += ', '+it.customerMaster.name+'['+it.customerMaster.code+']'
                    }else{
                        msg = it.customerMaster.name+'['+it.customerMaster.code+']'
                    }
                    isExistingCustomer = true
                }
            }

            if(incentiveSetupService.checkOverlappingMultipleProgram(quantityBasedIncentive.class.getSimpleName(), quantityBasedIncentive.id, quantityBasedIncentive.effectiveDateFrom, quantityBasedIncentive.effectiveDateTo)){
                message = this.getMessage("Quantity Based Incentive", Message.ERROR, "Multiple Incentive program can’t overlap. \nPlease select a valid date range.")
                return message
            }else{
                if(isExistingCustomer){
                    message = this.getMessage("Quantity Based Incentive", Message.ERROR, "Customer "+msg+" already exists.")
                }else if(!quantityBasedIncentive.validate()) {
                    message = this.getValidationErrorMessage(quantityBasedIncentive)
                }else{
                    message = this.getMessage("Quantity Based Incentive", Message.SUCCESS, SUCCESS_SAVE)
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

            QuantityBasedIncentive quantityBasedIncentive = QuantityBasedIncentive.get(Long.parseLong(params.id))
            quantityBasedIncentive.properties = params

            quantityBasedIncentive.userUpdated = applicationUser
            quantityBasedIncentive.lastUpdated = new Date()

            List<QuantityBasedIncentiveSlab> quantityBasedIncentiveSlabList = []
            List<QuantityBasedIncentiveCustomers> quantityBasedIncentiveCustomersList = []

            List<QuantityBasedIncentiveSlab> quantityBasedIncentiveSlabDeleteList = []
            List<QuantityBasedIncentiveCustomers> quantityBasedIncentiveCustomersDeleteList = []

            params.allIncentive.each{key, val ->
                if(val instanceof Map){
                    if(!val.id){
                        QuantityBasedIncentiveSlab quantityBasedIncentiveSlab = new QuantityBasedIncentiveSlab()
                        quantityBasedIncentiveSlab.quantityBasedIncentive = quantityBasedIncentive

                        quantityBasedIncentiveSlab.finishProduct = FinishProduct.get(Long.parseLong(val.productId))
                        quantityBasedIncentiveSlab.quantityFrom = Float.parseFloat(val.quantityFrom)
                        quantityBasedIncentiveSlab.quantityTo = Float.parseFloat(val.quantityTo)
                        quantityBasedIncentiveSlab.incentiveAmount = Float.parseFloat(val.incentiveAmount)

                        quantityBasedIncentiveSlab.userCreated = applicationUser
                        quantityBasedIncentiveSlab.dateCreated = new Date()

                        quantityBasedIncentiveSlabList.add(quantityBasedIncentiveSlab)
                    }
                }
            }

            params.customerList.each{key, val ->
                if(val instanceof Map){
                    QuantityBasedIncentiveCustomers quantityBasedIncentiveCustomers = new QuantityBasedIncentiveCustomers()
                    quantityBasedIncentiveCustomers.quantityBasedIncentive = quantityBasedIncentive
                    quantityBasedIncentiveCustomers.customerMaster = CustomerMaster.read(val.customerId)

                    quantityBasedIncentiveCustomers.userCreated = applicationUser
                    quantityBasedIncentiveCustomers.dateCreated = new Date()

                    quantityBasedIncentiveCustomersList.add(quantityBasedIncentiveCustomers)
                }
            }

            if(params.containsKey('incentiveSlabDeleteList')){
                params.incentiveSlabDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        QuantityBasedIncentiveSlab quantityBasedIncentiveSlab = QuantityBasedIncentiveSlab.get(Long.parseLong(val.id))
                        quantityBasedIncentiveSlabDeleteList.add(quantityBasedIncentiveSlab)
                    }
                }
            }

            if(params.containsKey('incentiveCustomersDeleteList')){
                params.incentiveCustomersDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        QuantityBasedIncentiveCustomers quantityBasedIncentiveCustomers = QuantityBasedIncentiveCustomers.get(Long.parseLong(val.id))
                        quantityBasedIncentiveCustomersDeleteList.add(quantityBasedIncentiveCustomers)
                    }
                }
            }

            Map map = [:]
            map.put("quantityBasedIncentive",quantityBasedIncentive)
            map.put("quantityBasedIncentiveSlabList",quantityBasedIncentiveSlabList)
            map.put("quantityBasedIncentiveCustomersList",quantityBasedIncentiveCustomersList)

            map.put("quantityBasedIncentiveSlabDeleteList",quantityBasedIncentiveSlabDeleteList)
            map.put("quantityBasedIncentiveCustomersDeleteList",quantityBasedIncentiveCustomersDeleteList)

            message = this.preCondition(params, map)

            if(message.type == 1){
                QuantityBasedIncentive quantityBasedIncentiveRow = incentiveSetupService.createQuantityBasedIncentive(map)
                if (quantityBasedIncentiveRow) {
                    message = this.getMessage("Quantity Based Incentive", Message.SUCCESS, "Quantity Based Incentive Updated Successfully.")
                } else {
                    message = this.getMessage("Quantity Based Incentive", Message.ERROR, FAIL_SAVE)
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
