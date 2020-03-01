package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.masterproduct.CreateMasterProductAction
import com.bits.bdfp.inventory.product.masterproduct.DeleteMasterProductAction
import com.bits.bdfp.inventory.product.masterproduct.ListMasterProductAction
import com.bits.bdfp.inventory.product.masterproduct.UpdateMasterProductAction
import com.bits.bdfp.inventory.product.masterproduct.ReadMasterProductAction
import com.bits.bdfp.inventory.product.masterproduct.SearchMasterProductAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class MasterProductController {

    @Autowired
    private CreateMasterProductAction createMasterProductAction
    @Autowired
    private UpdateMasterProductAction updateMasterProductAction
    @Autowired
    private ListMasterProductAction listMasterProductAction
    @Autowired
    private DeleteMasterProductAction deleteMasterProductAction
    @Autowired
    private ReadMasterProductAction readMasterProductAction
    @Autowired
    private SearchMasterProductAction searchMasterProductAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listMasterProductAction.execute(params, null)
        render listMasterProductAction.postCondition(null, list) as JSON
    }

    def show = {
        MasterProduct masterProduct = new MasterProduct()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [masterProduct: masterProduct, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        MasterProduct masterProduct = new MasterProduct(params)
        MasterProduct masterProductInstance = createMasterProductAction.preCondition(applicationUser, masterProduct)
        Message message = null
        if (masterProductInstance == null) {
            message = createMasterProductAction.getValidationErrorMessage(masterProduct)
        } else {
            masterProductInstance = createMasterProductAction.execute(null, masterProductInstance)
            if (masterProductInstance) {
                message = createMasterProductAction.getMessage(masterProductInstance,Message.SUCCESS, createMasterProductAction.SUCCESS_SAVE)
            } else {
                message = createMasterProductAction.getMessage(masterProduct,Message.ERROR, createMasterProductAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readMasterProductAction.execute(params, null)
        render  result as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateMasterProductAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteMasterProductAction.execute(params, null)
        render message as JSON

    }


    def search = {
        MasterProduct masterProduct = searchMasterProductAction.execute(params.fieldName, params.fieldValue)
        if (masterProduct) {
            render masterProduct as JSON
        } else {
            render ''
        }

    }
}
