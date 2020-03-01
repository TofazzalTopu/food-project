package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.customer.customermaster.ListCustomerAutoCompleteByApplicationUserAction
import com.bits.bdfp.customer.customermaster.ListCustomerForPrintPrimaryInvoiceAction
import com.bits.bdfp.customer.customermaster.ReadCustomerBalanceAndShippingAddressAction
import com.bits.bdfp.inventory.demandorder.approveprimarydemandorder.ApprovePrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.approveprimarydemandorder.ListFinishedProductForSelectedPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.approveprimarydemandorder.ListPrimaryDemandOrderForApproveAction
import com.bits.bdfp.inventory.demandorder.approveprimarydemandorder.RejectPrimaryDemandOrderAction

import com.bits.bdfp.customer.customermaster.CustomerAutoCompleteForPrimaryDemandOrderAction
import com.bits.bdfp.customer.customermaster.ListCustomerByApplicationUserAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.CancelNewPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.CancelSalesInvoiceAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.CancelSecondaryInvoiceAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.CreateNewPrimaryDemandOrderAction

import com.bits.bdfp.inventory.demandorder.primarydemandorder.CreatePrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.CreatePrimaryOrderFromSecondaryOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.DeletePrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.DeletePrimaryOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.FetchDefaultCustomerFromDpAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.FlexListProductForNewPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.GeneratePrimaryOrderFromSecondaryOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.GetAuthorizedEmployeeInfoAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.InvoiceNoAutoCompleteAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListApprovalHistoryAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListForUpdateDeliveryDateAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListGridForCashThroughBankAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListInfoByEnterpriseAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListPrimaryOrderDetailsAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListPrimaryOrderForEditAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.PrimaryOrderAutoCompleteAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.RollbackReceivedPrimaryInvoiceAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.SaveSecondaryPrintStatusAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.UpdateDeliveryDateAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.UpdatePrimaryOrderFromSecondaryOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListOrderDetailsByPrimaryOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ReadNewPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.RptPrintInvoiceAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.UpdateNewPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.processanorder.ProductListSeelctAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.CancelDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.ListCustomerOrdersAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListOrderStatusAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListPrimaryDemandOrderDefaultCustomerAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.OrderNoAutoCompleteListAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.SearchNewPrimaryOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.UpdatePrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ReadPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.SearchPrimaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.ListSecondaryInvoiceAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductPriceByCustomerAction
import com.bits.bdfp.inventory.sales.distributionpoint.DistributionPointDefaultCustomerAction

import com.bits.bdfp.inventory.product.finishproduct.ListProductByEnterpriseAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.MergeDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails.ListDetailsByDemandOrderAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListDistributionPointByApplicationUser
import com.bits.bdfp.reports.SearchReportInfoAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import com.docu.security.UserType
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON
import com.bits.bdfp.inventory.demandorder.primarydemandorder.ListPrintInvoiceAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.SavePrintStatusAction

import java.text.DateFormat
import java.text.SimpleDateFormat

class PrimaryDemandOrderController {


