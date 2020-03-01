package com.bits.bdfp.finance

import com.bits.bdfp.finance.customerpayment.ListAccountByCustomerAction
import com.bits.bdfp.finance.sdinterestcalculation.CreateSecurityDepositInterestAction
import com.bits.bdfp.finance.sdinterestcalculation.SdInterestCalculationAction
import com.bits.bdfp.finance.viewandeditsecuritydeposit.ListTerritoryByEnterpriseAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class SDInterestCalculationController {
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListTerritoryByEnterpriseAction listTerritoryByEnterpriseAction
    @Autowired
    ListAccountByCustomerAction listAccountByCustomerAction
    @Autowired
    SdInterestCalculationAction sdInterestCalculationAction
    @Autowired
    CreateSecurityDepositInterestAction createSecurityDepositInterestAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": enterpriseList, "total": enterpriseList.size()]

        render(template: "/SDInterestCalculation/show", model: [applicationUser:applicationUser,
                                         result:result as JSON,
                                         enterpriseList:enterpriseList])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createSecurityDepositInterestAction.execute(params, applicationUser)
        render message as JSON
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
        Object object = listTerritoryByEnterpriseAction.getDpDefaultCustomer(params);
        if(object){
            render object as JSON
        }else{
            render 0;
        }
    }

    def getDpDefaultCustomersTp = {
        List list = listTerritoryByEnterpriseAction.getDpDefaultCustomersTp(params);
        if(list.size() > 0){
            render list as JSON
        }else{
            render 0;
        }
    }
    def getDpDefaultCustomersIc = {
        List list = sdInterestCalculationAction.getDpDefaultCustomersIc(params);
        if(list.size() > 0){
            render list as JSON
        }else{
            render 0;
        }
    }

    def getCalculatedInterestByQuarter = {
        Map message = createSecurityDepositInterestAction.getCalculatedInterestByQuarter(params)
        render message as JSON
    }
}
