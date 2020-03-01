package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.setup.chargetype.CreateChargeTypeAction
import com.bits.bdfp.inventory.setup.chargetype.DeleteChargeTypeAction
import com.bits.bdfp.inventory.setup.chargetype.ListChargeTypeAction
import com.bits.bdfp.inventory.setup.chargetype.UpdateChargeTypeAction
import com.bits.bdfp.inventory.setup.chargetype.ReadChargeTypeAction
import com.bits.bdfp.inventory.setup.chargetype.SearchChargeTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ChargeTypeController {

    @Autowired
    private CreateChargeTypeAction createChargeTypeAction
    @Autowired
    private UpdateChargeTypeAction updateChargeTypeAction
    @Autowired
    private ListChargeTypeAction listChargeTypeAction
    @Autowired
    private DeleteChargeTypeAction deleteChargeTypeAction
    @Autowired
    private ReadChargeTypeAction readChargeTypeAction
    @Autowired
    private SearchChargeTypeAction searchChargeTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listChargeTypeAction.execute(params, null)
        render listChargeTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        ChargeType chargeType = new ChargeType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result = [:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size() > 0) {
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [chargeType: chargeType, list: enterpriseList, result: result as JSON])
    }

    def create = {
        ChargeType chargeType = new ChargeType(params)
        ApplicationUser applicationUser = session?.applicationUser
        ChargeType chargeTypeInstance = createChargeTypeAction.preCondition(applicationUser, chargeType)
        Message message = null
        if (chargeTypeInstance == null) {
            message = createChargeTypeAction.getValidationErrorMessage(chargeType)
        } else {
            chargeTypeInstance = createChargeTypeAction.execute(null, chargeTypeInstance)
            if (chargeTypeInstance) {
                message = createChargeTypeAction.getMessage(chargeTypeInstance, Message.SUCCESS, createChargeTypeAction.SUCCESS_SAVE)
            } else {
                message = createChargeTypeAction.getMessage(chargeType, Message.ERROR, createChargeTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readChargeTypeAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateChargeTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteChargeTypeAction.execute(params, null)
        render message as JSON
    }


    def search = {
        ChargeType chargeType = searchChargeTypeAction.execute(params.fieldName, params.fieldValue)
        if (chargeType) {
            render chargeType as JSON
        } else {
            render ''
        }

    }
}
