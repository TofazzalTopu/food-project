package com.bits.bdfp.reports

import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class DailyCollectionReportController {

    def index = {}
    def jasperService
    SpringSecurityService springSecurityService
    static defaultAction = "show"
    @Autowired
    SearchReportInfoAction searchReportInfoAction
    def show = {
//      List <Object> salesManList = searchReportInfoAction.fetchSalesmanListAll()
         List<Object> list =  searchReportInfoAction.dpList()
        //render(view: "show", model: [list: SubLedger.list().unique{it.accCode}])
        render(view: "show", model: [list:list])
    }

//  added by liton  22-02-2017
    def getSalesmanListByDp = {
        render searchReportInfoAction.getSalesmanListByDp(Long.parseLong(params.dpId)) as JSON
    }
//  end

    def printJournal = {
        params._file = "CollectionReport/DailyCollectionsReport.jasper"
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        // params.salesMan = "0"
        params.put('dpID', Integer.parseInt(params.dpPoint))
        params.put('userId', Integer.parseInt(applicationUser.id.toString()))
        params.put('salesManId', Long.parseLong(params.salesMan))

        params._name = "DailyCollectionReport" + "_" + DateUtil.now()
        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            log.error(e.message)
            render(e.message)
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
