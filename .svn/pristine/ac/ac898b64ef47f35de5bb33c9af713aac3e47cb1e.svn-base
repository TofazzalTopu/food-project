package com.bits.bdfp.reports

import com.docu.commons.DateUtil
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class SalesManSecurityDuesController {
    def jasperService
    static defaultAction = "show"

    @Autowired
    SearchReportInfoAction searchReportInfoAction

    def show = {
        //render(view: "show", model: [list: SubLedger.list().unique{it.accCode}])
        List<Object> list =searchReportInfoAction.dpList();
      //  List<Object> customerList = searchReportInfoAction.customerList();
        // List<CustomerSalesChannel> salesChannel =searchReportInfoAction.detailSalesChannelList()
        render(view: 'show', model: [list: list]);
    }

    def printReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/miscellaneous/salesmandues/"
        params._file = "miscellaneous/salesmandues/salesmanSecurityAndDuesMainReport.jasper"

        params.put('dpID', Long.parseLong(params.dpoint) )
        params.put('givenDate', params.givenDate)
        params._name = "salesmanSecurityDuesReport" + "_" + DateUtil.now()

        try {
            JasperReportDef reportDef = new JasperReportDef(name: params._file,
                    parameters: params,
                    fileFormat: JasperExportFormat.PDF_FORMAT)

            ByteArrayOutputStream byteArrayOutputStream = jasperService.generateReport(reportDef)
            OutputStream os = response.outputStream
            response.contentLength = byteArrayOutputStream.size()
            response.contentType = "application/pdf"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
            os << byteArrayOutputStream.toByteArray()
            os.flush()
            os.close()
        } catch (Exception e) {
            log.error(e.message)
            render(e.message)
        }

    }
}
