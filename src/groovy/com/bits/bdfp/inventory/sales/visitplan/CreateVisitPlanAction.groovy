package com.bits.bdfp.inventory.sales.visitplan

import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.sales.InvoiceService
import com.bits.bdfp.inventory.setup.PosCustomer
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.bits.bdfp.inventory.warehouse.FinishedProductBookedDetails
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.setup.salestarget.SalesHeadService
import com.bits.bdfp.setup.salestarget.VisitPlan
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
 * Created by mdalinaser.khan on 9/29/15.
 */
@Component("createVisitPlanAction")
class CreateVisitPlanAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    Message message
    @Autowired
    SalesHeadService salesHeadService

    public Object preCondition(Object params, Object object) {
            return null
    }

    public Object execute(Object params, Object object) {
        try {
            Map map = [:]
            ApplicationUser applicationUser = (ApplicationUser) object
            List<VisitPlan> visitPlanList = ObjectUtil.instantiateObjects(params.items, VisitPlan.class)
            visitPlanList.each {
                it.userCreated = applicationUser
                it.dateCreated = new Date()
            }
            map.put('visitPlanList',visitPlanList)
            salesHeadService.createVisitPlan(map)
            return this.getMessage(visitPlanList, Message.SUCCESS, "Visit plans Created Successfully")
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}