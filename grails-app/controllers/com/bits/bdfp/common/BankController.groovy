package com.bits.bdfp.common

import com.bits.bdfp.common.bank.CreateBankAction
import com.bits.bdfp.common.bank.DeleteBankAction
import com.bits.bdfp.common.bank.ListBankAction
import com.bits.bdfp.common.bank.UpdateBankAction
import com.bits.bdfp.common.bank.ReadBankAction
import com.bits.bdfp.common.bank.SearchBankAction
import com.bits.bdfp.common.bank.LoadBankByEnterpriseAction
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class BankController {

    @Autowired
    private CreateBankAction createBankAction
    @Autowired
    private UpdateBankAction updateBankAction
    @Autowired
    private ListBankAction listBankAction
    @Autowired
    private DeleteBankAction deleteBankAction
    @Autowired
    private ReadBankAction readBankAction
    @Autowired
    private SearchBankAction searchBankAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    LoadBankByEnterpriseAction loadBankByEnterpriseAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listBankAction.execute(params, null)
        render listBankAction.postCondition(null, list) as JSON
    }

    def show = {
        Bank bank = new Bank()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [bank: bank,list: enterpriseList,result:result as JSON])
    }

    def create = {
        Bank bank = new Bank(params)
        ApplicationUser applicationUser=session?.applicationUser
        Bank bankInstance = createBankAction.preCondition(applicationUser, bank)
        Message message = null
        if (bankInstance == null) {
            message = createBankAction.getValidationErrorMessage(bank)
        } else {
            bankInstance = createBankAction.execute(null, bankInstance)
            if (bankInstance) {
                message = createBankAction.getMessage(bankInstance,Message.SUCCESS, createBankAction.SUCCESS_SAVE)
            } else {
                message = createBankAction.getMessage(bank,Message.ERROR, createBankAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readBankAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateBankAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteBankAction.execute(params, null)
        render message as JSON
    }


    def search = {
        Bank bank = searchBankAction.execute(params.fieldName, params.fieldValue)
        if (bank) {
            render bank as JSON
        } else {
            render ''
        }

    }
    def loadBank={
        ApplicationUser applicationUser=session?.applicationUser
        List list=loadBankByEnterpriseAction.execute(params,applicationUser)
        render list  as JSON

    }
}
