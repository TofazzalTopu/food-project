package com.bits.bdfp.reports

import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class ProductReceivedRegisterReportController {
    def index = {}
    def jasperService
    SpringSecurityService springSecurityService
    static defaultAction = "show"

    @Autowired
    SearchReportInfoAction searchReportInfoAction

    def show = {
        List<Object> list =searchReportInfoAction.dpList();
        render(view: "show", model: [list: list])
    }

    def printReport = {
        params._file = "ProductReceived/Product_received_register.jasper"

        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        params._name = "Product_received_register" + "_" + DateUtil.now()
        params.put('dpID', Long.parseLong( params.dpPoint))
        params.put('dateFrom', params.ledgerDateFrom)
        params.put('dateTo', params.ledgerDateTo)
        params.put('userId', Long.parseLong(applicationUser.id.toString()))
        Map values = params.clone()
        JasperReportDef  reportDefTemp = new JasperReportDef(name: params._file,
                 parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDefTemp)
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
