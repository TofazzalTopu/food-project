package com.bits.bdfp.common

import com.bits.bdfp.common.bankbranch.CreateBankBranchAction
import com.bits.bdfp.common.bankbranch.DeleteBankBranchAction
import com.bits.bdfp.common.bankbranch.ListBankBranchAction
import com.bits.bdfp.common.bankbranch.UpdateBankBranchAction
import com.bits.bdfp.common.bankbranch.ReadBankBranchAction
import com.bits.bdfp.common.bankbranch.SearchBankBranchAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class BankBranchController {

    @Autowired
    private CreateBankBranchAction createBankBranchAction
    @Autowired
    private UpdateBankBranchAction updateBankBranchAction
    @Autowired
    private ListBankBranchAction listBankBranchAction
    @Autowired
    private DeleteBankBranchAction deleteBankBranchAction
    @Autowired
    private ReadBankBranchAction readBankBranchAction
    @Autowired
    private SearchBankBranchAction searchBankBranchAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listBankBranchAction.execute(params, null)
        render listBankBranchAction.postCondition(null, list) as JSON
    }

    def show = {
        BankBranch bankBranch = new BankBranch()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [bankBranch: bankBranch,list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        BankBranch bankBranch = new BankBranch(params)
        BankBranch bankBranchInstance = createBankBranchAction.preCondition(applicationUser, bankBranch)
        Message message = null
        if (bankBranchInstance == null) {
            message = createBankBranchAction.getValidationErrorMessage(bankBranch)
        } else {
            bankBranchInstance = createBankBranchAction.execute(null, bankBranchInstance)
            if (bankBranchInstance) {
                message = createBankBranchAction.getMessage(bankBranchInstance,Message.SUCCESS, createBankBranchAction.SUCCESS_SAVE)
            } else {
                message = createBankBranchAction.getMessage(bankBranch,Message.ERROR, createBankBranchAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readBankBranchAction.execute(params, null)
       render result as JSON
    }
    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateBankBranchAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteBankBranchAction.execute(params, null)
        render message as JSON
    }


    def search = {
        BankBranch bankBranch = searchBankBranchAction.execute(params.fieldName, params.fieldValue)
        if (bankBranch) {
            render bankBranch as JSON
        } else {
            render ''
        }

    }
    def loadBankBranch={
        Bank bank = Bank.read(Long.parseLong(params.id))
        List list= BankBranch.findAllByBank(bank)
        render list as JSON
    }
}
