package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.producttype.CreateProductTypeAction
import com.bits.bdfp.inventory.product.producttype.DeleteProductTypeAction
import com.bits.bdfp.inventory.product.producttype.ListProductTypeAction
import com.bits.bdfp.inventory.product.producttype.UpdateProductTypeAction
import com.bits.bdfp.inventory.product.producttype.ReadProductTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ProductTypeController {

    @Autowired
    private CreateProductTypeAction createProductTypeAction
    @Autowired
    private UpdateProductTypeAction updateProductTypeAction
    @Autowired
    private ListProductTypeAction listProductTypeAction
    @Autowired
    private DeleteProductTypeAction deleteProductTypeAction
    @Autowired
    private ReadProductTypeAction readProductTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listProductTypeAction.execute(params, null)
        render listProductTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        ProductType productType = new ProductType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [productType: productType, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        ProductType productType = new ProductType(params)
        ProductType productTypeInstance = createProductTypeAction.preCondition(applicationUser, productType)
        Message message = null
        if (productTypeInstance == null) {
            message = createProductTypeAction.getValidationErrorMessage(productType)
        } else {
            productTypeInstance = createProductTypeAction.execute(null, productTypeInstance)
            if (productTypeInstance) {
                message = createProductTypeAction.getMessage("Product Type", Message.SUCCESS, createProductTypeAction.SUCCESS_SAVE)
            } else {
                message = createProductTypeAction.getMessage("Product Type", Message.ERROR, createProductTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readProductTypeAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateProductTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteProductTypeAction.execute(params, null)
        render message as JSON

    }



}
