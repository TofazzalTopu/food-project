package com.bits.bdfp.common

import com.bits.bdfp.common.bankaccount.CreateBankAccountAction
import com.bits.bdfp.common.bankaccount.DeleteBankAccountAction
import com.bits.bdfp.common.bankaccount.ListBankAccountAction
import com.bits.bdfp.common.bankaccount.LoadBankAccountAction

import com.bits.bdfp.common.bankaccount.UpdateBankAccountAction
import com.bits.bdfp.common.bankaccount.ReadBankAccountAction
import com.bits.bdfp.common.bankaccount.SearchBankAccountAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class BankAccountController {

    @Autowired
    private CreateBankAccountAction createBankAccountAction
    @Autowired
    private UpdateBankAccountAction updateBankAccountAction
    @Autowired
    private ListBankAccountAction listBankAccountAction
    @Autowired
    private DeleteBankAccountAction deleteBankAccountAction
    @Autowired
    private ReadBankAccountAction readBankAccountAction
    @Autowired
    private SearchBankAccountAction searchBankAccountAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    LoadBankAccountAction loadBankAccountAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listBankAccountAction.execute(params, null)
        render listBankAccountAction.postCondition(null, list) as JSON
    }

    def show = {
        BankAccount bankAccount = new BankAccount()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [bankAccount: bankAccount,list: enterpriseList,result:result as JSON])
    }

    def create = {
        BankAccount bankAccount = new BankAccount(params)
        ApplicationUser applicationUser=session?.applicationUser
        BankAccount bankAccountInstance = createBankAccountAction.preCondition(applicationUser, bankAccount)
        Message message = null
        if (bankAccountInstance == null) {
            message = createBankAccountAction.getValidationErrorMessage(bankAccount)
        } else {
            bankAccountInstance = createBankAccountAction.execute(null, bankAccountInstance)
            if (bankAccountInstance) {
                message = createBankAccountAction.getMessage("Bank Account",Message.SUCCESS, createBankAccountAction.SUCCESS_SAVE)
            } else {
                message = createBankAccountAction.getMessage("Bank Account",Message.ERROR, createBankAccountAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readBankAccountAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateBankAccountAction.execute(params, applicationUser)
        render message as JSON

    }

    def delete = {

        Message message = deleteBankAccountAction.execute(params, null)
        render message as JSON

    }

    def search = {
        BankAccount bankAccount = searchBankAccountAction.execute(params.fieldName, params.fieldValue)
        if (bankAccount) {
            render bankAccount as JSON
        } else {
            render ''
        }


    }
    def loadBankAccountByEnterprise={
       ApplicationUser applicationUser = session?.applicationUser
        List list=loadBankAccountAction.execute(params,applicationUser)
        render list as JSON
    }
}
