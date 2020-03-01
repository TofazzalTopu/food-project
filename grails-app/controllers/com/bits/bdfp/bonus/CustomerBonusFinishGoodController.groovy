package com.bits.bdfp.bonus

import com.bits.bdfp.bonus.customerbonusfinishgood.CheckBonusEligibilityAction
import com.bits.bdfp.bonus.customerbonusfinishgood.ConfirmBonusAction
import com.bits.bdfp.bonus.customerbonusfinishgood.CreateCustomerBonusFinishGoodAction
import com.bits.bdfp.bonus.customerbonusfinishgood.DeleteCustomerBonusFinishGoodAction
import com.bits.bdfp.bonus.customerbonusfinishgood.ListCheckBonusEligibilityAction
import com.bits.bdfp.bonus.customerbonusfinishgood.ListCustomerBonusFinishGoodAction
import com.bits.bdfp.bonus.customerbonusfinishgood.UpdateCustomerBonusFinishGoodAction
import com.bits.bdfp.bonus.customerbonusfinishgood.ReadCustomerBonusFinishGoodAction
import com.bits.bdfp.bonus.customerbonusfinishgood.SearchCustomerBonusFinishGoodAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListForUpdateDeliveryDateAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerBonusFinishGoodController {

    @Autowired
    private CreateCustomerBonusFinishGoodAction createCustomerBonusFinishGoodAction
    @Autowired
    private UpdateCustomerBonusFinishGoodAction updateCustomerBonusFinishGoodAction
    @Autowired
    private ListCustomerBonusFinishGoodAction listCustomerBonusFinishGoodAction
    @Autowired
    private DeleteCustomerBonusFinishGoodAction deleteCustomerBonusFinishGoodAction
    @Autowired
    private ReadCustomerBonusFinishGoodAction readCustomerBonusFinishGoodAction
    @Autowired
    private SearchCustomerBonusFinishGoodAction searchCustomerBonusFinishGoodAction
    @Autowired
    ListCheckBonusEligibilityAction listCheckBonusEligibilityAction
    @Autowired
    CheckBonusEligibilityAction checkBonusEligibilityAction
    @Autowired
    ConfirmBonusAction confirmBonusAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerBonusFinishGoodAction.execute(params, null)
        render listCustomerBonusFinishGoodAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerBonusFinishGood customerBonusFinishGood = new CustomerBonusFinishGood()
        render(template: "show", model: [customerBonusFinishGood: customerBonusFinishGood])
    }

    def create = {
        CustomerBonusFinishGood customerBonusFinishGood = new CustomerBonusFinishGood(params)
        CustomerBonusFinishGood customerBonusFinishGoodInstance = createCustomerBonusFinishGoodAction.preCondition(null, customerBonusFinishGood)
        Message message = null
        if (customerBonusFinishGoodInstance == null) {
            message = createCustomerBonusFinishGoodAction.getValidationErrorMessageForUI(customerBonusFinishGood)
        } else {
            customerBonusFinishGoodInstance = createCustomerBonusFinishGoodAction.execute(null, customerBonusFinishGoodInstance)
            if (customerBonusFinishGoodInstance) {
                message = createCustomerBonusFinishGoodAction.getSuccessMessageForUI(customerBonusFinishGoodInstance, createCustomerBonusFinishGoodAction.SUCCESS_SAVE)
            } else {
                message = createCustomerBonusFinishGoodAction.getErrorMessageForUI(customerBonusFinishGood, createCustomerBonusFinishGoodAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readCustomerBonusFinishGoodAction.execute(params, null) as JSON
    }

    def update = {
        CustomerBonusFinishGood customerBonusFinishGood = new CustomerBonusFinishGood(params)
        Object object = updateCustomerBonusFinishGoodAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateCustomerBonusFinishGoodAction.getValidationErrorMessageForUI(customerBonusFinishGood)
        } else {
            int noOfRows = (int) updateCustomerBonusFinishGoodAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateCustomerBonusFinishGoodAction.getSuccessMessageForUI(customerBonusFinishGood, updateCustomerBonusFinishGoodAction.SUCCESS_UPDATE)
            } else {
                message = updateCustomerBonusFinishGoodAction.getErrorMessageForUI(customerBonusFinishGood, updateCustomerBonusFinishGoodAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        CustomerBonusFinishGood customerBonusFinishGood = deleteCustomerBonusFinishGoodAction.execute(params, null);
        Message message = null
        if (customerBonusFinishGood) {
            int rowCount = (int) deleteCustomerBonusFinishGoodAction.preCondition(null, customerBonusFinishGood);
            if (rowCount > 0) {
                message = deleteCustomerBonusFinishGoodAction.getSuccessMessageForUI(customerBonusFinishGood, deleteCustomerBonusFinishGoodAction.SUCCESS_DELETE);
            } else {
                message = deleteCustomerBonusFinishGoodAction.getErrorMessageForUI(customerBonusFinishGood, deleteCustomerBonusFinishGoodAction.FAIL_DELETE);
            }
        } else {
            message = deleteCustomerBonusFinishGoodAction.getErrorMessageForUI(customerBonusFinishGood, deleteCustomerBonusFinishGoodAction.ALREADY_DELETED);
        }
        render message as JSON;
    }


    def showBonusEligibility = {
        render(view: 'showBonusEligibility')
    }

    def listForCheckEligibility = {
        ApplicationUser applicationUser = session?.applicationUser
        render listCheckBonusEligibilityAction.execute(params, applicationUser) as JSON
    }
    def checkEligibility = {
        Map map = checkBonusEligibilityAction.execute(params,null)
        render map as JSON
    }

    def popupViewEligibleListPanel={
        Map map = checkBonusEligibilityAction.execute(params,null)
        render(view: 'popupViewEligibleListPanel', model: [aaData: map.eligibilityList as JSON])

    }

    def confirmBonus = {
        Message message = null
        ApplicationUser applicationUser = session?.applicationUser
        message = confirmBonusAction.execute(params, applicationUser)
        render message as JSON
    }

}
