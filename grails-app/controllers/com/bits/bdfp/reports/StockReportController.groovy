package com.bits.bdfp.reports

import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

class StockReportController {
    def jasperService

    def showStockView = {
        render (view: "/reportList/stockReport/factoryStockReport")
    }

    def rptStockReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._name = "StockReport_" + DateUtil.now()
        params._file = "stockReport/rptFactoryStock.jasper"
        String searchCriteria = ""
        if(params.batchNo){
            searchCriteria += "Batch No: " + params.batchNo
        }
        params.searchCriteria = searchCriteria
        ApplicationUser applicationUser=session?.applicationUser
        params.put('username',applicationUser.username)
        if(!params.stockDate){
            params.stockDate = DateUtil.getCurrentDateFormatAsString()
        }

        params.stockDateDisplay = params.stockDate
        Date stockDate = DateUtil.getSimpleDate(params.stockDate)
        params.stockDate = DateUtil.getFlexibleDateFormatAsString(stockDate, CommonConstants.DATE_FORMAT_Y_M_D);

        def reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception ex) {
            log.error(e.message)
            render(ex.message)
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
