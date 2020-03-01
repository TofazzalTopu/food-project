package com.bits.bdfp.inventory.retailorder

import com.bits.bdfp.customer.customermaster.ListCustomerByApplicationUserAction
import com.bits.bdfp.inventory.product.productprice.GetProductPriceByCustomerAndProductAction
import com.bits.bdfp.inventory.retailorder.processretailorder.AutoProcessRetailOrderAction
import com.bits.bdfp.inventory.retailorder.processretailorder.CancelRetailInvoiceAction
import com.bits.bdfp.inventory.retailorder.processretailorder.ListCustomerAvailableStockAction
import com.bits.bdfp.inventory.retailorder.processretailorder.ListManualProcessOrderDetailsAction
import com.bits.bdfp.inventory.retailorder.processretailorder.ListRetailInvoiceAction
import com.bits.bdfp.inventory.retailorder.processretailorder.ListRetailOrderDetailsForProcessAction
import com.bits.bdfp.inventory.retailorder.processretailorder.ListRetailOrderForProcessAction
import com.bits.bdfp.inventory.retailorder.processretailorder.ListSecondaryOrderNoForProcessAction
import com.bits.bdfp.inventory.retailorder.processretailorder.ManualProcessRetailOrderAction
import com.bits.bdfp.inventory.retailorder.retailorder.ReadRetailOrderAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import com.docu.security.UserType
import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class ProcessRetailOrderController {

    SpringSecurityService  springSecurityService
    @Autowired
    ListRetailOrderForProcessAction listRetailOrderForProcessAction
    @Autowired
    ListRetailOrderDetailsForProcessAction listRetailOrderDetailsForProcessAction
    @Autowired
    ListSecondaryOrderNoForProcessAction listSecondaryOrderNoForProcessAction
    @Autowired
    GetProductPriceByCustomerAndProductAction getProductPriceByCustomerAndProductAction
    @Autowired
    ReadRetailOrderAction readRetailOrderAction
    @Autowired
    ListManualProcessOrderDetailsAction listManualProcessOrderDetailsAction
    @Autowired
    ManualProcessRetailOrderAction manualProcessRetailOrderAction
    @Autowired
    AutoProcessRetailOrderAction autoProcessRetailOrderAction
    @Autowired
    ListRetailInvoiceAction listRetailInvoiceAction
    @Autowired
    ListCustomerByApplicationUserAction listCustomerByApplicationUserAction
    @Autowired
    CancelRetailInvoiceAction cancelRetailInvoiceAction
    @Autowired
    ListCustomerAvailableStockAction listCustomerAvailableStockAction
    def jasperService

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def listOrderForProcess = {
        render listRetailOrderForProcessAction.execute(params, null) as JSON
    }

    def listOrderDetailsForProcess = {
        render listRetailOrderDetailsForProcessAction.execute(params, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        UserType userType = applicationUser.userType
        if(userType && userType.id == ApplicationConstants.USER_TYPE_CUSTOMER ){
            render(view: '/retailOrder/processRetailOrder/show')
        }else{
            render(view: '/retailOrder/consolidateRetailOrder/unAuthorized')
        }
    }

    def secondaryOrderAutoCompleteForProcess = {
        render listSecondaryOrderNoForProcessAction.execute(params, null) as JSON
    }

    def showManualProcess = {
        RetailOrder retailOrder =  (RetailOrder)readRetailOrderAction.execute(params, null)
        render(template: "/retailOrder/processRetailOrder/manualProcess", model: [retailOrder: retailOrder])
    }

    def listManualOrderDetails = {
        render listManualProcessOrderDetailsAction.execute(params, null) as JSON
    }

    def processManually = {
        Message message = manualProcessRetailOrderAction.execute(params, null)
        render message as JSON
    }

    def processAutomatically = {
        Message message = autoProcessRetailOrderAction.execute(params, null)
        render message as JSON
    }

    def searchInvoice = {
        render(view: "/retailOrder/processRetailOrder/printRetailInvoice")
    }

    def listRetailInvoice = {
        render listRetailInvoiceAction.execute(params, null) as JSON
    }

    def rptPrintRetailInvoice = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/invoice/"
        params._file = "invoice/print_retail_invoice.jasper"
        String[] ids = params.invoiceIds.split(",")
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        for (int i = 0; i < ids.length; i++) {
            if (ids[i]) {
                params.put('invoiceId', ids[i])
                params._name = "RetailInvoice_" + DateUtil.now()
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
            render ex.message
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def viewCancelInvoice = {
        render(view: "/retailOrder/cancelInvoice/show")
    }

    def popupCustomerListPanel = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.put("customerId", applicationUser.customerMasterId)
        params.put("categoryId", ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID)
        List customerList = new ArrayList()
        customerList = (List) listCustomerByApplicationUserAction.execute(params, null)

        render(view: '/retailOrder/cancelInvoice/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def cancelRetailInvoice = {
        Message message = cancelRetailInvoiceAction.execute(params, null)
        render message as JSON
    }

    def popupAvailableProductListPanel = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.put("customerId", applicationUser.customerMasterId)
        List productListList = new ArrayList()
        productListList = (List) listCustomerAvailableStockAction.execute(params, null)
        render(view: '/retailOrder/processRetailOrder/popupAvailableProductList', model: [aaData: productListList as JSON])
    }

    def listProductAutoComplete = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.put("customerId", applicationUser.customerMasterId)
        render listCustomerAvailableStockAction.execute(params,null) as JSON
    }

    def productPriceByCustomerAndProduct = {
        Map productPrice = (Map) getProductPriceByCustomerAndProductAction.execute(params, null)
        Map result = [productPrice: productPrice.productPriceWithVat]
        render result as JSON
    }
}
