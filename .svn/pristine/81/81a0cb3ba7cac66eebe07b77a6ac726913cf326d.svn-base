package com.bits.bdfp.reports
import com.docu.commons.DateUtil
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class AccountStatementReportController {

    def index = {}
    def jasperService
    static defaultAction = "show"
    @Autowired
    SearchReportInfoAction searchReportInfoAction

    def show = {
        List<Object> list =searchReportInfoAction.chartOfAccountList();
        List<Object> customerList = searchReportInfoAction.customerList();
       // List<CustomerSalesChannel> salesChannel =searchReportInfoAction.detailSalesChannelList()
        render(view: 'show', model: [list: list,customerList:customerList]);
    }

    def printReport = {
        // params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/DailyCollectionsReport/"
        params._file = "miscellaneous/AccountStatement.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()


        params.put('chart_account_id',Long.parseLong(params.chartOfAccount) )
        params.put('customer_code', Long.parseLong(params.customer)   )
        params.put('dateFrom',params.dateFrom)
        params.put('date',params.dateFrom)
        params.put('dateTo',params.dateTo)

        params._name = "Account_Statement_Report" + "_" + DateUtil.now()

        Map values = params.clone()

        reportDefTemp = new JasperReportDef(name: params._file,
                 parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
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
