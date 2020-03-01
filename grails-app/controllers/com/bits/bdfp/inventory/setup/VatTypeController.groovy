package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.setup.vattype.CreateVatTypeAction
import com.bits.bdfp.inventory.setup.vattype.DeleteVatTypeAction
import com.bits.bdfp.inventory.setup.vattype.ListVatTypeAction
import com.bits.bdfp.inventory.setup.vattype.UpdateVatTypeAction
import com.bits.bdfp.inventory.setup.vattype.ReadVatTypeAction
import com.bits.bdfp.inventory.setup.vattype.SearchVatTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class VatTypeController {

    @Autowired
    private CreateVatTypeAction createVatTypeAction
    @Autowired
    private UpdateVatTypeAction updateVatTypeAction
    @Autowired
    private ListVatTypeAction listVatTypeAction
    @Autowired
    private DeleteVatTypeAction deleteVatTypeAction
    @Autowired
    private ReadVatTypeAction readVatTypeAction
    @Autowired
    private SearchVatTypeAction searchVatTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listVatTypeAction.execute(params, null)
        render listVatTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        VatType vatType = new VatType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [vatType: vatType,list: enterpriseList,result:result as JSON])
    }

    def create = {
        VatType vatType = new VatType(params)
        ApplicationUser applicationUser=session?.applicationUser
        VatType vatTypeInstance = createVatTypeAction.preCondition(applicationUser, vatType)
        Message message = null
        if (vatTypeInstance == null) {
            message = createVatTypeAction.getValidationErrorMessage(vatType)
        } else {
            vatTypeInstance = createVatTypeAction.execute(null, vatTypeInstance)
            if (vatTypeInstance) {
                message = createVatTypeAction.getMessage("Vat Type",Message.SUCCESS, createVatTypeAction.SUCCESS_SAVE)
            } else {
                message = createVatTypeAction.getMessage("Vat Type", Message.ERROR, createVatTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readVatTypeAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateVatTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteVatTypeAction.execute(params, null)
        render message as JSON
    }



    def search = {
        VatType vatType = searchVatTypeAction.execute(params.fieldName, params.fieldValue)
        if (vatType) {
            render vatType as JSON
        } else {
            render ''
        }

    }
}
