package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.setup.discounttype.CreateDiscountTypeAction
import com.bits.bdfp.inventory.setup.discounttype.DeleteDiscountTypeAction
import com.bits.bdfp.inventory.setup.discounttype.ListDiscountTypeAction
import com.bits.bdfp.inventory.setup.discounttype.UpdateDiscountTypeAction
import com.bits.bdfp.inventory.setup.discounttype.ReadDiscountTypeAction
import com.bits.bdfp.inventory.setup.discounttype.SearchDiscountTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DiscountTypeController {

    @Autowired
    private CreateDiscountTypeAction createDiscountTypeAction
    @Autowired
    private UpdateDiscountTypeAction updateDiscountTypeAction
    @Autowired
    private ListDiscountTypeAction listDiscountTypeAction
    @Autowired
    private DeleteDiscountTypeAction deleteDiscountTypeAction
    @Autowired
    private ReadDiscountTypeAction readDiscountTypeAction
    @Autowired
    private SearchDiscountTypeAction searchDiscountTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDiscountTypeAction.execute(params, null)
        render listDiscountTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        DiscountType discountType = new DiscountType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [discountType: discountType,list: enterpriseList,result:result as JSON])
    }

    def create = {
        DiscountType discountType = new DiscountType(params)
        ApplicationUser applicationUser=session?.applicationUser
        DiscountType discountTypeInstance = createDiscountTypeAction.preCondition(applicationUser, discountType)
        Message message = null
        if (discountTypeInstance == null) {
            message = createDiscountTypeAction.getValidationErrorMessage(discountType)
        } else {
            discountTypeInstance = createDiscountTypeAction.execute(null, discountTypeInstance)
            if (discountTypeInstance) {
                message = createDiscountTypeAction.getMessage("Discount Type",Message.SUCCESS, createDiscountTypeAction.SUCCESS_SAVE)
            } else {
                message = createDiscountTypeAction.getMessage("Discount Type",Message.ERROR, createDiscountTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readDiscountTypeAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateDiscountTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteDiscountTypeAction.execute(params, null)
        render message as JSON
    }


    def search = {
        DiscountType discountType = searchDiscountTypeAction.execute(params.fieldName, params.fieldValue)
        if (discountType) {
            render discountType as JSON
        } else {
            render ''
        }

    }
}
