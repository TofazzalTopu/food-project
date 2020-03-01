package com.bits.bdfp.common

import com.bits.bdfp.common.cashpool.CreateCashPoolAction
import com.bits.bdfp.common.cashpool.DeleteCashPoolAction
import com.bits.bdfp.common.cashpool.ListCashPoolAction
import com.bits.bdfp.common.cashpool.UpdateCashPoolAction
import com.bits.bdfp.common.cashpool.ReadCashPoolAction
import com.bits.bdfp.common.cashpool.SearchCashPoolAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CashPoolController {

    @Autowired
    private CreateCashPoolAction createCashPoolAction
    @Autowired
    private UpdateCashPoolAction updateCashPoolAction
    @Autowired
    private ListCashPoolAction listCashPoolAction
    @Autowired
    private DeleteCashPoolAction deleteCashPoolAction
    @Autowired
    private ReadCashPoolAction readCashPoolAction
    @Autowired
    private SearchCashPoolAction searchCashPoolAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCashPoolAction.execute(params, null)
        render listCashPoolAction.postCondition(null, list) as JSON
    }

    def show = {
        CashPool cashPool = new CashPool()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [cashPool: cashPool,list: enterpriseList,result:result as JSON])
    }

    def create = {
        CashPool cashPool = new CashPool(params)
        ApplicationUser applicationUser=session?.applicationUser
        CashPool cashPoolInstance = createCashPoolAction.preCondition(applicationUser, cashPool)
        Message message = null
        if (cashPoolInstance == null) {
            message = createCashPoolAction.getValidationErrorMessage(cashPool)
        } else {
            cashPoolInstance = createCashPoolAction.execute(null, cashPoolInstance)
            if (cashPoolInstance) {
                message = createCashPoolAction.getMessage(cashPoolInstance,Message.SUCCESS, createCashPoolAction.SUCCESS_SAVE)
            } else {
                message = createCashPoolAction.getMessage(cashPool,Message.ERROR, createCashPoolAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readCashPoolAction.execute(params, null)
        render result as JSON
    }


    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateCashPoolAction.execute(params, applicationUser)
        render message as JSON

    }

    def delete = {

        Message message = deleteCashPoolAction.execute(params, null)
        render message as JSON

    }



    def search = {
        CashPool cashPool = searchCashPoolAction.execute(params.fieldName, params.fieldValue)
        if (cashPool) {
            render cashPool as JSON
        } else {
            render ''
        }

    }
}
