package com.bits.bdfp.inventory.retailorder.consolidateretailorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.history.SecondaryOrderHistory
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.commons.ObjectUtil
import org.springframework.stereotype.Component
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import java.text.SimpleDateFormat
/**
 * Created by mdalinaser.khan on 1/18/16.
 */
@Component("createSecondaryOrderFromRetailOrderAction")
class CreateSecondaryOrderFromRetailOrderAction extends Action {

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
                return this.getMessage("Create Secondary Order", Message.ERROR, "Order Date can't be greater than Delivery Date")
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
            SecondaryDemandOrder secondaryDemandOrder = new SecondaryDemandOrder(params)
            Date dateNow = secondaryDemandOrder.dateOrder  // new Date() for LIVE
            secondaryDemandOrder.dateOrder = dateNow
            secondaryDemandOrder.dateCreated = dateNow
            secondaryDemandOrder.userOrderPlaced = applicationUser
            secondaryDemandOrder.userCreated = applicationUser
            SimpleDateFormat formatMonth = new SimpleDateFormat ("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat ("YY")
            String currentYear = formatYear.format(dateNow)
            EnterpriseConfiguration enterpriseConfiguration = secondaryDemandOrder.customerMaster.enterpriseConfiguration
            secondaryDemandOrder.orderNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration,"SECONDARY_DEMAND_ORDER", "", "", "", "", "", currentMonth, currentYear,"","")
            secondaryDemandOrder.userTentativeDelivery = CustomerMaster.get(applicationUser.customerMasterId)
            if(params.isSubmitted) {
                secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.UNDER_PROCESS
                secondaryDemandOrder.isForwared = true
            }else{
                secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.NOT_SUBMITTED
                secondaryDemandOrder.isForwared = false
            }
            secondaryDemandOrder.dateCreated = new Date()
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
                secondaryDemandOrderDetails.secondaryDemandOrder = secondaryDemandOrder
                secondaryDemandOrderDetails.amount = secondaryDemandOrderDetails.rate * secondaryDemandOrderDetails.quantity
                secondaryDemandOrderDetails.dateCreated = new Date()
                secondaryDemandOrderDetails.userCreated = applicationUser
                if(!secondaryDemandOrderDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(secondaryDemandOrderDetails).messageBody[0].toString())
                }
            }

            String[] retailOrderIds = params.retailOrderIds.split(',')

            Map map = new LinkedHashMap()
            map.put('secondaryDemandOrder', secondaryDemandOrder)
            map.put('secondaryDemandOrderDetails', secondaryDemandOrderDetailList)
            map.put('retailOrderIds', retailOrderIds)

            secondaryDemandOrder = retailOrderService.createSecondaryDemandOrder(map)

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

            if (secondaryDemandOrder) {
                secondaryOrderHistoryList.each {
                    if(it.validate()){
                        secondaryDemandOrderService.createSecondaryOrderHistory(it)
                    }
                }
            }
            if (secondaryDemandOrder) {
                message = this.getMessage(secondaryDemandOrder, Message.SUCCESS, "Secondary Demand Order Created Successfully for Order No: " + secondaryDemandOrder.orderNo)
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
