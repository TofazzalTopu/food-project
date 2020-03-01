package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.customer.customermaster.ListCustomerAutoCompleteByApplicationUserAction
import com.bits.bdfp.customer.customermaster.ListCustomerByApplicationUserAction
import com.bits.bdfp.customer.customermaster.ListDpCustomerByApplicationUserAction
import com.bits.bdfp.customer.customermaster.ListPosCustomerAutoCompleteByApplicationUserAction
import com.bits.bdfp.customer.customermaster.ListPosCustomerByApplicationUserAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductByEnterpriseAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductPriceByCustomerAction
import com.bits.bdfp.inventory.sales.invoice.CreateInvoiceAction
import com.bits.bdfp.inventory.sales.invoice.ListBatchwiseProductForDirectInvoiceAction
import com.bits.bdfp.inventory.sales.invoice.ListCustomerForUnadjustedInvoiceAction
import com.bits.bdfp.inventory.sales.invoice.ListUnadjustedInvoiceAction
import com.bits.bdfp.inventory.setup.poscustomer.ReadPosCustomerByApplicationUserAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class InvoiceController {

    @Autowired
    ListCustomerByApplicationUserAction listCustomerByApplicationUserAction
    @Autowired
    ListDpCustomerByApplicationUserAction listDpCustomerByApplicationUserAction
    @Autowired
    ListPosCustomerByApplicationUserAction listPosCustomerByApplicationUserAction
    @Autowired
    ListCustomerAutoCompleteByApplicationUserAction listCustomerAutoCompleteByApplicationUserAction
    @Autowired
    ListPosCustomerAutoCompleteByApplicationUserAction listPosCustomerAutoCompleteByApplicationUserAction
    @Autowired
    ListBatchwiseProductForDirectInvoiceAction listBatchwiseProductForDirectInvoiceAction
    @Autowired
    CreateInvoiceAction createInvoiceAction
    @Autowired
    ListProductByEnterpriseAction listProductByEnterpriseAction
    @Autowired
    ReadPosCustomerByApplicationUserAction readPosCustomerByApplicationUserAction
    @Autowired
    ListProductPriceByCustomerAction listProductPriceByCustomerAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListCustomerForUnadjustedInvoiceAction listCustomerForUnadjustedInvoiceAction
    @Autowired
    ListUnadjustedInvoiceAction listUnadjustedInvoiceAction
    def jasperService

    def show = {
        Map customer = (Map) readPosCustomerByApplicationUserAction.execute(params, null)
        render(view: "show", model: [customer: customer])
    }

    def customerAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('customerId', applicationUser.id)
//        if(params.isPosCustomer != 'true'){
//            render listPosCustomerAutoCompleteByApplicationUserAction.execute(params, null) as JSON
//        } else {
//            render listCustomerAutoCompleteByApplicationUserAction.execute(params, null) as JSON
//        }
//        render listCustomerAutoCompleteByApplicationUserAction.execute(params, null) as JSON
        render listDpCustomerByApplicationUserAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList
        ApplicationUser applicationUser = session?.applicationUser
        params.put('customerId', applicationUser.id)
//        if(params.isPosCustomer == 'true'){
//            customerList = (List) listPosCustomerByApplicationUserAction.execute(params, null)
//        } else {
//            customerList = (List) listCustomerByApplicationUserAction.execute(params, null)
//        }
//        customerList = (List) listCustomerByApplicationUserAction.execute(params, null)
        customerList = (List) listDpCustomerByApplicationUserAction.execute(params, null)
        render(view: '/invoice/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def popupProductListPanel = {
//        List list = (List) listProductByEnterpriseAction.execute(params, null)
        render(view: '/invoice/popupProductList', model: ['customerId': params.customerId])
    }

//    def popupProductListPanel = {
//        render(view: '/invoivpopupProductList', model: ['customerId': params.customerId])
//    }

    def jsonProductList = {
        Map map = [:]
        List data = (List)listProductPriceByCustomerAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def listProductAutoComplete = {
        render listProductPriceByCustomerAction.execute(params, null) as JSON
    }

    def create = {
        params.isApprovalRequired = false
        params.isApproved = true
        ApplicationUser applicationUser = session?.applicationUser
        Message message = (Message) createInvoiceAction.execute(params, applicationUser)
        render message as JSON
    }

    def generateInvoiceReport = {
        params.SUB_REPORT_DIR = servletContext.getRealPath("/") + "reports/invoice/"
        params._name = "Invoice_" + params.inviceNo + "_"+ DateUtil.now()
        params._file = "invoice/rpt_invoice.jasper"
        def reportDef = new JasperReportDef(name:params._file,
                parameters: params,
                fileFormat:JasperExportFormat.PDF_FORMAT )

        ByteArrayOutputStream byteArrayOutputStream = jasperService.generateReport(reportDef)
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showUnadjusted = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": list, "total": list.size()]
        render(view: "showUnadjusted", model: [result:result as JSON, list: list])
    }

    def listCustomerByGeoAndCategory = {
        ApplicationUser applicationUser = session?.applicationUser
        render listCustomerForUnadjustedInvoiceAction.execute(params, applicationUser) as JSON
    }

    def popUpCustomerList = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = (List) listCustomerForUnadjustedInvoiceAction.execute(params, applicationUser)
        render(view: 'popUpCustomerListByCategoryAndGeo', model: [aaData: list as JSON])
    }

    def listInvoice = {
        ApplicationUser applicationUser = session?.applicationUser
        Object objList = listUnadjustedInvoiceAction.execute(params, applicationUser)
        render listUnadjustedInvoiceAction.postCondition(objList, null) as JSON
    }

    def listInvoiceByCustomerId = {
        ApplicationUser applicationUser = session?.applicationUser
        Object objList = listUnadjustedInvoiceAction.executeByCustomerId(params, applicationUser)
        render listUnadjustedInvoiceAction.postCondition(objList, null) as JSON
    }
    def productBatchDetails = {
        render listBatchwiseProductForDirectInvoiceAction.execute(params, null) as JSON
    }
}
