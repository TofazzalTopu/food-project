package com.bits.bdfp.inventory.product.externalproduct

import com.bits.bdfp.inventory.product.externalproductstock.CreateExternalProductStockAction
import com.bits.bdfp.inventory.product.externalproductstock.ListExternalProductStockAction
import com.bits.bdfp.inventory.product.externalproductstock.ReadExternalProductStockAction
import com.bits.bdfp.inventory.product.externalproductstock.UpdateExternalProductStockAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class ExternalProductStockController {

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    @Autowired
    CreateExternalProductStockAction createExternalProductStockAction
    @Autowired
    ListExternalProductStockAction listExternalProductStockAction
    @Autowired
    ReadExternalProductStockAction readExternalProductStockAction
    @Autowired
    UpdateExternalProductStockAction updateExternalProductStockAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map enterpriseList = ["results": list, "total": list.size()]
        render(view: "show", model: [externalProductList: ExternalProduct.findByIsActive(true),list:list, enterpriseList : enterpriseList as JSON])
    }
    def list = {
        List list = listExternalProductStockAction.execute(params, null)
        render listExternalProductStockAction.postCondition(null, list) as JSON
    }
    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        ExternalProductStock externalProductStock = new ExternalProductStock(params)
        ExternalProductStock externalProductInstance = createExternalProductStockAction.preCondition(applicationUser, externalProductStock)
        Message message = null
        if (externalProductInstance == null) {
            message = createExternalProductStockAction.getValidationErrorMessage(externalProductStock)
        } else {
            externalProductInstance = createExternalProductStockAction.execute(null, externalProductInstance)
            if (externalProductInstance) {
                message = createExternalProductStockAction.getMessage(externalProductInstance, Message.SUCCESS, createExternalProductStockAction.SUCCESS_SAVE)
            } else {
                message = createExternalProductStockAction.getMessage(externalProductInstance, Message.ERROR, createExternalProductStockAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }
    def edit = {
        Map result = readExternalProductStockAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateExternalProductStockAction.execute(params, applicationUser)
        render message as JSON
    }
}
