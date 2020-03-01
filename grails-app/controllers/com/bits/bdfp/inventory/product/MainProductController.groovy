package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.mainproduct.CreateMainProductAction
import com.bits.bdfp.inventory.product.mainproduct.DeleteMainProductAction
import com.bits.bdfp.inventory.product.mainproduct.ListMainProductAction
import com.bits.bdfp.inventory.product.mainproduct.UpdateMainProductAction
import com.bits.bdfp.inventory.product.mainproduct.ReadMainProductAction
import com.bits.bdfp.inventory.product.mainproduct.SearchMainProductAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class MainProductController {

    @Autowired
    private CreateMainProductAction createMainProductAction
    @Autowired
    private UpdateMainProductAction updateMainProductAction
    @Autowired
    private ListMainProductAction listMainProductAction
    @Autowired
    private DeleteMainProductAction deleteMainProductAction
    @Autowired
    private ReadMainProductAction readMainProductAction
    @Autowired
    private SearchMainProductAction searchMainProductAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listMainProductAction.execute(params, null)
        render listMainProductAction.postCondition(null, list) as JSON
    }

    def show = {
        MainProduct mainProduct = new MainProduct()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [mainProduct: mainProduct, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        MainProduct mainProduct = new MainProduct(params)
        MainProduct mainProductInstance = createMainProductAction.preCondition(applicationUser, mainProduct)
        Message message = null
        if (mainProductInstance == null) {
            message = createMainProductAction.getValidationErrorMessage(mainProduct)
        } else {
            mainProductInstance = createMainProductAction.execute(null, mainProductInstance)
            if (mainProductInstance) {
                message = createMainProductAction.getMessage(mainProductInstance, Message.SUCCESS, "Main Product saved successfully")
            } else {
                message = createMainProductAction.getMessage(mainProduct, Message.ERROR, "Main Product not saved")
            }
        }
        render message as JSON
    }

    def edit = {
        render readMainProductAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateMainProductAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteMainProductAction.execute(params, null)
        render message as JSON
    }

    def search = {
        MainProduct mainProduct = searchMainProductAction.execute(params.fieldName, params.fieldValue)
        if (mainProduct) {
            render mainProduct as JSON
        } else {
            render ''
        }

    }
}
