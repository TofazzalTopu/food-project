package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.inventory.demandorder.*
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.workflow.Workflow
import com.bits.bdfp.inventory.workflow.WorkflowCustomerMapping
import com.bits.bdfp.inventory.workflow.WorkflowService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by NZ on 9/20/2015.
 */
@Component("createPrimaryOrderFromSecondaryOrderAction")
class CreatePrimaryOrderFromSecondaryOrderAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService
    @Autowired
    WorkflowService workflowService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object.get('primaryDemandOrder')
            if (!primaryDemandOrder.validate()) {
                message = this.getValidationErrorMessage(primaryDemandOrder)
            } else {
                message = this.getMessage(primaryDemandOrder, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("PrimaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            PrimaryDemandOrder primaryDemandOrder = new PrimaryDemandOrder(params)
            Date dateNow = new Date()
            primaryDemandOrder.orderDate = dateNow
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.idEnterprise))
            primaryDemandOrder.orderNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "PRIMARY_DEMAND_ORDER", "", "", "", "", "", currentYear, currentMonth, "", "")
            primaryDemandOrder.dateCreated = dateNow
            primaryDemandOrder.userOrderPlaced = applicationUser
            primaryDemandOrder.userCreated = applicationUser
            primaryDemandOrder.isNew = false
            if (Workflow.countByEnterpriseConfigurationAndMenuName(enterpriseConfiguration, ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM) > 0
                    && WorkflowCustomerMapping.countByCustomerMaster(primaryDemandOrder.customerOrderFor) > 0) {
                primaryDemandOrder.isApprovalRequired = true
                primaryDemandOrder.isApproved = false
                primaryDemandOrder.demandOrderStatus = DemandOrderStatus.WAITING_FOR_APPROVAL
            } else {
                primaryDemandOrder.isApprovalRequired = false
                primaryDemandOrder.isApproved = true
                primaryDemandOrder.demandOrderStatus = DemandOrderStatus.SENT_FOR_PROCESSING
            }

            int length = Integer.parseInt(params.count)
            PrimaryDemandOrderDetails[] primaryDemandOrderDetails = new PrimaryDemandOrderDetails[length]
            List<PrimaryOrderHistory> primaryOrderHistoryList = []
            params.items.each { key, val ->
                for (int i = 0; i < length; i++) {
                    if (key == "details[" + i + "]") {
                        primaryDemandOrderDetails[i] = new PrimaryDemandOrderDetails()
                        primaryDemandOrderDetails[i].primaryDemandOrder = primaryDemandOrder
                        primaryDemandOrderDetails[i].finishProduct = FinishProduct.read(Long.parseLong(val.id))
                        primaryDemandOrderDetails[i].amount = Float.parseFloat(val.amount)
                        primaryDemandOrderDetails[i].quantity = Float.parseFloat(val.quantity)
                        primaryDemandOrderDetails[i].rate = Float.parseFloat(val.rate)
                        primaryDemandOrderDetails[i].dateCreated = new Date()
                        primaryDemandOrderDetails[i].userCreated = applicationUser

                        //Primary Order History Entry -- Start
                        PrimaryOrderHistory primaryOrderHistory = new PrimaryOrderHistory()
                        primaryOrderHistory.primaryDemandOrder = primaryDemandOrder
                        primaryOrderHistory.finishProduct = FinishProduct.read(Long.parseLong(val.id))
                        primaryOrderHistory.previousQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        primaryOrderHistory.newQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        primaryOrderHistory.dateCreated = new Date()
                        primaryOrderHistory.updatedBy = applicationUser
                        primaryOrderHistoryList.add(primaryOrderHistory)
                        //-- End ---
                    }
                }
            }

            String[] ids = params.ids.split(',')

            Map map = new LinkedHashMap()
            map.put('primaryDemandOrder', primaryDemandOrder)
            map.put('primaryDemandOrderDetails', primaryDemandOrderDetails)
            map.put('ids', ids)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                primaryDemandOrder = primaryDemandOrderService.createPrimaryOrder(map)
                if (primaryDemandOrder) {
                    primaryDemandOrderService.changeStatus(params)
                    primaryOrderHistoryList.each {
                        if (it.validate()) {
                            primaryDemandOrderService.createPrimaryOrderHistory(it)
                        }
                    }
                    message = this.getMessage(primaryDemandOrder, Message.SUCCESS, "Primary Demand Order Saved Successfully for Order No: " + primaryDemandOrder.orderNo)
                } else {
                    message = this.getMessage(primaryDemandOrder, Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Primary Demand Order", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
