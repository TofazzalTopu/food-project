package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.workflow.Workflow
import com.bits.bdfp.inventory.workflow.WorkflowCustomerMapping
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
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
 * Created by mdalinaser.khan on 9/20/15.
 */
@Component("createNewPrimaryDemandOrderAction")
class CreateNewPrimaryDemandOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    Message message
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object
            if (!primaryDemandOrder.validate()) {
                return null
            }
            return primaryDemandOrder
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            PrimaryDemandOrder primaryDemandOrder = new PrimaryDemandOrder()
            primaryDemandOrder.properties = params
            if (!params.customerId) {
                return this.getMessage(primaryDemandOrder, Message.ERROR, "Customer is not selected")
            }
            CustomerMaster customerMaster = CustomerMaster.get(Long.parseLong(params.customerId))
            primaryDemandOrder.customerOrderFor = customerMaster
            primaryDemandOrder.dateExpectedDeliver = primaryDemandOrder.dateProposedDelivery
            primaryDemandOrder.userCreated = applicationUser
            primaryDemandOrder.userOrderPlaced = applicationUser
            if (params.shipTo == 'ship') {
                CustomerShippingAddress shippingAddress = CustomerShippingAddress.get(Long.parseLong(params.shippingAddressId))
                primaryDemandOrder.shippingAddress = shippingAddress
            }
            primaryDemandOrder.orderDate = new Date()
            primaryDemandOrder.dateCreated = new Date()
            primaryDemandOrder.isNew = true
            ApplicationUserEnterprise applicationUserEnterprise = ApplicationUserEnterprise.findByApplicationUser(applicationUser)
            if (!applicationUserEnterprise) {
                return this.getMessage(primaryDemandOrder, Message.ERROR, "User is not in any Enterprise")
            }

            if (Workflow.countByEnterpriseConfigurationAndMenuName(customerMaster.enterpriseConfiguration, ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM) > 0
                    && WorkflowCustomerMapping.countByCustomerMaster(primaryDemandOrder.customerOrderFor) > 0) {
                primaryDemandOrder.isApprovalRequired = true
                primaryDemandOrder.isApproved = false
                primaryDemandOrder.demandOrderStatus = DemandOrderStatus.WAITING_FOR_APPROVAL
            } else {
                primaryDemandOrder.isApprovalRequired = false
                primaryDemandOrder.isApproved = true
                primaryDemandOrder.demandOrderStatus = DemandOrderStatus.SENT_FOR_PROCESSING
            }
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            EnterpriseConfiguration enterpriseConfiguration = applicationUserEnterprise.enterpriseConfiguration
            primaryDemandOrder.orderNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "PRIMARY_DEMAND_ORDER", "", "", "", "", "", currentMonth, currentYear, "", "")
            if (!primaryDemandOrder.validate()) {
                return this.getValidationErrorMessage(primaryDemandOrder)
            }

            CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.findByCustomerMaster(customerMaster)
            if (customerTerritorySubArea) {
                primaryDemandOrder.territorySubArea = customerTerritorySubArea.territorySubArea
            }

            List<PrimaryOrderHistory> primaryOrderHistoryList = []
            List<PrimaryDemandOrderDetails> primaryDemandOrderDetailList = []
            params.products.each { key, val ->
                if (val instanceof Map) {
                    PrimaryDemandOrderDetails primaryDemandOrderDetails = new PrimaryDemandOrderDetails(val)
                    primaryDemandOrderDetails.dateCreated = new Date()
                    primaryDemandOrderDetails.userCreated = primaryDemandOrder.userCreated
                    primaryDemandOrderDetails.primaryDemandOrder = primaryDemandOrder
                    primaryDemandOrderDetails.finishProduct = FinishProduct.get(Long.parseLong(val.productId))
                    primaryDemandOrderDetails.rate = Float.parseFloat(val.unitPrice)
                    primaryDemandOrderDetails.quantity = Float.parseFloat(val.transferQty)
                    primaryDemandOrderDetails.amount = Float.parseFloat(val.unitPrice) * Float.parseFloat(val.transferQty)
                    primaryDemandOrderDetailList.add(primaryDemandOrderDetails)


                    PrimaryOrderHistory primaryOrderHistory = new PrimaryOrderHistory()
                    primaryOrderHistory.dateCreated = new Date()
                    primaryOrderHistory.finishProduct = FinishProduct.get(Long.parseLong(val.productId))
                    primaryOrderHistory.updatedBy = applicationUser
                    primaryOrderHistory.primaryDemandOrder = primaryDemandOrder
                    primaryOrderHistory.previousQuantity = Float.parseFloat(val.transferQty)
                    primaryOrderHistory.newQuantity = Float.parseFloat(val.transferQty)
                    primaryOrderHistoryList.add(primaryOrderHistory)
                }
            }

            Map mapInstance = [:]
            mapInstance.put(PrimaryDemandOrder.class.simpleName, primaryDemandOrder)
            mapInstance.put(PrimaryDemandOrderDetails.class.simpleName, primaryDemandOrderDetailList)
            primaryDemandOrder = primaryDemandOrderService.createNew(mapInstance)
            int noOfRows = primaryDemandOrderService.createORUpdatePrimaryOrderHistory(primaryOrderHistoryList)
            if (primaryDemandOrder) {
                message = this.getMessage(primaryDemandOrder, Message.SUCCESS, "New Primary Demand Order Created Successfully. Order No: " + primaryDemandOrder.orderNo)
            } else {
                message = this.getMessage(primaryDemandOrder, Message.ERROR, this.FAIL_SAVE)
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}