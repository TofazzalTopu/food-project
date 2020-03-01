package com.bits.bdfp.inventory.product.externalproduct

import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class ExternalProductController {

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    @Autowired
    CreateExternalProductAction createExternalProductAction
    @Autowired
    ListExternalProductAction listExternalProductAction
    @Autowired
    ReadExternalProductAction readExternalProductAction
    @Autowired
    UpdateExternalProductAction updateExternalProductAction

    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    def show = {
        ExternalProduct externalProduct = new ExternalProduct()
        ApplicationUser applicationUser = session?.applicationUser
        Map result = [:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size() > 0) {
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(view: "show", model: [externalProduct: externalProduct, list: enterpriseList, result: result as JSON])
    }

    def list = {
        List list = listExternalProductAction.execute(params, null)
        render listExternalProductAction.postCondition(null, list) as JSON
    }
    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        ExternalProduct externalProduct = new ExternalProduct(params)
        ExternalProduct externalProductInstance = createExternalProductAction.preCondition(applicationUser, externalProduct)
        Message message = null
        if (externalProductInstance == null) {
            message = createExternalProductAction.getValidationErrorMessage(externalProduct)
        } else {
            externalProductInstance = createExternalProductAction.execute(null, externalProductInstance)
            if (externalProductInstance) {
                message = createExternalProductAction.getMessage(externalProductInstance, Message.SUCCESS, createExternalProductAction.SUCCESS_SAVE)
            } else {
                message = createExternalProductAction.getMessage(externalProductInstance, Message.ERROR, createExternalProductAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }
    def edit = {
        Map result = readExternalProductAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateExternalProductAction.execute(params, applicationUser)
        render message as JSON
    }
}
