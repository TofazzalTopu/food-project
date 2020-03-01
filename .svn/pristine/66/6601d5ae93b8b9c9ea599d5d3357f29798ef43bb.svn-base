package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.productcategory.CreateProductCategoryAction
import com.bits.bdfp.inventory.product.productcategory.DeleteProductCategoryAction
import com.bits.bdfp.inventory.product.productcategory.ListProductCategoryAction
import com.bits.bdfp.inventory.product.productcategory.UpdateProductCategoryAction
import com.bits.bdfp.inventory.product.productcategory.ReadProductCategoryAction
import com.bits.bdfp.inventory.product.productcategory.SearchProductCategoryAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ProductCategoryController {

    @Autowired
    private CreateProductCategoryAction createProductCategoryAction
    @Autowired
    private UpdateProductCategoryAction updateProductCategoryAction
    @Autowired
    private ListProductCategoryAction listProductCategoryAction
    @Autowired
    private DeleteProductCategoryAction deleteProductCategoryAction
    @Autowired
    private ReadProductCategoryAction readProductCategoryAction
    @Autowired
    private SearchProductCategoryAction searchProductCategoryAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listProductCategoryAction.execute(params, null)
        render listProductCategoryAction.postCondition(null, list) as JSON
    }

    def show = {
        ProductCategory productCategory = new ProductCategory()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [productCategory: productCategory, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        ProductCategory productCategory = new ProductCategory(params)
        ProductCategory productCategoryInstance = createProductCategoryAction.preCondition(applicationUser, productCategory)
        Message message = null
        if (productCategoryInstance == null) {
            message = createProductCategoryAction.getValidationErrorMessage(productCategory)
        } else {
            productCategoryInstance = createProductCategoryAction.execute(null, productCategoryInstance)
            if (productCategoryInstance) {
                message = createProductCategoryAction.getMessage("Product Category",Message.SUCCESS, createProductCategoryAction.SUCCESS_SAVE)
            } else {
                message = createProductCategoryAction.getMessage("Product Category",Message.ERROR, createProductCategoryAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readProductCategoryAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateProductCategoryAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteProductCategoryAction.execute(params, null)
        render message as JSON

    }




    def search = {
        ProductCategory productCategory = searchProductCategoryAction.execute(params.fieldName, params.fieldValue)
        if (productCategory) {
            render productCategory as JSON
        } else {
            render ''
        }

    }
}
