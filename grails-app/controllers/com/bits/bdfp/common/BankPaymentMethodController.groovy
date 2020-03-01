package com.bits.bdfp.common

import com.bits.bdfp.common.bank.LoadBankByEnterpriseAction
import com.bits.bdfp.common.bankpaymentmethod.CreateBankPaymentMethodAction
import com.bits.bdfp.common.bankpaymentmethod.DeleteBankPaymentMethodAction
import com.bits.bdfp.common.bankpaymentmethod.ListBankPaymentMethodAction
import com.bits.bdfp.common.bankpaymentmethod.UpdateBankPaymentMethodAction
import com.bits.bdfp.common.bankpaymentmethod.ReadBankPaymentMethodAction
import com.bits.bdfp.common.bankpaymentmethod.SearchBankPaymentMethodAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class BankPaymentMethodController {

    @Autowired
    private CreateBankPaymentMethodAction createBankPaymentMethodAction
    @Autowired
    private UpdateBankPaymentMethodAction updateBankPaymentMethodAction
    @Autowired
    private ListBankPaymentMethodAction listBankPaymentMethodAction
    @Autowired
    private DeleteBankPaymentMethodAction deleteBankPaymentMethodAction
    @Autowired
    private ReadBankPaymentMethodAction readBankPaymentMethodAction
    @Autowired
    private SearchBankPaymentMethodAction searchBankPaymentMethodAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listBankPaymentMethodAction.execute(params, null)
        render listBankPaymentMethodAction.postCondition(null, list) as JSON
    }

    def show = {
        BankPaymentMethod bankPaymentMethod = new BankPaymentMethod()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [bankPaymentMethod: bankPaymentMethod,list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        BankPaymentMethod bankPaymentMethod = new BankPaymentMethod(params)
        BankPaymentMethod bankPaymentMethodInstance = createBankPaymentMethodAction.preCondition(applicationUser, bankPaymentMethod)
        Message message = null
        if (bankPaymentMethodInstance == null) {
            message = createBankPaymentMethodAction.getValidationErrorMessage(bankPaymentMethod)
        } else {
            bankPaymentMethodInstance = createBankPaymentMethodAction.execute(null, bankPaymentMethodInstance)
            if (bankPaymentMethodInstance) {
                message = createBankPaymentMethodAction.getMessage(bankPaymentMethodInstance,Message.SUCCESS, createBankPaymentMethodAction.SUCCESS_SAVE)
            } else {
                message = createBankPaymentMethodAction.getMessage(bankPaymentMethod,Message.ERROR, createBankPaymentMethodAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readBankPaymentMethodAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateBankPaymentMethodAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteBankPaymentMethodAction.execute(params, null)
        render message as JSON
    }


    def search = {
        BankPaymentMethod bankPaymentMethod = searchBankPaymentMethodAction.execute(params.fieldName, params.fieldValue)
        if (bankPaymentMethod) {
            render bankPaymentMethod as JSON
        } else {
            render ''
        }

    }
}
