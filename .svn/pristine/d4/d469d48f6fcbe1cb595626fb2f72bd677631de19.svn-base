package com.bits.bdfp.rest

import com.bits.bdfp.inventory.retailorder.RetailOrder
import com.bits.bdfp.inventory.retailorder.RetailOrderDetails
import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat

@Component("createRetailOrderByMobileAction")
class CreateRetailOrderByMobileAction extends Action{
    Message message
    public static final Log log = LogFactory.getLog(CreateRetailOrderByMobileAction.class)
    private final String MESSAGE_HEADER = 'New Retail Order'
    private final String MESSAGE_SUCCESS = 'Retail Order created successfully'

    @Autowired
    RetailOrderService retailOrderService
    @Autowired
    SpringSecurityService springSecurityService

    public Object preCondition(def params, def object) {
        return null
    }

    public Map execute(def params, def object) {
        RetailOrder retailOrder = null
        try {
            retailOrder = new RetailOrder()
            retailOrder.properties = params

//            retailOrder.enterprise = EnterpriseConfiguration.read(params.enterpriseId)
//            retailOrder.deliveryMan = CustomerMaster.read(params.deliveryManId)
//            retailOrder.orderPlacedFor = CustomerMaster.read(Long.parseLong(params.orderPlacedFor.id))
//            retailOrder.territorySubArea = TerritorySubArea.read(Long.parseLong(params.territorySubArea.id))
//
//            retailOrder.orderDate = DateUtil.getSimpleDate(params.orderDate)
//            retailOrder.deliveryDate = DateUtil.getSimpleDate(params.deliveryDate)

            retailOrder.userCreated = (ApplicationUser) object
            retailOrder.orderPlacedBy = retailOrder.userCreated
            if(params.isSubmitted) {
                retailOrder.isOrderSubmitted = true
            }else{
                retailOrder.isOrderSubmitted = false
            }
            retailOrder.orderNo = 'AUTO'
            if (!retailOrder.validate()) {
                return this.getValidationErrorMessage(retailOrder)
            }
//            List<RetailOrderDetails> retailOrderDetailsList = ObjectUtil.instantiateObjects(params.items, RetailOrderDetails.class)
            List<RetailOrderDetails> retailOrderDetailsList = []

            params.items.each{
                RetailOrderDetails retailOrderDetails = new RetailOrderDetails(it)
                retailOrderDetailsList.add(retailOrderDetails)
            }

            int orderDetailsCount = retailOrderDetailsList.size()
            if(orderDetailsCount <= 0){
                return this.getMessage(retailOrder, Message.ERROR, "No Items are selected")
            }
            retailOrderDetailsList.each {  retailOrderDetails ->
                retailOrderDetails.userCreated = retailOrder.userCreated
                retailOrderDetails.dateCreated = new Date()
                retailOrderDetails.lastUpdated = new Date()
                retailOrderDetails.retailOrder = retailOrder
                retailOrderDetails.amount = retailOrderDetails.rate * retailOrderDetails.quantity
                if(!retailOrderDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(retailOrderDetails).messageBody[0].toString())
                }
            }

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat ("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat ("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)
            retailOrder.orderNo = CodeGenerationUtil.instance.generateCode(retailOrder.enterprise, "RETAIL_ORDER", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            retailOrder.dateCreated = new Date()
            retailOrder.lastUpdated = new Date()
            retailOrderService.create(retailOrder)
            retailOrderDetailsList.each {
                retailOrderService.saveOrderDetails(it)
            }
            message = this.getMessage(retailOrder, Message.SUCCESS, "New Retail Order Created Successfully, Order No: " + retailOrder.orderNo)
            return [message:message, orderNo:retailOrder.orderNo]
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Retail Order", Message.ERROR, "${ex.message}")
        }

    }

    public Object postCondition(def params, def object) {
        return null
    }
}