    @Autowired
    private CreatePrimaryDemandOrderAction createPrimaryDemandOrderAction
    @Autowired
    private UpdatePrimaryDemandOrderAction updatePrimaryDemandOrderAction
    @Autowired
    private ListPrimaryDemandOrderAction listPrimaryDemandOrderAction
    @Autowired
    private DeletePrimaryDemandOrderAction deletePrimaryDemandOrderAction
    @Autowired
    private ReadPrimaryDemandOrderAction readPrimaryDemandOrderAction
    @Autowired
    private SearchPrimaryDemandOrderAction searchPrimaryDemandOrderAction
    @Autowired
    ListOrderStatusAction listOrderStatusAction
    @Autowired
    OrderNoAutoCompleteListAction orderNoAutoCompleteListAction
    @Autowired
    ListApprovalHistoryAction listApprovalHistoryAction
    @Autowired
    private ListPrimaryDemandOrderForApproveAction listPrimaryDemandOrderForApproveAction
    @Autowired
    DistributionPointDefaultCustomerAction distributionPointDefaultCustomerAction
    @Autowired
    ListPrimaryDemandOrderDefaultCustomerAction listPrimaryDemandOrderDefaultCustomerAction
    @Autowired
    ListFinishedProductForSelectedPrimaryDemandOrderAction listFinishedProductForSelectedPrimaryDemandOrderAction
    @Autowired
    ApprovePrimaryDemandOrderAction approvePrimaryDemandOrderAction
    @Autowired
    RejectPrimaryDemandOrderAction rejectPrimaryDemandOrderAction
    @Autowired
    CustomerAutoCompleteForPrimaryDemandOrderAction customerAutoCompleteForPrimaryDemandOrderAction
    @Autowired
    FlexListProductForNewPrimaryDemandOrderAction flexListProductForNewPrimaryDemandOrderAction
    @Autowired
    ListCustomerByApplicationUserAction listCustomerByApplicationUserAction
    @Autowired
    ListProductByEnterpriseAction listProductByEnterpriseAction
    @Autowired
    CreateNewPrimaryDemandOrderAction createNewPrimaryDemandOrderAction
    @Autowired
    SearchNewPrimaryOrderAction searchNewPrimaryOrderAction
    @Autowired
    ListCustomerOrdersAction listCustomerOrdersAction
    @Autowired
    ListDetailsByDemandOrderAction listDetailsByDemandOrderAction
    @Autowired
    MergeDemandOrderAction mergeDemandOrderAction
    @Autowired
    CancelDemandOrderAction cancelDemandOrderAction
    @Autowired
    GeneratePrimaryOrderFromSecondaryOrderAction generatePrimaryOrderFromSecondaryOrderAction
    @Autowired
    CreatePrimaryOrderFromSecondaryOrderAction createPrimaryOrderFromSecondaryOrderAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListPrimaryOrderForEditAction listPrimaryOrderForEditAction
    @Autowired
    ListPrimaryOrderDetailsAction listPrimaryOrderDetailsAction
    @Autowired
    UpdatePrimaryOrderFromSecondaryOrderAction updatePrimaryOrderFromSecondaryOrderAction
    @Autowired
    DeletePrimaryOrderAction deletePrimaryOrderAction
    @Autowired
    PrimaryOrderAutoCompleteAction primaryOrderAutoCompleteAction

    @Autowired
    ReadNewPrimaryDemandOrderAction readNewPrimaryDemandOrderAction
    @Autowired
    ListOrderDetailsByPrimaryOrderAction listOrderDetailsByPrimaryOrderAction
    @Autowired
    UpdateNewPrimaryDemandOrderAction updateNewPrimaryDemandOrderAction
    @Autowired
    ListPrintInvoiceAction listPrintInvoiceAction
    @Autowired
    ListSecondaryInvoiceAction listSecondaryInvoiceAction
    @Autowired
    SavePrintStatusAction savePrintStatusAction
    @Autowired
    RptPrintInvoiceAction rptPrintInvoiceAction
    @Autowired
    ListCustomerAutoCompleteByApplicationUserAction listCustomerAutoCompleteByApplicationUserAction
    @Autowired
    ListForUpdateDeliveryDateAction listForUpdateDeliveryDateAction
    @Autowired
    UpdateDeliveryDateAction updateDeliveryDateAction
    @Autowired
    ReadCustomerBalanceAndShippingAddressAction readCustomerBalanceAndShippingAddressAction
    @Autowired
    CancelNewPrimaryDemandOrderAction cancelNewPrimaryDemandOrderAction
    @Autowired
    ListGridForCashThroughBankAction  listGridForCashThroughBankAction
    @Autowired
    InvoiceNoAutoCompleteAction printInvoiceNoAutoCompleteAction
    @Autowired
    ListInfoByEnterpriseAction listInfoByEnterpriseAction
    @Autowired
    ListDistributionPointByApplicationUser listDistributionPointByApplicationUser
    @Autowired
    CancelSalesInvoiceAction cancelSalesInvoiceAction
    @Autowired
    FetchDefaultCustomerFromDpAction fetchDefaultCustomerFromDpAction
    @Autowired
    ListProductPriceByCustomerAction listProductPriceByCustomerAction
    @Autowired
    SaveSecondaryPrintStatusAction saveSecondaryPrintStatusAction
    SpringSecurityService  springSecurityService

    @Autowired
    CancelSecondaryInvoiceAction cancelSecondaryInvoiceAction

    @Autowired
    SearchReportInfoAction searchReportInfoAction

