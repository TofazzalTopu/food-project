package com.bits.bdfp.reports

import com.docu.common.Message
import com.docu.commons.DateUtil
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class DailyDemandOrderReportController {

    @Autowired
    SearchReportInfoAction searchReportInfoAction
    def index = {}
    def jasperService
    static defaultAction = "show"
    def show = {
        //render(view: "show", model: [list: SubLedger.list().unique{it.accCode}])
        render(view: "show")
    }

    def generateDailyDemandOrder = {
        Message message = new Message();
        String js;

        List list = searchReportInfoAction.getSummaryDailyDemandOrder(params, null)
        if (list.size() > 0) {
            Map map = searchReportInfoAction.checkConditionDailyDemandOrder(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render ("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }

    def printReport = {
        // params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/DailyCollectionsReport/"
        params._file = "DailyDemandOrder/DailyDemandOrder.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        String date1 = params.dateFrom
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')

        String date2 = params.dateTo
        Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
        String newtoDate = oldtoDate.format('yyyy-MM-dd')

        params._name = "daily_demand_order" + "_" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                //  parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            log.error(e.message)
            return (e.message)
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()}
}
