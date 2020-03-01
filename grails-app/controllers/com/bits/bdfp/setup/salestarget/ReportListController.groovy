package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.bonus.onepercentbonus.ListBranchByTerritoryAction
import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.product.ProductPricingType
import com.bits.bdfp.inventory.product.finishproduct.ListFinishProductAction
import com.bits.bdfp.promotionsetup.GetFactorySalableProductListAction
import com.bits.bdfp.reports.SearchReportInfoAction
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.converters.JSON
import net.sf.jasperreports.engine.JRParameter
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class ReportListController {

    @Autowired
    ListBranchByTerritoryAction listBranchByTerritoryAction

    @Autowired
    SearchReportInfoAction searchReportInfoAction
    @Autowired
    GetFactorySalableProductListAction getFactorySalableProductListAction
    @Autowired
    ListFinishProductAction listFinishProductAction
    // def index = {}
    def jasperService
    static defaultAction = "show"
    def show = {
        render(view: 'employeeList');
    }

    def rptEmployeeList = {

        params._file = "listReport/employee.jasper"
        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
//                params.put('reportCount', i+1)
        params._name = "EmployeeList" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.XLS_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/vnd.ms-excel"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xls");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()

//        Message message;
//        String js;
//        List list = reportExcelFormatAction.execute(params, null)
//        if (list.size() > 0) {
//            Map map = reportExcelFormatAction.postCondition(params, null)
//            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
//
//        } else {
////            message = rptWorkOrderReportAction.getUserMessage("No Data Found", null)
////            message=rptWorkOrderReportAction.getMessage("Serial Number", Message.ERROR, "No data found")
//            render "no data found"
//        }
//        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
//        render js
    }
    def territoryInfo = {
        render(view: 'territoryList');
    }

    def rptTerritoryList = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "listReport/territory.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        params.put('username', username)
        params._name = "TerritoryList" + DateUtil.now()
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

    def customerInfo = {
        List<CustomerCategory> categoryList = CustomerCategory.list()
        render(view: 'customerList', model: [categoryList: categoryList]);
    }

    def rptCustomerList = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "listReport/customer.jasper"

        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        params.put('username', username)

        params._name = "CustomerList" + DateUtil.now()

        def reportDefTemp = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDefTemp)
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

    def productInfo = {
        render(view: 'productList');
    }

    def rptProductList = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "listReport/product.jasper"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()

        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        params.put('username', username)


        params._name = "ProductList" + DateUtil.now()
        Map values = params.clone()
        reportDefTemp = new JasperReportDef(name: params._file,
                parameters: values,
                fileFormat: JasperExportFormat.PDF_FORMAT)
        reportDef.add(reportDefTemp)

        ByteArrayOutputStream byteArrayOutputStream = null/*
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {

        }*/
        byteArrayOutputStream = jasperService.generateReport(reportDef)

        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }
    def priceInfo = {
        render(view: 'priceList', model: [productPricingTypeList: ProductPricingType.findAllByIsActive(true)]);

    }
    def rptPriceList = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"

        List<JasperReportDef> reportDef = new ArrayList<JasperReportDef>()
        def reportDefTemp = new Object()
        ApplicationUser applicationUser = session?.applicationUser
        String username = applicationUser.username

        if (params.fromDate) {
            String date1 = params.fromDate
            Date oldFromDate = Date.parse('dd-MM-yyyy', date1)
            String newFromDate = oldFromDate.format('yyyy-MM-dd')
            params.put('fromDate', newFromDate)
        }

        if (params.toDate) {
            String date2 = params.toDate
            Date oldToDate = Date.parse('dd-MM-yyyy', date2)
            String newToDate = oldToDate.format('yyyy-MM-dd')
            params.put('toDate', newToDate)
        }

        params.put('priceName', params.pricename)
        params.put('priceType', params.pricetype)
        if (params.status) {
            params.put('status', Integer.parseInt(params.status))
        }
        params.put('username', username)
