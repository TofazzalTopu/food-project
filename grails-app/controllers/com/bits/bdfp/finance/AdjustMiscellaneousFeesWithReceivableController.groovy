package com.bits.bdfp.finance

import com.bits.bdfp.finance.customerpayment.CreateAdjustMiscellaneousFeesWithReceivableAction
import com.bits.bdfp.finance.customerpayment.UtilAdjustMiscellaneousFeesWithReceivableAction
import com.bits.bdfp.finance.customerpayment.WithdrawAdjustMiscellaneousFeesWithReceivableAction
import com.bits.bdfp.inventory.warehouse.miscellaneoustransactions.UtilMiscellaneousTransactionsAction
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class AdjustMiscellaneousFeesWithReceivableController {
    @Autowired
    UtilAdjustMiscellaneousFeesWithReceivableAction utilAdjustMiscellaneousFeesWithReceivableAction
    @Autowired
    CreateAdjustMiscellaneousFeesWithReceivableAction createAdjustMiscellaneousFeesWithReceivableAction
    @Autowired
    WithdrawAdjustMiscellaneousFeesWithReceivableAction withdrawAdjustMiscellaneousFeesWithReceivableAction
    @Autowired
    UtilMiscellaneousTransactionsAction utilMiscellaneousTransactionsAction

    def show = {
        render (view: "show")
    }

    def getAllCustomerList = {
        List customerList =  utilMiscellaneousTransactionsAction.getAllCustomerListByKey(params.searchKey)
        if(params.searchKey){
            render customerList as JSON
        }else{
            render(view: 'popUpCustomerList', model: [aaData: customerList as JSON])
        }
    }

    def getCustomerInfoByCustomerId = {
        render utilAdjustMiscellaneousFeesWithReceivableAction.getCustomerInfoByCustomerId(Long.parseLong(params.customerId)) as JSON
    }

    def adjust = {
        ApplicationUser applicationUser = session?.applicationUser
        render createAdjustMiscellaneousFeesWithReceivableAction.execute(params,applicationUser) as JSON
    }

    def withdraw = {
        ApplicationUser applicationUser = session?.applicationUser
        render withdrawAdjustMiscellaneousFeesWithReceivableAction.execute(params,applicationUser) as JSON
    }
}
