package com.bits.bdfp.customer

import com.bits.bdfp.customer.customerAssetLending.ListCustomerAssetLendingAction
import com.bits.bdfp.customer.customerAssetLending.ListCustomerAssetRecoveryAction
import com.bits.bdfp.customer.customerAssetLending.LoadCustomerCategoryAction
import com.bits.bdfp.customer.customerasset.CreateAssetLendingAction
import com.bits.bdfp.customer.customerasset.CreateAssetRecoveryAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction


import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerAssetLendingController {

    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListCustomerAssetLendingAction listCustomerAssetLendingAction
    @Autowired
    CreateAssetLendingAction createAssetLendingAction
    @Autowired
    CreateAssetRecoveryAction createAssetRecoveryAction
    @Autowired
    ListCustomerAssetRecoveryAction listCustomerAssetRecoveryAction
    @Autowired
    LoadCustomerCategoryAction loadCustomerCategoryAction


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        ApplicationUser applicationUser = session?.applicationUser
        Object objList = listCustomerAssetLendingAction.execute(params, applicationUser)
        render listCustomerAssetLendingAction.postCondition(objList, null) as JSON
    }

    def listRecovery = {
        ApplicationUser applicationUser = session?.applicationUser
        Object objList = listCustomerAssetRecoveryAction.execute(params, applicationUser)
        render listCustomerAssetLendingAction.postCondition(objList, null) as JSON
    }

    def show = {
        AssetLending assetLending = new AssetLending()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [assetLending: assetLending, list: enterpriseList, result: result as JSON])

    }

    def createNew = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createAssetLendingAction.execute(params, applicationUser)
        render message as JSON

    }
    def createNewRecovery = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createAssetRecoveryAction.execute(params, applicationUser)
        render message as JSON
    }

    def getNetReceivable={

            Object object = listCustomerAssetLendingAction.getNetReceivable(params);
            if(object){
                render object as JSON
            }else{
                render 0;
            }

    }


    def popupCustomerListPanel = {
        List customerList = new ArrayList()
        customerList = (List) listCustomerAssetLendingAction.executeCustomerByCode(params, null)
        render(view: '/customerAssetLending/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def listCustomerByCode = {
        render listCustomerAssetLendingAction.executeCustomerByCode(params, null) as JSON
    }
    def loadCustomerCategory = {
        List list = loadCustomerCategoryAction.execute(params, null)
        render list as JSON
    }
}
