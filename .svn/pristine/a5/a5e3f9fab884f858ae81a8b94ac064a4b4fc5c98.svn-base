package com.bits.bdfp.inventory.ledgerreport

import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

class JournalReportController {
    def jasperService
    static defaultAction = "show"
    def show = {
        render(view: "show")
    }
    def printJournal = {
        params.COMMON_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "journalReport/rptJournalReport.jasper"
        params._name = "JournalReport_" + DateUtil.now()
        ApplicationUser applicationUser = session?.applicationUser
        params.put('username',applicationUser.username)

        params.dateRange = "From:" + params.ledgerDateFrom + " To:" + params.ledgerDateTo

        Date startDate = DateUtil.getSimpleDate(params.ledgerDateFrom)
        params.ledgerDateFrom = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);

        Date endDate = DateUtil.getSimpleDate(params.ledgerDateTo);
        params.ledgerDateTo = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);

        def reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

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
}