//                params.put('reportCount', i+1)
        if (params.pricetype == 'Standard') {
            params._file = "listReport/price.jasper"
            params._name = "Price" + params.territory + "_" + DateUtil.now()
            Map values = params.clone()
            reportDefTemp = new JasperReportDef(name: params._file,
                    parameters: values,
                    fileFormat: JasperExportFormat.PDF_FORMAT)
            reportDef.add(reportDefTemp)

            ByteArrayOutputStream byteArrayOutputStream = null
            try {
                byteArrayOutputStream = jasperService.generateReport(reportDef)
            } catch (Exception e) {
                return e.message;
            }
            OutputStream os = response.outputStream
            response.contentLength = byteArrayOutputStream.size()
            response.contentType = "application/pdf"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");
            // inline; attachment
            os << byteArrayOutputStream.toByteArray()

            os.flush()
            os.close()
        } else {
            params._file = "listReport/priceNegotiating.jasper"
            params._name = "Price" + params.territory + "_" + DateUtil.now()
            Map values = params.clone()
            reportDefTemp = new JasperReportDef(name: params._file,
                    parameters: values,
                    fileFormat: JasperExportFormat.PDF_FORMAT)
            reportDef.add(reportDefTemp)

            ByteArrayOutputStream byteArrayOutputStream = null
            try {
                byteArrayOutputStream = jasperService.generateReport(reportDef)
            } catch (Exception e) {
                return e.message;
            }
            OutputStream os = response.outputStream
            response.contentLength = byteArrayOutputStream.size()
            response.contentType = "application/pdf"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");
            os << byteArrayOutputStream.toByteArray()

            os.flush()
            os.close()
        }
    }

    def showDailyDemandRegister = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('userId', applicationUser.id)
        params.put('rpt', '1')
        List branchList = (List) listBranchByTerritoryAction.execute(params, null)
        render(view: 'dailyDemandRegister', model: [branchList: branchList, size: branchList.size()])
    }

    def showZoneWiseDemand = {
        render(view: 'dailyZoneWiseDemand')
    }

    def showRegionWiseDemand = {
        render(view: 'dailyRegionWiseDemand')
    }

    def showFactorySalesReport = {
        render(view: 'factorySalesReport')
    }

    def rptFactorySales = {
        params._file = "listReport/factorySalesReport.jasper"

        params._name = "FactorySalesReport_" + params.date
        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            render(e.message)
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        if (params.format == 'pdf') {
            response.contentType = "application/pdf"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def rptDailyRegionWiseDemand = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "listReport/dailyRegionWiseDemandAll.jasper"
        params._name = "DailyRegionWiseDemand_" + params.date

        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            render(e.message)
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        if (params.format == 'pdf') {
            response.contentType = "application/pdf"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def rptDailyZoneWiseDemand = {

//        params._file = "listReport/DivisionAndSalesChannelWiseDemandReport.jasper"
        params._file = "listReport/dailyZoneWiseDemandAll.jasper"

        params._name = "DailyZoneWiseDemand_" + params.date

        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

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
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }
    def rptDailyDemandRegister = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "listReport/dailyDemandRegisterAll.jasper"

        params._name = "DailyProductDemandRegister_" + params.date
        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception e) {
            render(e.message)
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

    def showSalesChannelWiseDemandReport = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.findAllByIsActive(true)
        List<CustomerSalesChannel> customerSalesChannelList = CustomerSalesChannel.findAllByIsActive(true)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()

        List finishProductList = listFinishProductAction.finishProductList(params, null)
        List productList = listFinishProductAction.getMainProduct(params, null)
        render(view: "/reportList/salesChannelWiseDemandReport/show", model: [territoryConfigurationListSize: territoryConfigurationList.size(),
                                                                              customerSalesChannelListSize  : customerSalesChannelList.size(),
                                                                              customerSalesChannelList      : customerSalesChannelList as JSON,
                                                                              customerCategoryListSize      : customerCategoryList.size(),
                                                                              customerCategoryList          : customerCategoryList as JSON,
                                                                              productListSize               : productList.size(),
                                                                              productList                   : productList as JSON,
                                                                              finishProductList             : finishProductList as JSON,
                                                                              finishProductListSize         : finishProductList.size()])

    }
    def salesChannelWiseDemandReport = {
        params._file = "listReport/divisionAndSalesChannelWiseDemandReport.jasper"
        params._name = "DivisionAndSalesChannelWiseDemand_" + new Date().toTimestamp().format('dd_MM_yyyy')
        params.put("isAll", params.isAll)
        params.put('packSizeIds', params.packSizeIds)
        params.put('channelIds', params.channelIds)
        params.put('categoryIds', params.categoryIds)
        params.put('fromDateDisplay', params.fromDate)
        params.put('toDateDisplay', params.toDate)

        if (params.fromDate) {
            String date1 = params.fromDate
            Date oldFromDate = Date.parse('dd-MM-yyyy', date1)
            String newFromDate = oldFromDate.format('yyyy-MM-dd')
            params.put('fromDate', newFromDate)
        }

        if (params.toDate) {
            String date2 = params.toDate
            Date oldToDate = Date.parse('dd-MM-yyyy', date2)
            String newToDate = oldToDate.format('yyyy-MM-dd')
            params.put('toDate', newToDate)
        }
        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

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
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showSalesChannelAndUserWiseDemandReport = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.findAllByIsActive(true)
        List<CustomerSalesChannel> customerSalesChannelList = CustomerSalesChannel.findAllByIsActive(true)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()

        List finishProductList = listFinishProductAction.finishProductList(params, null)
        List productList = listFinishProductAction.getMainProduct(params, null)
        render(view: "/reportList/salesChannelAndUserWiseDemandReport/show", model: [territoryConfigurationListSize: territoryConfigurationList.size(),
                                                                                     customerSalesChannelListSize  : customerSalesChannelList.size(),
                                                                                     customerSalesChannelList      : customerSalesChannelList as JSON,
                                                                                     customerCategoryListSize      : customerCategoryList.size(),
                                                                                     customerCategoryList          : customerCategoryList as JSON,
                                                                                     productListSize               : productList.size(),
                                                                                     productList                   : productList as JSON,
                                                                                     finishProductList             : finishProductList as JSON,
                                                                                     finishProductListSize         : finishProductList.size()])

    }
    def salesChannelAndUserWiseDemandReport = {
        params._file = "listReport/salesChannelAndUserWiseDemandReport.jasper"
        params._name = "SalesChannelAndUserWiseDemandReport" + new Date().toTimestamp().format('dd_MM_yyyy')
        params.put("isAll", params.isAll)
        params.put('packSizeIds', params.packSizeIds)
        params.put('channelIds', params.channelIds)
        params.put('categoryIds', params.categoryIds)
        params.put('fromDateDisplay', params.fromDate)
        params.put('toDateDisplay', params.toDate)
        ApplicationUser applicationUser = session.applicationUser
        Long id = applicationUser.id

        params.put('applicationUserId', String.valueOf(id))

        if (params.fromDate) {
            String date1 = params.fromDate
            Date oldFromDate = Date.parse('dd-MM-yyyy', date1)
            String newFromDate = oldFromDate.format('yyyy-MM-dd')
            params.put('fromDate', newFromDate)
        }

        if (params.toDate) {
            String date2 = params.toDate
            Date oldToDate = Date.parse('dd-MM-yyyy', date2)
            String newToDate = oldToDate.format('yyyy-MM-dd')
            params.put('toDate', newToDate)
        }
        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

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
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showRegionWiseDemandReport = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.findAllByIsActive(true)
        List<CustomerSalesChannel> customerSalesChannelList = CustomerSalesChannel.findAllByIsActive(true)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()
        List finishProductList = listFinishProductAction.finishProductList(params, null)

        List productList = listFinishProductAction.getMainProduct(params, null)
        render(view: "/reportList/regionWiseDemandReport/show", model: [territoryConfigurationListSize: territoryConfigurationList.size(),
                                                                        customerSalesChannelListSize  : customerSalesChannelList.size(),
                                                                        customerSalesChannelList      : customerSalesChannelList as JSON,
                                                                        customerCategoryListSize      : customerCategoryList.size(),
                                                                        customerCategoryList          : customerCategoryList as JSON,
                                                                        productListSize               : productList.size(),
                                                                        productList                   : productList as JSON,
                                                                        finishProductList             : finishProductList as JSON,
                                                                        finishProductListSize         : finishProductList.size()])

    }
    def regionWiseDemandReport = {
        params._file = "listReport/regionWiseDemandReport.jasper"
        params._name = "RegionWiseDemand_" + new Date().toTimestamp().format('dd_MM_yyyy')

        params.put('packSizeIds', params.packSizeIds)
        params.put('channelIds', params.channelIds)
        params.put('categoryIds', params.categoryIds)
        params.put('isAll', params.isAll)

        params.put('fromDateDisplay', params.fromDate)
        params.put('toDateDisplay', params.toDate)

        String date1 = params.fromDate
        Date oldFromDate = Date.parse('dd-MM-yyyy', date1)
        String newFromDate = oldFromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldToDate = Date.parse('dd-MM-yyyy', date2)
        String newToDate = oldToDate.format('yyyy-MM-dd')

        params.put('fromDate', newFromDate)
        params.put('toDate', newToDate)
        if (params.format != 'pdf') {
            params.put(JRParameter.IS_IGNORE_PAGINATION, true);
        }
        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

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
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showRegionAndUserWiseDemandReport = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.findAllByIsActive(true)
        List<CustomerSalesChannel> customerSalesChannelList = CustomerSalesChannel.findAllByIsActive(true)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()
        List finishProductList = listFinishProductAction.finishProductList(params, null)

        List productList = listFinishProductAction.getMainProduct(params, null)
        render(view: "/reportList/regionAndUserWiseDemandReport/show", model: [territoryConfigurationListSize: territoryConfigurationList.size(),
                                                                               customerSalesChannelListSize  : customerSalesChannelList.size(),
                                                                               customerSalesChannelList      : customerSalesChannelList as JSON,
                                                                               customerCategoryListSize      : customerCategoryList.size(),
                                                                               customerCategoryList          : customerCategoryList as JSON,
                                                                               productListSize               : productList.size(),
                                                                               productList                   : productList as JSON,
                                                                               finishProductList             : finishProductList as JSON,
                                                                               finishProductListSize         : finishProductList.size()])

    }
    def regionAndUserWiseDemandReport = {
        params._title = "UserAndRegionWiseDemand"
        params._file = "listReport/regionAndUserWiseDemandReport.jasper"
        params._name = "UserAndRegionWiseDemand_" + new Date().toTimestamp().format('dd_MM_yyyy')

        params.put('packSizeIds', params.packSizeIds)
        params.put('channelIds', params.channelIds)
        params.put('categoryIds', params.categoryIds)
        params.put('isAll', params.isAll)

        params.put('fromDateDisplay', params.fromDate)
        params.put('toDateDisplay', params.toDate)

        String date1 = params.fromDate
        Date oldFromDate = Date.parse('dd-MM-yyyy', date1)
        String newFromDate = oldFromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldToDate = Date.parse('dd-MM-yyyy', date2)
        String newToDate = oldToDate.format('yyyy-MM-dd')

        ApplicationUser applicationUser = session.applicationUser
        Long id = applicationUser.id

        params.put('applicationUserId', String.valueOf(id))
        params.put('fromDate', newFromDate)
        params.put('toDate', newToDate)
        if (params.format != 'pdf') {
            params.put(JRParameter.IS_IGNORE_PAGINATION, true);
        }
        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)

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
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def regionWiseSalesSummaryReport = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.findAllByIsActive(true)
        List<CustomerSalesChannel> customerSalesChannelList = CustomerSalesChannel.findAllByIsActive(true)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()
        List finishProductList = listFinishProductAction.finishProductList(params, null)

        List productList = listFinishProductAction.getMainProduct(params, null)
        render(view: "/reportList/regionWiseSalesSummaryReport/show", model: [territoryConfigurationListSize: territoryConfigurationList.size(),
                                                                              customerSalesChannelListSize  : customerSalesChannelList.size(),
                                                                              customerSalesChannelList      : customerSalesChannelList as JSON,
                                                                              customerCategoryListSize      : customerCategoryList.size(),
                                                                              customerCategoryList          : customerCategoryList as JSON,
                                                                              productListSize               : productList.size(),
                                                                              productList                   : productList as JSON,
                                                                              finishProductList             : finishProductList as JSON,
                                                                              finishProductListSize         : finishProductList.size()])

    }
    def showRegionWiseSalesSummaryReport = {
        params._file = "listReport/regionWiseSalesSummaryReport.jasper"
        params._name = "RegionWiseSalesSummaryReport_" + new Date().toTimestamp().format('dd_MM_yyyy');

        params.put('packSizeIds', params.packSizeIds)
        params.put('channelIds', params.channelIds)
        params.put('categoryIds', params.categoryIds)
        params.put('isAll', params.isAll)

        params.put('fromDateDisplay', params.fromDate)
        params.put('toDateDisplay', params.toDate)

        String date1 = params.fromDate
        Date oldFromDate = Date.parse('dd-MM-yyyy', date1)
        String newFromDate = oldFromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldToDate = Date.parse('dd-MM-yyyy', date2)
        String newToDate = oldToDate.format('yyyy-MM-dd')

        params.put('fromDate', newFromDate)
        params.put('toDate', newToDate)
        if (params.format != 'pdf') {
            params.put(JRParameter.IS_IGNORE_PAGINATION, true);
        }

        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)
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
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def salesChannelWiseSalesSummaryReport = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.findAllByIsActive(true)
        List<CustomerSalesChannel> customerSalesChannelList = CustomerSalesChannel.findAllByIsActive(true)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()
        List finishProductList = listFinishProductAction.finishProductList(params, null)

        List productList = listFinishProductAction.getMainProduct(params, null)
        render(view: "/reportList/salesChannelWiseSalesSummaryReport/show", model: [territoryConfigurationListSize: territoryConfigurationList.size(),
                                                                                    customerSalesChannelListSize  : customerSalesChannelList.size(),
                                                                                    customerSalesChannelList      : customerSalesChannelList as JSON,
                                                                                    customerCategoryListSize      : customerCategoryList.size(),
                                                                                    customerCategoryList          : customerCategoryList as JSON,
                                                                                    productListSize               : productList.size(),
                                                                                    productList                   : productList as JSON,
                                                                                    finishProductList             : finishProductList as JSON,
                                                                                    finishProductListSize         : finishProductList.size()])

    }
    def showSalesChannelWiseSalesSummaryReport = {
        params._file = "listReport/divisionAndSalesChannelWiseSalesSummaryReport.jasper"
        params._name = "channelWiseSalesSummaryReport_" + new Date().toTimestamp().format('dd_MM_yyyy')
        params.put('isAll', params.isAll)

        params.put('packSizeIds', params.packSizeIds)
        params.put('channelIds', params.channelIds)
        params.put('categoryIds', params.categoryIds)
        params.put('fromDateDisplay', params.fromDate)
        params.put('toDateDisplay', params.toDate)

        String date1 = params.fromDate
        Date oldFromDate = Date.parse('dd-MM-yyyy', date1)
        String newFromDate = oldFromDate.format('yyyy-MM-dd')

        String date2 = params.toDate
        Date oldToDate = Date.parse('dd-MM-yyyy', date2)
        String newToDate = oldToDate.format('yyyy-MM-dd')

        params.put('fromDate', newFromDate)
        params.put('toDate', newToDate)

        JasperReportDef reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: params.format == 'pdf' ? JasperExportFormat.PDF_FORMAT : JasperExportFormat.XLSX_FORMAT)
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
        } else {
            response.contentType = "application/xls"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".xlsx");
        }
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def getProductPackSizeByProduct = {
        Map map = listFinishProductAction.finishProductPackSizeByProduct(params, null)
        render map as JSON
    }
    def getCategoryByChannelId = {
        Map map = listFinishProductAction.getCategoryByChannelId(params, null)
        render map as JSON
    }
}