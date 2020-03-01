package com.bits.bdfp.finance

import com.bits.bdfp.customer.customermaster.ListCustomerByEnterpriseAction
import com.bits.bdfp.finance.customerpayment.CancelPaymentAction
import com.bits.bdfp.finance.customerpayment.CreateCustomerPaymentAction
import com.bits.bdfp.finance.customerpayment.CreateCustomerPaymentDepositPoolAction
import com.bits.bdfp.finance.customerpayment.CreateDepositCashToDepositPoolAction
import com.bits.bdfp.finance.customerpayment.FetchCashPoolBalanceAction
import com.bits.bdfp.finance.customerpayment.ListAccountByCustomerAction
import com.bits.bdfp.finance.customerpayment.ListCashPoolByCustomerAction
import com.bits.bdfp.finance.customerpayment.ListCashPoolForDepositAction
import com.bits.bdfp.finance.customerpayment.ListCurrencyDenominationAction
import com.bits.bdfp.finance.customerpayment.ListCustomerByDPAction
import com.bits.bdfp.finance.customerpayment.ListCustomerByUserAction
import com.bits.bdfp.finance.customerpayment.ListDepositCashPaymentAction
import com.bits.bdfp.finance.customerpayment.ListDepositPoolForDepositAction
import com.bits.bdfp.finance.customerpayment.ListNonFactoryDpAction
import com.bits.bdfp.finance.customerpayment.ListSecurityDepositPaymentAction
import com.bits.bdfp.finance.customerpayment.ListTransactionNoAction
import com.bits.bdfp.finance.customerpayment.ListWithdrawDenominationAction
import com.bits.bdfp.finance.customerpayment.RollbackDepositToHoAction
import com.bits.bdfp.finance.customerpayment.WithdrawCashFromDepositPoolAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.CreateSecondaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.DeleteSecondaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.ListForUpdateDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.ListMRNoAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.ListSecondaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.LoadOrderNoAutoCompleteAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.LoadOrderNoAutoCompleteForUpdateAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.ReadSecondaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.SearchSecondaryDemandOrderAction
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.UpdateSecondaryDemandOrderAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductByEnterpriseAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductByOrderForUpdateAction
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.ApplicationUserDistributionPoint
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class CustomerPaymentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Autowired
    private CreateSecondaryDemandOrderAction createSecondaryDemandOrderAction
    @Autowired
    private UpdateSecondaryDemandOrderAction updateSecondaryDemandOrderAction
    @Autowired
    private ListSecondaryDemandOrderAction listSecondaryDemandOrderAction
    @Autowired
    private DeleteSecondaryDemandOrderAction deleteSecondaryDemandOrderAction
    @Autowired
    private ReadSecondaryDemandOrderAction readSecondaryDemandOrderAction
    @Autowired
    private SearchSecondaryDemandOrderAction searchSecondaryDemandOrderAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListCustomerByEnterpriseAction listCustomerByEnterpriseAction
    @Autowired
    ListProductByEnterpriseAction listProductByEnterpriseAction
    @Autowired
    ListForUpdateDemandOrderAction listForUpdateDemandOrderAction
    @Autowired
    LoadOrderNoAutoCompleteAction loadOrderNoAutoCompleteAction
    @Autowired
    LoadOrderNoAutoCompleteForUpdateAction loadOrderNoAutoCompleteForUpdateAction
    @Autowired
    ListProductByOrderForUpdateAction listProductByOrderForUpdateAction
    @Autowired
    CreateCustomerPaymentAction createCustomerPaymentAction
    @Autowired
    ListCurrencyDenominationAction listCurrencyDenominationAction
    @Autowired
    ListMRNoAction listMRNoAction
    @Autowired
    ListAccountByCustomerAction listAccountByCustomerAction
    @Autowired
    ListCashPoolByCustomerAction listCashPoolByCustomerAction
    @Autowired
    ListDepositCashPaymentAction listDepositCashPaymentAction
    @Autowired
    CreateCustomerPaymentDepositPoolAction createCustomerPaymentDepositPoolAction
    @Autowired
    ListSecurityDepositPaymentAction listSecurityDepositPaymentAction
    @Autowired
    ListCustomerByUserAction listCustomerByUserAction
    @Autowired
    ListNonFactoryDpAction listNonFactoryDpAction
    @Autowired
    ListCashPoolForDepositAction listCashPoolForDepositAction
    @Autowired
    ListDepositPoolForDepositAction listDepositPoolForDepositAction
    @Autowired
    CreateDepositCashToDepositPoolAction createDepositCashToDepositPoolAction
    @Autowired
    ListCustomerByDPAction listCustomerByDPAction
    @Autowired
    FetchCashPoolBalanceAction fetchCashPoolBalanceAction
    @Autowired
    ListTransactionNoAction listTransactionNoAction
    @Autowired
    WithdrawCashFromDepositPoolAction withdrawCashFromDepositPoolAction
    @Autowired
    ListWithdrawDenominationAction listWithdrawDenominationAction
    @Autowired
    RollbackDepositToHoAction rollbackDepositToHoAction
    @Autowired
    CancelPaymentAction cancelPaymentAction

    def jasperService

    def list = {
        ApplicationUser applicationUser = session?.applicationUser
        render listSecondaryDemandOrderAction.execute(params, applicationUser) as JSON

    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        List isFactory = listAccountByCustomerAction.preCondition(null, applicationUser)
        String currentDate = DateUtil.getCurrentDateFormatAsString()
        Map result = ["results": list, "total": list.size()]
        render(template: "show", model: [ result: result as JSON, currentDate: currentDate,
                                         list                : list, applicationUser: applicationUser,
                                         isFactory           : isFactory[0] ? isFactory[0].is_factory : false])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createCustomerPaymentAction.execute(params, applicationUser)
        render message as JSON
    }
    def listCurrencyDenomination = {
        ApplicationUser applicationUser = session?.applicationUser
        render listCurrencyDenominationAction.execute(params, applicationUser) as JSON
    }

    def showPaymentReceipt = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": list, "total": list.size()]
        render(template: "showSalesReceipt", model: [enterpriseList: result as JSON, list: list, applicationUser: applicationUser])
    }

    def listMRNo = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('userId', applicationUser.id)
        List list = listMRNoAction.execute(params, null)
        render listMRNoAction.postCondition(null, list) as JSON
    }

    def listMRNoForAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('userId', applicationUser.id)
        List list = listMRNoAction.listMRNoForAutoComplete(params, null)
        render list as JSON
       // render listMRNoAction.postCondition(null, list) as JSON
    }
    def generateInvoiceReport = {
        params.SUB_REPORT_DIR = servletContext.getRealPath("/") + "reports/moneyReceipt/"
        params._file = "moneyReceipt/moneyReceipt.jasper"
        params.company_logo = servletContext.getRealPath("/") + "images/company_logo/company_logo.png"
        String[] ids = params.mr.split(',')
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        for (int i = 0; i < ids.length; i++) {
            if (ids[i]) {
                params.put('mr', ids[i])
                params._name = "MoneyReceipt_" + params.mr + "_" + DateUtil.now()
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

    def listAccount = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = listAccountByCustomerAction.execute(params, applicationUser)
        render list as JSON
    }

    def listCashPool = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = listCashPoolByCustomerAction.execute(params, applicationUser)
        render list as JSON
    }
    def listCashDepositInfo = {
        render listDepositCashPaymentAction.execute(params, null) as JSON
    }
    def listCashDepositAmount = {
        render listDepositCashPaymentAction.getListForCashDepositPaymentsTotal(params, null) as JSON
    }
    def depositCashToPoolShow = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        params.put('entId', list[0].id)
        Map result = listCashPoolByCustomerAction.execute(params, applicationUser)
        List poolList = result.get('cash')
        List depositPoolList = result.get('deposit')
        List bankList = listAccountByCustomerAction.execute(params, applicationUser)
        render(template: "/depositCashToPool/show", model: [poolList: poolList, bankList: bankList, depositPoolList: depositPoolList])
    }
    def createDepositCashToPool = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createCustomerPaymentDepositPoolAction.execute(params, applicationUser)
        render message as JSON
    }

    def customerAutoComplete = {
        render listCustomerByDPAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = new ArrayList()
        customerList = (List) listCustomerByDPAction.execute(params, null)
        render(template: '/customerPayment/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def listSecurityDeposit = {
        ApplicationUser applicationUser = session?.applicationUser
        render listSecurityDepositPaymentAction.execute(params, applicationUser) as JSON
    }

    def showDepositCashToDepositPool = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        String recentDate = DateUtil.getCurrentDateFormatAsString()
        Map result = ["results": list, "total": list.size()]
        render(view: "/depositCashToPool/showDeposit", model: [result: result as JSON, recentDate: recentDate, list: list])
    }

    def loadDp = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("id",applicationUser.id)
        render listNonFactoryDpAction.execute(params, null) as JSON
    }

    def loadCashPool = {
        render listCashPoolForDepositAction.execute(params, null) as JSON
    }
    def totalReceivableAmount = {
        render listCashPoolForDepositAction.totalReceivableAmount(params, null) as JSON
    }

    def loadDepositPool = {
        render listDepositPoolForDepositAction.execute(params, null) as JSON
    }

    def createDepositCashToDepositPool = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createDepositCashToDepositPoolAction.execute(params, applicationUser)
        render message as JSON
    }

    def fetchCashPoolBalance = {
        Map cashPoolBalance = (Map)fetchCashPoolBalanceAction.execute(params, null)
        render cashPoolBalance as JSON
    }
    def fetchCashPoolBalanceForCashInHandFromCOA= {
        render fetchCashPoolBalanceAction.fetchCashPoolBalanceForCashInHandFromCOA(params, null) as JSON
    }

    def showWithdrawCashFromDepositPool = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        String recentDate = DateUtil.getCurrentDateFormatAsString()
        Map result = ["results": list, "total": list.size()]
        render(view: "/withdrawCashFromDepositPool/show", model: [result: result as JSON, recentDate: recentDate, list: list])
    }

    def fetchTransactionNo = {
        render listTransactionNoAction.execute(params, null) as JSON
    }

    def withdrawCashFromDepositPool = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = withdrawCashFromDepositPoolAction.execute(params, applicationUser)
        render message as JSON
    }

    def listDenomination = {
        List list = listWithdrawDenominationAction.execute(params, null)
        render listWithdrawDenominationAction.postCondition(null, list) as JSON
//        render listWithdrawDenominationAction.execute(params, null) as JSON
    }

    def rollBackDepositToHo = {
        ApplicationUser applicationUser = session?.applicationUser
        List<DistributionPoint> dpList = new ArrayList<DistributionPoint>()
        List<ApplicationUserDistributionPoint> applicationUserDistributionPointList = ApplicationUserDistributionPoint.findAllByApplicationUser(applicationUser)
        applicationUserDistributionPointList.each {
            if(!it.distributionPoint.isFactory){
                dpList.add(it.distributionPoint)
            }
        }
        render(view: "/rollBackDepositToHo/show", model: [dpList:dpList])
    }

    def getDepositPool = {
        DistributionPoint distributionPoint = DistributionPoint.read(params.dpId)
//        List<DepositCashToDepositPool> depositCashToDepositPoolList = DepositCashToDepositPool.findAllByHoDepositAndStatus(true,ApplicationConstants.NOT_ACKNOWLEDGED)
        List<DepositCashToDepositPool> depositCashToDepositPoolList = DepositCashToDepositPool.withCriteria(){
            eq('hoDeposit', true)
            eq('status', ApplicationConstants.NOT_ACKNOWLEDGED)
            eq('distributionPoint', distributionPoint)
        }
        render depositCashToDepositPoolList as JSON
    }

    def executeRollback = {
        ApplicationUser applicationUser = session.applicationUser
        render rollbackDepositToHoAction.execute(params,applicationUser) as JSON
    }

    def cancelPayment = {
        ApplicationUser applicationUser = session.applicationUser
        render cancelPaymentAction.execute(params,applicationUser) as JSON
    }
}
