package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.inventory.demandorder.primarydemandorder.GetAuthorizedEmployeeInfoAction
import com.bits.bdfp.inventory.demandorder.primarydemandorder.RptPrintLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.CreateLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.DeleteLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.InvoiceNoAutoCompleteAction
import com.bits.bdfp.inventory.sales.loadingslip.ListInvoiceAction
import com.bits.bdfp.inventory.sales.loadingslip.ListLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.ListPrintLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.LoadingSlipNoAutoCompleteAction
import com.bits.bdfp.inventory.sales.loadingslip.UpdateLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.ReadLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.SearchLoadingSlipAction
import com.bits.bdfp.inventory.sales.loadingslip.UpdateLoadingSlipPrintStatusAction
import com.bits.bdfp.reports.SearchReportInfoAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import groovy.sql.GroovyRowResult
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class LoadingSlipController {

    @Autowired
    private CreateLoadingSlipAction createLoadingSlipAction
    @Autowired
    private UpdateLoadingSlipAction updateLoadingSlipAction
    @Autowired
    private ListLoadingSlipAction listLoadingSlipAction
    @Autowired
    private DeleteLoadingSlipAction deleteLoadingSlipAction
    @Autowired
    private ReadLoadingSlipAction readLoadingSlipAction
    @Autowired
    private SearchLoadingSlipAction searchLoadingSlipAction
    @Autowired
    ListInvoiceAction listInvoiceAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    InvoiceNoAutoCompleteAction invoiceNoAutoCompleteAction
    @Autowired
    ListPrintLoadingSlipAction listPrintLoadingSlipAction
    @Autowired
    LoadingSlipNoAutoCompleteAction loadingSlipNoAutoCompleteAction
    @Autowired
    UpdateLoadingSlipPrintStatusAction updateLoadingSlipPrintStatusAction
    @Autowired
    RptPrintLoadingSlipAction rptPrintLoadingSlipAction
    @Autowired
    DeleteLoadingSlipAction deleteLoadingSlipAction
    @Autowired
    SearchReportInfoAction searchReportInfoAction
    @Autowired
    GetAuthorizedEmployeeInfoAction getAuthorizedEmployeeInfoAction

    def jasperService

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listLoadingSlipAction.execute(params, null)
        render listLoadingSlipAction.postCondition(null, list) as JSON
    }

    def show = {
        LoadingSlip loadingSlip = new LoadingSlip()
        ApplicationUser applicationUser = session?.applicationUser

        List<GroovyRowResult> list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        List<CustomerSalesChannel> salesChannelList = searchReportInfoAction.detailSalesChannelList()
        render(template: "show", model: [loadingSlip: loadingSlip, enterpriseList: list, salesChannelList: salesChannelList, salesChannelSize: salesChannelList.size()])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createLoadingSlipAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        render readLoadingSlipAction.execute(params, null) as JSON
    }

    def update = {
        LoadingSlip loadingSlip = new LoadingSlip(params)
        Object object = updateLoadingSlipAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateLoadingSlipAction.getValidationErrorMessageForUI(loadingSlip)
        } else {
            int noOfRows = (int) updateLoadingSlipAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateLoadingSlipAction.getSuccessMessageForUI(loadingSlip, updateLoadingSlipAction.SUCCESS_UPDATE)
            } else {
                message = updateLoadingSlipAction.getErrorMessageForUI(loadingSlip, updateLoadingSlipAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        LoadingSlip loadingSlip = deleteLoadingSlipAction.execute(params, null);
        Message message = null
        if (loadingSlip) {
            int rowCount = (int) deleteLoadingSlipAction.preCondition(null, loadingSlip);
            if (rowCount > 0) {
                message = deleteLoadingSlipAction.getSuccessMessageForUI(loadingSlip, deleteLoadingSlipAction.SUCCESS_DELETE);
            } else {
                message = deleteLoadingSlipAction.getErrorMessageForUI(loadingSlip, deleteLoadingSlipAction.FAIL_DELETE);
            }
        } else {
            message = deleteLoadingSlipAction.getErrorMessageForUI(loadingSlip, deleteLoadingSlipAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        LoadingSlip loadingSlip = searchLoadingSlipAction.execute(params.fieldName, params.fieldValue)
        if (loadingSlip) {
            render loadingSlip as JSON
        } else {
            render ''
        }

    }

    def listInvoices = {
        List list = listInvoiceAction.execute(params, null)
        render listInvoiceAction.postCondition(null, list) as JSON
    }

    def listInvoiceForAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        render invoiceNoAutoCompleteAction.execute(params, applicationUser) as JSON
    }

    def printLoadingSlip = {
        render(view: '/loadingSlip/printLoadingSlip')
    }

    def cancelLoadingSlip = {
        render(view: '/loadingSlip/cancelLoadingSlip')
    }

    def getLoadingSlipByInvoiceNumber = {
        List list = deleteLoadingSlipAction.getLoadingSlipByInvoiceNumber(params)
        render deleteLoadingSlipAction.postCondition(null, list) as JSON
    }

    def deleteLoadingSlipByInvoiceNumber = {
        Message message = deleteLoadingSlipAction.deleteLoadingSlipDetails(params, null);
        render message as JSON;
    }

    def loadingSlipList = {
        ApplicationUser applicationUser = session?.applicationUser
        render listPrintLoadingSlipAction.execute(params, applicationUser) as JSON
    }

    def loadingSlipNoAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        render loadingSlipNoAutoCompleteAction.execute(params, applicationUser) as JSON
    }

    def printLoadingSlipCount = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateLoadingSlipPrintStatusAction.execute(params, applicationUser)
        render message as JSON
    }

    def printLoadingSlipAsPdf = {
//        if (params.slipNo) {
//            params.SUB_REPORT_DIR = servletContext.getRealPath("/") + "reports/loadingSlip/"
//            Map map = rptPrintLoadingSlipAction.postCondition(params, null)
//            chain(controller: 'docuJasper', action: 'index', model: [data: null], params: map.params)
//        } else {
//            render 'No Data Found!'
//        }

        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/loadingSlip/"
        params._file = "loadingSlip/print_loading_slip.jasper"

        List userInfo = getAuthorizedEmployeeInfoAction.getAuthorizedEmployeeInfo()
        params.put("userName", userInfo.userName[0])
        params.put("name", userInfo.name[0])
        params.put("pin", userInfo.pin[0])
        params.put("departmentName", userInfo.departmentName[0])
        params.put("designationName", userInfo.designationName[0])

        String[] ids = params.slipNo.split(',')
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        for (int i = 0; i < ids.length; i++) {
            if (ids[i]) {
                params.put('inputLoadingSlipNumber', ids[i])
                params.put('reportCount', i + 1)
                params._name = "LoadingSlip" + params.slipNo + "_" + DateUtil.now()
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
}
