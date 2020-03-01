package com.bits.bdfp.inventory.retailorder

import com.bits.bdfp.customer.customermaster.ListCustomerAutoCompleteByApplicationUserAction
import com.bits.bdfp.customer.customermaster.ListCustomerByApplicationUserAction
import com.bits.bdfp.finance.customerpayment.GetCustomerBalanceAction
import com.bits.bdfp.inventory.retailorder.retailcashcollect.ApplyCashCollectionForInvoiceAction
import com.bits.bdfp.inventory.retailorder.retailcashcollect.ListNonPaidRetailInvoiceAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired

class RetailCashCollectController {

    @Autowired
    ListNonPaidRetailInvoiceAction listNonPaidRetailInvoiceAction
    @Autowired
    GetCustomerBalanceAction getCustomerBalanceAction
    @Autowired
    ListCustomerByApplicationUserAction listCustomerByApplicationUserAction
    @Autowired
    ApplyCashCollectionForInvoiceAction applyCashCollectionForInvoiceAction
    @Autowired
    ListCustomerAutoCompleteByApplicationUserAction listCustomerAutoCompleteByApplicationUserAction
    SpringSecurityService  springSecurityService

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def show = {
        render(view: "/retailOrder/cashCollect/show")
    }

    def customerAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("customerId", applicationUser.customerMasterId)
        params.put('applicationUserId', applicationUser.id)
        params.put("categoryId", ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID)
        render listCustomerAutoCompleteByApplicationUserAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.put("customerId", applicationUser.customerMasterId)
        params.put("categoryId", ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID)
        List customerList = new ArrayList()
        customerList = (List) listCustomerByApplicationUserAction.execute(params, null)

        render(view: '/retailOrder/cashCollect/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def listNonPaidRetailInvoice = {
        render listNonPaidRetailInvoiceAction.execute(params, null) as JSON
    }

    def getCustomerBalance = {
        double  balance = (Double) getCustomerBalanceAction.execute(params, null)
        Map result = [balance: balance]
        render result as JSON
    }

    def applyCashCollection = {
        Message message = applyCashCollectionForInvoiceAction.execute(params, null)
        render message as JSON
    }

}
