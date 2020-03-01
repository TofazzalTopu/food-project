package com.bits.bdfp.accounts

import com.bits.bdfp.accounts.mushak.CreateMushakAction
import com.bits.bdfp.accounts.mushak.DeleteMushakAction
import com.bits.bdfp.accounts.mushak.ListDistributionPointForMushakAction
import com.bits.bdfp.accounts.mushak.ListInvoiceDetailsForMushakAction
import com.bits.bdfp.accounts.mushak.ListInvoiceForMushakAction
import com.bits.bdfp.accounts.mushak.ListMushakAction
import com.bits.bdfp.accounts.mushak.ListMushakDetailsAction
import com.bits.bdfp.accounts.mushak.UpdateMushakAction
import com.bits.bdfp.accounts.mushak.ReadMushakAction
import com.bits.bdfp.accounts.mushak.SearchMushakAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class MushakController {

    @Autowired
    private CreateMushakAction createMushakAction
    @Autowired
    private UpdateMushakAction updateMushakAction
    @Autowired
    private ListMushakAction listMushakAction
    @Autowired
    private DeleteMushakAction deleteMushakAction
    @Autowired
    private ReadMushakAction readMushakAction
    @Autowired
    private SearchMushakAction searchMushakAction
    @Autowired
    ListInvoiceForMushakAction listInvoiceForMushakAction
    @Autowired
    ListInvoiceDetailsForMushakAction listInvoiceDetailsForMushakAction
    @Autowired
    ListDistributionPointForMushakAction listDistributionPointForMushakAction
    @Autowired
    ListMushakDetailsAction listMushakDetailsAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listMushakAction.execute(params, null)
        render listMushakAction.postCondition(objList, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('id', applicationUser.id)
        List dpList = listDistributionPointForMushakAction.execute(params, null)
        render(view: "show", model: [dpList: dpList as JSON, dpSize: dpList? dpList.size():0])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createMushakAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Mushak mushak = readMushakAction.execute(params, null)
        render(template: "edit", model: [mushak: mushak])
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateMushakAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteMushakAction.execute(params, null)
        render message as JSON
    }

    def search = {
        List data = searchMushakAction.execute(params, null)
        if (data) {
            render data as JSON
        } else {
            render ''
        }
    }

    def popupInvoiceListPanel = {
        List list = (List) listInvoiceForMushakAction.execute(null,params)
        render(view: 'popUpInvoiceList', model: [aaData: list as JSON])
    }

    def listInvoice = {
        render listInvoiceForMushakAction.execute(null, params) as JSON
    }

    def listInvoiceDetails = {
        Object objList = listInvoiceDetailsForMushakAction.execute(params, null)
        render listInvoiceDetailsForMushakAction.postCondition(null, objList) as JSON
    }

    def viewMushak = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('id', applicationUser.id)
        List dpList = listDistributionPointForMushakAction.execute(params, null)
        render(view: "showUpdate", model: [dpList: dpList as JSON, dpSize: dpList? dpList.size():0])
    }

    def listDetails = {
        Object objList = listMushakDetailsAction.execute(params, null)
        render listMushakDetailsAction.postCondition(objList, null) as JSON
    }

    def deleteDetail = {
        Message message = deleteMushakAction.preCondition(params, null)
        render message as JSON
    }
}
