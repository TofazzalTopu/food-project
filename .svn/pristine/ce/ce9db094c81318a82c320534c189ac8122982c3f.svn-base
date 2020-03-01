package com.bits.bdfp.reports

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.inventory.warehouse.warehouse.ListFactoryWarehouseByApplicationUserAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.security.ApplicationUser
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class HoReceivableReportController {
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListFactoryWarehouseByApplicationUserAction listFactoryWarehouseByApplicationUserAction
    @Autowired
    SearchReportInfoAction searchReportInfoAction

    def jasperService

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()
        List<CustomerSalesChannel> salesChannelList = searchReportInfoAction.detailSalesChannelList()
        render(template: "show", model: [customerCategoryList: customerCategoryList, customerSize: customerCategoryList.size(), enterpriseList: list, size: list.size(), salesChannelList: salesChannelList, salesChannelSize: salesChannelList.size()])
    }


    def reportHoReceivable = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "listReport/reportHoReceivable.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        params.put('salesChannelId', params.salesChannelId)
        params.put('customerCategoryId', params.customerCategoryId)

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

        params._name = "ReportHoReceivable_" + new Date().toTimestamp().format('dd_MM_yyyy')
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)
        reportDef.add(reportDefTemp)
        ByteArrayOutputStream byteArrayOutputStream = null

        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            render(e.message)
            log.error(e.message)
            throw new RuntimeException(e.getMessage())
        }

        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        if (params.format == 'pdf') {
            response.contentType = "application/pdf"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");
            // inline; attachment
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }


}
