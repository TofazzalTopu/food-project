package com.bits.bdfp.report

import com.bits.bdfp.reports.SearchReportInfoAction
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class StockOutReportController {

    def jasperService
    SpringSecurityService springSecurityService
    static defaultAction = "show"

    @Autowired
    private SearchReportInfoAction searchReportInfoAction

    def show = {
        render(view:"show")
    }

    def generateStockOutReport = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.put("userId", Long.parseLong(applicationUser.id.toString()))
        params._file = "stockOutReport/StockOutReport.jasper"
        params._name = "StockOutReport" + "_" + DateUtil.now()
        List reportData = (List)searchReportInfoAction.executeReport(params, null)

        JasperReportDef reportDefTemp = new JasperReportDef(name: params._file,
                reportData: reportData,
                parameters: params,
                fileFormat: JasperExportFormat.XLS_FORMAT)

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
        response.contentType = "application/xls"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xls");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

}
