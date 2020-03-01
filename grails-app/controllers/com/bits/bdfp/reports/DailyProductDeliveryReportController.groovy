package com.bits.bdfp.reports

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerSalesChannel
import com.docu.commons.DateUtil
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class DailyProductDeliveryReportController {

    def index = {}
    def jasperService
    static defaultAction = "show"
    @Autowired
    SearchReportInfoAction searchReportInfoAction

    def show = {
        List<CustomerCategory> list =searchReportInfoAction.detailCustomerCatagorywithoutSalesmanList();
        List<CustomerSalesChannel> salesChannel =searchReportInfoAction.detailSalesChannelList()
        render(view: 'show', model: [list: list,salesChannel:salesChannel]);
    }

    def printReport = {
        // params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/DailyCollectionsReport/"
        params._file = "deliveryReport/DailyProductDeliveryReport.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        String date1 = params.tranDate
        Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
        String newfromDate = oldfromDate.format('yyyy-MM-dd')
        params.put('tranDate', params.tranDate)
        params.put('categoryId', Integer.parseInt('1')  )
        params.put('salesChannel',Integer.parseInt( '3'))

        params._name = "Daily_Product_Delivery_Report" + "_" + DateUtil.now()
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
            response.contentType = "application/xls"   // msword; pdf; richtext; html
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
