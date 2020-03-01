package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.setup.quotation.CreateQuotationAction
import com.bits.bdfp.inventory.setup.quotation.DeleteQuotationAction
import com.bits.bdfp.inventory.setup.quotation.ListProductForCsAction
import com.bits.bdfp.inventory.setup.quotation.ListQuotationAction
import com.bits.bdfp.inventory.setup.quotation.ListQuotationNoAction
import com.bits.bdfp.inventory.setup.quotation.ShowCsListAction
import com.bits.bdfp.inventory.setup.quotation.UpdateQuotationAction
import com.bits.bdfp.inventory.setup.quotation.ReadQuotationAction
import com.bits.bdfp.inventory.setup.quotation.SearchQuotationAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class QuotationController {

    @Autowired
    private CreateQuotationAction createQuotationAction
    @Autowired
    private UpdateQuotationAction updateQuotationAction
    @Autowired
    private ListQuotationAction listQuotationAction
    @Autowired
    private DeleteQuotationAction deleteQuotationAction
    @Autowired
    private ReadQuotationAction readQuotationAction
    @Autowired
    private SearchQuotationAction searchQuotationAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListQuotationNoAction listQuotationNoAction
    @Autowired
    ListProductForCsAction listProductForCsAction
    @Autowired
    ShowCsListAction showCsListAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listQuotationAction.execute(params, null)
        render listQuotationAction.postCondition(objList, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": list, "total": list.size()]
        render(view: "show", model: [result: result as JSON, list: list])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createQuotationAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readQuotationAction.execute(params, null)
        render data as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateQuotationAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteQuotationAction.execute(params, null)
        render message as JSON
    }

    def search = {
        Quotation quotation = (Quotation) searchQuotationAction.execute(params, null)
        if (quotation) {
            render quotation as JSON
        } else {
            render ''
        }
    }

    def loadPage = {
        if(params.fieldValue){
            Quotation quotation = (Quotation) searchQuotationAction.execute(params, null)
            if(quotation) {
                render(template: "create", model: [quotation: quotation])
            }else{
                render ''
            }
        }else{
            render(template: "create")
        }
    }

    def showCs = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Long entId
        if(list.size() == 1){
            entId = list[0].id
        }
        Map result = ["results": list, "total": list.size()]
        render(view: "showCs", model: [result: result as JSON, list: list, entId: entId])
    }

    def listQuotationNo = {
        Object objList = listQuotationNoAction.execute(params, null)
        render listQuotationNoAction.postCondition(objList, null) as JSON
    }

    def listProduct = {
        Object objList = listProductForCsAction.execute(params, null)
        render listProductForCsAction.postCondition(objList, null) as JSON
    }

    def listCs = {
        Object objList = showCsListAction.execute(params, null)
        render showCsListAction.postCondition(objList, null) as JSON
    }
}
