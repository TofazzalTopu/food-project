package com.bits.bdfp.bill

import com.bits.bdfp.bill.createbill.CreateBillAction
import com.bits.bdfp.bill.createbill.DeleteCreateBillAction
import com.bits.bdfp.bill.createbill.ListCreateBillAction
import com.bits.bdfp.bill.createbill.UpdateCreateBillAction
import com.bits.bdfp.bill.createbill.ReadCreateBillAction
import com.bits.bdfp.bill.createbill.SearchCreateBillAction
import com.bits.bdfp.customer.customermaster.ListCustomerByGeoLocationAction
import com.bits.bdfp.geolocation.territoryconfiguration.SearchTerritoryConfigurationAction
import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON
import java.text.SimpleDateFormat


class CreateBillController {

    @Autowired
    private CreateBillAction createBillAction
    @Autowired
    private UpdateCreateBillAction updateCreateBillAction
    @Autowired
    private ListCreateBillAction listCreateBillAction
    @Autowired
    private DeleteCreateBillAction deleteCreateBillAction
    @Autowired
    private ReadCreateBillAction readCreateBillAction
    @Autowired
    private SearchCreateBillAction searchCreateBillAction

    @Autowired
    ListCustomerByGeoLocationAction listCustomerByGeoLocationAction
    @Autowired
    private SearchTerritoryConfigurationAction searchTerritoryConfigurationAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"
    def jasperService

    def list = {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = new Date()
        params.billGenerationDate = sdf.format(dt)

        Object objList = listCreateBillAction.execute(params, null)
        render listCreateBillAction.postCondition(objList, null) as JSON
    }
    def listByCriteria = {
        List list = (List) listCreateBillAction.listByCriteria(params, null)
        render listCreateBillAction.postCondition(null, list) as JSON
    }
    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = searchTerritoryConfigurationAction.fetchTerritoryByUserId(params, null)

        render(view: "show", model: [list: list])
    }

    def view = {
        render(view: "view")
    }

    def create = {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = new Date()
        params.billGenerationDate = sdf.format(dt)
        Message message = createBillAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readCreateBillAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateCreateBillAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteCreateBillAction.execute(params, null)
        render message as JSON
    }

    def deleteBill = {
        Invoice.executeUpdate(" UPDATE Invoice SET isBill=0 WHERE code IN (SELECT invoiceNumber FROM CreateBill WHERE billNumber ='bill3')")
        CreateBill.executeUpdate("update  CreateBill set isActive= 0 where billNumber='" + params.billNumber + "'")
        Message message = deleteCreateBillAction.executeDeleteBill(params, null)
        render message as JSON
    }

    def search = {
        CreateBill createBill = (CreateBill) searchCreateBillAction.execute(params, null)
        if (createBill) {
            render createBill as JSON
        } else {
            render ''
        }
    }

    def searchBill = {

        Object createBill = (CreateBill) searchCreateBillAction.searchBill(params, null)

        if (createBill) {
            render createBill as JSON
        } else {
            render ''
        }

    }

    def listCustomerByCode = {
        render listCreateBillAction.executeCustomerByCode(params, null) as JSON
    }
    def listCustomerCodeByCriteria = {
        render listCreateBillAction.searchBillByCriteria(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = new ArrayList()
        // params.put("categoryId", ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID)
        // customerList = (List) listCreateBillAction.searchCustomerByGeoLocation(params, null)
        customerList = (List) listCreateBillAction.executeCustomerByCode(params, null)
        render(view: '/createBill/popupCustomerList', model: [aaData: customerList as JSON])
    }



    def billReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/createBill/"
        params._file = "createBill/newBill.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        params.put('po', params.po)

        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            e.printStackTrace();
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        //response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showEditBillForm = {

        //Object createBill = listCreateBillAction.findByBill(params, null)
        // List<CreateBill> createBill = listCreateBillAction.execute(params, null)
        //render(view:"popUpPrintBill", model:[createBill:createBill]) as JSON
        Object objList = listCreateBillAction.findByBill(params, null)
        render(view: "popUpPrintBill")
    }
    def findListByBill = {

        Object objList = listCreateBillAction.findByBill(params, null)
        render listCreateBillAction.postCondition(objList, null) as JSON
    }
    def editBill = {
        render(view: "popUpPrintBill")
    }

    def viewReportBeforeCreateBill = {
        Message message = new Message();
        String js;

        List list = createBillAction.executeReport(params, null)
        if (list.size() > 0) {
            Map map = createBillAction.postCondition(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }

    /*def popupProductListPanel = {
        render(view: '/createBill/popupProductList', model: ['customerId': params.customerId,'territorySubAreaId': params.territorySubAreaId])
    }*/
}
