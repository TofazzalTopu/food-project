package com.bits.bdfp.reports

import com.bits.bdfp.customer.customercategory.SearchCustomerCategoryAction
import com.docu.common.Message
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class SummaryDailyDeliveryReportController {

    @Autowired
     SearchCustomerCategoryAction searchCustomerCategoryAction
    @Autowired
    SearchReportInfoAction searchReportInfoAction
    def index = {}
    def jasperService
    static defaultAction = "show"
    def show = {
        //render(view: "show", model: [list: SubLedger.list().unique{it.accCode}])
        List list = searchCustomerCategoryAction.executeCustomerCategory(params, null)
        render(view: "show", model: [list:list, listSize:list.size()])
    }

    def generateSummaryDailyDeliveryReport = {
        Message message = new Message();
        String js;

        List list = searchReportInfoAction.getSummaryDailyDeliveryReport(params, null)
        if (list.size() > 0) {
            Map map = searchReportInfoAction.checkConditionForSummaryDailyDeliver(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render ("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }
/*
    def printReport = {
        // params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/DailyCollectionsReport/"
        params._file = "deliveryReport/summary_daily_delivery_report.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

//        params.put('categoryId', Long.parseLong(params.categoryId))
        params.put('fromDate', params.ledgerDateFrom)
        params.put('toDate', params.ledgerDateTo)
//        params.put('ledgerDateTo', params.ledgerDateTo)
//                params.put('reportCount', i+1)
        params._name = "summary_daily_delivery_report" + "_" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                //  parameters: values,
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
}*/
}
