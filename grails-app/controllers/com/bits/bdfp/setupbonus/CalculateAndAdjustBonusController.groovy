package com.bits.bdfp.setupbonus

import com.bits.bdfp.calculateandadjustbonus.ListPromotionAndPackageByCriteriaAction
import com.bits.bdfp.calculateandadjustbonus.PostAdjustPrimaryInvoiceAction
import com.bits.bdfp.calculateandadjustbonus.PostAdjustSecondaryInvoiceAction
import com.bits.bdfp.customer.CustomerLevel
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.promotion.Promotion
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class CalculateAndAdjustBonusController {
    @Autowired
    ListPromotionAndPackageByCriteriaAction listPromotionAndPackageByCriteriaAction
    @Autowired
    PostAdjustPrimaryInvoiceAction postAdjustPrimaryInvoiceAction
    @Autowired
    PostAdjustSecondaryInvoiceAction postAdjustSecondaryInvoiceAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def show = {
        List<Promotion> promotionList = Promotion.findAllByIsActiveAndCalculationStatus(true,'post');
        render(view: "show", model: [promotionList: promotionList])
    }

    def search = {
        List list = listPromotionAndPackageByCriteriaAction.execute(params, null)
        render listPromotionAndPackageByCriteriaAction.postCondition(null, list) as JSON
    }

    def adjust = {
        ApplicationUser applicationUserSession = session?.applicationUser
        ApplicationUser applicationUser = ApplicationUser.read(applicationUserSession.id)
        if(applicationUser){
            CustomerMaster customerMaster = CustomerMaster.read(applicationUser?.customerMasterId)
            if(applicationUser.userType.id == ApplicationConstants.USER_TYPE_SUPER_ADMIN || applicationUser.userType.id == ApplicationConstants.USER_TYPE_ADMIN ){
                // Adjust primary invoice
                render postAdjustPrimaryInvoiceAction.execute(params,applicationUser) as JSON
            }else if(customerMaster.customerLevel == CustomerLevel.PRIMARY){
                // Adjust secondary invoice
                render postAdjustSecondaryInvoiceAction.execute(params,applicationUser) as JSON
            }else{
                // Unauthorised access
                Message message = new Message()
                message.messageTitle = "Calculate and Adjust Bonus"
                message.messageBody = "You not authorised to perform this operation."
                message.type = 0
            }
        }

        params
//        List list = listPromotionAndPackageByCriteriaAction.execute(params, null)
//        render listPromotionAndPackageByCriteriaAction.postCondition(null, list) as JSON
    }
}
