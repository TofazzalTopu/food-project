package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.reports.SearchReportInfoAction
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class StatementFormatController {

//    def index = {}
    @Autowired
    SearchReportInfoAction searchReportInfoAction

    def jasperService
    static defaultAction = "show"
    def show = {
//        List<TerritoryConfiguration> list =searchReportInfoAction.detailTerritoryList()
        render(view: 'statementFormat')

    }

    def rptStatementFormat = {
//        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/salesReport/"
//        params._file = "salesReport/Sales_Report_MT.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
//        params.put('territoryId', params.territory)
//        params.put('territoryArea', params.territoryArea)
//                params.put('reportCount', i+1)
//        params._name = "SalesReportMT" +  "_" + DateUtil.now()
//        Map values = params.clone()
//        reportDefTemp = new JasperReportDef(name: params._file,
//                parameters: values,
//                fileFormat: JasperExportFormat.PDF_FORMAT)
//        reportDef.add(reportDefTemp)
//
//        ByteArrayOutputStream byteArrayOutputStream = null
///        try {
//            byteArrayOutputStream = jasperService.generateReport(reportDef)
//        } catch (Exception e) {

//        }
//        OutputStream os = response.outputStream
//        response.contentLength = byteArrayOutputStream.size()
//        response.contentType = "application/pdf"   // msword; pdf; richtext; html
//        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
//        os << byteArrayOutputStream.toByteArray()
//
//        os.flush()
//        os.close()
    }
    def fetchDropDownList = {
        List list =  searchReportInfoAction.execute(params)
        render list as JSON

    }
    def targetVsachievementVolume = {
        render(view: 'targetVSachievementVolume');

    }

    def rpttargetVsachievementVolume = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/Target_vs_Ach_volume.jasper"
        ApplicationUser applicationUser=session?.applicationUser
      //  List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
       // def reportDefTemp = new Object()

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        String targetYear = newtoDate.substring(0,4)
        String username= applicationUser.customerMasterId
        String appusername= applicationUser.username

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
        params.put('username',username)
        params.put('appusername',appusername)
        params.put('targetYear',targetYear)
        params._name = "TargetVsAchievementVolume" + "_" + DateUtil.now()
        Map values = params.clone()
        JasperReportDef reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDefTemp)
        } catch (Exception e) {

        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def targetVsachievementValue = {
        render(view: 'targetVSachievementValue');
    }

    def rpttargetVsachievementValue = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesReport/Target_vs_Ach_amount.jasper"
        ApplicationUser applicationUser=session?.applicationUser
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        String targetYear = newtoDate.substring(0,4)
        String username= applicationUser.customerMasterId
        String appusername= applicationUser.username

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
        params.put('username',username)
        params.put('appusername',appusername)
        params.put('targetYear',targetYear)
        params._name = "TargetVsAchievementValue" + "_" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def salesVsCollection = {
        render(view: 'salesVsCollection');

    }

    def rptSalesVsCollectionChannel = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesVscollection/SalesVsCollectionChannel.jasper"
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        ApplicationUser applicationUser=session?.applicationUser
        String username= applicationUser.username
        String channel=params.channel

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
        params.put('username',username)
        params.put('channel',channel)
        params._name = "SalesVsCollectionChannel" + "_" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def rptSalesVsCollectionDivision = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesVscollection/SalesVsCollectionDiv.jasper"
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        ApplicationUser applicationUser=session?.applicationUser
        String username= applicationUser.username

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')
        String division= params.division

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
        params.put('division',division)
        params.put('username',username)
        params._name = "SalesVsCollectionDivision" + "_" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }



    def rptSalesVsCollectionDistrict = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesVscollection/SalesVsCollectionDistrict.jasper"
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        ApplicationUser applicationUser=session?.applicationUser
        String username= applicationUser.username

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')
        String district=params.district

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
        params.put('district', district)
        params.put('username',username)
        params._name = "SalesVsCollectionDistrict" + "_" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def rptSalesVsCollectionThana = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "salesVscollection/SalesVsCollectionThana.jasper"
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        ApplicationUser applicationUser=session?.applicationUser
        String username= applicationUser.username

        String date1 = params.fromDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')
        String thana=params.thana

        params.put('fromDate', newfromDate)
        params.put('toDate', newtoDate)
        params.put('thana',thana)
        params.put('username',username)
        params._name = "SalesVsCollectionThana" + "_" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

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
