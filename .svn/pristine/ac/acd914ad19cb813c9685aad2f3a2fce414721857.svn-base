package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.text.SimpleDateFormat

@Component("createSecondaryDemandOrderAction")
class CreateSecondaryDemandOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
    CustomerMasterService customerMasterService
    @Autowired
    FinishProductService finishProductService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            SecondaryDemandOrder secondaryDemandOrder = map.secondaryDemandOrder
            List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList = map.secondaryDemandOrderDetailsList
            boolean isValidate = true
            if (!secondaryDemandOrder.validate()) {
                isValidate = false
                message = this.getValidationErrorMessage(secondaryDemandOrder)
            } else {
                if (secondaryDemandOrderDetailsList && secondaryDemandOrderDetailsList.size() > 0) {
                    secondaryDemandOrderDetailsList.each {
                        if (!it.validate()) {
                            isValidate = false
                            message = this.getValidationErrorMessage(it)
                        }
                    }
                } else {
                    isValidate = false
                    message = this.getMessage(secondaryDemandOrder, Message.ERROR, "You did not select any product for order.Please add product.")
                }

            }
            if (isValidate) {
                if (secondaryDemandOrder.dateOrder <= secondaryDemandOrder.dateDeliver) {
                    message = this.getMessage(secondaryDemandOrder, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(secondaryDemandOrder, Message.ERROR, "Delivery Date Can not be less than order date")
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("SecondaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SecondaryDemandOrder secondaryDemandOrder = new SecondaryDemandOrder()
            secondaryDemandOrder.properties = params
            ApplicationUser applicationUser = (ApplicationUser) object
            EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(Long.parseLong(params?.enterpriseConfiguration?.toString()))
            if (!enterpriseConfiguration) {
                return this.getMessage(secondaryDemandOrder, Message.ERROR, "Enterprise is not available")
            }
            secondaryDemandOrder.userTentativeDelivery = secondaryDemandOrder.customerMaster
            //CustomerMaster.get(applicationUser.customerMasterId)
            secondaryDemandOrder.orderNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "SECONDARY_DEMAND_ORDER", "", "", "", "", "", currentMonth, currentYear, "", "")
            secondaryDemandOrder.userOrderPlaced = applicationUser
            secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.UNDER_PROCESS
            secondaryDemandOrder.dateCreated = new Date()
            secondaryDemandOrder.userCreated = applicationUser
            secondaryDemandOrder.isForwared = Boolean.FALSE

            List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList = ObjectUtil.instantiateObjects(params.items, SecondaryDemandOrderDetails.class)
            int orderDetailsCount = secondaryDemandOrderDetailsList.size()
            if (orderDetailsCount <= 0) {
                return this.getMessage(secondaryDemandOrder, Message.ERROR, "No Item Selected")
            }

            secondaryDemandOrderDetailsList.each { secondaryDemandOrderDetails ->
                secondaryDemandOrderDetails.secondaryDemandOrder = secondaryDemandOrder
                secondaryDemandOrderDetails.amount = secondaryDemandOrderDetails.rate * secondaryDemandOrderDetails.quantity
                secondaryDemandOrderDetails.userCreated = applicationUser
                secondaryDemandOrderDetails.dateCreated = new Date()
            }

            Map map = [:]
            map.put('secondaryDemandOrder', secondaryDemandOrder)
            map.put('secondaryDemandOrderDetailsList', secondaryDemandOrderDetailsList)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                secondaryDemandOrder = secondaryDemandOrderService.create(map)
                if (secondaryDemandOrder) {
                    message = this.getMessage(secondaryDemandOrder, Message.SUCCESS, "Secondary Demand Order Saved Successfully for Order no: " + secondaryDemandOrder.orderNo)
                } else {
                    message = this.getMessage(secondaryDemandOrder, Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("SecondaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}