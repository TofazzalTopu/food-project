package com.bits.bdfp.finance

import com.bits.bdfp.finance.customerpayment.ListAccountByCustomerAction
import com.bits.bdfp.finance.customerpayment.ListPopUpCustomerByTerritoryAction
import com.bits.bdfp.finance.customerpayment.UpdateSecurityDepositAction
import com.bits.bdfp.finance.viewandeditsecuritydeposit.ListSalesManSDBalanceAction
import com.bits.bdfp.finance.viewandeditsecuritydeposit.ListTerritoryByEnterpriseAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class ViewAndEditSecurityDepositController {
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListTerritoryByEnterpriseAction listTerritoryByEnterpriseAction
    @Autowired
    ListAccountByCustomerAction listAccountByCustomerAction
    @Autowired
    UpdateSecurityDepositAction updateSecurityDepositAction
    @Autowired
    ListPopUpCustomerByTerritoryAction listPopUpCustomerByTerritoryAction
    @Autowired
    ListSalesManSDBalanceAction listSalesManSDBalanceAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": enterpriseList, "total": enterpriseList.size()]

        render(template: "show", model: [applicationUser:applicationUser,
                                         result:result as JSON,
                                         enterpriseList:enterpriseList])
    }

    def getTerritoryListByEnterprise = {
        List list = listTerritoryByEnterpriseAction.execute(params,null);
        render list as JSON
    }

    def getDpListByTerritory = {
        List list = listTerritoryByEnterpriseAction.getDpListByTerritory(params);
        render list as JSON
    }

    def getDpDefaultCustomer = {
        Object object = listTerritoryByEnterpriseAction.getDpDefaultCustomerWithSecurityDepositBalance(params);
        render object as JSON
    }

    def getOtherCustomersSd = {
        Object object = listTerritoryByEnterpriseAction.getOtherCustomersSd(params);
        if(object){
            render object as JSON
        }else{
            render 0;
        }
    }

    def getDpDefaultCustomersTp = {
        List list =  listSalesManSDBalanceAction.execute(params, null)
//                listTerritoryByEnterpriseAction.getDpDefaultCustomersTp(params);
//        Double totalAmount = 0
        if(list.size() > 0){
            render list as JSON
        }else{
            render 0;
        }
    }

    def popupDefaultCustomerListPanel={
        ApplicationUser applicationUser = session?.applicationUser
        List isFactory = listAccountByCustomerAction.preCondition(null, applicationUser)
        render(view: 'popUpDefaultSdList', model: [
            customerId: params.customerId
        ])
    }

    def listCustomersSd = {
        render listTerritoryByEnterpriseAction.getDpDefaultCustomersSdList(params, null) as JSON
    }

    def updateSecurityDeposit={
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateSecurityDepositAction.execute(params, applicationUser)
        render message as JSON
    }

    def popUpCustomerListByTerritory = {
        ApplicationUser applicationUser = session?.applicationUser
        List customerList = listPopUpCustomerByTerritoryAction.execute(params,applicationUser)
        if(params.key){
            render customerList as JSON
        }else{
            render(view: 'popUpOtherCustomerList', model: [aaData: customerList as JSON])
        }
    }
}
