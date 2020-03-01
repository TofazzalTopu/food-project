package com.bits.bdfp.finance

import com.bits.bdfp.finance.cashreceivedfrombranch.CreateCashReceivedFromBranchAction
import com.bits.bdfp.finance.cashreceivedfrombranch.DeleteCashReceivedFromBranchAction
import com.bits.bdfp.finance.cashreceivedfrombranch.FetchBankAndCashAction
import com.bits.bdfp.finance.cashreceivedfrombranch.FetchDepositToDepositPoolDataAction
import com.bits.bdfp.finance.cashreceivedfrombranch.FetchTransactionNoAction
import com.bits.bdfp.finance.cashreceivedfrombranch.ListCashReceivedFromBranchAction
import com.bits.bdfp.finance.cashreceivedfrombranch.UpdateCashReceivedFromBranchAction
import com.bits.bdfp.finance.cashreceivedfrombranch.ReadCashReceivedFromBranchAction
import com.bits.bdfp.finance.cashreceivedfrombranch.SearchCashReceivedFromBranchAction
import com.bits.bdfp.inventory.warehouse.transferproducts.UtilSubInventoryToSubInventoryTransferAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CashReceivedFromBranchController {

    @Autowired
    private CreateCashReceivedFromBranchAction createCashReceivedFromBranchAction
    @Autowired
    private UpdateCashReceivedFromBranchAction updateCashReceivedFromBranchAction
    @Autowired
    private ListCashReceivedFromBranchAction listCashReceivedFromBranchAction
    @Autowired
    private DeleteCashReceivedFromBranchAction deleteCashReceivedFromBranchAction
    @Autowired
    private ReadCashReceivedFromBranchAction readCashReceivedFromBranchAction
    @Autowired
    private SearchCashReceivedFromBranchAction searchCashReceivedFromBranchAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    FetchBankAndCashAction fetchBankAndCashAction
    @Autowired
    FetchTransactionNoAction fetchTransactionNoAction
    @Autowired
    FetchDepositToDepositPoolDataAction fetchDepositToDepositPoolDataAction

    @Autowired
    UtilSubInventoryToSubInventoryTransferAction utilSubInventoryToSubInventoryTransferAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listCashReceivedFromBranchAction.execute(params, null)
        render listCashReceivedFromBranchAction.postCondition(objList, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        String currentDate = DateUtil.getCurrentDateFormatAsString()


        List dpListByUserId=utilSubInventoryToSubInventoryTransferAction.getDistributionListByUserId(applicationUser.id)

        Map result = ["results": list, "total": list.size()]
        render(view: "show", model: [result: result as JSON, list: list,dpListByUserId:dpListByUserId, currentDate: currentDate])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createCashReceivedFromBranchAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readCashReceivedFromBranchAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateCashReceivedFromBranchAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteCashReceivedFromBranchAction.execute(params, null)
        render message as JSON
    }

    def search = {
        CashReceivedFromBranch cashReceivedFromBranch = (CashReceivedFromBranch) searchCashReceivedFromBranchAction.execute(params, null)
        if (cashReceivedFromBranch) {
            render cashReceivedFromBranch as JSON
        } else {
            render ''
        }
    }

    def loadBankAndCash = {
        render fetchBankAndCashAction.execute(params, null) as JSON
    }

    def loadTransactionNo = {
        render fetchTransactionNoAction.execute(params, null) as JSON
    }

    def fetchData = {
        render fetchDepositToDepositPoolDataAction.execute(params, null) as JSON
    }
}
