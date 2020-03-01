package com.bits.bdfp.inventory.retailorder.consolidateretailorder

import com.bits.bdfp.history.SecondaryOrderHistory
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by mdalinaser.khan on 1/19/16.
 */
@Component("updateSecondaryOrderCreatedFromRetailOrderAction")
class UpdateSecondaryOrderCreatedFromRetailOrderAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService
    @Autowired
    SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService
    @Autowired
    RetailOrderService retailOrderService
    @Autowired
    ProductPriceService productPriceService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) object
            if (!secondaryDemandOrder.validate()) {
                return this.getValidationErrorMessage(secondaryDemandOrder)
            }
            if(secondaryDemandOrder.dateOrder > secondaryDemandOrder.dateDeliver){
                return this.getMessage("Update Secondary Order", Message.ERROR, "Order Date can't be greater than Delivery Date")
            }
            return this.getMessage(secondaryDemandOrder, Message.SUCCESS, this.SUCCESS_SAVE)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Secondary Demand Order", Message.ERROR, "${ex.message}")
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            SecondaryDemandOrder secondaryDemandOrder = secondaryDemandOrderService.read(Long.parseLong(params?.id?.toString()))
            secondaryDemandOrder.properties = params
            Date dateNow = new Date()
            secondaryDemandOrder.userUpdated = applicationUser
            secondaryDemandOrder.lastUpdated = dateNow

            message = this.preCondition(null, secondaryDemandOrder)
            if (message.type != 1) {
                return message
            }

            List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailList = ObjectUtil.instantiateObjects(params.items, SecondaryDemandOrderDetails.class)
            int orderDetailsCount = secondaryDemandOrderDetailList.size()
            if(orderDetailsCount <= 0){
                return this.getMessage(secondaryDemandOrder, Message.ERROR, "No Item Selected")
            }
            secondaryDemandOrderDetailList.each { secondaryDemandOrderDetails ->
                Map productPrice = productPriceService.getProductPriceByCustomerAndProduct(secondaryDemandOrder.userTentativeDelivery.id, secondaryDemandOrderDetails.finishProduct.id, secondaryDemandOrder.territorySubArea.id)
                secondaryDemandOrderDetails.rate = productPrice.productPriceWithVat
                secondaryDemandOrderDetails.amount = secondaryDemandOrderDetails.rate * secondaryDemandOrderDetails.quantity
                if(secondaryDemandOrderDetails.id){
                    secondaryDemandOrderDetails.userUpdated = applicationUser
                    secondaryDemandOrderDetails.lastUpdated = dateNow
                }else{
                    secondaryDemandOrderDetails.dateCreated = new Date()
                    secondaryDemandOrderDetails.userCreated = applicationUser
                    secondaryDemandOrderDetails.secondaryDemandOrder = secondaryDemandOrder
                }

                if(!secondaryDemandOrderDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(secondaryDemandOrderDetails).messageBody[0].toString())
                }
            }

            if(params.isSubmit){
                secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.UNDER_PROCESS
                secondaryDemandOrder.isForwared = true
            }

            //Secondary Order History Entry -- Start
            List<SecondaryOrderHistory> secondaryOrderHistoryList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    SecondaryOrderHistory orderHistory = SecondaryOrderHistory.findBySecondaryDemandOrderAndFinishProduct(secondaryDemandOrder, FinishProduct.get(Long.parseLong(val.finishProduct.id)))
                    if (orderHistory) {
                        orderHistory.newQuantity = Float.parseFloat(val.quantity)
                        orderHistory.updatedBy = applicationUser
                        secondaryOrderHistoryList.add(orderHistory)
                    } else {
                        SecondaryOrderHistory secondaryOrderHistory = new SecondaryOrderHistory()
                        secondaryOrderHistory.secondaryDemandOrder = secondaryDemandOrder
                        secondaryOrderHistory.finishProduct = FinishProduct.get(Long.parseLong(val.finishProduct.id))
                        secondaryOrderHistory.previousQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        secondaryOrderHistory.newQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        secondaryOrderHistory.dateCreated = new Date()
                        secondaryOrderHistory.updatedBy = applicationUser
                        secondaryOrderHistoryList.add(secondaryOrderHistory)
                    }
                }
            }
            //-- End ---

            Map map = new LinkedHashMap()
            map.put('secondaryDemandOrder', secondaryDemandOrder)
            map.put('secondaryDemandOrderDetails', secondaryDemandOrderDetailList)

            secondaryDemandOrder = retailOrderService.updateSecondaryDemandOrder(map)
            if (secondaryDemandOrder) {
                secondaryOrderHistoryList.each {
                    if(it.validate()){
                        secondaryDemandOrderService.createSecondaryOrderHistory(it)
                    }
                }
            }
            if (secondaryDemandOrder) {
                if(params.isSubmit){
                    message = this.getMessage(secondaryDemandOrder, Message.SUCCESS, "Secondary Demand Order Submitted Successfully.")
                }else{
                    message = this.getMessage(secondaryDemandOrder, Message.SUCCESS, "Secondary Demand Order Updated Successfully.")
                }
            } else {
                message = this.getMessage(secondaryDemandOrder, Message.ERROR, this.FAIL_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
