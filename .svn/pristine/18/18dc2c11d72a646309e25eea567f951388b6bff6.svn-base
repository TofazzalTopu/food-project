package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.productpricingtype.CreateProductPricingTypeAction
import com.bits.bdfp.inventory.product.productpricingtype.DeleteProductPricingTypeAction
import com.bits.bdfp.inventory.product.productpricingtype.ListProductPricingTypeAction
import com.bits.bdfp.inventory.product.productpricingtype.UpdateProductPricingTypeAction
import com.bits.bdfp.inventory.product.productpricingtype.ReadProductPricingTypeAction
import com.bits.bdfp.inventory.product.productpricingtype.SearchProductPricingTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ProductPricingTypeController {

    @Autowired
    private CreateProductPricingTypeAction createProductPricingTypeAction
    @Autowired
    private UpdateProductPricingTypeAction updateProductPricingTypeAction
    @Autowired
    private ListProductPricingTypeAction listProductPricingTypeAction
    @Autowired
    private DeleteProductPricingTypeAction deleteProductPricingTypeAction
    @Autowired
    private ReadProductPricingTypeAction readProductPricingTypeAction
    @Autowired
    private SearchProductPricingTypeAction searchProductPricingTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listProductPricingTypeAction.execute(params, null)
        render listProductPricingTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        ProductPricingType productPricingType = new ProductPricingType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [productPricingType: productPricingType, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        ProductPricingType productPricingType = new ProductPricingType(params)
        ProductPricingType productPricingTypeInstance = createProductPricingTypeAction.preCondition(applicationUser, productPricingType)
        Message message = null
        if (productPricingTypeInstance == null) {
            message = createProductPricingTypeAction.getValidationErrorMessage(productPricingType)
        } else {
            productPricingTypeInstance = createProductPricingTypeAction.execute(null, productPricingTypeInstance)
            if (productPricingTypeInstance) {
                message = createProductPricingTypeAction.getMessage(productPricingTypeInstance,Message.SUCCESS, createProductPricingTypeAction.SUCCESS_SAVE)
            } else {
                message = createProductPricingTypeAction.getMessage(productPricingType,Message.ERROR, createProductPricingTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readProductPricingTypeAction.execute(params, null)
        render  result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateProductPricingTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteProductPricingTypeAction.execute(params, null)
        render message as JSON

    }


    def search = {
        ProductPricingType productPricingType = searchProductPricingTypeAction.execute(params.fieldName, params.fieldValue)
        if (productPricingType) {
            render productPricingType as JSON
        } else {
            render ''
        }

    }
}
