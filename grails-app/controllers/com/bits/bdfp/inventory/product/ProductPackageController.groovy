package com.bits.bdfp.inventory.product
import com.bits.bdfp.inventory.product.packagetype.ListPackageByEnterpriseAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductForPackageByEnterpriseAction
import com.bits.bdfp.inventory.product.productpackage.CreateProductPackageAction
import com.bits.bdfp.inventory.product.productpackage.DeleteProductPackageAction
import com.bits.bdfp.inventory.product.productpackage.ListProductPackageAction
import com.bits.bdfp.inventory.product.productpackage.UpdateProductPackageAction
import com.bits.bdfp.inventory.product.productpackage.ReadProductPackageAction
import com.bits.bdfp.inventory.product.productpackage.SearchProductPackageAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.settings.measureunitconfiguration.ListMeasurementUnitByEnterpriseAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ProductPackageController {

    @Autowired
    private CreateProductPackageAction createProductPackageAction
    @Autowired
    private UpdateProductPackageAction updateProductPackageAction
    @Autowired
    private ListProductPackageAction listProductPackageAction
    @Autowired
    private DeleteProductPackageAction deleteProductPackageAction
    @Autowired
    private ReadProductPackageAction readProductPackageAction
    @Autowired
    private SearchProductPackageAction searchProductPackageAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListProductForPackageByEnterpriseAction listProductForPackageByEnterpriseAction
    @Autowired
    ListPackageByEnterpriseAction listPackageByEnterpriseAction
    @Autowired
    ListMeasurementUnitByEnterpriseAction listMeasurementUnitByEnterpriseAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listProductPackageAction.execute(params, null)
        render listProductPackageAction.postCondition(null, list) as JSON
    }

    def show = {
        ProductPackage productPackage = new ProductPackage()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [productPackage: productPackage,list: enterpriseList,result:result as JSON])
    }

    def create = {
        ProductPackage productPackage = new ProductPackage(params)
        ApplicationUser applicationUser=session?.applicationUser
        ProductPackage productPackageInstance = createProductPackageAction.preCondition(applicationUser, productPackage)
        Message message = null
        if (productPackageInstance == null) {
            message = createProductPackageAction.getValidationErrorMessage(productPackage)
        } else {
            productPackageInstance = createProductPackageAction.execute(null, productPackageInstance)
            if (productPackageInstance) {
                message = createProductPackageAction.getMessage(productPackageInstance,Message.SUCCESS, createProductPackageAction.SUCCESS_SAVE)
            } else {
                message = createProductPackageAction.getMessage(productPackage,Message.ERROR, createProductPackageAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result= readProductPackageAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateProductPackageAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteProductPackageAction.execute(params, null)
        render message as JSON
    }



    def search = {
        ProductPackage productPackage = searchProductPackageAction.execute(params.fieldName, params.fieldValue)
        if (productPackage) {
            render productPackage as JSON
        } else {
            render ''
        }

    }

    def listProduct = {
        render listProductForPackageByEnterpriseAction.execute(params,null) as JSON
    }

    def popupProductListPanel = {
        List productList = new ArrayList()
        productList = (List) listProductForPackageByEnterpriseAction.execute(params, null)
        render(view: 'popupProductListForPackage', model: [aaData: productList as JSON])
    }

    def listPackageType={
        List list= listPackageByEnterpriseAction.execute(params,null)
        render list as JSON
    }

    def listMeasurementUnit={
        List list= listMeasurementUnitByEnterpriseAction.execute(params,null)
        render list as JSON
    }
}
