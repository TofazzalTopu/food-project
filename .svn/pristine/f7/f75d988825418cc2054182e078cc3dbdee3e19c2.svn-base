package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.reports.SearchReportInfoAction
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class SalesReportMTController {

    @Autowired
    SearchReportInfoAction searchReportInfoAction
    SpringSecurityService springSecurityService

    def jasperService
    static defaultAction = "show"

    def show = {
        List<TerritoryConfiguration> list = searchReportInfoAction.detailTerritoryList()
        render(view: 'salesReportMT', model: [list: list]);
    }

    def rptSalesReportMT = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/Sales_Report_MT.jasper"

        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        params.put('username', username)
        params._name = "SalesReportMT" + params.territory + "_" + DateUtil.now()

        JasperReportDef reportDef = new JasperReportDef(name: params._file,
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

    def salesReportInstitutional = {
        List<TerritoryConfiguration> list = searchReportInfoAction.detailTerritoryList()
        render(view: 'salesReportInstitutional', model: [list: list]);
    }

    def rptSalesReportINS = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/Sales_Report_INST.jasper"

        params.username = springSecurityService.getCurrentUser().username

        params._name = "SalesReportINST" + params.territory + "_" + DateUtil.now()
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

    def salesReportHEAD = {
        List<TerritoryConfiguration> list = searchReportInfoAction.detailTerritoryList()
        render(view: 'headofSalesReport', model: [list: list]);
    }

    def rptSalesReportHEAD = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/Sales_Report_HEAD.jasper"

        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        params.put('fromDateDisplay', params.fromDate)
        params.put('toDateDisplay', params.toDate)

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)

        params.put('username', username)
        params._name = "SalesReportHEAD" + "_" + DateUtil.now()
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
//        response.outputStream << byteArrayOutputStream.toByteArray()
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def salesSummaryReport = {
        List<CustomerCategory> list = searchReportInfoAction.detailCustomerCatagoryList()
        List<CustomerSalesChannel> salesChannel = searchReportInfoAction.detailSalesChannelList()
        render(view: 'salesSummaryReport', model: [list: list, salesChannel: salesChannel]);
    }

    def rptSalesSummaryReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/Sales_Summary_Report.jasper"

        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        params.put('username', username)
        params._name = "SalesSummaryReport" + "_" + DateUtil.now()
        params.put("_format", "PDF")
        chain(controller: 'jasper', action: 'index', model: [data: null], params: params)
          /*
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
        */
    }


    def receivedVsdelivery = {
        render(view: 'receivedVsdeliveryReport');
    }

    def rptreceivedVsdelivery = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/receivedVsdelivery.jasper"

        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)


//        params.put('fromDate', params.fromDate)
//        params.put('toDate', params.toDate)
        params.put('username', username)

        params._name = "SalesReportHEAD" + "_" + DateUtil.now()
        Map values = params.clone()
        JasperReportDef reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDefTemp)
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

    def dcrForSales = {
        ApplicationUser applicationUser = session?.applicationUser
        Long id = applicationUser.id
        List<CustomerMaster> list = searchReportInfoAction.salesOfficerList(id)
        render(view: 'dcrForSalesOfficer',model: [list: list]);
    }

    def rptDcrForSales = {

        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/DCR_SALES.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
        params.put('spo', params.salesOfficer)
        params.put('division', params.division)
        params.put('district', params.district)
        params.put('username',username)
        if(params.salesofficer){
            params.put('salesofficertext', params.salesofficertext)
        } else {
            params.values().remove(params.salesofficertext)
        }

        if(params.division ) {
            params.put('divisiontext', params.divisiontext)
        } else {
            params.values().remove(params.divisiontext)
        }

        if(params.district ) {
            params.put('districttext', params.districttext)
        } else {
            params.values().remove(params.districttext)
        }

//                params.put('reportCount', i+1)
        params._name = "DCR_SALES" + "_" + DateUtil.now()

        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.XLS_FORMAT)
        reportDef.add(reportDefTemp)

        try {
            ByteArrayOutputStream byteArrayOutputStream = jasperService.generateReport(reportDef)

            OutputStream os = response.outputStream
            response.contentLength = byteArrayOutputStream.size()
            response.contentType = "application/vnd.ms-excel"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xls");  // inline; attachment
            os << byteArrayOutputStream.toByteArray()
            os.flush()
            os.close()
        }
        catch (Exception e) {
            log.error(e.message)
            render(e.message)
        }
    }

}
