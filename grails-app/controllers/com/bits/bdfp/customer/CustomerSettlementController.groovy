package com.bits.bdfp.customer

import com.bits.bdfp.customer.customermaster.GetDefaultCustomerByDPAction
import com.bits.bdfp.customer.customermaster.ListDeliveryManByDistributionPointAction
import com.bits.bdfp.customer.customermaster.ListExternalCustomerByGeoLocationAction
import com.bits.bdfp.customer.customermaster.ReadCustomerSettlementDataAction
import com.bits.bdfp.customer.customersettlement.AdjustWithCustomerReceivableAction
import com.bits.bdfp.customer.customersettlement.ReadCustomerSettlementAction
import com.bits.bdfp.customer.customersettlement.SearchCustomerSettlementAction
import com.bits.bdfp.customer.customersettlement.WithdrawCustomerSettlementAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerSettlementController {

    @Autowired
    private ReadCustomerSettlementAction readCustomerSettlementAction
    @Autowired
    private SearchCustomerSettlementAction searchCustomerSettlementAction
    @Autowired
    EnterpriseAutocompleteListAction  enterpriseAutocompleteListAction
    @Autowired
    ListExternalCustomerByGeoLocationAction listExternalCustomerByGeoLocationAction
    @Autowired
    ListDeliveryManByDistributionPointAction listDeliveryManByDistributionPointAction
    @Autowired
    ReadCustomerSettlementDataAction readCustomerSettlementDataAction
    @Autowired
    GetDefaultCustomerByDPAction getDefaultCustomerByDPAction
    @Autowired
    AdjustWithCustomerReceivableAction adjustWithCustomerReceivableAction
    @Autowired
    WithdrawCustomerSettlementAction withdrawCustomerSettlementAction

    static allowedMethods = [adjustWithReceivable: "POST", withdraw: "POST"]
    static defaultAction = "show"


    def show = {
        CustomerSettlement customerSettlement = new CustomerSettlement()
//        customerSettlement.settlementDate = new Date()
        ApplicationUser applicationUser = session?.applicationUser
        Map result = [:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(view: "show", model: [customerSettlement:customerSettlement, list: enterpriseList, result:result as JSON])
    }

    def adjustWithReceivable = {
        Message message = adjustWithCustomerReceivableAction.execute(params, null)
        render message as JSON
    }

    def withdraw = {
        Message message = withdrawCustomerSettlementAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readCustomerSettlementAction.execute(params, null)
        render data as JSON
    }

    def search = {
        CustomerSettlement customerSettlement = (CustomerSettlement) searchCustomerSettlementAction.execute(params, null)
        if (customerSettlement) {
            render customerSettlement as JSON
        } else {
            render ''
        }
    }

    def branchCustomerAutoComplete = {
        render listDeliveryManByDistributionPointAction.execute(params, null) as JSON
    }

    def popupBranchCustomerListPanel = {
        List customerList = new ArrayList()
        customerList = (List) listDeliveryManByDistributionPointAction.execute(params, null)
        render(view: '/customerSettlement/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def othersCustomerAutoComplete = {
        render listExternalCustomerByGeoLocationAction.execute(params, null) as JSON
    }

    def popupOthersCustomerListPanel = {
        List customerList = new ArrayList()
        customerList = (List) listExternalCustomerByGeoLocationAction.execute(params, null)
        render(view: '/customerSettlement/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def getCustomerSettlementData = {
        Map settlementData = (Map) readCustomerSettlementDataAction.execute(params, null)
        render settlementData as JSON
    }

    def getDefaultCustomerByDP = {
        Map defaultCustomer = (Map) getDefaultCustomerByDPAction.execute(params, null)
        render defaultCustomer as JSON
    }
}
