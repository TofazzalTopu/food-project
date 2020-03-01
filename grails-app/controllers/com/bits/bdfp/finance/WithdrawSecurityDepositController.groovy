package com.bits.bdfp.finance

import com.bits.bdfp.common.BankBranch
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.viewandeditsecuritydeposit.ListTerritoryByEnterpriseAction
import com.bits.bdfp.finance.withdrawSecurityDeposit.CreateWithdrawSecurityDepositAction
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.common.CodeGenerationUtil
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class WithdrawSecurityDepositController {
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListTerritoryByEnterpriseAction listTerritoryByEnterpriseAction

    @Autowired
    CreateWithdrawSecurityDepositAction createWithdrawSecurityDepositAction

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

    def create={
        ApplicationUser applicationUser=session?.applicationUser
        Message message = null

            message = createWithdrawSecurityDepositAction.execute(params, applicationUser)
            if (message) {
                message = createWithdrawSecurityDepositAction.getMessage("Withdraw Security Deposit",Message.SUCCESS, createWithdrawSecurityDepositAction.SUCCESS_SAVE)
            } else {
                message = createWithdrawSecurityDepositAction.getMessage("Withdraw Security Deposit",Message.ERROR, createWithdrawSecurityDepositAction.FAIL_SAVE)
            }
        render message as JSON
    }

    def getTerritoryListByEnterprise = {
        List list = listTerritoryByEnterpriseAction.execute(params,null);
        render list as JSON
    }

    def getDpListByTerritory = {
//        List list = listTerritoryByEnterpriseAction.getDpListByTerritory(params);
        List list = listTerritoryByEnterpriseAction.getDpListByTerritoryEnterprise(params);
        render list as JSON
    }

    def getDpDefaultCustomer = {
        Object object = listTerritoryByEnterpriseAction.getDpDefaultCustomerWithDepositBelance(params);
        if(object){
            render object as JSON
        }else{
            render 0;
        }
    }

    def getDpDefaultCustomersTp = {
        List list = listTerritoryByEnterpriseAction.listSalesManSDBalance(params);
        if(list.size() > 0){
            render list as JSON
        }else{
            render 0;
        }
    }
}
