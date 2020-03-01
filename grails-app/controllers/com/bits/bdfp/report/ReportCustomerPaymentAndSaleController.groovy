package com.bits.bdfp.report


import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class ReportCustomerPaymentAndSaleController {

    @Autowired
    def jasperService
    SpringSecurityService springSecurityService
    static defaultAction = "show"

    def show = {
        //List<TerritoryConfiguration> list =searchReportInfoAction.detailTerritoryList()
        render(view: '_show');
    }

    def rptCustomerPaymentAndSalesReport = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/salesReport/"
        params._file = "salesReport/ReportCustomerPaymentAndSales.jasper"

        String date1 = "01-" + params.fromDate
        //Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        //  String newfromDate = oldfromDate.format('yyyy-MM-dd')

        params.put('fromDate', date1)
        params.put('customerID', Long.parseLong(params.cusID))
        //params.put('cusID', 89)
        params.put('username', applicationUser.username)
        params._name = "ReportCustomerPaymentAndSales" + "_" + DateUtil.now()
        Map values = params.clone()

        JasperReportDef reportDefTemp = new JasperReportDef(name: params._file,
           parameters: values,
           fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDefTemp)
        } catch (Exception ex) {
            log.error(ex.message)
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
