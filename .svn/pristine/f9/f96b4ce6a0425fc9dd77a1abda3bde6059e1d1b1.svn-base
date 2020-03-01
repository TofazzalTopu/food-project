package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.pricingcategory.CreatePricingCategoryAction
import com.bits.bdfp.inventory.product.pricingcategory.DeletePricingCategoryAction
import com.bits.bdfp.inventory.product.pricingcategory.ListPricingCategoryAction
import com.bits.bdfp.inventory.product.pricingcategory.UpdatePricingCategoryAction
import com.bits.bdfp.inventory.product.pricingcategory.ReadPricingCategoryAction
import com.bits.bdfp.inventory.product.pricingcategory.SearchPricingCategoryAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class PricingCategoryController {

    @Autowired
    private CreatePricingCategoryAction createPricingCategoryAction
    @Autowired
    private UpdatePricingCategoryAction updatePricingCategoryAction
    @Autowired
    private ListPricingCategoryAction listPricingCategoryAction
    @Autowired
    private DeletePricingCategoryAction deletePricingCategoryAction
    @Autowired
    private ReadPricingCategoryAction readPricingCategoryAction
    @Autowired
    private SearchPricingCategoryAction searchPricingCategoryAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listPricingCategoryAction.execute(params, null)
        render listPricingCategoryAction.postCondition(null, list) as JSON
    }

    def show = {
        PricingCategory pricingCategory = new PricingCategory()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [pricingCategory: pricingCategory, list: enterpriseList,result:result as JSON])
    }

    def create = {
        PricingCategory pricingCategory = new PricingCategory(params)
        ApplicationUser applicationUser=session?.applicationUser
        PricingCategory pricingCategoryInstance = createPricingCategoryAction.preCondition(applicationUser, pricingCategory)
        Message message = null
        if (pricingCategoryInstance == null) {
            message = createPricingCategoryAction.getValidationErrorMessage(pricingCategory)
        } else {
            pricingCategoryInstance = createPricingCategoryAction.execute(null, pricingCategoryInstance)
            if (pricingCategoryInstance) {
                message = createPricingCategoryAction.getMessage(pricingCategoryInstance,Message.SUCCESS, createPricingCategoryAction.SUCCESS_SAVE)
            } else {
                message = createPricingCategoryAction.getMessage(pricingCategory,Message.ERROR, createPricingCategoryAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readPricingCategoryAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updatePricingCategoryAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deletePricingCategoryAction.execute(params, null)
        render message as JSON

    }


    def search = {
        PricingCategory pricingCategory = searchPricingCategoryAction.execute(params.fieldName, params.fieldValue)
        if (pricingCategory) {
            render pricingCategory as JSON
        } else {
            render ''
        }

    }
}
