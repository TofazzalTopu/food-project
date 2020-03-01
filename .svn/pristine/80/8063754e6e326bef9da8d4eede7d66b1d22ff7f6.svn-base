package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.CreateSecondaryDemandOrderDetailsAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.DeleteSecondaryDemandOrderDetailsAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.ListSecondaryDemandOrderDetailsAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.UpdateOrderQuantityAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.UpdateSecondaryDemandOrderDetailsAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.ReadSecondaryDemandOrderDetailsAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.SearchSecondaryDemandOrderDetailsAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class SecondaryDemandOrderDetailsController {

    @Autowired
    private CreateSecondaryDemandOrderDetailsAction createSecondaryDemandOrderDetailsAction
    @Autowired
    private UpdateSecondaryDemandOrderDetailsAction updateSecondaryDemandOrderDetailsAction
    @Autowired
    private ListSecondaryDemandOrderDetailsAction listSecondaryDemandOrderDetailsAction
    @Autowired
    private DeleteSecondaryDemandOrderDetailsAction deleteSecondaryDemandOrderDetailsAction
    @Autowired
    private ReadSecondaryDemandOrderDetailsAction readSecondaryDemandOrderDetailsAction
    @Autowired
    private SearchSecondaryDemandOrderDetailsAction searchSecondaryDemandOrderDetailsAction
    @Autowired
    UpdateOrderQuantityAction updateOrderQuantityAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        ApplicationUser applicationUser = session?.applicationUser
        render listSecondaryDemandOrderDetailsAction.execute(params,applicationUser) as JSON

    }

    def show = {
      SecondaryDemandOrderDetails secondaryDemandOrderDetails = new SecondaryDemandOrderDetails()
      render(template: "show", model:[secondaryDemandOrderDetails:secondaryDemandOrderDetails])
    }


    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = createSecondaryDemandOrderDetailsAction.execute(params, applicationUser)
        render message as JSON
    }


    def edit = {
      render readSecondaryDemandOrderDetailsAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateSecondaryDemandOrderDetailsAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteSecondaryDemandOrderDetailsAction.execute(params, null)
        render message as JSON
    }
    def updateDetails={
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateOrderQuantityAction.execute(params, applicationUser)
        render message as JSON
    }

}
