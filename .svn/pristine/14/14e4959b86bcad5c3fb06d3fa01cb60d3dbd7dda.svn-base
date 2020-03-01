package com.bits.bdfp.customer

import com.bits.bdfp.customer.customerasset.CreateAssetLendingAction
import com.bits.bdfp.customer.customerasset.CreateAssetRecoveryAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class CustomerAssetLendingRecoveryController {

    @Autowired
    CreateAssetLendingAction createAssetLendingAction
    @Autowired
    CreateAssetRecoveryAction createAssetRecoveryAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    def show = {
        render(template: "show")
    }
    def createNew = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createAssetLendingAction.execute(params, applicationUser)
        render message as JSON

    }
    def showRecovery = {
        render(template: "showRecovery")
    }
    def createNewRecovery = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createAssetRecoveryAction.execute(params, applicationUser)
        render message as JSON
    }

}