    @Autowired
    RollbackReceivedPrimaryInvoiceAction rollbackReceivedPrimaryInvoiceAction
    @Autowired
    ProductListSeelctAction productListSeelctAction
    @Autowired
    ListCustomerForPrintPrimaryInvoiceAction listCustomerForPrintPrimaryInvoiceAction

    @Autowired
    GetAuthorizedEmployeeInfoAction getAuthorizedEmployeeInfoAction

    def jasperService

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listPrimaryDemandOrderAction.execute(params, null)
        render listPrimaryDemandOrderAction.postCondition(null, list) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        List distributionPointList = (List)listDistributionPointByApplicationUser.execute(params, null)
        render(view: "show", model: [enterpriseList: list as JSON, size: list.size(), distributionPointList: distributionPointList])
    }

    def getGeoLocationForCustomer = {
        List geoLocationList =  (List)listDistributionPointByApplicationUser.getGeolocationByCustomer(params)
        render geoLocationList as JSON
    }
    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createPrimaryOrderFromSecondaryOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def createNew = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = (Message) createNewPrimaryDemandOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def updateNew = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = (Message) updateNewPrimaryDemandOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        render readPrimaryDemandOrderAction.execute(params, null) as JSON
    }

    def updatePrimaryDemandOrder = {
        Message message = updatePrimaryDemandOrderAction.updatePrimaryDemandOrder(params, null)
        render message as JSON
    }

    def update = {
        PrimaryDemandOrder primaryDemandOrder = new PrimaryDemandOrder(params)
        Object object = updatePrimaryDemandOrderAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updatePrimaryDemandOrderAction.getValidationErrorMessageForUI(primaryDemandOrder)
        } else {
            int noOfRows = (int) updatePrimaryDemandOrderAction.execute(null, object)
            if (noOfRows > 0) {
                message = updatePrimaryDemandOrderAction.getSuccessMessageForUI(primaryDemandOrder, updatePrimaryDemandOrderAction.SUCCESS_UPDATE)
            } else {
                message = updatePrimaryDemandOrderAction.getErrorMessageForUI(primaryDemandOrder, updatePrimaryDemandOrderAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        PrimaryDemandOrder primaryDemandOrder = deletePrimaryDemandOrderAction.execute(params, null);
        Message message = null
        if (primaryDemandOrder) {
            int rowCount = (int) deletePrimaryDemandOrderAction.preCondition(null, primaryDemandOrder);
            if (rowCount > 0) {
                message = deletePrimaryDemandOrderAction.getSuccessMessageForUI(primaryDemandOrder, deletePrimaryDemandOrderAction.SUCCESS_DELETE);
            } else {
                message = deletePrimaryDemandOrderAction.getErrorMessageForUI(primaryDemandOrder, deletePrimaryDemandOrderAction.FAIL_DELETE);
            }
        } else {
            message = deletePrimaryDemandOrderAction.getErrorMessageForUI(primaryDemandOrder, deletePrimaryDemandOrderAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        PrimaryDemandOrder primaryDemandOrder = searchPrimaryDemandOrderAction.execute(params.fieldName, params.fieldValue)
        if (primaryDemandOrder) {
            render primaryDemandOrder as JSON
        } else {
            render ''
        }

    }
    def viewOrderStatus = {
        render(view: 'searchOrderStatus')
    }

    def orderStatusList = {
        ApplicationUser applicationUser = session?.applicationUser
        render listOrderStatusAction.execute(params, applicationUser) as JSON
    }

    def listOrderNoAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        render orderNoAutoCompleteListAction.execute(params, applicationUser) as JSON
    }

    def listInvoiceNoAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        render printInvoiceNoAutoCompleteAction.execute(params, applicationUser) as JSON
    }

    def popupApprovalListPanel = {
        Map result = listApprovalHistoryAction.execute(params, null)
        render(template: 'primaryDemandOrderHistory', model: [initiatorList: result.initiatorList, approvalUserList: result.approvalUserList, pendingUserList: result.pendingUserList])
    }

    def fetchProductListForUpdatePrimaryDemandOrder ={
        ApplicationUser applicationUser = session?.applicationUser
        List productList = productListSeelctAction.execute(params, applicationUser)
        render productList as JSON
    }
    def showPrimaryDemandOrderForApprove = {
        render(template: "/approvePrimaryDemandOrder/show")
    }
    def popupProductListPanelList = {
        render(view: 'popupProductList', model: ['customerId': params.customerId,'territorySubAreaId': params.territorySubAreaId])
    }
    def jsonProductListForPrimaryOrder = {
        Map map = [:]
        List data = (List)listProductPriceByCustomerAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }
    def autoCompleteCustomerListForApprovePrimaryDemandOrder = {
        List list = listPrimaryDemandOrderDefaultCustomerAction.execute(params, null)
        render list as JSON

    }

    def getPrimaryDemandOrderDetails = {
        Map primaryDemandOrderList = listPrimaryDemandOrderForApproveAction.execute(params, null)
        render primaryDemandOrderList as JSON
    }

    def editProductDetailsForSelectedPrimaryDemandOrder = {
        Map finishedProductList = listFinishedProductForSelectedPrimaryDemandOrderAction.execute(params, null)
        render finishedProductList as JSON
    }

    def approveSelectedPrimaryDemandOrder = {
        Message message = null
        ApplicationUser applicationUser = session?.applicationUser
        message = approvePrimaryDemandOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def confirmRejectionNote = {
        render(template: '/approvePrimaryDemandOrder/rejectionNote');
    }

    def rejectSelectedPrimaryDemandOrder = {
        Message message = null
        ApplicationUser applicationUser = session?.applicationUser
        message = rejectPrimaryDemandOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def showNewPrimaryDemandOrder = {
        CustomerMaster customerMaster = null
        List distributionPointList = []
        List customerShippingAddressList = []
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        UserType userType = applicationUser.userType

        if(userType){
            if (userType.id == ApplicationConstants.USER_TYPE_OTHER && applicationUser.customerMasterId) {
                customerMaster = CustomerMaster.get(applicationUser.customerMasterId)
                distributionPointList = (List)listDistributionPointByApplicationUser.execute(params, null)
                customerShippingAddressList = CustomerShippingAddress.findAllByCustomerMaster(customerMaster)
            }else if(userType.id == ApplicationConstants.USER_TYPE_CUSTOMER && applicationUser.customerMasterId){
                customerMaster = CustomerMaster.get(applicationUser.customerMasterId)
                distributionPointList = (List)listDistributionPointByApplicationUser.getListDistributionPointByDpWarehouseDefaultCustomer()
                customerShippingAddressList = CustomerShippingAddress.findAllByCustomerMaster(customerMaster)
            } else {
                render(view: "/demandOrder/newPrimaryDemandOrder/unAuthorized")
                return
            }
        }else{
            render(view: "/demandOrder/newPrimaryDemandOrder/unAuthorized")
            return
        }

        PrimaryDemandOrder primaryDemandOrder = new PrimaryDemandOrder()
        render(view: "/demandOrder/newPrimaryDemandOrder/show", model: [primaryDemandOrder: primaryDemandOrder,userType:  userType.id,
                                                                        customerMaster:customerMaster, distributionPointList:distributionPointList,
                                                                        customerShippingAddressList : customerShippingAddressList])
    }

    def customerAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("customerId", applicationUser.customerMasterId)
        params.put('applicationUserId', applicationUser.id)
        render listCustomerAutoCompleteByApplicationUserAction.execute(params, null) as JSON
    }

    def customerAutoCompleteForNewPrimaryDemandOrder = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("customerId", applicationUser.customerMasterId)
        params.put('applicationUserId', applicationUser.id)
        render listCustomerAutoCompleteByApplicationUserAction.listCustomerByApplicationUserForNewPrimaryDemandOrder(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = new ArrayList()
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        UserType userType = applicationUser.userType

        if(userType){
            if(userType.id == ApplicationConstants.USER_TYPE_OTHER && applicationUser.customerMasterId){
                params.put("customerId", applicationUser.customerMasterId)
                params.put('applicationUserId', applicationUser.id)
                customerList = (List) listCustomerByApplicationUserAction.execute(params, null)
            }else{
                render (view: "/demandOrder/newPrimaryDemandOrder/unAuthorized")
                return
            }
        }else{
            render (view: "/demandOrder/newPrimaryDemandOrder/unAuthorized")
            return
        }
        render(view: '/demandOrder/newPrimaryDemandOrder/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def popupProductListPanel = {
        if(!params.customerId){
            params.customerId = params.id
        }
        if(!params.territorySubAreaId){
            params.territorySubAreaId = params.territorySubAreaId
        }
        render(view: '/demandOrder/newPrimaryDemandOrder/popupProductList', model: ['customerId': params.customerId,'territorySubAreaId': params.territorySubAreaId])
    }

    def jsonProductList = {
        List data = (List)listProductPriceByCustomerAction.listProductForRetailOrder(params, null)
        render data as JSON
    }

    def jsonProductListForEdit = {
        Map map = [:]
        List data = (List)listProductPriceByCustomerAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def viewSearchNewDemandOrder = {
        render(view: "/demandOrder/newPrimaryDemandOrder/searchPrimaryDemandOrder")
    }

    def searchNewPrimaryDemandOrder = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("applicationUserId", applicationUser.id)
        render searchNewPrimaryOrderAction.execute(params, null) as JSON
    }

    def listCustomerOrders = {
        render listCustomerOrdersAction.execute(params, null) as JSON
    }

    def listOrderDetails = {
        String result = listDetailsByDemandOrderAction.execute(params, null)
        render result
    }

    def mergeOrders = {
        Message message = mergeDemandOrderAction.execute(params, null)
        render message as JSON;
    }

    def cancelOrders = {
        Message message = cancelDemandOrderAction.execute(params, null)
        render message as JSON;
    }

    def generatePrimaryOrder = {
        render generatePrimaryOrderFromSecondaryOrderAction.execute(params, null) as JSON
    }

    def showListForEdit = {
        render(template: 'searchPrimaryOrder')
    }

    def orderListFrGrid = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('applicationUser', applicationUser)
        List list = listPrimaryOrderForEditAction.execute(params, null)
        render listPrimaryOrderForEditAction.postCondition(null, list) as JSON
    }

    def editPrimaryOrder = {
        PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) readPrimaryDemandOrderAction.execute(params, null)
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy")
        String expected = format.format(primaryDemandOrder.dateExpectedDeliver)
        String propose = format.format(primaryDemandOrder.dateProposedDelivery)
        String order = format.format(primaryDemandOrder.orderDate)
        List distributionPointList = (List)listDistributionPointByApplicationUser.execute(params, null)
        render(template: "edit", model: [primaryDemandOrder: primaryDemandOrder, dateExpectedDeliver: expected,
                                         dateProposedDelivery: propose, orderDate: order, distributionPointList:distributionPointList])
//        render(template: "edit", model: [primaryDemandOrder: primaryDemandOrder])
    }

    def listDetailsForEdit = {
        List list = listPrimaryOrderDetailsAction.execute(params, null)
        render listPrimaryOrderDetailsAction.postCondition(null, list) as JSON
    }

    def updatePrimaryOrder = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updatePrimaryOrderFromSecondaryOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def deletePrimaryOrder = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = deletePrimaryOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def listOrderForAutoComplete={
        ApplicationUser applicationUser = session?.applicationUser
        render primaryOrderAutoCompleteAction.execute(params,applicationUser) as JSON
    }

    def readNewPrimaryDemandOrder = {
        List distributionPointList = []
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.put("applicationUserId", applicationUser.id)
        Map primaryDemandOrder = readNewPrimaryDemandOrderAction.execute(params, null)

        UserType userType = applicationUser.userType

        if(userType){
            if (userType.id == ApplicationConstants.USER_TYPE_OTHER && applicationUser.customerMasterId) {
                distributionPointList = (List)listDistributionPointByApplicationUser.execute(params, null)
            }else if(userType.id == ApplicationConstants.USER_TYPE_CUSTOMER && applicationUser.customerMasterId){
                distributionPointList = (List)listDistributionPointByApplicationUser.getListDistributionPointByDpWarehouseDefaultCustomer()
            }
        }
        render(view: "/demandOrder/newPrimaryDemandOrder/updateDemandOrder", model: [primaryDemandOrder: primaryDemandOrder, distributionPointList:distributionPointList])
    }

    def loadPrimaryDemandOrderDetails = {
        render listOrderDetailsByPrimaryOrderAction.execute(params, null) as JSON
    }

    def viewPrintInvoice = {
        List<CustomerSalesChannel> salesChannelList =  searchReportInfoAction.detailSalesChannelList()
        render(view: 'printSalesInvoice', model: [salesChannelList : salesChannelList as JSON,salesChannelSize : salesChannelList.size() ])
    }

    def viewInvoiceForRollback = {     // View for RollBack Received Invoice for DP
        render(view: 'viewInvoiceForRollback')
    }

    def primaryInvoiceListForRollback = {   // Invoice List for Rollback
        render listPrintInvoiceAction.primaryInvoiceListForRollback(params, null) as JSON
    }

    def rollbackReceivedPrimaryInvoice = {   // Execute Rollback
        render rollbackReceivedPrimaryInvoiceAction.execute(params, null) as JSON
    }

    def viewPrintSecondaryInvoice={
        render(view: 'printSecondarySalesInvoice')
    }

    def viewCancelInvoice = {
        render(view: 'cancelSalesInvoice')
    }

    def printInvoiceList = {
        render listPrintInvoiceAction.execute(params, null) as JSON
    }

    def printSecondaryInvoiceList = {
        render listPrintInvoiceAction.secondaryInvoiceList(params, null) as JSON
    }

    def cancelInvoiceList = {
        render listPrintInvoiceAction.cancelInvoiceList(params,null) as JSON
    }

    def viewSecondaryInvoice={
        render(view: 'cancelSecondaryInvoice')
    }

    def viewCancelSecondaryInvoiceList={
        render listSecondaryInvoiceAction.secondaryInvoiceList(params,null) as JSON
    }

    def cancelSecondaryInvoice={
        ApplicationUser applicationUser = session?.applicationUser
        Message message = cancelSecondaryInvoiceAction.execute(params, applicationUser)
        render message as JSON
    }

    def printInvoice ={
        ApplicationUser applicationUser = session?.applicationUser
        Message message = savePrintStatusAction.execute(params, applicationUser)
        render message as JSON
    }

    def printSecondaryInvoice = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = saveSecondaryPrintStatusAction.execute(params, applicationUser)
        render message as JSON
    }

    def cancelInvoice = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = cancelSalesInvoiceAction.execute(params, applicationUser)
        render message as JSON
    }

    // Print Primary Invoice
    def rptPrintInvoice = {
//        List list = rptPrintInvoiceAction.execute(params,null)
//        if (list.size() > 0) {
//            params.SUB_REPORT_DIR = servletContext.getRealPath("/") + "reports/invoice/"
//            Map map = rptPrintInvoiceAction.postCondition(params, null)
//            chain(controller: 'docuJasper', action: 'index', model: [data: null], params: map.params)
//        } else {
//            render 'no data found '
//        }
        List userInfo = getAuthorizedEmployeeInfoAction.getAuthorizedEmployeeInfo()
        params.put("userName", userInfo.userName[0])
        params.put("name", userInfo.name[0])
        params.put("pin", userInfo.pin[0])
        params.put("departmentName", userInfo.departmentName[0])
        params.put("designationName", userInfo.designationName[0])

        if(params.invoiceNo){
            String invoiceNo = params.invoiceNo
            if(invoiceNo.contains("DI")){
                Invoice directInvoice = Invoice.findByCode(invoiceNo)
                if(directInvoice){
                    params.invoiceIds = directInvoice.id.toString()
                }
            }
        }

        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/invoice/"
//        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "invoice/print_invoice.jasper"
        String[] ids = params.invoiceIds.split(",")
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        for (int i = 0; i < ids.length; i++) {
            if (ids[i]) {
                params.put('invoiceId', ids[i])
//                params.put('reportCount', i+1)
                params._name = "SalesInvoice" + params.invoiceId + "_" + DateUtil.now()
                Map values = params.clone()
                reportDefTemp = new JasperReportDef(name: params._file,
                        parameters: values,
                        fileFormat: JasperExportFormat.PDF_FORMAT)
                reportDef.add(reportDefTemp)
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            render e.message
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()


    }
    def showUpdateDeliveryDate = {
        render(view: 'updateDeliveryDate', model: [ids: params.ids])
    }

    def listForUpdateDeliveryDate = {
        ApplicationUser applicationUser = session?.applicationUser
        render listForUpdateDeliveryDateAction.execute(params, applicationUser) as JSON
    }

    def updateDeliveryDate = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateDeliveryDateAction.execute(params, applicationUser)
        render message as JSON
    }

    def readCustomerBalanceAndAddress = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("applicationUserId", applicationUser.id)
        Map result = (Map) readCustomerBalanceAndShippingAddressAction.execute(params, null)
        render result as JSON
    }

    def cancelNewDemandOrder = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("applicationUserId", applicationUser.id)
        Message message = cancelNewPrimaryDemandOrderAction.execute(params, null)
        render message as JSON
    }
    def listGridForCashThroughBank={
        ApplicationUser applicationUser = session?.applicationUser
        render listGridForCashThroughBankAction.execute(params, applicationUser) as JSON
    }
    def listEnterpriseInfo = {
        render listInfoByEnterpriseAction.execute(params, null) as JSON
    }

    def fetchDefaultCustomer = {
        render fetchDefaultCustomerFromDpAction.execute(params, null) as JSON
    }

    def rptPrintSecondaryInvoice = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/invoice/"
        params._file = "invoice/print_secondary_invoice.jasper"

        List userInfo = getAuthorizedEmployeeInfoAction.getAuthorizedEmployeeInfo()
        params.put("userName", userInfo.userName[0])
        params.put("name", userInfo.name[0])
        params.put("pin", userInfo.pin[0])
        params.put("departmentName", userInfo.departmentName[0])
        params.put("designationName", userInfo.designationName[0])

        String[] ids = params.invoiceNo.split(',')
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        for (int i = 0; i < ids.length; i++) {
            if (ids[i]) {
                params.put('invoiceNo', ids[i])
                params._name = "SecondaryInvoice" + params.invoiceNo + "_" + DateUtil.now()
                Map values = params.clone()
                reportDefTemp = new JasperReportDef(name: params._file,
                        parameters: values,
                        fileFormat: JasperExportFormat.PDF_FORMAT)
                reportDef.add(reportDefTemp)
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            render e.message
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()

    }

    def rptPrintDeliveryChallan = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/invoice/"
        params._file = "invoice/print_primary_delivery_chillan.jasper"
        String[] ids = params.invoiceNo.split(',')
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        for (int i = 0; i < ids.length; i++) {
            if (ids[i]) {
                params.put('invoiceNo', ids[i])
//                params.put('reportCount', i+1)
                params._name = "SalesInvoice" + params.invoiceNo + "_" + DateUtil.now()
                Map values = params.clone()
                reportDefTemp = new JasperReportDef(name: params._file,
                        parameters: values,
                        fileFormat: JasperExportFormat.PDF_FORMAT)
                reportDef.add(reportDefTemp)
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception ex) {
            render("Unexpected Error")
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def rptPrintSecondaryDeliveryChallan = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/invoice/"
        params._file = "invoice/print_secondary_delivery_chillan.jasper"
        String[] ids = params.invoiceNo.split(',')
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        for (int i = 0; i < ids.length; i++) {
            if (ids[i]) {
                params.put('invoiceNo', ids[i])
//                params.put('reportCount', i+1)
                params._name = "SecondaryInvoice" + params.invoiceNo + "_" + DateUtil.now()
                Map values = params.clone()
                reportDefTemp = new JasperReportDef(name: params._file,
                        parameters: values,
                        fileFormat: JasperExportFormat.PDF_FORMAT)
                reportDef.add(reportDefTemp)
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()

    }

    def loadDataEligibilitycheck = {
//        List list = orderNoAutoCompleteListAction.customerCOAdetails(params)
//        render(view: '/primaryDemandOrder/popupEligibilityCriteria',model:[customerId:params.id,list:list])
        List list = orderNoAutoCompleteListAction.customerCOAdetails(params)
        render list as JSON
    }
    def popUpEligibility = {
//        ApplicationUser applicationUser = session?.applicationUser
        render(view: '/primaryDemandOrder/popupEligibilityCriteria',model:[params:params])

    }


    def newpopUpEligibility = {
        render(view: '/demandOrder/newPrimaryDemandOrder/newpopupEligibilityCriteria',model:[params:params])

    }

    def listCustomerForPrintPrimaryInvoice = {
        render listCustomerForPrintPrimaryInvoiceAction.execute(params, null) as JSON
    }

    def popupCustomerListPanelForPrintPrimaryInvoice = {
        List customerList = (List) listCustomerForPrintPrimaryInvoiceAction.execute(params, null)
        render(view: 'popupCustomerListForPrint', model: [aaData: customerList as JSON])
    }

}
