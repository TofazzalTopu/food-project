package com.bits.bdfp.finance

import com.bits.bdfp.finance.expensefromdpcashpool.CancelExpenseFromDPCashPoolAction
import com.bits.bdfp.finance.expensefromdpcashpool.CreateExpenseFromDPCashPoolAction
import com.bits.bdfp.finance.expensefromdpcashpool.DeleteExpenseFromDPCashPoolAction
import com.bits.bdfp.finance.expensefromdpcashpool.ListExpenseFromDPCashPoolAction
import com.bits.bdfp.finance.expensefromdpcashpool.UpdateExpenseFromDPCashPoolAction
import com.bits.bdfp.finance.expensefromdpcashpool.ReadExpenseFromDPCashPoolAction
import com.bits.bdfp.finance.expensefromdpcashpool.SearchExpenseFromDPCashPoolAction
import com.bits.bdfp.inventory.warehouse.transferproducts.UtilSubInventoryToSubInventoryTransferAction
import com.bits.bdfp.finance.customerpayment.FetchCashPoolBalanceAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ExpenseFromDPCashPoolController {

    @Autowired
    private CreateExpenseFromDPCashPoolAction createExpenseFromDPCashPoolAction
    @Autowired
    private UpdateExpenseFromDPCashPoolAction updateExpenseFromDPCashPoolAction
    @Autowired
    private ListExpenseFromDPCashPoolAction listExpenseFromDPCashPoolAction
    @Autowired
    private DeleteExpenseFromDPCashPoolAction deleteExpenseFromDPCashPoolAction
    @Autowired
    private ReadExpenseFromDPCashPoolAction readExpenseFromDPCashPoolAction
    @Autowired
    private SearchExpenseFromDPCashPoolAction searchExpenseFromDPCashPoolAction
    @Autowired
    FetchCashPoolBalanceAction fetchCashPoolBalanceAction

    @Autowired
    UtilSubInventoryToSubInventoryTransferAction utilSubInventoryToSubInventoryTransferAction

    @Autowired
    private CancelExpenseFromDPCashPoolAction cancelExpenseFromDPCashPoolAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listExpenseFromDPCashPoolAction.execute(params, null)
        render listExpenseFromDPCashPoolAction.postCondition(objList, null) as JSON
    }

    def listExpenseRollback = {
        Object objList = listExpenseFromDPCashPoolAction.executeExpenseRollback(params, null)
        render listExpenseFromDPCashPoolAction.postCondition(objList, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List disList = utilSubInventoryToSubInventoryTransferAction.getDistributionListByUserId(applicationUser.id)
//        List list = searchExpenseFromDPCashPoolAction.fetchDistributionPointList(params, null)
        render(view: "show", model: [ disList:disList as JSON, distSize:disList? disList.size():0])
    }

    def showExpenses = {
        ApplicationUser applicationUser = session?.applicationUser
        List disList=utilSubInventoryToSubInventoryTransferAction.getDistributionListByUserId(applicationUser.id)
        render(view: "showExpenseRollback", model: [ disList:disList as JSON, distSize:disList? disList.size():0])
    }

    def cancelExpense = {
        ApplicationUser applicationUser = session.applicationUser
        render cancelExpenseFromDPCashPoolAction.execute(params, applicationUser) as JSON
    }
    def create = {
        Message message = createExpenseFromDPCashPoolAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readExpenseFromDPCashPoolAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateExpenseFromDPCashPoolAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteExpenseFromDPCashPoolAction.execute(params, null)
        render message as JSON
    }

    def search = {
        ExpenseFromDPCashPool expenseFromDPCashPool = (ExpenseFromDPCashPool) searchExpenseFromDPCashPoolAction.execute(params, null)
        if (expenseFromDPCashPool) {
            render expenseFromDPCashPool as JSON
        } else {
            render ''
        }
    }

    def fetchDistributionPointList ={
        List list = searchExpenseFromDPCashPoolAction.fetchDistributionPointList(params, null)
        render list as JSON
    }
    def fetchExpenditureHeadsList ={
        List list = searchExpenseFromDPCashPoolAction.fetchExpenditureHeads(params, null)
        render list as JSON
    }
    def fetchCashPoolList ={
        List list = searchExpenseFromDPCashPoolAction.fetchCashPoolList(params, null)
        render list as JSON
    }
    def fetchCashPoolBalance = {
        Map cashPoolBalance = (Map)fetchCashPoolBalanceAction.execute(params, null)
        render cashPoolBalance as JSON
    }
}
