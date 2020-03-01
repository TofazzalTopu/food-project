package com.bits.bdfp.common

import com.bits.bdfp.common.depositpool.CreateDepositPoolAction
import com.bits.bdfp.common.depositpool.DeleteDepositPoolAction
import com.bits.bdfp.common.depositpool.ListDepositPoolAction
import com.bits.bdfp.common.depositpool.UpdateDepositPoolAction
import com.bits.bdfp.common.depositpool.ReadDepositPoolAction
import com.bits.bdfp.common.depositpool.SearchDepositPoolAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DepositPoolController {

    @Autowired
    private CreateDepositPoolAction createDepositPoolAction
    @Autowired
    private UpdateDepositPoolAction updateDepositPoolAction
    @Autowired
    private ListDepositPoolAction listDepositPoolAction
    @Autowired
    private DeleteDepositPoolAction deleteDepositPoolAction
    @Autowired
    private ReadDepositPoolAction readDepositPoolAction
    @Autowired
    private SearchDepositPoolAction searchDepositPoolAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDepositPoolAction.execute(params, null)
        render listDepositPoolAction.postCondition(null, list) as JSON
    }

    def show = {
        DepositPool depositPool = new DepositPool()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [depositPool: depositPool,list: enterpriseList,result:result as JSON])
    }

    def create = {
        DepositPool depositPool = new DepositPool(params)
        ApplicationUser applicationUser=session?.applicationUser
        DepositPool depositPoolInstance = createDepositPoolAction.preCondition(applicationUser, depositPool)
        Message message = null
        if (depositPoolInstance == null) {
            message = createDepositPoolAction.getValidationErrorMessage(depositPool)
        } else {
            depositPoolInstance = createDepositPoolAction.execute(null, depositPoolInstance)
            if (depositPoolInstance) {
                message = createDepositPoolAction.getMessage(depositPoolInstance,Message.SUCCESS, createDepositPoolAction.SUCCESS_SAVE)
            } else {
                message = createDepositPoolAction.getMessage(depositPool,Message.ERROR, createDepositPoolAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readDepositPoolAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateDepositPoolAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteDepositPoolAction.execute(params, null)
        render message as JSON
    }



    def search = {
        DepositPool depositPool = searchDepositPoolAction.execute(params.fieldName, params.fieldValue)
        if (depositPool) {
            render depositPool as JSON
        } else {
            render ''
        }

    }
}
