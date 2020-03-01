package com.bits.bdfp.reports

import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired


class UhtSaleAndStockStatementController {

    def jasperService
    static defaultAction = "show"

    @Autowired
    private SearchReportInfoAction searchReportInfoAction


    def show = {
        render(view:"show")
    }

    def generateUHTStockStatement = {
        Message message = new Message();
        String js;

        List list = searchReportInfoAction.getSaleStockStatement(params, null)
        if (list.size() > 0) {
            Map map = searchReportInfoAction.checkConditionForUHTStatement(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render ("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }

    def generateUhtSaleStockStatementDetails = {
        Message message = new Message();
        String js;

        List list = searchReportInfoAction.executeUhtSaleStatementDetails(params, null)
        if (list.size() > 0) {
            Map map = searchReportInfoAction.checkConditionForUHTStatementDetails(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render ("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }


    def printLedger = {
        params._file = "UHTSale/uhtSaleStockStatementDetails.jasper"

        //params._name = "LedgerReport_" + DateUtil.now()
        ApplicationUser applicationUser = session?.applicationUser
        params.put('username',applicationUser.username)

        params.postfixCode=params.postfixCode
        params.prefixCode=params.prefixCode
        def reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        byteArrayOutputStream = jasperService.generateReport(reportDef)

        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }
}
