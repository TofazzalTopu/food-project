package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.setup.poscustomer.CreatePosCustomerAction
import com.bits.bdfp.inventory.setup.poscustomer.DeletePosCustomerAction
import com.bits.bdfp.inventory.setup.poscustomer.ListPosCustomerAction
import com.bits.bdfp.inventory.setup.poscustomer.UpdatePosCustomerAction
import com.bits.bdfp.inventory.setup.poscustomer.ReadPosCustomerAction
import com.bits.bdfp.inventory.setup.poscustomer.SearchPosCustomerAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class PosCustomerController {

    @Autowired
    private CreatePosCustomerAction createPosCustomerAction
    @Autowired
    private UpdatePosCustomerAction updatePosCustomerAction
    @Autowired
    private ListPosCustomerAction listPosCustomerAction
    @Autowired
    private DeletePosCustomerAction deletePosCustomerAction
    @Autowired
    private ReadPosCustomerAction readPosCustomerAction
    @Autowired
    private SearchPosCustomerAction searchPosCustomerAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listPosCustomerAction.execute(params, null)
        render listPosCustomerAction.postCondition(null, list) as JSON
    }

    def show = {
        PosCustomer posCustomer = new PosCustomer()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [posCustomer: posCustomer,list: enterpriseList,result:result as JSON])
    }

    def create = {
        PosCustomer posCustomer = new PosCustomer(params)
        ApplicationUser applicationUser=session?.applicationUser
        PosCustomer posCustomerInstance = createPosCustomerAction.preCondition(applicationUser, posCustomer)
        Message message = null
        if (posCustomerInstance == null) {
            message = createPosCustomerAction.getValidationErrorMessage(posCustomer)
        } else {
            posCustomerInstance = createPosCustomerAction.execute(null, posCustomerInstance)
            if (posCustomerInstance) {
                message = createPosCustomerAction.getMessage(posCustomerInstance, Message.SUCCESS,createPosCustomerAction.SUCCESS_SAVE)
            } else {
                message = createPosCustomerAction.getMessage(posCustomer,Message.ERROR, createPosCustomerAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result =  readPosCustomerAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updatePosCustomerAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deletePosCustomerAction.execute(params, null)
        render message as JSON
    }



    def search = {
        PosCustomer posCustomer = searchPosCustomerAction.execute(params.fieldName, params.fieldValue)
        if (posCustomer) {
            render posCustomer as JSON
        } else {
            render ''
        }

    }
}
