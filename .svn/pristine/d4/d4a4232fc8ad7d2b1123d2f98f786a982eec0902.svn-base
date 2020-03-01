package com.bits.bdfp.finance

import com.bits.bdfp.finance.adjustSecurityDeposit.CreateAdjustSecurityDepositAction
import com.bits.bdfp.finance.viewandeditsecuritydeposit.ListSalesManSDBalanceAction
import com.bits.bdfp.finance.viewandeditsecuritydeposit.ListTerritoryByEnterpriseAction
import com.bits.bdfp.finance.withdrawSecurityDeposit.CreateWithdrawSecurityDepositAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class AdjustSecurityDepositWithInvoiceController {
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListTerritoryByEnterpriseAction listTerritoryByEnterpriseAction
    @Autowired
    CreateAdjustSecurityDepositAction createAdjustSecurityDepositAction
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
        List list = listTerritoryByEnterpriseAction.getDpListByTerritoryEnterprise(params);
        render list as JSON
    }

    def getDpDefaultCustomer = {
//        Object object = listTerritoryByEnterpriseAction.getDpDefaultCustomerWithDepositBelance(params);
        Object object = listTerritoryByEnterpriseAction.getDpDefaultCustomerWithSecurityDepositBalance(params);
        if(object){
            render object as JSON
        }else{
            render 0;
        }
    }

    def getDpDefaultCustomersTp = {
//        List list = listTerritoryByEnterpriseAction.getDpDefaultCustomersTp(params);
        List list =  listSalesManSDBalanceAction.execute(params, null)
        if(list.size() > 0){
            render list as JSON
        }else{
            render 0;
        }
    }

    def create={
        ApplicationUser applicationUser=session?.applicationUser
        Message message = null
        message=createAdjustSecurityDepositAction.execute(params,applicationUser)
        render message as JSON
    }

    def fetchDepositBalance = {
        Object object = listTerritoryByEnterpriseAction.fetchPrimaryCustomerDepositBalance(params);
        if(object){
            render object as JSON
        }else{
            render 0;
        }
    }
}
